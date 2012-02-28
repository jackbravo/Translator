package compiler;
import compiler.tree.*;

/**
 * Parser of the language. It consists of a recursive descent parser
 * that reads all the code and builds a Tree out of every statement found.
 * 
 * @author Joaquin Bravo
 * @version Feb, 2004
 */
public class Parser implements LangConstants
{
	
	private Scanner scn;
	private Lexeme current;
	private Tree resultTree;
	
	public Parser(Scanner s)
	{
		scn = s;
	}
	
	/**
	 * Move to the next lexeme in the scanner and return the last.
	 * 
	 * @return Last lexeme stored.
	 */
	public Lexeme advance()
	{
		Lexeme tmp = current;
		current = scn.readLexeme();
		return tmp;
	}
	
	/**
	 * Insist that the current lexeme  is of the given type.
	 * If it is, advance to the next lexeme. Otherwise an error is reported
	 * 
	 * @param type Lexeme type that you are expecting
	 * @return Last lexeme stored.
	 * @throws Exception if the lexeme doesn't match the specified value.
	 */
	public Lexeme match(String type) throws Exception
	{
		if( check(type) ) {
			Lexeme tmp = current;
			current = scn.readLexeme();
			return tmp;
		}
		else
		{
			String t = current.getType();
			if(t.equals(STR) || t.equals(VAR))
				throw new Exception(type + " expected around line " + scn.lineno()
								+ ". Found " + t + ": " + current.getData());
			else
				throw new Exception(type + " expected around line " + scn.lineno()
										+ ". Found " + current.getType());
		}
	}
	
	/**
	 * Check whether or not the current lexeme is of the given type
	 * 
	 * @param type Lexeme type that you are expecting
	 * @return true if <pre>currentLexeme.type == type</pre>
	 */
	public boolean check(String type)
	{
		return current.getType().equals(type);
	}
	
	/**
	 * Move the scanner one lexeme back.
	 * 
	 * @param back Last lexeme read.
	 */
	public void pushBack(Lexeme back)
	{
		current = back;
		scn.pushBack();
	}
	
	/**
	 * Top level of the grammar.
	 * <pre>
	 * statement ::= function def
	 *          | assig stmt
	 *          | while stmt
	 *          | if stmt
	 * </pre>
	 * 
	 * @return a Tree object containing the statement
	 * @throws Exception
	 */
	public Tree statement() throws Exception
	{
		if(check(IF)) { return ifStatement(); }
		else if(check(FN)) { return fnStatement(); }
		else if(check(WHILE)) { return whileStatement(); }
		else if(check(INC)) { return includeStatement(); }
		else return assignStatement();
	}
	
	/**
	 * Parses an IF statement.
	 * <pre>
	 * ifStatement ::= IF ( boolExp ) block END
	 *               | IF ( boolExp ) block ELSE block END
	 * </pre>
	 * 
	 * @return ifTree object representing the ifStatement
	 * @throws Exception
	 */
	public IfTree ifStatement() throws Exception
	{
		IfTree ifStmt = new IfTree(match(IF));
		match(O_PAR);
		ifStmt.setCondition(boolExp());
		match(C_PAR);
		ifStmt.setThen(block());
		if(check(ELSE))
		{
			match(ELSE);
			ifStmt.setElse(block()); 
		}
		//match(END);
		return ifStmt;
	}
	
	/**
	 * Parses a function declaration.
	 * <pre>
	 * fnStatement ::= FN var ( optArgList ) block END
	 * </pre>
	 * 
	 * @return fnTree
	 * @throws Exception
	 */
	public FnTree fnStatement() throws Exception
	{
		FnTree fnStmt = new FnTree(match(FN));
		fnStmt.setVar(match(VAR));
		match(O_PAR);
		fnStmt.setArgs(optArgList());
		match(C_PAR);
		fnStmt.setBlock(block());
		//match(END);
		return fnStmt;
	}
	
