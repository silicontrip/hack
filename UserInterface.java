interface UserInterface {
public abstract updateDeck(Deck d);
public abstract updateBoard(Board b);
public abstract requestMove(Callback c);
public abstract updateScores(HashMap<Colour,Integer> s);
public abstract showWinner (Colour w);
}
