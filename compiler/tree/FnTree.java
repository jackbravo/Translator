package compiler.tree;
import compiler.*;

/**
 * @author Joaquin Bravo
 * @version Mar, 2004
 */
public class FnTree implements LangConstants, Tree
{
	
	private Lexeme root;
	private Lexeme var;
	private Tree args;
	private Tree block;
	
	public FnTree(Lexeme r) throws Exception
	{
		root = r;
	}
	
	public String toString(){
		if (args == null) return root + " " + var + "( )" + block + "\n" + END;
		return root + " " + var + "(" + args + ")\n" + block + "\n" + END;
	}
	
	public Lexeme getRoot() { return root; }
	public Lexeme getVar() { return var; }
	public Tree getArgs() { return args; }
	public Tree getBlock() { return block; }
	
	public void setRoot(Lexeme r) {	root = r; }
	public void setVar(Lexeme v) { var = v; }
	public void setArgs(Tree a) { args = a; }
	public void setBlock(Tree b) { block = b; }
	
	public Lexeme eval(Environment env)
	{
		try
		{
			FnTree self = new FnTree(root);
			self.setVar(var);
			self.setBlock(block);
			self.setArgs(args);
			env.put(var, self);
		}
		catch(Exception e) { System.out.println(e.getMessage()); }
		return null;
	}
}
