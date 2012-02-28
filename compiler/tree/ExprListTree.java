package compiler.tree;
import compiler.*;

/**
 * @author Joaquin Bravo
 * @version Mar, 2004
 */
public class ExprListTree implements LangConstants, Tree
{
	
	private Tree root;
	private Tree next;
	
	public ExprListTree(Tree r) throws Exception
	{
		root = r;
	}
	
	public String toString(){
		if(next!=null) return root + ", " + next;
		else return " " + root + " ";
	}
	
	public Tree getRoot() { return root; }
	public Tree getNext() { return next; }
	
	public void setRoot(Tree r) { root = r; }
	public void setNext(Tree n) { next = n; }
	
	public int count()
	{
		int count = 1;
		if (next != null)
		{
			ExprListTree n = (ExprListTree)next;
			count = 1 + n.count();
		}
		return count;
	}
	
	public Lexeme eval(Environment env) { return root.eval(env); }
}
