public class allcards {


public static void main(String[] args)
{

	Colour c = Colour.allColours().get(0);
	Deck d = new Deck(c);

	for (Card cd: d.getArray())
	{
		for (Card cdr: cd.getAllRotations())
		{
			System.out.println(cdr);
		}
	}

}


}
