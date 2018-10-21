import java.io.*;
import java.util.*;

import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;

import java.net.URL;


public class UIGui extends UserInterface {

	JFrame frame;
	JPanel messagePanel;
	UIGuiBoardPanel boardPanel;
	UIGuiDeckPanel deckPanel;
	JPanel main;

	Boolean waitCard = true;

	JLabel messageLabel;

	HashMap<String,BufferedImage> cardImage;

	private Deck deck;
	private Board board;
	private Colour player=null;

	// Tautology class 
	// maybe should make it paladromic... UIGiu
	public UIGui() throws IOException { 
	
		initAllCardKeys();  // create hashmap of card images

	}

	public void show() {

		frame = new JFrame();
		frame.setTitle("Hack");

		messagePanel = new JPanel();
		messagePanel.setPreferredSize(new Dimension(1200, 16));

		messageLabel = new JLabel();
		messageLabel.setText("Status:");
		messagePanel.add(messageLabel);
	
		boardPanel = new UIGuiBoardPanel(cardImage);
		//boardPanel.setPreferredSize(new Dimension(1200, 704));
		//boardPanel.setBackground(Color.black);

		deckPanel = new UIGuiDeckPanel(cardImage,boardPanel);
		//deckPanel.setPreferredSize(new Dimension(1200, 128));
		//deckPanel.setBackground(Color.gray);

		main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		main.add(boardPanel);
		main.add(messagePanel);
		main.add(deckPanel);


       // JButton go = new JButton("GO.");
		// main.add(go);


		frame.getContentPane().add(main,BorderLayout.CENTER);
        frame.setSize(1280,900);
	}

	private void initAllCardKeys() throws IOException
	{
		cardImage = new HashMap<String,BufferedImage>();

		for (Colour c: Colour.allColours()) 
		{
			Deck d = new Deck(c);

			for (Card cd: d.getArray())
				for (Card cdr: cd.getAllRotations())
					cardImage.put (cdr.imageName(),getImage(cdr.imageName()+".png"));
				
		}
	}

	private BufferedImage getImage(String imageName) throws IOException {        
        java.net.URL imageURL = Hack.class.getResource("Cards/" + imageName);
        if (imageURL != null) {

			BufferedImage img = ImageIO.read(imageURL);

			//ImageIcon img = new ImageIcon(imageURL);

            return img;
        }
		System.out.println("Card Image "+ imageName + ": null");
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

		deckPanel.setDeck(d);
	}


	public void updateBoard (Board b) { 
		board = b;
		boardPanel.setBoard(b);
	}
	private void drawBoard()
	{
		frame.setVisible(true);
		frame.repaint() ;
	}

	public CardLocation requestMove()
	{
        drawBoard();
		ArrayList<CardLocation> mv = board.validMoves(deck);

		deckPanel.validCards(mv);

		/*
		System.out.println("Valid Moves:");
		int chosenMove = 0;

		for (CardLocation cl: mv)
			{
				Board nb = board.playNew(cl.location, cl.card);
				HashMap<Colour,Integer> scores = nb.getScore(Colour.allColours());
				
				System.out.println ("" + chosenMove+": " + cl.location + ": " + cl.card+ " S:"+ scores.get(player) );
                chosenMove++;
		}
*/
		boardPanel.waiting();
		while (boardPanel.waitMove()) { 
		// System.out.println("waiting for move...");
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
		}
		return boardPanel.getMove();

	}
    public void updateScores(HashMap<Colour,Integer> s) {
		StringBuilder sc = new StringBuilder();
        for (Colour cl: s.keySet())
			sc.append(""+cl+": " + s.get(cl));

		messageLabel.setText(sc.toString());
		messagePanel.repaint();
    }

	public void showWinner (Colour w){ 
		messageLabel.setText("Winner: " + w);
		messagePanel.repaint();
	}

}
