import java.util.HashMap;
interface UserInterface {
public abstract void updateDeck(Deck d);
public abstract void updateBoard(Board b);
public abstract CardLocation requestMove();
public abstract void updateScores(HashMap<Colour,Integer> s);
public abstract void showWinner (Colour w);
public static String name() { return ""; }
}
