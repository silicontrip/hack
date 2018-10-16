import java.util.ArrayList;
public class test {

	public static ArrayList<CardLocation> validMoves (Board b, Deck d)
	{
		ArrayList<CardLocation> moves = new ArrayList<CardLocation>();
		for (BoardLocation bl: b.getValidSpace())
			for (Card cd: d.getArray())
			{
				if (b.isValid(bl,cd))
					moves.add(new CardLocation(cd,bl));
		
			}
		return moves;
	}
	public static void main(String[] args) {

		Board b = new Board();

		Deck d  = new Deck(Colour.red);

		d.shuffle();

		int startCardIndex = d.startPosition();

		System.out.println("start: " + startCardIndex);
		System.out.println(d);
		
		Card c = d.getCard(startCardIndex);
		System.out.println ("Start: " + c);

		b.play(new BoardLocation(0,0),c);

		d.removeCard(startCardIndex);
		System.out.println(d);
		
		System.out.println("board:" + b);

		ArrayList<CardLocation> mv = validMoves(b,d);
		for (CardLocation cl: mv)
			System.out.println ("" + cl.location + ": " + cl.card );

	}
}
