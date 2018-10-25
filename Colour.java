import java.util.ArrayList;
public class Colour {
	ColourType type;

	public Colour (ColourType n) { type = n; }
	public Colour (Colour c) { type = c.getType(); }
	public ColourType getType() { return type; }
	public static ArrayList<Colour> allColours() 
	{
		ArrayList<Colour> cl = new ArrayList<Colour>();
		cl.add(new Colour(ColourType.blue));
		cl.add(new Colour(ColourType.red));
		cl.add(new Colour(ColourType.cyan));
		cl.add(new Colour(ColourType.yellow));
		cl.add(new Colour(ColourType.purple));
		return cl;
	}

	public String toString() 
	{
		if (type==ColourType.red) return new String ("R");
		if (type==ColourType.blue) return new String ("B");
		if (type==ColourType.cyan) return new String ("C");
		if (type==ColourType.yellow) return new String ("Y");
		if (type==ColourType.purple) return new String ("P");
		return ("none");
	}
	@Override
	public int hashCode() { return type.hashCode(); }
	@Override 
        public boolean equals (Object o)
        {
                if (o == this) return true;
                if (!(o instanceof Colour)) return false;
                Colour l = (Colour) o;
		return l.type == type;
        }
}
