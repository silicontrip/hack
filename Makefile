
CP=.
CLASSES= Board.class Card.class Colour.class Deck.class HashBoard.class BoardLocation.class Node.class NodeType.class \
test.class

ANONCLASSES=
JARS=
MAINCLASS=

all: classes

classes: $(CLASSES)


%.class: %.java
	javac  -classpath $(CP) -encoding utf8 -Xlint:deprecation -Xlint:unchecked  $<

