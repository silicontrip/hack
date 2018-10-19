public class UICli extends UserInterface {

	private Deck deck;
	private Board board;

	public UICli() { ; }

	//public static String name() { return "random"; }

	public void updateDeck (Deck d) { deck = d; }
	public void updateBoard (Board b) { board = b; }

}
