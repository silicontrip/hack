import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

public class UIPattern extends UserInterface {

	private Deck deck;
	private Board board;
	private Colour player=null;

	private BoardLocation [][] crossArray;

	public UIPattern() { 
		crossArray = new BoardLocation[5][4];
		
		// centre
		crossArray[0][0] = new BoardLocation (-1,0);
		crossArray[0][1] = new BoardLocation (1,0);
		crossArray[0][2] = new BoardLocation (0,-1);
		crossArray[0][3] = new BoardLocation (0,1);
		// north
		crossArray[1][0] = new BoardLocation (0,-1);
		crossArray[1][1] = new BoardLocation (-1,-1);
		crossArray[1][2] = new BoardLocation (1,-1);
		crossArray[1][3] = new BoardLocation (0,-2);

		// east
		crossArray[2][0] = new BoardLocation (1,0);
		crossArray[2][1] = new BoardLocation (1,-1);
		crossArray[2][2] = new BoardLocation (1,1);
		crossArray[2][3] = new BoardLocation (2,0);

		// south
		crossArray[3][0] = new BoardLocation (0,1);
		crossArray[3][1] = new BoardLocation (-1,1);
		crossArray[3][2] = new BoardLocation (1,1);
		crossArray[3][3] = new BoardLocation (0,2);

		// west
		crossArray[4][0] = new BoardLocation (-1,0);
		crossArray[4][1] = new BoardLocation (-1,-1);
		crossArray[4][2] = new BoardLocation (-1,1);
		crossArray[4][3] = new BoardLocation (-2,0);
	
 
	}
	public void show() {;}


	public void updateDeck (Deck d) { 
		deck = d; 
		if (player==null)
			player = deck.getCard(0).getColour();
	}
	public void updateBoard (Board b) { board = b; }

	public void updateScores(HashMap<Colour,Integer> s) {; }
	public void showWinner (Colour w){;}

	private int cross (BoardLocation bl, Colour cl)
	{
		int crossScore = 0;
		// hunt north
		int x = bl.getX();
		int y = bl.getY();
		
		for (int i=0;i<5;i++)  // cross Array dimension 1
		{
			int tempScore = 0;
			for (int j=0;j<4;j++)  // cross Array dimension 1
			{
		//		System.out.println("ij: " + i + " " + j);
				BoardLocation newLoc = bl.add(crossArray[i][j]);
			// evil evil conditional logic.
			// arrays are made that the first element of the second dimension can be any colour, unless it's the first element of the first dimension
				if (i!=0 && j==0)  
				{
					if (!board.isEmpty(newLoc))
					{
						//System.out.println("Centre isn't empty: " + newLoc);
						tempScore ++;
					}
				}
				else
				{
					if (!board.isEmpty(newLoc))
					{
						if (board.getCard(newLoc).getColour().equals(cl))
						{
						//System.out.println("Cross has our card: "+ newLoc);
							tempScore ++;
						}
						else 
						{
						//System.out.println("Cross has other player: "+newLoc);
							tempScore = 0;
							break;
						}
					}
				}
			}
			//System.out.println(" " + tempScore);
			if (tempScore > crossScore)
				crossScore = tempScore;

		}
		//System.out.println("");
		return crossScore;

	}

	public CardLocation requestMove()
	{
		ArrayList<CardLocation> mv = board.validMoves(deck);

		int score=0;
		int move=0;
		int chosenMove=0;
		for (CardLocation cl: mv)
		{
			Board nb = board.playNew(cl.location, cl.card);
			HashMap<Colour,Integer> scores = nb.getScore(Colour.allColours());
			int crossScore = cross(cl.location,player); // currently look for our colour
		// find the move that gives us most score, but will also help our Hack Master Goal.
			int moveScore = (scores.get(player)+1) * (crossScore+1);
			//System.out.println("" + cl.location +"," + cl.card + ": " + scores.get(player) + "/" + crossScore + "=" + moveScore);
			if(moveScore >score)
			{
				//System.out.println("*");
				score = moveScore;
				chosenMove = move;
			}
		    move++;
		}
		//System.out.println("---");

		return mv.get(chosenMove);
	}

}
