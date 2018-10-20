import java.io.*;
import java.util.*;

import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.net.URL;


public class UIGui extends UserInterface {

	JFrame frame;
	JPanel messagePanel;
	JPanel boardPanel;
	JPanel deckPanel;

	Jlabel messageLabel;

	HashMap<String,Image> cardImage;

	private Deck deck;
	private Board board;
	private Colour player=null;

	// Tautology class 
	// maybe should make it paladromic... UIGiu
	public UIGui() { 
	

		allCardKeys();

		frame = new JFrame();
		frame.setTitle("Hack");

		messagePanel = new JPanel();
		messageLabel = new JLabel();
	
		boardPanel = new JPanel();
		deckPanel = new JPanel();

		
	
	}

	ArrayList<String> allCardKeys()
	{
		ArrayList<String> cardKeys = new ArrayList<String>();
		Colour c = Colour.allColours().get(0);
		Deck d = new Deck(c);

		for (Card cd: d.getArray())
		{
			for (Card cdr: cd.getAllRotations())
			{
				cardImage.put (cdr.imageName(),getImage(cdr.imageName()+".png"));
			}
		}
	}

	private Image getImage(String imageName) {
                
                java.net.URL imageURL = Hack.class.getResource("/" + imageName);
                if (imageURL != null) {
                        return new ImageIcon(imageURL).getImage();
                }
                return null;
        }



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
	}


	public void updateBoard (Board b) { 
		board = b;
	}
	private void drawBoard()
	{
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
	StringBuilder sc = new StringBuilder()
        for (Colour cl: s.keySet())
		sc.append(""+cl+": " + s.get(cl));

	messagePanel.setText(sc.toString());
	messagePanel.updateUI();
    }

	public void showWinner (Colour w){ 
		messagePanel.setText("Winner: " + w);
		messagePanel.updateUI();
	}

}
