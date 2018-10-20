import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class UIGuiBoardPanel extends JPanel implements ActionListener
{
    Board board;
    HashMap<String,BufferedImage> cardImages;
    Card highLightCard;
    private static final int tileSize=64;

    UIGuiBoardPanel(HashMap<String,BufferedImage> ci) 
    {
        super();
        cardImages=ci;
        highLightCard=null;
    }

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

				// make configurable
				int locx = tileSize * x + centreX;
				int locy = tileSize * y + centreY;
                Card c = board.getCard(x,y);
                if (c != null) {
                       //     System.out.println("draw card: " + c.imageName() + " @ " +x + ","+y);

                BufferedImage ci = cardImages.get(c.imageName());

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
               // canvas.setStroke(highLightStroke);
                canvas.setPaint(Color.red);
                canvas.drawRect (locx, locy, tileSize, tileSize);  
            }
        }

    }

    public void actionPerformed(ActionEvent e) { this.repaint(); } 

}
