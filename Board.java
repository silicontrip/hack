
public class Board {
	//DiamondMatrix<Card> bd;
	HashBoard board;
	//HashMap<BoardLocation,Card> board;

	Board() { 
		//bd = new DiamondMatrix<Card>(); 
		board = new HashBoard();
	}

	public Boolean isEmpty (int x, int y) {
		return board.isEmpty(x,y);
		//return board.get(new BoardLocation(x,y)) == null;
	}

// much game logic here
	public Boolean isValid(int x, int y, Card c) {
		// If the board is empty and a start card is played
		if (board.size() == 0 && x==0 && y==0)
			return c.isStart();

		// if placing a card on another card
		if (!isEmpty(x,y))
			return false;
		
		// if placing a card next to no other card
		if ( getNorthCard(x,y) == null && getEastCard(x,y) == null && getSouthCard(x,y) == null && getWestCard(x,y) == null )
			return false;

		// if all 4 compass points match (matching against an empty square is valid)
		if ( c.matchNorth(getNorthCard(x,y)) && c.matchEast(getEastCard(x,y)) && c.matchSouth(getSouthCard(x,y)) && c.matchWest(getWestCard(x,y)) ) 
			return true;

		return false;
		
	}

	public void play(int x, int y, Card c) {
			if (isValid(x,y,c)) {
				board.set(x,y,c);
			}
	}

	public Set<BoardLocation> getValidSpace()
	{
		for (BoardLocation b : board.)
	}

	public Card getNorthCard(int x, int y) { return board.get(x,y-1); }
	public Card getEastCard(int x, int y) { return board.get(x+1,y); }
	public Card getSouthCard(int x, int y) { return board.get(x,y+1); }
	public Card getWestCard(int x, int y) { return board.get(x-1,y); }

	public String toString() {
		return board.toString();
	}

}