	/**
	 * Parses a while statement.
	 * <pre>
	 * whileStatement ::= WHILE ( boolExp ) block END
	 * </pre>
	 * 
	 * @return WhileTree
	 * @throws Exception
	 */
	public WhileTree whileStatement() throws Exception
	{
		WhileTree whileStmt = new WhileTree(match(WHILE));
		match(O_PAR);
		whileStmt.setCondition(boolExp());
		match(C_PAR);
		whileStmt.setBlock(block());
		//match(END);
		return whileStmt;
	}
	
	/**
	 * Parses an optional list.
	 * The list can be empty, otherwise it will call the method argList()
	 * <pre>
	 * optArgList ::= empty | argList
	 * </pre>
	 * 
	 * @return ListTree
	 * @throws Exception
	 */
	public ListTree optArgList() throws Exception
	{
		if(check(VAR)) return argList();
		return null;
	}
	
	/**
	 * Parses and argument list consisting of several VAR lexemes
	 * separated by commas.
	 * <pre>
	 * argList ::= VAR {, VAR}*
	 * </pre>
	 * 
	 * @return ListTree
	 * @throws Exception
	 */
	public ListTree argList() throws Exception
	{
		ListTree args = new ListTree(match(VAR));
		if(check(COMMA)) {
			match(COMMA);
			args.setNext(argList());
		}
		return args;
	}
	
	/**
	 * Parses an optional expresion list.
	 * The list can be empty, otherwise it will call the method argList()
	 * <pre>
	 * optExprList ::= empty | exprList
	 * </pre>
	 * 
	 * @return ListTree
	 * @throws Exception
	 */
	public ExprListTree optExprList() throws Exception
	{
		if(!check(C_PAR)) return exprList();
		return null;
	}
	
	/**
	 * Parses and argument list consisting of several VAR lexemes
	 * separated by commas.
	 * <pre>
	 * exprList ::= VAR {, VAR}*
	 * </pre>
	 * 
	 * @return ListTree
	 * @throws Exception
	 */
	public ExprListTree exprList() throws Exception
		{
			ExprListTree args = new ExprListTree(expr());
			if(check(COMMA)) {
				match(COMMA);
				args.setNext(exprList());
			}
			return args;
		}
	
	/**
	 * A single block of statements.
	 * <pre>
	 * block ::= { statement }*
	 * </pre>
	 * 
	 * @return BlockTree
	 * @throws Exception
	 */
	public BlockTree block() throws Exception
	{
		BlockTree stmts = new BlockTree(statement());
		checkTerminal();
		if(!check(END) && !check(ELSE))
			stmts.setNext(block());
		return stmts;
	}
	
	/**
	 * Parses a mathematical expresion. <br>
	 * It can be an assignment statement or a regular expresion. If
	 * it is an assignment it has to begin with a variable.
	 * <pre>
	 * assignStatement ::= VAR = expr ; | exprStmt
	 * </pre>
	 * 
	 * @return ExprTree
	 * @throws Exception
	 */
	public Tree assignStatement() throws Exception
	{
		if(check(VAR))
		{
			Lexeme var = match(VAR);
			if(!check(ASG))
			{
				pushBack(var);
				return exprStmt();
			}
			ExprTree assign = new ExprTree(match(ASG));
			assign.setLeft(new ExprTree(var));
			assign.setRight(expr());
			//match(SEMIC);
			return assign;
		}
		else
		{
			return exprStmt();
		}
	}
	
	/**
	 * Parses an expresion statement.
	 * Unlike the regular expresion, this one has to be terminated by ";"
	 * <pre>
	 * exprStmt ::= expr ;
	 * </pre>
	 * 
	 * @return an ExprTree repreting a single statement.
	 * @throws Exception
	 */
	public ExprTree exprStmt() throws Exception
	{
		ExprTree exprStmt = expr();
		//match(SEMIC);
		return exprStmt;
	}
	
