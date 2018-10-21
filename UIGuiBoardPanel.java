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
        highLightX=0; // stupid sentinel value
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

    public void setBoard(Board b) { 
        board = b;
    }

    public void setHighlightCard(Card c) { highLightCard = c; }

    @Override
    public Dimension getPreferredSize ()
    {

    	BoardLocation min = board.getMin();
        BoardLocation max = board.getMax();
        int dx = (max.getX() - min.getX() + 1) * tileSize;
        int dy = (max.getY() - min.getY() + 1) * tileSize;

       // System.out.println("UIGuiBoardPanel::getPreferredSize "+ dx +"," +dy);

        return new Dimension(dx,dy);

    }

    @Override
    public void paintComponent(Graphics g) {
           // System.out.println("UIGuiBoardPanel::paintComponent");

        super.paintComponent(g);

        Composite card_greyed = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 0.5f );
        Composite card_opaque = AlphaComposite.getInstance(AlphaComposite.SRC_OVER , 1.0f );

	    BoardLocation min = board.getMin();
        BoardLocation max = board.getMax();
       // System.out.println ("min: " + min + " max: "+max);

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
                       //     System.out.println("draw card: " + c.imageName() + " @ " +x + ","+y);

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
               // System.out.println("valid: " + bl);
                int locx = tileSize * bl.getX() + centreX;
				int locy = tileSize * bl.getY() + centreY;
               // canvas.setStroke(highLightStroke);
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

    // public void actionPerformed(ActionEvent e) { this.repaint(); } 
    public void mouseEntered(MouseEvent e) { 
        drawCard=true; 
       // System.out.println("Mouse Entered");
    }
    public void mouseExited(MouseEvent e) { 
        drawCard=false; 
      //  System.out.println("Mouse exit");

    }
    public void mouseDragged(MouseEvent e) { ; }
    public void mouseReleased(MouseEvent e) { ; }
    public void mousePressed(MouseEvent e) { ; }

    public void mouseMoved(MouseEvent e) {

        Dimension d = this.getSize();

        int newX = (int)Math.floor((e.getX()-d.getWidth() / 2.0) * 1.0 / tileSize);
        int newY = (int)Math.floor((e.getY()-d.getHeight() / 2.0) * 1.0 / tileSize);
/*
        int centreX = (int) d.getWidth() / 2;
		int centreY = (int) d.getHeight() / 2;

        int x = e.getX();
        int y = e.getY();

        double tileX = (x-centreX) * 1.0 / tileSize;
        double tileY = (y-centreY) * 1.0 / tileSize;

        int newX = (int)Math.floor(tileX);
        int newY = (int)Math.floor(tileY);

        System.out.println("" +x +"(" + (x-centreX) +"),"+y+ "("+ (y-centreY) + ") -> "+ tileX +","+tileY + " (" + newX + "," + newY +")");
*/

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
        System.out.println("mouseClicked: " + e);

        if (board.isValid(bl, highLightCard))
        {
         chosenMove = new CardLocation(highLightCard,bl);
        System.out.println("valid move");
        } else { System.out.println("not valid move"); }
	}
}
