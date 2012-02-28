package compiler;

/**
 * <p>Single unit of the new programming language.</p>
 * <p>There are several types of lexemes indicated by the <code>type</code>
 * variable, and more can be added later as they are not defined by this class.
 * Never the less, they can be classified in:</p>
 * <p><b>Strings</b>: String lexemes delimited by "</p>
 * <p><b>Numbers</b>: Numeric lexemes of type int</p>
 * <p><b>Operators</b>: Char lexemes like + - / * =</p>
 * <p><b>Punctuation</b>: Char lexemes like ; , ( ) { }</p>
 * <p><b>Keywords</b>: String lexemes like "while" and "if"</p>
 * <p><b>Variables</b>: Any other string lexeme not recognized as keyword</p>
 * 
 * @author Joaquin Bravo
 * @version 1.0
 */
public class Lexeme implements LangConstants
{
    private String type;
    private String data;
    private int value;
    
    /**
     * <p>Constructor for objects of class Lexeme.</p>
     * It creates any kind of string lexeme. Most likly used to create
     * String (STR) lexemes and varible (VAR) lexemes.
     * 
     * @param lexType   Lexeme type
     * @param lexData   Lexeme data, can receive Strings and Integers.
     */
	public Lexeme(String lexType, String lexData)
	{
        type = lexType;
        data = lexData;
	}

	/**
	 * <p>Constructor for objects of class Lexeme.</p>
	 * It receives a number and stores it as a NUM lexeme.
	 * 
	 * @param lexValue	Lexeme number, the numeric value of the NUM.
	 */
	public Lexeme(int lexValue)
	{
		type = NUM;
		value = lexValue;
	}

    /**
     * <p>Constructor for objects of class Lexeme.</p>
     * It creates an operator ( + - = == &lt &gt / * ) or
     * punctuation ( , ; ( ) { } ) Lexeme.
     * </br>
     * If it receives the Char <code>'~'</code> it will create an
     * assignment lexeme (==).
     */
    public Lexeme(char lexOper)
    {
        if(lexOper == '+') type = ADD;
		if(lexOper == '-') type = SUB;
		if(lexOper == '*') type = MUL;
		if(lexOper == '/') type = DIV;
		if(lexOper == '=') type = ASG;
		if(lexOper == '~') type = EQ;
		if(lexOper == '>') type = GT;
		if(lexOper == '<') type = LT;
		
		if(lexOper == ',') type = COMMA;
		if(lexOper == ';') type = SEMIC;
		if(lexOper == '(') type = O_PAR;
		if(lexOper == ')') type = C_PAR;
		if(lexOper == '{') type = O_COR;
		if(lexOper == '}') type = C_COR;
		
		if(lexOper == '1') type = TRUE;
		if(lexOper == '0') type = FALSE;
    }
    
    /**
     * Constructor for objects of class Lexeme.
     * </br>
     * It is used for keyword type lexemes like WHILE, IF, END, etc.
     * If it doesn't find an appropiate keyword, it will assume it is a variable.
     * 
     * It gets a string and determinates if it is a keyword or just a variable.
     */
    public Lexeme(String str)
    {
    	if (str.equals(IF) || str.equals(ELSE) ||
    		str.equals(FN) || str.equals(END) ||
    		str.equals(WHILE) || str.equals(INC))
    		type = str;
    	else
    	{
    		type = VAR;
    		data = str;
    	}
    }

    public int getValue() { return value; }
    public String getData() { return data; }
    public String getType() { return type; }

    public void setValue(int newValue) { value = newValue; }
    public void setData(String newData) { data = newData; }
    public void setType(String newType) { type = newType; }

    /**
     * Return a string representation of the object.
     * </br>
     * It includes the lexeme type and any other stored value if available.
     * 
     * @return a string representation of the object
     */
    public String toString()
    {
        if (type.equals(NUM)) return "" + value;
        if (type.equals(VAR)) return data;
        if (type.equals(STR)) return '"' + data + '"';
        return type;
    }
    
    /**
     * Compares the specified Object with this Lexeme for equality,
     * as per the definition in the Object class.
     * 
     * @override equals in class Object
     * @return true if the specified object is equal to this lexeme
     * @param obj object to be compared for equality with this lexeme
     */
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		
		//Test object
		Lexeme l = (Lexeme)obj;
		if (!type.equals(l.getType())) return false;
    	
		if (l.getType().equals(NUM))
			if (l.getValue() == value) return true;
			else return false;
		if (l.getType().equals(VAR))
			if (l.getData().equals(data)) return true;
			else return false;
		if (l.getType().equals(STR))
			if (l.getData().equals(data)) return true;
			else return false;
    	
		return true;
	}
    
	public int hashCode()
	{
		int hash = 7;
		hash = 31 * hash + value;
		hash = 31 * hash + (null == data ? 0 : data.hashCode());
		hash = 31 * hash + (null == type ? 0 : type.hashCode());
		return hash;
	}
    
    /**
     * Return a Lexeme containing the sum of the parameter with
     * the current lexeme. If it is a number it does an arithmetic addition.
     * If its a string it concatenates the two strings.
     * 
     * @param l Lexme you want to add
     * @return concatenated string or addition of the two numeric lexemes.
     */
    public Lexeme add(Lexeme l)
    {
    	Lexeme result = null;
    	if(type.equals(NUM)) result = new Lexeme( value+l.getValue() );
    	if(type.equals(STR)) result = new Lexeme( data+l.getData() );
    	return result;
    }
	public Lexeme sub(Lexeme l)
	{
		Lexeme result = null;
		if(type.equals(NUM)) result = new Lexeme( value-l.getValue() );
		return result;
	}
	public Lexeme mul(Lexeme l)
	{
		Lexeme result = null;
		if(type.equals(NUM)) result = new Lexeme( value*l.getValue() );
		return result;
	}
	public Lexeme div(Lexeme l)
	{
		Lexeme result = null;
		if(type.equals(NUM)) result = new Lexeme( value/l.getValue() );
		return result;
	}
	public Lexeme gt(Lexeme l)
	{
		Lexeme result = null;
		if(type.equals(NUM)) result = ( value>l.getValue() ? new Lexeme(TRUE) : null );
		return result;
	}
	public Lexeme lt(Lexeme l)
	{
		Lexeme result = null;
		if(type.equals(NUM)) result = ( value<l.getValue() ? new Lexeme(TRUE) : null );
		return result;
	}
	public Lexeme eq(Lexeme l)
	{
		Lexeme result = null;
		result = ( this.equals(l) ? new Lexeme(TRUE) : null );
		return result;
	}
}
