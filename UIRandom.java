import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

public class UIRandom extends UserInterface {

	private Deck deck;
	private Board board;
	private Random rand;

	public UIRandom() {
		rand = new Random();
	}

	//public static String name() { return "random"; }

	public void updateDeck (Deck d) { deck = d; }
	public void updateBoard (Board b) { board = b; }

	public void updateScores(HashMap<Colour,Integer> s) {; }
	public void showWinner (Colour w){;}

	public CardLocation requestMove()
	{
		ArrayList<CardLocation> mv = validMoves();
		int chosenMove = rand.nextInt(mv.size());
		return mv.get(chosenMove);
	}

	private ArrayList<CardLocation> validMoves ()
	{
		ArrayList<CardLocation> moves = new ArrayList<CardLocation>();
		for (BoardLocation bl: board.getValidSpace())
			for (Card cd: deck.getArray())
			{
				for (Card cdr: cd.getAllRotations())
				{
					//System.out.println("rotated: " + cdr);
					if (board.isValid(bl,cdr))
						moves.add(new CardLocation(cdr,bl));
				}
				//System.out.println("--");
		
			}
		return moves;
	}

}
