import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

import java.io.*;

public class UIWeight extends UserInterface {

	private Deck deck;
	private Board board;
	private Colour player=null;
	
	private HashMap<Integer,Integer> weights;
	private ArrayList<Integer> moves;

	public UIWeight() { 
		// load network weights
		moves = new ArrayList<Integer>();
		try {
			FileInputStream fi = new FileInputStream(new File("UIWeight.pojo"));
			ObjectInputStream oi = new ObjectInputStream(fi);
			weights = (HashMap<Integer,Integer>) oi.readObject();
			oi.close();
		} catch (Exception e) {
			weights = new HashMap<Integer,Integer>();
			e.printStackTrace();
		} // maybe other errors...



	}
    public void show() {;}

	//public static String name() { return "random"; }

	public void updateDeck (Deck d) { 
		deck = d; 
	    if (player==null)
            player = deck.getCard(0).getColour();
	}
	public void updateBoard (Board b) { board = b; }

	public void updateScores(HashMap<Colour,Integer> s) {
		; 
	}
	public void showWinner (Colour w)
	{
		if (player.equals(w))
		{
			//reward net
			for (Integer mv: moves)
				weights.put(mv,weights.get(mv)+1);
		} else {
			// punish net
			for (Integer mv: moves)
				weights.put(mv,weights.get(mv)-1);
		}
		// save net
		try {
			FileOutputStream f = new FileOutputStream(new File("UIWeight.pojo"));
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(weights);
			o.close();
		} catch (Exception e) {
			// silently fail :-(
		}
	}

	private Integer getFive(BoardLocation bl, Colour c)
	{
		int cx = bl.getX();
		int cy = bl.getY();
		int state = 0;
		for (int y=cy-2; y<cy+3; y++)
			for (int x=cx-2; x <cx+3; x++)
			{
				Card cd = board.getCard(x,y);
				if (cd != null)
					if (c.equals(cd.getColour()))
						state = state | 1;
				state = state << 1;
			}
			// need to do rotation.

		return new Integer(state);
	}

	public CardLocation requestMove()
	{
		ArrayList<CardLocation> mv = board.validMoves(deck);

		int score=0;
		int move=0;
		CardLocation chosenMove=null;
		int best = 0;
		Integer bestState=0;
		for (CardLocation cl: mv)
		{
			Integer state = getFive(cl.location,player);
			if (!weights.containsKey(state))
				weights.put(state,new Integer(128));

			if (weights.get(state) > best)
			{
				best = weights.get(state);
				bestState = state;
				chosenMove = cl; 
			}

		}
		if (bestState !=0)
			moves.add(bestState);
		return chosenMove;
	}

}
