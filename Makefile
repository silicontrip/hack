
CP=.
CLASSES= Board.class Card.class Colour.class ColourType.class Deck.class HashBoard.class BoardLocation.class Node.class NodeType.class CardLocation.class \
UserInterface.class UIFactory.class ArgumentsGui.class UIRandom.class \
Hack.class test.class

ANONCLASSES=
JARS=
MAINCLASS=

all: hack.jar

hack.jar: classes
	jar cfm hack.jar MANIFEST.MF $(CLASSES)

classes: $(CLASSES)


%.class: %.java
	javac  -classpath $(CP) -encoding utf8 -Xlint:deprecation -Xlint:unchecked  $<

