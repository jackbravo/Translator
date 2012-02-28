#---------------------------------------------------------------
# Using this Makefile
#
#	To compile your java source (and generate documentation)
#
#	make 
#
#	To clean up your directory (e.g. before submission)
#
#	make clean
#
#---------------------------------------------------------------

JFLAGS=-Djava.compiler=NONE

# This is a rule to convert a file with .java extension
# into a file with a .class extension. The macro $< just
# supplies the name of the file (without the extension) 
# that invoked this rule.

%.class: %.java
	javac $<

# To satisfy the rule named scanner, we must have three
# class files (with date no later than the source .java files)
# and a file called scanner which should be an executable shell 
# script.  If you are using more than three classes, add the names to
# the list.  If you are using differently named classes, change the
# names in the list of class files.
eval: IPL.class compiler/Scanner.class compiler/Lexeme.class compiler/Parser.class

IPL.class: compiler/Scanner.class compiler/Lexeme.class compiler/Parser.class

compiler/Scanner.class: compiler/Lexeme.class compiler/LangConstants.class

compiler/Lexeme.class: compiler/LangConstants.class compiler/Environment.class

# Run javadoc on all hava source files in this directory.
# This rule depends upon the rule named html, which makes the
# html directory if does not already exist.
doc: html
	javadoc -private -author -version -d html/ *.java compiler/*.java compiler/tree/*.java

# Make the html subdirectory.
html:
	mkdir html

# Clean all the class files and documentation out of the directory 
clean:
	rm --force compiler/tree/*.class compiler/*.class *.class 
	rm --force -r html
