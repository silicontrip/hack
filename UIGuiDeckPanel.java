import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class UIGuiDeckPanel extends JPanel implements  MouseMotionListener, MouseListener
{
    Deck deck;
    HashMap<String,BufferedImage> cardImages;
    private static final int tileSize=128;
    HashSet<Card> validCards; 
    private int highLight;

    private final Composite card_greyed;
    private final Composite card_opaque;
    private final BasicStroke highLightStroke;

    UIGuiBoardPanel boardPanel;

    UIGuiDeckPanel(HashMap<String,BufferedImage> ci,UIGuiBoardPanel bp) {
        super();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        cardImages=ci;
        boardPanel=bp; // because we highlight valid moves on the board as the mouse hovers over the cards
        highLight=-1;

        card_greyed = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 0.5f );
        card_opaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1.0f );
        highLightStroke = new BasicStroke(4.0f);

    }

    public void enableMouse() { 
        this.addMouseListener(this);
        this.addMouseMotionListener(this);   
    }

    public void disableMouse() { 
        this.removeMouseListener(this);
        this.removeMouseMotionListener(this);  
        highLight = -1; 
    }

    public void unHighLight () { highLight = -1; }
    public void setDeck(Deck d) { deck = d; }

    public void validCards(ArrayList<CardLocation> mv)
    {
        validCards = new HashSet<Card>();
        for(CardLocation cl: mv)
            validCards.add(cl.card);
    }

    @Override
    public Dimension getPreferredSize() { return new Dimension(deck.size() * tileSize,tileSize); }

    private Boolean validHighlight(int hl)
    {
        if (hl >= deck.size())
            return false;
        if (validCards.size() == 0)
            return true;
        return validCards.contains(deck.getCard(hl));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D canvas = (Graphics2D)g;
        canvas.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int pos = 0;
        for (Card c: deck.getArray())
        {
            if (validCards != null  && (validCards.size() ==  0 || validCards.contains(c)))
                canvas.setComposite(card_opaque);
            else 
                canvas.setComposite(card_greyed);
            
            BufferedImage ci = cardImages.get(c.imageName());

            canvas.drawImage(ci,pos*tileSize,0,tileSize,tileSize,null);
            pos++;
        }

        if (highLight>=0)
        {
                int x = highLight * tileSize;
                canvas.setStroke(highLightStroke);
                canvas.setPaint(Color.red);
                canvas.drawRect (x, 0, tileSize, tileSize);  

                // draw rotate buttons

        }
  
    }
    public void mouseEntered(MouseEvent e) { ; }
    public void mouseExited(MouseEvent e) { ; }
    public void mouseDragged(MouseEvent e) { ; }
    public void mouseReleased(MouseEvent e) { ; }
    public void mousePressed(MouseEvent e) { ; }

    public void mouseMoved(MouseEvent e) {
        // highlight card
        int newHighlight = e.getX() / tileSize;
        if (newHighlight != highLight )
        {
            if (validHighlight(newHighlight))
            {
                highLight = newHighlight;
                this.repaint();
                boardPanel.setHighlightCard(deck.getCard(highLight));
                boardPanel.repaint();
            }
        }
    }

    public void mouseClicked(MouseEvent e) { 
        // ... select card		
        // how to rotate
        Card rot =  deck.getCard(highLight);
        rot.rotateCW();
        boardPanel.setHighlightCard(rot);
        boardPanel.repaint();
        this.repaint();
	}
}
