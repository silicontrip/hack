import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
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
		Random rand = new Random();
		Board b = new Board();

		Deck d = new Deck(new Colour(ColourType.red));

		d.shuffle();

		int startCardIndex = d.startPosition();

		System.out.println("starting deck: " + d);
		System.out.println("start card position: " + startCardIndex);
		
		Card c = d.getCard(startCardIndex);
		System.out.println ("Start card: " + c);

		ArrayList<CardLocation> mv = validMoves(b,d);
		System.out.println("valid Moves: " + mv.size());
		while (d.size() > 0 && mv.size() > 0) 
		{
			
			int chosenMove = rand.nextInt(mv.size());

			b.play(mv.get(chosenMove).location, mv.get(chosenMove).card);
			System.out.println("board:" + b);
			HashMap<Colour,Integer> scores = b.getScore();
			HashMap<Colour,Boolean> wins = b.getWin();
			for (Colour cl: Colour.allColours())
				System.out.println("" + cl + ": " + scores.get(cl) + "/" + wins.get(cl));

			d.removeCard(mv.get(chosenMove).card);
			System.out.println("deck: " + d);
		

			mv = validMoves(b,d);
			System.out.println("Valid Moves:");
			for (CardLocation cl: mv)
				System.out.println ("" + cl.location + ": " + cl.card );
		}

	}
}
