import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;

public class Board {
	HashBoard board;

	Board() { 
		board = new HashBoard();
	}
	
	private Board (HashBoard hb) {
		board = new HashBoard(hb);
	}

	public Boolean isEmpty (BoardLocation b) { return board.isEmpty(b); } 

	// much game logic here
	public Boolean isValid(BoardLocation bl, Card c) {
		// If the board is empty and a start card is played VALID
		if (board.size() == 0 && bl.isOrigin())
			return c.isStart();

		// if placing a card on another card INVALID
		if (!isEmpty(bl))
			return false;
		
		// if placing a card next to no other card INVALID
		if ( isEmpty(bl.getNorth()) && isEmpty(bl.getEast()) && isEmpty(bl.getSouth()) && isEmpty(bl.getWest())) 
			return false;

		// if all 4 compass points match (matching against an empty square is valid) VALID
		if ( c.matchNorth(getNorthCard(bl)) && c.matchEast(getEastCard(bl)) && c.matchSouth(getSouthCard(bl)) && c.matchWest(getWestCard(bl)) ) 
			return true;

		// need to check for rotation.

		return false;
		
	}

	//public void play(int x, int y, Card c) {
	public void play(CardLocation cl) { play(cl.location, cl.card);}
	public void play(BoardLocation bl, Card c) {
		if (isValid(bl,c)) {
			board.set(bl,c);
		}
	}

	public Board playNew (BoardLocation bl, Card c) {
		Board nb = new Board(board);
		if (nb.isValid(bl,c)) 
			nb.board.set(bl,c);
		return nb;
	}

	
	public ArrayList<Card> getAdjacentCards(BoardLocation bl) 
	{
		ArrayList<Card> cards = new ArrayList<Card>();
		if (!isEmpty(bl.getNorth())) cards.add(getNorthCard(bl));
		if (!isEmpty(bl.getEast())) cards.add(getEastCard(bl));
		if (!isEmpty(bl.getSouth())) cards.add(getSouthCard(bl));
		if (!isEmpty(bl.getWest())) cards.add(getWestCard(bl));
		return cards;
	}

	public HashSet<BoardLocation> getEmptyAdjacent(BoardLocation bl) 
	{
		HashSet<BoardLocation> empty = new HashSet<BoardLocation>();
		if (isEmpty(bl.getNorth())) empty.add(bl.getNorth());
		if (isEmpty(bl.getEast())) empty.add(bl.getEast());
		if (isEmpty(bl.getSouth())) empty.add(bl.getSouth());
		if (isEmpty(bl.getWest())) empty.add(bl.getWest());
		return empty;
	}

	public HashSet<BoardLocation> getValidSpace()
	{
		HashSet<BoardLocation> validSpaces = new HashSet<BoardLocation>();
		if (board.getUsedLocations().size() == 0)
			validSpaces.add(new BoardLocation(0,0));
		for (BoardLocation bl : board.getUsedLocations())
		{
			// add all empty adjacent spaces
			validSpaces.addAll(getEmptyAdjacent(bl));
		}
		return validSpaces;
	}

	private HashMap<Colour,Integer> initScore(ArrayList<Colour> ac) {
		HashMap<Colour,Integer> spaceScore = new HashMap<Colour,Integer>();
		for (Colour cl: ac)
			spaceScore.put(cl,new Integer(0));
		return spaceScore;
	}
	public HashMap<Colour,Integer> getScore(ArrayList<Colour> ac) 
	{
		HashMap<Colour,Integer> totalScore = initScore(ac);
		for (BoardLocation bl : board.getUsedLocations())
		{
			HashMap<Colour,Integer> spaceScore = initScore(ac);
			for (Card c : getAdjacentCards(bl))
				spaceScore.put(c.getColour(),new Integer(spaceScore.get(c.getColour()) + 1));	

			for (Colour cl: ac)
				if (spaceScore.get(cl) > 1 ) 
					totalScore.put(cl,new Integer(spaceScore.get(cl)-1+totalScore.get(cl)));
		}
		return totalScore;
	}
	public Colour getWinColour()
	{
		HashMap<Colour,Boolean> winMap = getWin(Colour.allColours());
		for (Colour cl: winMap.keySet())
			if (winMap.get(cl))
				return cl;
		return null;
	}	
	public HashMap<Colour,Boolean> getWin(ArrayList<Colour> ac) 
	{
		HashMap<Colour,Boolean> totalScore = new HashMap<Colour,Boolean>();
		for (Colour cl: Colour.allColours())
			totalScore.put(cl,false);
		for (BoardLocation bl : board.getUsedLocations())
		{
			HashMap<Colour,Integer> spaceScore = initScore(ac);
			for (Card c : getAdjacentCards(bl))
				spaceScore.put(c.getColour(),new Integer(spaceScore.get(c.getColour()) + 1));	

			for (Colour cl: ac)
				if (spaceScore.get(cl) == 4 ) 
					totalScore.put(cl,true);
		}
		return totalScore;
	}

	public Card getNorthCard(BoardLocation bl) { return board.get(bl.getNorth()); }
	public Card getEastCard(BoardLocation bl) { return board.get(bl.getEast()); }
	public Card getSouthCard(BoardLocation bl) { return board.get(bl.getSouth()); }
	public Card getWestCard(BoardLocation bl) { return board.get(bl.getWest()); }

	public BoardLocation getMin() { return board.getMin(); }
	public BoardLocation getMax() { return board.getMax(); }

	public String toString() {
		return board.toString();
	}

	// this seems to be used a bit
	public ArrayList<CardLocation> validMoves (Deck d)
	{
		ArrayList<CardLocation> moves = new ArrayList<CardLocation>();
		for (BoardLocation bl: this.getValidSpace())
			for (Card cd: d.getArray())
			{
				for (Card cdr: cd.getAllRotations())
					if (this.isValid(bl,cdr))
						moves.add(new CardLocation(cdr,bl));		
			}
		return moves;
	}

}
