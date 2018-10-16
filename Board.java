import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;

public class Board {
	HashBoard board;

	Board() { 
		board = new HashBoard();
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

		return false;
		
	}

	//public void play(int x, int y, Card c) {
	public void play(BoardLocation bl, Card c) {
		if (isValid(bl,c)) {
			board.set(bl,c);
		}
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
		for (BoardLocation bl : board.getUsedLocations())
		{
			// add all empty adjacent spaces
			validSpaces.addAll(getEmptyAdjacent(bl));
		}
		return validSpaces;
	}

	private HashMap<Colour,Integer> initScore() {
		HashMap<Colour,Integer> spaceScore = new HashMap<Colour,Integer>();
		// iterate ENUM anyone?
		for (Colour cl: Colour.allColours())
		{
			spaceScore.put(cl,new Integer(0));
			//System.out.println ("" + cl + ": " + spaceScore.get(cl));
		}
		return spaceScore;
	}
	public HashMap<Colour,Integer> getScore() 
	{
		HashMap<Colour,Integer> totalScore = initScore();
		for (BoardLocation bl : board.getUsedLocations())
		{
			HashMap<Colour,Integer> spaceScore = initScore();
			for (Card c : getAdjacentCards(bl))
			{
			//	Colour cl = c.getColour();
			//	System.out.println ("" + cl + ": " + spaceScore.get(cl));
				spaceScore.put(c.getColour(),new Integer(spaceScore.get(c.getColour()) + 1));	
			}

			for (Colour cl: Colour.allColours())
			{
			//	System.out.println ("" + cl + ": " + spaceScore.get(cl));
				if (spaceScore.get(cl) > 1 ) 
					totalScore.put(cl,new Integer(spaceScore.get(cl)-1+totalScore.get(cl)));
			}
		}
		return totalScore;
	}

	public Card getNorthCard(BoardLocation bl) { return board.get(bl.getNorth()); }
	public Card getEastCard(BoardLocation bl) { return board.get(bl.getEast()); }
	public Card getSouthCard(BoardLocation bl) { return board.get(bl.getSouth()); }
	public Card getWestCard(BoardLocation bl) { return board.get(bl.getWest()); }

	public String toString() {
		return board.toString();
	}

}
