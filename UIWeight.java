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
		/*
		int count=0;
		for (Integer mv: moves) {
			System.out.println("" +count+ ": " + mv);
			count ++;
		}
		*/
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

	private int[][] getFiveMatrix(BoardLocation bl, Colour c)
	{
		int[][] mat = new int[5][5];

		int cx = bl.getX();
		int cy = bl.getY();
		for (int y=cy-2; y<cy+3; y++)
			for (int x=cx-2; x <cx+3; x++)
			{
				Card cd = board.getCard(x,y);
				mat[x+2-cx][y+2-cy] = 0;

				if (cd != null)
					if (c.equals(cd.getColour()))
						mat[x+2-cx][y+2-cy] = 1;
					else
						mat[x+2-cx][y+2-cy] = 2;
			}
		return mat;
	}

	private int calcState (int[][] matrix)
	{
		int state = 0;

		for (int y=0; y<5; y++)
			for (int x=0; x<5; x++) {
				state = state * 3;
				state = state | matrix[x][y];
			}

		return state;
	}

	private int[][] rotateMatrix(int[][] matrix)
	{
		int[][] mat = new int[5][5];

		for (int x=0;x<5;x++)
			for (int y=4; y>=0; y--)
				mat[4-y][x] = matrix[x][y];

		return mat;
	}

	private int[][] mirrorMatrix(int[][] matrix)
	{
		int[][] mat = new int[5][5];

		for (int y=0;y<5;y++)
			for (int x=4; x>=0; x--)
				mat[4-x][y] = matrix[x][y];

		return mat;
	}

	private Integer getFive(BoardLocation bl, Colour c)
	{
		int minState;
		int state;
		int[][] mat = getFiveMatrix(bl,c);
		
		state = calcState(mat);
		minState = state;

		mat = rotateMatrix(mat);
		state = calcState(mat);
		if (state<minState)
			minState = state;

		mat = rotateMatrix(mat);
		state = calcState(mat);
		if (state<minState)
			minState = state;

		mat = rotateMatrix(mat);
		state = calcState(mat);
		if (state<minState)
			minState = state;
			
		mat = rotateMatrix(mat);
		mat = mirrorMatrix(mat);
		state = calcState(mat);
		if (state<minState)
			minState = state;
	
		mat = rotateMatrix(mat);
		state = calcState(mat);
		if (state<minState)
			minState = state;

		mat = rotateMatrix(mat);
		state = calcState(mat);
		if (state<minState)
			minState = state;

		mat = rotateMatrix(mat);
		state = calcState(mat);
		if (state<minState)
			minState = state;

			return new Integer(minState);

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
			/*
			for (Colour pp: board.getColours())
			{
				// this should help with defensive play
				Integer state = getFive(cl.location,pp);
				if (!weights.containsKey(state))
					weights.put(state,new Integer(128));

				if (weights.get(state) > best)
				{
					best = weights.get(state);
					bestState = state;
					chosenMove = cl; 
				}
			}
			*/
			Integer state = getFive(cl.location,player);
			if (!weights.containsKey(state))
				weights.put(state,new Integer(128));

			if (weights.get(state) > best)
			{
				best = weights.get(state);
				bestState = state;
				chosenMove = cl; 
			}

			System.out.println("loc:" + cl.location + " state:"+ state + " weight:" + weights.get(state));

		}
		System.out.println("---");
		if (bestState !=0)
			moves.add(bestState);
		return chosenMove;
	}

}
