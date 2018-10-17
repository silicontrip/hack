public class Hack implements Runnable {
	private String[] arguments;

        public Hack(String[] a) { 
		arguments = a;
	}

        public static void main(String[] args) {
		// put arguments into hack class
                Hack hp = new Hack(args);
                javax.swing.SwingUtilities.invokeLater(hp);
        }

        public void run() {
		// depending on how this is invoked 
		if (arguments.length==0)
			
			// 1) setup invoke Gui 
			
		// then create UIs from arguments
		
        }
}


// determine how many players
// determine player object types

// player ArrayList
// show deck
// show board

// determine first player
// rotate array to first player

// scores
// winner
// deck size

// while (alldecks>0 && !winner)
//{
//for (Player : PlayerArray)
// {
// Player.requestMove()
// isValid
// board.play(card)
// other Players: update board
// winner ? allPlaeyrs show winner
// Player.deck.removeCard(card)
// calculate Scores
// show Scores
// }
//}


}