	/**
	 * A regular mathematical expresion.
	 * Top level of operator precedence (adding and substracting)
	 * <pre>
	 * expr ::= term {[+ | -] term }*
	 * </pre>
	 * 
	 * @return ExprTree part of a larger statement.
	 * @throws Exception
	 */
	public ExprTree expr() throws Exception
	{
		ExprTree root = term();
		while(check(ADD) || check(SUB))
		{
			ExprTree temp = new ExprTree(advance());
			temp.setLeft(root);
			temp.setRight(term());
			root = temp;
		}
		return root;
	}

	/**
	 * Second level of precedence.
	 * <pre>
	 * term ::= primary {[* | /] primary }*
	 * </pre>
	 * 
	 * @return ExprTree with a term ( * or / )
	 * @throws Exception
	 */
	public ExprTree term() throws Exception
	{
		ExprTree root = primary();
		while(check(MUL) || check(DIV))
		{
			ExprTree temp = new ExprTree(advance());
			temp.setLeft(root);
			temp.setRight(primary());
			root = temp;
		}
		return root;
	}
	
	/**
	 * Parses a primary unit of a mathematical expresion.
	 * <pre>
	 * primary ::= neg | NUM  | STR | ( expr )
	 *           | VAR ( optExprList )
	 * </pre>
	 * 
	 * @return ExprTree
	 * @throws Exception
	 */
	public ExprTree primary() throws Exception
	{
		if(check(SUB)) return neg();
		if(check(NUM)) return new ExprTree(match(NUM));
		if(check(STR)) return new ExprTree(match(STR));
		else if (check(VAR))
		{
			ExprTree root = new ExprTree(match(VAR));
			if(check(O_PAR))
			{
				match(O_PAR);
				ExprTree temp = new ExprTree(new Lexeme(FN));
				temp.setLeft(root);
				temp.setRight(optExprList());
				match(C_PAR);
				return temp;
			}
			else return root;
		}
		else
		{
			match(O_PAR);
			ExprTree root = expr();
			match(C_PAR);
			return root;
		}
	}
	
	/**
	 * Parses a single number. It can be a negative number.
	 * <pre>
	 * num ::= - NUM | - VAR
	 * </pre>
	 * 
	 * @return A tree storing a NUM lexeme.
	 * @throws Exception
	 */
	public ExprTree neg() throws Exception
	{
		ExprTree neg = new ExprTree(match(SUB));
		if(check(NUM))
		{
			Lexeme num = match(NUM);
			num.setValue(num.getValue()*(-1));
			return new ExprTree(num);
		}
		else
		{
			neg.setRight(new ExprTree(match(VAR)));
			return neg;
		}
		
	}
	
	/**
	 * Parses a boolean expresion.
	 * <pre>
	 * boolExpr ::= expr { [<|==|>] expr }?
	 * </pre>
	 * 
	 * @return ExprTree with a Boolean expresion
	 * @throws Exception if it doesn't find &lt, &gt or ==
	 */
	public ExprTree boolExp() throws Exception
	{
		ExprTree root, temp;
		temp = expr();
		if(check(GT) || check(LT) || check(EQ))
		{
			root = new ExprTree(advance());
			root.setLeft(temp);
			root.setRight(expr());
			return root;
		}
		return temp;
	}
	
	/**
	 * Check for a terminal lexeme that ends a statement.
	 * 
	 * @throws Exception - Parser Exception
	 */
	public void checkTerminal() throws Exception
	{
		if(check(SEMIC) || check(END))
			advance();
		else
			throw new Exception("Ending lexeme expected (; or end)");
	}
	
	/**
	 * Include statement
	 * <pre>
	 * includeStatement ::= include "filename"
	 * </pre>
	 * 
	 * @throws Exception
	 */
	public IncTree includeStatement() throws Exception
	{
		match(INC);
		return new IncTree(match(STR));
	}
	
	public Lexeme getCurrent() { return current; }
}
