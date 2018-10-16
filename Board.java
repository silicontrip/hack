import java.util.HashSet;

public class Board {
	//DiamondMatrix<Card> bd;
	HashBoard board;
	//HashMap<BoardLocation,Card> board;

	Board() { 
		//bd = new DiamondMatrix<Card>(); 
		board = new HashBoard();
	}

	//public Boolean isEmpty (int x, int y) { return board.isEmpty(x,y); } 
	public Boolean isEmpty (BoardLocation b) { return board.isEmpty(b); } 

	// much game logic here
//	public Boolean isValid(int x, int y, Card c) { return isValid(new BoardLocation(x,y),c);  }
	public Boolean isValid(BoardLocation bl, Card c) {
		// If the board is empty and a start card is played
		if (board.size() == 0 && bl.isOrigin())
			return c.isStart();

		// if placing a card on another card
		if (!isEmpty(bl))
			return false;
		
		// if placing a card next to no other card
		if ( isEmpty(bl.getNorth()) && isEmpty(bl.getEast()) && isEmpty(bl.getSouth()) && isEmpty(bl.getWest())) 
			return false;

		// if all 4 compass points match (matching against an empty square is valid)
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

	//public Card getNorthCard(int x, int y) { return board.get(x,y-1); }
	public Card getNorthCard(BoardLocation bl) { return board.get(bl.getNorth()); }
	//public Card getEastCard(int x, int y) { return board.get(x+1,y); }
	public Card getEastCard(BoardLocation bl) { return board.get(bl.getEast()); }
	//public Card getSouthCard(int x, int y) { return board.get(x,y+1); }
	public Card getSouthCard(BoardLocation bl) { return board.get(bl.getSouth()); }
	//public Card getWestCard(int x, int y) { return board.get(x-1,y); }
	public Card getWestCard(BoardLocation bl) { return board.get(bl.getWest()); }

	public String toString() {
		return board.toString();
	}

}
