import java.util.HashMap;
import java.util.ArrayList;
import java.util.NoSuchElementException;


public class HackTrainer  {
	private String[] arguments;
	HashMap<Colour,UserInterface> playersInterface;
	HashMap<Colour,Deck> playersDeck;

    public HackTrainer(String[] a) { 
		arguments = a;
	}

    public static void main(String[] args) {
		// put arguments into hack class
        HackTrainer hp = new HackTrainer(args);
		hp.run();
    }

    public void run() {
			// no gui invocation
			// then create UIs from arguments
			try {
				// loop this count times
				for (int i=0; i<1000;i++)
				{
					System.out.println("play count: "+i);
					this.setPlayers(UIFactory.getPlayers(arguments));
					start();
				}
			} catch (NoSuchElementException e) {
					System.out.println("Too Many Arguments");
			} catch (RuntimeException e) {
					System.out.println("Error: " + e.getMessage());
					e.printStackTrace();
			} catch (Exception e) {
					e.printStackTrace();
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

	public void setPlayers(HashMap<Colour,UserInterface> p) { playersInterface = p; }

	public void start() throws Exception
	{
		// System.out.println("Players: " + playersInterface.size());

		Board b = new Board();
		playersDeck = new HashMap<Colour,Deck>();
		ArrayList<Colour> colourOrder = new ArrayList<Colour>();

		for (Colour thisColour: playersInterface.keySet())
		{
			colourOrder.add(thisColour);
			// determine colour
			UserInterface ui = playersInterface.get(thisColour);
			Deck thisDeck = new Deck(thisColour);
			thisDeck.shuffle();
			playersDeck.put(thisColour,thisDeck);

			System.out.println("" + thisColour + "/" + ui.getClass().getName());

			ui.show();
			ui.updateBoard(b);
			ui.updateDeck(thisDeck);
		}
		
		// determine start player
		int startPlayer=0;
		// int startLocation = 999;  // couldn't do it smarter
		int startLocation = playersDeck.get(colourOrder.get(0)).startPosition(); // yes I can.

		for (int i=1; i<colourOrder.size(); i++)
		//for (Deck td: playerDeck.keySet())
		{
			Deck td = playersDeck.get(colourOrder.get(i));
			int thisStart  = td.startPosition();
			if (thisStart < startLocation)
			{
				startLocation = thisStart;
				startPlayer=i;
			}
		}

	System.out.println("Start Player: " + colourOrder.get(startPlayer));

		// roll array until start player
		colourOrder = rotate(new ArrayList<Colour>(playersInterface.keySet()),startPlayer);
		
		Colour winner = null;
		HashMap<Colour,Integer> score=null;
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
				score = b.getScore(colourOrder);
				for (Colour updateColour: colourOrder)
				{
					UserInterface updateInterface  = playersInterface.get(updateColour);
					updateInterface.updateBoard(b);
					updateInterface.updateScores(score);
				//	System.out.println("Score: " + score);
					// show winner
					if (winner != null) {
						updateInterface.showWinner(winner);
					}
				}
				if (winner != null)
					break;
			}
			// System.out.println("round");
		}
		// do something at the end
		// System.out.println("game over");
		System.out.println("Hack Master: " + winner);
		System.out.println("Score: " + score);
		if (winner==null)
		{
			score = b.getScore(colourOrder);
			Colour pointsWinner=null;
			int highscore = -1;
			// notify winner by points
			for (Colour findColour: colourOrder)
			{
				if (score.get(findColour) > highscore)
				{
					highscore = score.get(findColour);
					pointsWinner = new Colour (findColour);
				}
			}
			// need a notify all method...
			for (Colour findColour: colourOrder)
			{
				UserInterface updateInterface  = playersInterface.get(findColour);
				updateInterface.showWinner(pointsWinner);
			}
		}
	}
}




