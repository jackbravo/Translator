package compiler;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Environment of the language.
 * 
 * @author Joaquin Bravo
 * @version May, 2004
 */

public class Environment {
	
	Hashtable nameSpace;
	Environment parent;
	
	/**
	 * Create an empty environment.
	 * You use this to create the first node in the chain of environments.
	 * It makes the scope work properly.
	 */
	public Environment()
	{
		nameSpace = new Hashtable();
		parent = null; // Top level
	}
	
	/**
	 * Extend an existing environment.
	 * Use this when the program enters a new scope
	 * (such as a function call).
	 * 
	 * @param env - parent environment.
	 */
	public void extend(Environment env) { parent = env; }
	
	/**
	 * Insert a new symbol into the environment
	 * 
	 * @param key - symbol
	 * @param value - associated value
	 * @return the previous value of the specified key in this environment, or <pre>null</pre> if it did not have one.
	 */
	public Object put(Object key, Object value) { return nameSpace.put(key, value); }
	
	/**
	 * Retrieve the value associated with the given symbol in the environment.
	 * Starts looking in the current environment, if it fails, it looks the parent environment
	 * (if any) recursively.
	 * 
	 * @param key - object to look the associated value
	 * @return value associated with the given key
	 */
	public Object lookup(Object key)
	{
		Object result;
		result = nameSpace.get(key);
		if (result != null) return result;
		if (parent != null) return parent.lookup(key);
		return null;
	}
	
	/**
	 * List all the associations in the environment.
	 */
	public void print()
	{
		Enumeration keys = nameSpace.keys();
		while( keys.hasMoreElements() )
		{
			 Object key = keys.nextElement();
			 System.out.println("------------------");
			 System.out.println("Key: " + key);
			 System.out.println("Val: " + nameSpace.get(key));
			 System.out.println("------------------");
		}
	}

}
