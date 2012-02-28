package compiler.tree;
import compiler.*;
import java.io.*;

/**
 * @author Joaquin Bravo
 * @version Mar, 2004
 */
public class IncTree implements LangConstants, Tree
{
	
	private Lexeme file;
	
	public IncTree(Lexeme r) throws Exception
	{
		if(r.getType().equals(STR)) file = r;
		else throw new Exception(">> file expected... found " + r.getData());
	}
	
	public String toString(){
		return "File: " + file;
	}
	
	public Lexeme getFile() { return file; }
	public void setFile(Lexeme r) {	file = r; }
	
	public Lexeme eval(Environment env)
	{
		try
		{
			Reader in = new FileReader(file.getData());
			Scanner scanObj = new Scanner(in);
			Parser parsObj = new Parser(scanObj);
		
			parsObj.advance();
			do
			{
				try
				{
					Tree pTree = parsObj.statement();
					Lexeme result = pTree.eval(env);
					parsObj.checkTerminal();
				}
				catch(Exception e)
				{
					System.err.println(">> " + e.getMessage());
					parsObj.advance();
				}
			} while(parsObj.getCurrent() != null);
		}
		catch(FileNotFoundException e)
		{
			System.out.println(">> Could not find file: " + file);
			return null;	
		}
		return null;
	}
}
