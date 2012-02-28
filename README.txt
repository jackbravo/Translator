------------------------------------------------------------------------
PROJECT TITLE: IPL
AUTHOR: Joaquin Bravo
DATE: May, 2004
------------------------------------------------------------------------

PURPOSE OF PROJECT:
Create an interactive programming language. The language can either
read the code from a file or directly from the console, in which case
it behaves interactively: you write one command and it prints the output.

HOW TO START THIS PROJECT:
execute the shell script testPorg
>./interp [filename]

USER INSTRUCTIONS:
>make
Compile the entire project

>make doc
Create the documentation and store it in a folder called html/

>make clean
Clean all the documentation and .class files

-----------
DESCRIPTION
-----------
Language data sheet:
The language is structured. It contains control structures such as while and if-else.
Parameter passing is done by value.
The variable scope is static.
Additions to the language are done via external files that act as libraries. They are
included into the program using the reserved word: include.
example:

inclue "math.txt"

math.txt is the provided math library with 3 functions:
- factorial(n);
- fibonacci(n);
- pow(n,exp);

This java project is organized in 4 classes:

- IPL
This class consists only in the main() method.
It imports the compiler package that contains all the other classes.

- compiler.LangConstants
This is not a class, it is only an interfase used to store the constants
that will be used to designate the various Lexeme types.

- compiler.Lexeme
This is the most basic class in the project. It contains the parsed tokens
and has several constructor to create diferent kinds of lexemes. Each lexeme
has information on the type of lexeme and the value asociated with it if it
is a string, variable or numeric lexeme.

- compiler.Scanner
Responsible for reading from any input given and parsing the lexemes.
It has a StreamTokenizer object that uses every time someone uses its
readLexeme() method to parse the input, and returns.

- compiler.Parser
It contains all the BNF grammar rules of the language and uses them to parse
the input and create a tree like structure for each statement.
NOTE: each tree it returns contains an eval method wich is called by the IPL
object to execute the programs.

- compiler.Environment
Data structure that stores all the variable bindings and function definitions
needed during execution time.