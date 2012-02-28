package compiler.tree;
import compiler.*;

/**
 * @author Joaquin Bravo
 * @version Mar, 2004
 */
public class BlockTree implements LangConstants, Tree
{
	
	private Tree stmt;
	private Tree next;
	
	public BlockTree(Tree s) throws Exception
	{
		stmt = s;
	}
	
	public String toString(){
		if (next == null) return "  " + stmt;
		return "  " + stmt + "\n  " + next;
	}
	
	public Tree getStmt() { return stmt; }
	public Tree getNext() { return next; }
	
	public void setRoot(Tree r) { stmt = r; }
	public void setNext(Tree n) { next = n; }
	
	public Lexeme eval(Environment env)
	{
		Lexeme result = null;
		result = stmt.eval(env);
		if (next != null)
			result = next.eval(env);
		return result;
	}
}
