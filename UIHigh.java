import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

public class UIHigh extends UserInterface {

	private Deck deck;
	private Board board;
    private Colour player=null;

	public UIHigh() { ; }

	//public static String name() { return "random"; }

	public void updateDeck (Deck d) { 
		deck = d; 
	    if (player==null)
            player = deck.getCard(0).getColour();
	}
	public void updateBoard (Board b) { board = b; }

	public void updateScores(HashMap<Colour,Integer> s) {; }
	public void showWinner (Colour w){;}

	public CardLocation requestMove()
	{
		ArrayList<CardLocation> mv = board.validMoves(deck);

		int score=0;
		int move=0;
		int chosenMove=0;
		for (CardLocation cl: mv)
		{
			Board nb = board.playNew(cl.location, cl.card);
			HashMap<Colour,Integer> scores = nb.getScore(Colour.allColours());
			if(scores.get(player)	>score)
			{
				score = scores.get(player);
				chosenMove = move;
			}
            move++;
		}

		return mv.get(chosenMove);
	}

}
