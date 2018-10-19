import java.util.HashMap;
import java.util.Iterator;
import java.lang.RuntimeException;
public class UIFactory {

	public UIFactory () { ; }
	
	private static HashMap<String,Class> getAllInterfaces() 
	{
		HashMap<String,Class> allInterfaceNames = new HashMap<String,Class>();
		allInterfaceNames.put(UIRandom.name(),UIRandom.class);
		return allInterfaceNames;
	}

	public static UserInterface getInterface(String iname) throws RuntimeException, InstantiationException, IllegalAccessException
	{
		Class c = getAllInterfaces().get(iname);
		if (c==null)
			throw new RuntimeException("Interface Not Found");
        return (UserInterface)c.newInstance();
	}

	public static HashMap<Colour,UserInterface> getPlayers(String[] a) throws RuntimeException, InstantiationException, IllegalAccessException
	{
		// colour iterator
		HashMap<Colour,UserInterface> players = new HashMap<Colour,UserInterface>();
		Iterator<Colour> ac = Colour.allColours().iterator();
		for (String iname: a)
		{
			Colour c = ac.next(); // happy to throw an exception if too many arguments specified.
			players.put(c,getInterface(iname));
		}
		return players;
	}

}
