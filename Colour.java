import java.util.ArrayList;
public class Colour {
	ColourType type;

	public Colour (ColourType n) { type = n; }
	public ColourType getType() { return type; }
	public static ArrayList<Colour> allColours() 
	{
		ArrayList<Colour> cl = new ArrayList<Colour>();
		cl.add(new Colour(ColourType.red));
		cl.add(new Colour(ColourType.blue));
		cl.add(new Colour(ColourType.cyan));
		cl.add(new Colour(ColourType.yellow));
		cl.add(new Colour(ColourType.purple));
		return cl;
	}

	public String toString() 
	{
		if (type==ColourType.red) return new String ("Red");
		if (type==ColourType.blue) return new String ("Blue");
		if (type==ColourType.cyan) return new String ("Cyan");
		if (type==ColourType.yellow) return new String ("Yellow");
		if (type==ColourType.purple) return new String ("Purple");
		return ("[INVALID]");
	}
}

	

