package compiler.tree;
import compiler.*;
import java.io.*;

/**
 * @author Joaquin Bravo
 * @version Mar, 2004
 */
public class ExprTree implements LangConstants, Tree
{
	private Lexeme root;
	private Tree right;
	private Tree left;
	
	public ExprTree(Lexeme r) throws Exception
	{
		root = r;
	}
	
	public String toString(){
		if(right == null && left == null) return root.toString();
		if(right == null && left != null) return "(" + root + " " + left + ")";
		if(left == null && right != null) return "(" + root + " " + right + ")";
		return "(" + left + " " + root + " " + right + ")";
	}
	
	public Lexeme getRoot() { return root; }
	public Tree getRight() { return right; }
	public Tree getleft() { return left; }
	
	public void setRoot(Lexeme r) {	root = r; }
	public void setRight(Tree r) { right = r; }
	public void setLeft(Tree l) { left = l; }
	
	public Lexeme eval(Environment env)
	{
		String type = root.getType();
		
		if (type.equals(NUM)) return root;
		if (type.equals(STR)) return root;
		if (type.equals(VAR))
		{
			Lexeme result = null;
			result = (Lexeme)env.lookup(root);
			if (result == null)
			{
				System.err.println(">> Variable undefined: " + root);
				return null;
			}
			return result;
		}
		if (type.equals(FN))
		{
			Lexeme name = ((ExprTree)left).getRoot();
			FnTree func = (FnTree)env.lookup(name);
			
			if(name.getData().equals("read")) return readFunc();
			
			if (func != null)
			{
				Environment child = null;
				ExprListTree rCall = null;
				ListTree rFunc = null;
				int argsCall = 0;
				int argsFunc = 0;
				
				// create the new environment with the old as the parent
				child = new Environment();
				child.extend(env);
				rCall = (ExprListTree)right;
				rFunc = (ListTree)func.getArgs();
				
				// check for equal number of arguments in the function call and definition
				if(rCall != null) argsCall = rCall.count();
				if(rFunc != null) argsFunc = rFunc.count();
				if (argsCall != argsFunc)
				{
					System.err.println(">> Wrong number of args on fnCall: " + name);
					return null;
				}
				
				// fill the child environment with the evaluated arguments in the function call
				for(int i = 0; i<argsCall ; i++)
				{
					Lexeme arg = rFunc.getRoot();
					Lexeme val = rCall.eval(env);
					child.put(arg,val);
					rFunc = (ListTree)rFunc.getNext();
					rCall = (ExprListTree)rCall.getNext();
				}
				return func.getBlock().eval(child);
			}
			else
			{
				System.err.println(">> Function undefined: " + name);
				return null;
			}
		}
		if (type.equals(ASG))
		{
			Lexeme result = right.eval(env);
			Lexeme name = ((ExprTree)left).getRoot();
			env.put(name,result);
			return result;
		}
		
		Lexeme leftResult = left.eval(env);
		Lexeme rightResult = right.eval(env);
		Lexeme result = null;
		
		if (type.equals(ADD)) { result = leftResult.add(rightResult); }
		if (type.equals(SUB)) { result = leftResult.sub(rightResult); }
		if (type.equals(MUL)) { result = leftResult.mul(rightResult); }
		if (type.equals(DIV)) { result = leftResult.div(rightResult); }
		if (type.equals(EQ)) { result = leftResult.eq(rightResult); }
		if (type.equals(GT)) { result = leftResult.gt(rightResult); }
		if (type.equals(LT)) { result = leftResult.lt(rightResult); }
		return result;
	}
	
	public Lexeme readFunc()
	{
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			Integer num = new Integer(in.readLine());
			return new Lexeme(num.intValue());
		}
		catch(IOException e)
		{
			System.err.println(e.getMessage());
			return null;
		}
	}
}
