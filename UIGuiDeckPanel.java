import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.HashMap;

public class UIGuiDeckPanel extends JPanel implements ActionListener
{
    Deck deck;
    HashMap<String,BufferedImage> cardImages;
    UIGuiDeckPanel(HashMap<String,BufferedImage> ci) {
        super();
        cardImages=ci;
        }

    private static final int tileSize=96;

    public void setDeck(Deck d) { 
                System.out.println("UIGuiDeckPanel::setDeck ");

        deck = d;
        }

    @Override
    public Dimension getPreferredSize() {
        int x = deck.size() * tileSize;
        System.out.println("UIGuiDeckPanel::getPreferredSize "+ x +"," +tileSize);

        return new Dimension(x,tileSize);
    }

    @Override
    public void paintComponent(Graphics g) {
        System.out.println("UIGuiDeckPanel::paintComponent");
        super.paintComponent(g);

        Graphics2D canvas = (Graphics2D)g;
        int pos = 0;
        for (Card c: deck.getArray())
        {
            System.out.println("draw card: " + c + " @ " + pos);
            BufferedImage ci = cardImages.get(c.imageName());

            canvas.drawImage(ci,pos*tileSize,0,tileSize,tileSize,null);
            pos++;
        }
  
    }
    public void actionPerformed(ActionEvent e) { this.repaint(); } 

}
