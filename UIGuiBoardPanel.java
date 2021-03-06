import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class UIGuiBoardPanel extends JPanel implements MouseMotionListener, MouseListener
{
    Board board;
    HashMap<String,BufferedImage> cardImages;
    Card highLightCard;

    Boolean drawCard;
    int highLightX;
    int highLightY;

    CardLocation chosenMove;

    private static final int tileSize=96;

    UIGuiBoardPanel(HashMap<String,BufferedImage> ci) 
    {
        super();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        cardImages=ci;
        highLightCard=null;
        highLightX=0; 
        highLightY=0;

        drawCard=false;
        chosenMove = null;
    }

    public void enableMouse() { 
        this.addMouseListener(this);
        this.addMouseMotionListener(this);   
    }

    public void disableMouse() { 
        this.removeMouseListener(this);
        this.removeMouseMotionListener(this);   
        drawCard=false;
        chosenMove = null;
        highLightCard=null;
    }
    public void waiting () { chosenMove = null; highLightCard=null; }
    public Boolean waitMove() { return chosenMove == null; }
    public CardLocation getMove() { return chosenMove; }
    public void setBoard(Board b) { board = b; }
    public void setHighlightCard(Card c) { highLightCard = c; }

    @Override
    public Dimension getPreferredSize ()
    {
    	BoardLocation min = board.getMin();
        BoardLocation max = board.getMax();
        int dx = (max.getX() + 1) * tileSize;
        int dy = (max.getY() + 1) * tileSize; 

        // display hack to get the scrollbars to appear.
        if (dy < 2048) dy = 2048;
        if (dx < 1024) dx = 1024;

        return new Dimension(dx,dy);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Composite card_greyed = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 0.5f );
        Composite card_opaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1.0f );

	    BoardLocation min = board.getMin();
        BoardLocation max = board.getMax();

		// TODO:  get this from the boardPanel itself
        Dimension d = this.getSize();
		int centreX = (int) d.getWidth() / 2;
		int centreY = (int) d.getHeight() / 2;
        Graphics2D canvas = (Graphics2D)g;

        for (int y=min.getY(); y<=max.getY(); y++)
        {
            for (int x=min.getX(); x<= max.getX(); x++)
            {
                canvas.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

				int locx = tileSize * x + centreX;
				int locy = tileSize * y + centreY;
                Card c = board.getCard(x,y);
                if (c != null) {
                    BufferedImage ci = cardImages.get(c.imageName());
                    canvas.setComposite(card_opaque);
                    canvas.drawImage(ci,locx,locy,tileSize,tileSize,null);
                }
            }
        }
        if (highLightCard != null)
        {
            ArrayList<BoardLocation> mv = board.validMoves(highLightCard);
            for (BoardLocation bl: mv)
            {
                int locx = tileSize * bl.getX() + centreX;
				int locy = tileSize * bl.getY() + centreY;
                canvas.setPaint(Color.red);
                canvas.drawRect (locx, locy, tileSize, tileSize);  
            }
            if (drawCard) {
                int locx = tileSize * highLightX + centreX;
				int locy = tileSize * highLightY + centreY;
                BufferedImage ci = cardImages.get(highLightCard.imageName());
                canvas.setComposite(card_greyed);
                canvas.drawImage(ci,locx,locy,tileSize,tileSize,null);
            }
        }
    }

    public void mouseEntered(MouseEvent e) { drawCard=true; }
    public void mouseExited(MouseEvent e) { drawCard=false; }
    public void mouseDragged(MouseEvent e) { ; }  // move board centre
    public void mouseReleased(MouseEvent e) { ; }
    public void mousePressed(MouseEvent e) { ; }

    public void mouseMoved(MouseEvent e) {

        Dimension d = this.getSize();
        int newX = (int)Math.floor((e.getX()-d.getWidth() / 2.0) * 1.0 / tileSize);
        int newY = (int)Math.floor((e.getY()-d.getHeight() / 2.0) * 1.0 / tileSize);

        if (newX!=highLightX || newY!=highLightY)
        {
            highLightX = newX;
            highLightY = newY;
            this.repaint();
        }
    }

    public void mouseClicked(MouseEvent e) { 
        BoardLocation bl = new BoardLocation(highLightX,highLightY);
        chosenMove = null;

        if (board.isValid(bl, highLightCard))
            chosenMove = new CardLocation(highLightCard,bl);

	}
}
