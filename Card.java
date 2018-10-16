import java.util.ArrayList;
import java.util.HashMap;
public class Card {

		private Node[] news;

		private Colour col;
		private Boolean start;
		private int rotate;

	public Card (Node n, Node e, Node s, Node w, Colour c, Boolean st)
	{
		this(n,e,s,w,c,st,0);
	}
	public Card (Node n, Node e, Node s, Node w, Colour c, Boolean st, int rot)
	{
		news = new Node[4];
		news[0] = n;
		news[1] = e;
		news[2] = s;
		news[3] = w;

		col = c;
		start = st;
		rotate = rot;
	}

	public ArrayList<Card> getAllRotations() {
		HashMap<String,Card> sym  = new HashMap<String,Card>();

		Card rc = this;
		for (int rotate =0; rotate < 4 ; rotate++)
		{
			if (sym.get(rc.getNodeRotation()) == null)
				sym.put(rc.getNodeRotation(),rc);
			rc = rc.newRotate();
		}
		return new ArrayList<Card> (sym.values());

	}

	private String getNodeRotation() {
		return "" + getNorth() + getEast() + getSouth() + getWest() ;
	}

	private Node getNode(int i) { return news[i]; }
	private int getRotate() { return rotate; }

	Node getNorth() { return news[rotate]; }
	Node getEast() { return news[(rotate+1)%4]; }
	Node getSouth() { return news[(rotate+2)%4]; }
	Node getWest() { return news[(rotate+3)%4]; }

	Colour getColour() { return col; }

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

	public String toString() { return new String (" " + col + ":{" + getRotate() + ":[" + getNorth() + "," + getEast() + "," + getSouth() + "," + getWest() + "]}"); }

	// reduce for symmetry
	private Card newRotate() { 
		Card nc = new Card (getNode(0),getNode(1),getNode(2),getNode(3),getColour(),isStart(),getRotate());
		nc.rotateCW();
		return nc;
	}
	private void resetRotation() { rotate = 0; }
	public void rotateCW() {  rotate = (rotate + 1) % 4; }
	public void rotateCCW() {  rotate = (rotate + 3) % 4; }
	public void rotate180() {  rotate = (rotate + 2) % 4; }

	@Override 
	public boolean equals(Object o) {
		if (o == this) return true;
                if (!(o instanceof Card)) return false;
                Card l = (Card) o;
		// compare without rotation
		return getNode(0)==l.getNode(0) && getNode(1)==l.getNode(1) && getNode(2)==l.getNode(2) && getNode(3)==l.getNode(3) && getColour()==l.getColour() ;
	}

	@Override
	public int hashCode() {
		return getNode(0).hashCode() ^ 4 * getNode(1).hashCode() ^ 16 * getNode(2).hashCode() ^ 64 * getNode(3).hashCode() ^ 320 * getColour().hashCode();
	}
}
