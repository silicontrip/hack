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
		ArrayList<CardLocation> mv = board.validMoves(deck);
		int chosenMove = rand.nextInt(mv.size());
		return mv.get(chosenMove);
	}

}
