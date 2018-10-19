import java.io.*;
import java.util.*;

public class UICli extends UserInterface {

	private Deck deck;
	private Board board;
    private Colour player=null;

	public UICli() { ; }

private String readln() {
	String ln = "";
	
	try {
		BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
		ln = is.readLine();
		// if (ln.length() == 0 ) return null;
	} catch (IOException e) {
			System.out.println("IOException: " + e);
	}
	return ln;
}

	//public static String name() { return "random"; }

	public void updateDeck (Deck d) { 
        deck = d; 
        if (player==null)
            player = deck.getCard(0).getColour();
        System.out.println("DECK: " + d);
    }

    private String cardFormatter(Card c, int r)
    {
        if (c==null)
            return "   ";
        if (r==0)
            return " " + c.getNorth().toString() + " ";
        if (r==1) 
            return c.getWest().toString() + c.getColour().toString() + c.getEast().toString();
        if (r==2)
            return " " + c.getSouth().toString() + " ";

            return "";
    }

	public void updateBoard (Board b) { 
        board = b;
    }
    private void drawBoard()
    {
        // much improvement required
        BoardLocation min = board.getMin();
        BoardLocation max = board.getMax();
       // System.out.println ("min: " + min + " max: "+max);
        for (int y=min.getY(); y<=max.getY(); y++)
        {
            StringBuilder[] rows = new StringBuilder[3];
            rows[0] = new StringBuilder("");
            rows[1] = new StringBuilder("");
            rows[2] = new StringBuilder("");

            for (int x=min.getX(); x<= max.getX(); x++)
            {
               // System.out.println(board.getCard(x,y));
                rows[0].append(cardFormatter(board.getCard(x,y),0));
                rows[1].append(cardFormatter(board.getCard(x,y),1));
                rows[2].append(cardFormatter(board.getCard(x,y),2));
            }
            System.out.println(rows[0]);
            System.out.println(rows[1]);
            System.out.println(rows[2]);
            
        }
        //System.out.println(b); 
    }

public CardLocation requestMove()
	{
        drawBoard();
		ArrayList<CardLocation> mv = board.validMoves(deck);

		System.out.println("Valid Moves:");
		int chosenMove = 0;

		for (CardLocation cl: mv)
			{
				Board nb = board.playNew(cl.location, cl.card);
				HashMap<Colour,Integer> scores = nb.getScore(Colour.allColours());
				
				System.out.println ("" + chosenMove+": " + cl.location + ": " + cl.card+ " S:"+ scores.get(player) );
                chosenMove++;
		}

        while (true)
        {
            try {
                String rd = readln();
                chosenMove = Integer.parseInt(rd);
                if (chosenMove >= 0 && chosenMove <= mv.size())
            	    return mv.get(chosenMove);
            } catch (NumberFormatException e) {
                ;
            }
        }

	}
    public void updateScores(HashMap<Colour,Integer> s) {
        System.out.println("-- SCORE --");
        for (Colour cl: s.keySet())
			System.out.println("" + cl + ": " + s.get(cl));
    }

	public void showWinner (Colour w){ 
        System.out.println("Winner: " + w);
    }

}
