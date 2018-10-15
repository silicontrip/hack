
public class Board {
	DiamondMatrix<Card> bd;

	Board() { 
		bd = new DiamondMatrix<Card>(); 
	}

	public Boolean isEmpty (int x, int y) {
		return bd.isEmpty(x,y);
	}

// much game logic here
	public Boolean isValid(int x, int y, Card c) {
		// If the board is empty and a start card is played
		if (bd.size() == 0 && x==0 && y==0)
			return c.isStart();

		// if placing a card on another card
		if (!isEmpty(x,y))
			return false;
		
		// if placing a card next to no other card
		if ( getNorth(x,y) == null && getEast(x,y) == null && getSouth(x,y) == null && getWest(x,y) == null )
			return false;

		// if all 4 compass points match (matching against an empty square is valid)
		if ( c.matchNorth(getNorth(x,y)) && c.matchEast(getEast(x,y)) && c.matchSouth(getSouth(x,y)) && c.matchWest(getWest(x,y)) ) 
			return true;

		return false;
		
	}

	public void play(int x, int y, Card c) {

			if (isValid(x,y,c)) {
				bd.set(x,y,c);
			}
	}

	public Card getNorth(int x, int y) { return bd.get(x,y-1); }
	public Card getEast(int x, int y) { return bd.get(x+1,y); }
	public Card getSouth(int x, int y) { return bd.get(x,y+1); }
	public Card getWest(int x, int y) { return bd.get(x-1,y); }

	public String toString() {
		return bd.toString();
	}

}
