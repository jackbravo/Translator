package compiler;
/**
 * Constants used by the language to specify the lexeme type.
 * They are stored in strings.
 * 
 * @author Joaquin Bravo
 * @version Jan, 2004
 */

public interface LangConstants
{
    String NUM = "NUM";
    String STR = "STR";
    String VAR = "VAR";
    String INC = "include";

    // Keywords
    String IF = "if";
    String FN = "fn";
    String END = "end";
    String ELSE = "else";
    String WHILE = "while";
    
    // True and Flase
    String TRUE = "true";
    String FALSE = "false";

    // Operators
    String ADD = "ADD";
    String SUB = "SUB";
    String MUL = "MUL";
    String DIV = "DIV";
	String ASG = "ASG";
    String EQ = "EQ";
    String GT = "GT";
    String LT = "LT";

    // Punctuation
    String COMMA = "COMMA";
    String SEMIC = "SEMIC";
    String O_PAR = "O_PAR";
    String C_PAR = "C_PAR";
    String O_COR = "O_COR";
    String C_COR = "C_COR";
}
