public class Card {
		private Node north;
		private Node east;
		private Node south;
		private Node west;

		private Colour col;
		private Boolean start;

	public Card (Node n, Node e, Node s, Node w, Colour c, Boolean st)
	{
		north = n;
		east = e;
		south = s;
		west = w;

		col = c;
		start = st;
	}

	Node getNorth() { return north; }
	Node getEast() { return east; }
	Node getSouth() { return south; }
	Node getWest() { return west; }

	Colour getColour() {  return col; }

	public Boolean matchNorth(Card c) { 
		if (c == null) return true;
		return getNorth().match(c.getSouth()); 
	}
	public Boolean matchEast(Card c) {
		if (c == null) return true;
		return getEast().match(c.getWest()); 
	}
	public Boolean matchSouth(Card c) { 
		if (c == null) return true;
		return getSouth().match(c.getNorth()); 
	}
	public Boolean matchWest(Card c) { 
		if (c == null) return true;
		return getWest().match(c.getEast()); 
	}
	
	public Boolean isStart() { return start==true; }

	public String toString() { return new String ("[" + north + "," + east + "," + south + "," + west + "]"); }

}
