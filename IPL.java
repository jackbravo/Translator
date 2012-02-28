import java.io.*;
import compiler.*;
import compiler.tree.*;

/**
 * Test class with the main method.
 * Iterates over the input until the last lexeme is reached with this code.<br>
 * Takes only one optional argument. A <pre>filename</pre> from where it reads the code.
 * 
 * @author Joaquin Bravo
 * @version Mar, 2004
 */
public class IPL {

	public static void main(String args[]) {
		Reader in;
		Scanner scanObj;
		Parser parsObj;
		Environment env;
		
		if (args.length > 0)
		{
			try { in = new FileReader(args[0]); }
			catch(FileNotFoundException e)
			{
				System.err.println(">> Could not find file: " + args[0]);
				return;
			}
		}
		else
			in = new BufferedReader(new InputStreamReader(System.in));
		
		scanObj = new Scanner(in);
		parsObj = new Parser(scanObj);
		env = new Environment();
		
		parsObj.advance();
		do
		{
			try
			{
				Tree pTree = parsObj.statement();
				// System.out.println(pTree);
				Lexeme result = pTree.eval(env);
				if (result != null) { System.out.println(">> " + result); }
				parsObj.checkTerminal();
			}
			catch(Exception e)
			{
				System.err.println(">> " + e.getMessage());
				parsObj.advance();
			}
		} while(parsObj.getCurrent() != null);
		try { in.close(); }
		catch(IOException e) {}
	}
	
	// test just the parser
	public static void testParser()
	{
		Reader in = new BufferedReader(new InputStreamReader(System.in));
		Scanner scanObj = new Scanner(in);
		Parser parsObj = new Parser(scanObj);
		parsObj.advance();
		
		do
		{
			try
			{
				Tree pTree = parsObj.statement(); //parse one statement
				System.out.println(pTree);
				parsObj.checkTerminal();
			}
			catch(Exception e)
			{
				System.err.println(e.getMessage());
				return;
			}
		} while(parsObj.getCurrent() != null);
	}
	
	// test just the scanner
	public static void testScann() {
		Reader in = new BufferedReader(new InputStreamReader(System.in));
		Scanner scanObj = new Scanner(in);
		
		Lexeme next = scanObj.readLexeme();
		while(next != null)
		{
			System.out.println(next);
			next = scanObj.readLexeme();
		}
	}
}
