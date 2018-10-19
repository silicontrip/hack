public class BoardLocation {
	private int x;
	private int y;

	public BoardLocation(int xx, int yy) {
		x = xx;
		y = yy;
	}

	public Boolean isOrigin () { return x==0 && y==0; }
	public BoardLocation getWest() { return new BoardLocation (x-1,y); }
	public BoardLocation getEast() { return new BoardLocation (x+1,y); }
	public BoardLocation getNorth() { return new BoardLocation (x,y-1); }
	public BoardLocation getSouth() { return new BoardLocation (x,y+1); }
	
	public int getX() { return x; }
	public int getY() { return y; }

	@Override
	public String toString() { return "" + x + ","+y; }

        @Override
        public boolean equals (Object o)
        {
                if (o == this) return true;
                if (!(o instanceof BoardLocation)) return false;
                BoardLocation l = (BoardLocation) o;
		return l.x==x && l.y==y;
        }

	@Override
	public int hashCode() { return x << 16 ^ y; }
}
