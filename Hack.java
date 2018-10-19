import java.util.HashMap;
import java.util.ArrayList;
public class Hack implements Runnable {
	private String[] arguments;
	HashMap<Colour,UserInterface> playersInterface;
	HashMap<Colour,Deck> playersDeck;

    public Hack(String[] a) { 
		arguments = a;
	}

    public static void main(String[] args) {
		// put arguments into hack class
        Hack hp = new Hack(args);
        javax.swing.SwingUtilities.invokeLater(hp);
    }

    public void run() {
		// depending on how this is invoked 
		if (arguments.length==0)
		{
			// 1) setup invoke Gui 
			ArgumentsGui g = new ArgumentsGui(this);
			g.show();
		} else {
			// then create UIs from arguments
			start(UIFactory.getPlayers(arguments));
		}
    }

	private static <T> ArrayList<T> rotate(ArrayList<T> aL, int shift)
	{
		if (aL.size() == 0)
			return aL;

		T element = null;
		for(int i = 0; i < shift; i++)
		{
			// remove last element, add it to front of the ArrayList
			element = aL.remove( aL.size() - 1 );
			aL.add(0, element);
		}

		return aL;
	}

	public int countDeck () {

		int total= 0;
		for (Deck dk: playersDeck.values())
			total += dk.size();
		return total;
	}

	public void start(HashMap<Colour,UserInterface> p)
	{
		playersInterface = p;

		Board b = new Board();
		playersDeck = new HashMap<Colour,Deck>();
		ArrayList<Colour> colourOrder;

		for (Colour thisColour: playersInterface.keySet())
		{
			// determine colour
			UserInterface ui = playersInterface.get(thisColour);
			Deck thisDeck = new Deck(thisColour);
			thisDeck.shuffle();
			playersDeck.put(thisColour,thisDeck);
			ui.updateBoard(b);
			ui.updateDeck(thisDeck);
		}
		
		// determine start player
		int startPlayer=0;

		int startLocation = playersDeck.get(0).startPosition();

		for (int i=0; i<playersDeck.size(); i++)
		{
			Deck td = playersDeck.get(i);
			int thisStart  = td.startPosition();
			if (thisStart < startLocation)
			{
				startLocation = thisStart;
				startPlayer=i;
			}
		}

		// roll array until start player
		colourOrder = rotate(new ArrayList<Colour>(playersInterface.keySet()),startPlayer);
		
		Colour winner = null;
		while (countDeck()>0 && winner == null) // not win, not out of cards
		{
			for (Colour thisColour: colourOrder)
			{
				UserInterface currentPlayer = playersInterface.get(thisColour);
				Deck currentDeck = playersDeck.get(thisColour);
				currentPlayer.updateBoard(b);
				currentPlayer.updateDeck(currentDeck);
				CardLocation cl = currentPlayer.requestMove(); 
				// wait for response...  implement blocking inside interface
				b.play(cl);
				currentDeck.removeCard(cl.card);
				currentPlayer.updateDeck(currentDeck);
				
				winner = b.getWinColour();
				HashMap<Colour,Integer> score = b.getScore(colourOrder);
				for (Colour updateColour: colourOrder)
				{
					UserInterface updateInterface  = playersInterface.get(updateColour);
					updateInterface.updateBoard(b);
					updateInterface.updateScores(score);
					// show winner
					if (winner != null)
						updateInterface.showWinner(winner);
				}
				if (winner != null)
					break;
			}
		
		}
		// do something at the end
	}
}




