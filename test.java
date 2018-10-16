public class test {
	public static void main(String[] args) {


		Board b = new Board();

		Deck d  = new Deck(Colour.red);

		d.shuffle();

		int startCardIndex = d.startPosition();

		System.out.println("start: " + startCardIndex);
		System.out.println(d);
		
		Card c = d.getCard(startCardIndex);
		System.out.println ("Start: " + c);

		b.play(0,0,c);

		d.removeCard(startCardIndex);
		System.out.println(d);
		
		System.out.println("board:" + b);

		for (BoardLocation bl: b.getValidSpace())
		for (Card cd: d.getArray())
		{
			Boolean valid = b.isValid(bl,cd);
			System.out.println ("" + bl +" " + valid + ": " + cd );
		}

	}
}
