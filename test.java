public class test {
	public static void main(String[] args) {
		Board b = new Board();

		Deck d  = new Deck(Colour.red);

		d.shuffle();


		System.out.println("start: " + d.startPosition());
		System.out.println(d);
		

	}
}
