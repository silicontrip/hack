
CP=.
CLASSES= Board.class Card.class Colour.class ColourType.class Deck.class HashBoard.class BoardLocation.class Node.class NodeType.class CardLocation.class \
UserInterface.class UIFactory.class ArgumentsGui.class UIRandom.class UICli.class UIHigh.class UIGui.class UIWeight.class UIPattern.class \
UIGuiBoardPanel.class UIGuiDeckPanel.class \
Hack.class  HackTrainer.class test.class allcards.class

ANONCLASSES=
JARS=
MAINCLASS=

all: hack.jar

hack.jar: classes
	jar cfm hack.jar MANIFEST.MF $(CLASSES) Cards

classes: $(CLASSES)


%.class: %.java
	javac  -classpath $(CP) -encoding utf8 -Xlint:deprecation -Xlint:unchecked  $<

