package compiler;
import java.io.*;

/**
 * <p>The <code>Scanner</code> class is the responsible for reading
 * from the input received in the constructor. It creates a
 * <code>StreamTokenizer</code> to break the input in tokens and create
 * lexemes with its <code>readLexeme()</code> method.</p>
 * 
 * <p>A usual client of the <code>Scanner</code> will look like this:
 * <pre>
 * while(next != null)
 *		{
 *			System.out.println(next);
 *			next = scanObj.readLexeme();
 *		}
 * </pre> 
 * 
 * @author Joaquin Bravo
 * @version 1.0
 */
public class Scanner implements LangConstants
{
	// instance variables - replace the example below with your own
	private StreamTokenizer st;
	private int tokenType;

	/**
	 * Constructor for objects of class Scanner
	 * </br>
	 * The constructor receives a <code>Reader</code> that uses to create
	 * a <code>StreamTokenizer</code> that will break the input into tokens.
	 * 
	 * @param in the Reader object necesary to create the tokenizer
	 */
	public Scanner(Reader in)
	{
		// initialise instance variables
		st = new StreamTokenizer(in);
		st.eolIsSignificant(false); // \n and \n\r are white spaces
		st.lowerCaseMode(false); // do not lower case words
		st.quoteChar(34); // the char 34 (") will be the string delim
		st.slashSlashComments(true); // C++ comments are allowed
		st.slashStarComments(true); // C-style comments are allowed
		st.ordinaryChar('+');
		st.ordinaryChar('-');
		st.ordinaryChar('+');
		st.ordinaryChar('/');
		st.ordinaryChar('=');
		st.ordinaryChar('<');
		st.ordinaryChar('>');
		st.ordinaryChar(',');
		st.ordinaryChar(';');
		st.ordinaryChar('{');
		st.ordinaryChar('}');
		st.ordinaryChar('(');
		st.ordinaryChar(')');
	}

	/**
	 * Reads a lexeme from the <code>Reader</code> and creates a
	 * Lexeme object wich will be returned.
	 * 
	 * @return Lexeme object corresponding to the token type.
	 */
	public Lexeme readLexeme()
	{
		Lexeme token = null;
		
		try
		{
			tokenType = st.nextToken();
			switch (tokenType)
			{
				case StreamTokenizer.TT_EOF:
					token = null;
					break;
				case StreamTokenizer.TT_NUMBER:
					token = new Lexeme((int)st.nval);
					break;
				case StreamTokenizer.TT_WORD:
					token = new Lexeme(st.sval);
					break;
				case 34: // case quoted string (34 = ")
					token = new Lexeme(STR, st.sval);
					break;
				case '=':
					if (st.nextToken() == '=') token = new Lexeme('~');
					else
					{
						token = new Lexeme('=');
						st.pushBack();
					}
					break;
				default:
					token = new Lexeme((char)tokenType);
					break;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		return token;
	}
	
	public int lineno() {
		return st.lineno();
	}
	
	public void pushBack() {
		st.pushBack();
	}
}
