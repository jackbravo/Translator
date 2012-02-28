package compiler.tree;
import compiler.*;

/**
 * @author Joaquin Bravo
 * @version Mar, 2004
 */
public class IfTree implements LangConstants, Tree
{
	
	private Lexeme root;
	private Tree condition;
	private Tree thenPart;
	private Tree elsePart;
	
	public IfTree(Lexeme r) throws Exception
	{
		if(r.getType().equals(IF)) root = r;
		else throw new Exception(">> If expected... found " + r.getType());
	}
	
	public String toString(){
		if(elsePart != null)
		{
			return root + " " + condition + "\n" + thenPart
						+ "\nelse\n" + elsePart + "\n" + END;
		}
		else
		{
			return root + " " + condition + "\n" + thenPart + "\n" + END;
		}
	}
	
	public Lexeme getRoot() { return root; }
	public Tree getCondition() { return condition; }
	public Tree getThen() { return thenPart; }
	public Tree getElse() { return elsePart; }
	
	public void setRoot(Lexeme r) {	root = r; }
	public void setCondition(Tree c) { condition = c; }
	public void setThen(Tree t) { thenPart = t; }
	public void setElse(Tree e) { elsePart = e; }
	
	public Lexeme eval(Environment env)
	{
		Lexeme cond = condition.eval(env);
		if( cond != null )
			return thenPart.eval(env);
		else
			if(elsePart != null) return elsePart.eval(env);
		return null;
	}
}
