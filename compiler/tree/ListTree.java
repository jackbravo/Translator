package compiler.tree;
import compiler.*;

/**
 * @author Joaquin Bravo
 * @version Mar, 2004
 */
public class ListTree implements LangConstants, Tree
{
	
	private Lexeme root;
	private Tree next;
	
	public ListTree(Lexeme r) throws Exception
	{
		root = r;
	}
	
	public String toString(){
		if(next!=null) return root + ", " + next;
		else return " " + root + " ";
	}
	
	public Lexeme getRoot() { return root; }
	public Tree getNext() { return next; }
	
	public void setRoot(Lexeme r) {	root = r; }
	public void setNext(Tree n) { next = n; }

	public int count()
	{
		int count = 1;
		if (next != null)
		{
			ListTree n = (ListTree)next;
			count = 1 + n.count();
		}
		return count;
	}

	public Lexeme eval(Environment env) { return null; }
}
