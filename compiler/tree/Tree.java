package compiler.tree;
import compiler.*;

/**
 * @author Joaquin Bravo
 * @version Feb, 2004
 */
public interface Tree {
	
	public Lexeme eval(Environment env);

	public String toString();

}
