import java.util.HashMap;
import java.util.Set;
public class HashBoard {
	private HashMap<BoardLocation,Card> al;

	HashBoard() { al = new HashMap<BoardLocation,Card>(); }
	HashBoard(HashBoard hb) { al = new HashMap<BoardLocation,Card>(hb.getMap()); }

	protected HashMap<BoardLocation,Card> getMap() {  return al; }

	public Boolean isEmpty(int x, int y) { return get(x,y)==null; }
	public Boolean isEmpty(BoardLocation index) { return get(index)==null; }

	public Card get(int x, int y) {
		BoardLocation index = new BoardLocation(x,y);
		return al.get(index);
	}

	public Card get(BoardLocation index) {return al.get(index);}
	public void set(BoardLocation index, Card t) { al.put(index,t); }

	public void set(int x, int y, Card t) {
		BoardLocation index = new BoardLocation(x,y);
		al.put(index,t);
	}

	public Set<BoardLocation> getUsedLocations() { return al.keySet(); }

	public int size() { return al.size(); }

	public String toString() {
		StringBuilder sb=new StringBuilder("[");  
		Boolean first = true;
		for (BoardLocation bl : al.keySet()) {
			Card c = al.get(bl);
			if (!first) {
				sb.append(",");
			}
			first = false;
			sb.append("{");
			sb.append(bl);
			sb.append(":");
			sb.append(c);
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}

}
