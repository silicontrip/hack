import java.io.*;
import java.util.*;

import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;

import java.net.URL;
import javax.swing.JScrollPane;

public class UIGui extends UserInterface {

	JFrame frame;
	JPanel messagePanel;
	UIGuiBoardPanel boardPanel;
	UIGuiDeckPanel deckPanel;
	JScrollPane boardScroll;
	JScrollPane deckScroll;
	JPanel main;

	Boolean waitCard = true;

	JLabel messageLabel;

	HashMap<String,BufferedImage> cardImage;

	private Deck deck;
	private Board board;
	private Colour player=null;

	// Tautology class 
	// maybe should make it paladromic... UIGiu
	public UIGui()  { 
		cardImage = null;
	}

	@Override
	public void show() throws IOException {

		initAllCardKeys();  // create hashmap of card images

		frame = new JFrame();
		frame.setTitle("Hack");

		messagePanel = new JPanel();
		messagePanel.setPreferredSize(new Dimension(1200, 24));

		messageLabel = new JLabel();
		messageLabel.setText("Status:");
		messagePanel.add(messageLabel);
		messagePanel.setBackground(Color.white);
	
		boardPanel = new UIGuiBoardPanel(cardImage);
		//boardPanel.setPreferredSize(new Dimension(1200, 704));
		boardPanel.setBackground(Color.black);

		 boardScroll = new JScrollPane(boardPanel);
		 boardScroll.setPreferredSize(new Dimension(1200, 640));

        boardScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        boardScroll.setVerticalScrollBarPolicy(JScrollPane. VERTICAL_SCROLLBAR_ALWAYS);
       // boardScroll.setBounds(0, 0, 2048, 2048);
		//boardScroll.getVerticalScrollBar().setValue(300);

		deckPanel = new UIGuiDeckPanel(cardImage,boardPanel);
		//deckPanel.setPreferredSize(new Dimension(1200, 128));
		deckPanel.setBackground(Color.gray);

		deckScroll = new JScrollPane(deckPanel);
        deckScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        deckScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        //deckScroll.setBounds(0, 0, 1200, 136);

		main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		main.add(boardScroll);
		main.add(messagePanel);
		main.add(deckScroll);

		frame.getContentPane().add(main,BorderLayout.CENTER);
		frame.setSize(1280,900);
	}

	private void initAllCardKeys() throws IOException
	{
		if (cardImage == null)
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
	}

	private BufferedImage getImage(String imageName) throws IOException {        
        java.net.URL imageURL = Hack.class.getResource("Cards/" + imageName);
        if (imageURL != null) 
			return ImageIO.read(imageURL);
        
		System.out.println("Card Image "+ imageName + ": null");
        return null;
    }

	public void updateDeck (Deck d) { 
		deck = d; 
		if (player==null)
		    player = deck.getCard(0).getColour();

		deckPanel.setDeck(d);
	}


	public void updateBoard (Board b) { 
		board = b;
		boardPanel.setBoard(b);
		frame.repaint() ;
	}
	private void drawBoard()
	{
		frame.setVisible(true);
		boardScroll.getVerticalScrollBar().setValue(700);  // trial and error value

		frame.repaint() ;
	}

	public CardLocation requestMove()
	{
        drawBoard();
		ArrayList<CardLocation> mv = board.validMoves(deck);

		deckPanel.validCards(mv);
		boardPanel.waiting();

		while (boardPanel.waitMove()) { 
			try {
				Thread.sleep(250); 
			} catch (InterruptedException ie) { ; }
		}
		return boardPanel.getMove();

	}

    public void updateScores(HashMap<Colour,Integer> s) {
		StringBuilder sc = new StringBuilder();
        for (Colour cl: s.keySet())
			sc.append(""+cl+": " + s.get(cl) + " ");

		messageLabel.setText(sc.toString());
		messagePanel.repaint();
    }

	public void showWinner (Colour w){ 
		messageLabel.setText("Winner: " + w);
		deckPanel.disableMouse();
		boardPanel.disableMouse();
		messagePanel.repaint();
	}

}
