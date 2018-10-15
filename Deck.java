import java.util.ArrayList;
import java.util.Random;
public class Deck {
	private ArrayList <Card> deck;

	public Deck (Colour col) {

		// node factory

		Node zero = new Node(NodeType.zero);
		Node one = new Node(NodeType.one);
		Node hash = new Node(NodeType.hash);
		Node nil = new Node(NodeType.nil); // I really wanted to call this null

		// deck factory

		deck = new ArrayList<Card>();
		deck.add(new Card(zero,zero,zero,zero,col,false));
		deck.add(new Card(zero,zero,one,zero,col,false));
		deck.add(new Card(one,one,one,zero,col,false));
		deck.add(new Card(one,one,one,one,col,false));
		deck.add(new Card(one,zero,one,zero,col,true));
		deck.add(new Card(zero,one,one,zero,col,false));
		deck.add(new Card(hash,hash,hash,hash,col,false));
		deck.add(new Card(hash,hash,hash,nil,col,false));
		deck.add(new Card(hash,nil,hash,nil,col,false));
		deck.add(new Card(nil,nil,hash,nil,col,false));
		deck.add(new Card(nil,hash,hash,nil,col,false));

	}

	public void shuffle()
	{
		Random rand = new Random();
		int deckLen = deck.size();
		for (int i=0; i< deckLen; i++)
		{
			int r = rand.nextInt(deckLen);
			Card temp = deck.get(i);
			deck.set(i,deck.get(r));
			deck.set(r,temp);
		}

	}

	// don't like this
	public ArrayList<Card> getArray() { return deck; }

	public Card getCard(int index) { return deck.get(index); }
	public void removeCard(int index) { deck.remove(index); }

	public int startPosition() {
		int pos = 0;
		for (Card c : deck) {
			if (c.isStart())
				return pos;
			pos++;
		}
		return pos;
	}
	
	public String toString() {
		StringBuilder sb=new StringBuilder("[");  
		Boolean first = true;
		for (Card c : deck) {
			if (!first) {
				sb.append(",");
			}
			first = false;
			sb.append(c);
		}
		sb.append("]");
		return sb.toString();
	}

}
