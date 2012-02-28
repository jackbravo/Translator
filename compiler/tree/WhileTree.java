package compiler.tree;
import compiler.*;

/**
 * @author Joaquin Bravo
 * @version Mar, 2004
 */
public class WhileTree implements LangConstants, Tree
{
	
	private Lexeme root;
	private Tree condition;
	private Tree block;
	
	public WhileTree(Lexeme r) throws Exception
	{
		if(r.getType().equals(WHILE)) root = r;
		else throw new Exception(">> while expected... found " + r.getType());
	}
	
	public String toString(){
		return root + " " + condition + "\n" + block + "\n" + END;
	}
	
	public Lexeme getRoot() { return root; }
	public Tree getCondition() { return condition; }
	public Tree getBlock() { return block; }
	
	public void setRoot(Lexeme r) {	root = r; }
	public void setCondition(Tree c) { condition = c; }
	public void setBlock(Tree b) { block = b; }
	
	public Lexeme eval(Environment env)
	{
		Lexeme result = null;
		while(condition.eval(env) != null)
		{
			result = block.eval(env);
		}
		return result;
	}
}
