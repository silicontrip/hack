import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.HashMap;

public class UIGuiBoardPanel extends JPanel implements ActionListener
{
    Board board;
    HashMap<String,BufferedImage> cardImages;
    UIGuiBoardPanel(HashMap<String,BufferedImage> ci) 
    {
        super();
        cardImages=ci;
        }

    public void setBoard(Board b) { 
                System.out.println("UIGuiBoardPanel::setBoard ");

        board = b;}

    @Override
    public Dimension getPreferredSize ()
    {

    	BoardLocation min = board.getMin();
        BoardLocation max = board.getMax();
        int dx = (max.getX() - min.getX() + 1) * 64;
        int dy = (max.getY() - min.getY() + 1) * 64;

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

        for (int y=min.getY(); y<=max.getY(); y++)
        {
        
            for (int x=min.getX(); x<= max.getX(); x++)
            {

                Graphics2D canvas = (Graphics2D)g;
                canvas.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

				// make configurable
				int locx = 64 * x + centreX;
				int locy = 64 * y + centreY;
                Card c = board.getCard(x,y);
                if (c != null) {
                       //     System.out.println("draw card: " + c.imageName() + " @ " +x + ","+y);

                BufferedImage ci = cardImages.get(c.imageName());

                canvas.drawImage(ci,locx,locy,64,64,null);
                }
            }
  
        }

    }

    public void actionPerformed(ActionEvent e) { this.repaint(); } 

}
