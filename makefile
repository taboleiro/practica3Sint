CONTEXT = ../..
JFLAGS = -g
JC = javac
LIBDIR = $(CONTEXT)/lib/servlet-api.jar
CLSDIR = ./WEB-INF/classes
.SUFFIXES: .java .class
.java.class:
	$(JC) -classpath $(LIBDIR) -d $(CLSDIR) *.java

CLASSES = \
	Canal.java \
	Programa.java \
	Errores.java \
	Sint25P2.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class