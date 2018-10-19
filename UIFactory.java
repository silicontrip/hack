import java.util.HashMap;
import java.util.Iterator;
import java.lang.RuntimeException;
public class UIFactory {

	public UIFactory () { ; }
	
	private static HashMap<String,Class> getAllInterfaces() 
	{
		HashMap<String,Class> allInterfaceNames = new HashMap<String,Class>();

		// System.out.println("" + UIRandom.name() +"/"+ UIRandom.class.getName());

		allInterfaceNames.put(UIRandom.class.getName(),UIRandom.class);
		return allInterfaceNames;
	}

	public static UserInterface getInterface(String iname) throws RuntimeException, InstantiationException, IllegalAccessException
	{
		Class c = getAllInterfaces().get(iname);
		if (c==null)
			throw new RuntimeException("Invalid Interface name: " + iname);

		UserInterface ui = (UserInterface) c.newInstance();

		//System.out.println("Class:" + c + "/" + ui.name());

        return ui;
	}

	public static HashMap<Colour,UserInterface> getPlayers(String[] a) throws RuntimeException, InstantiationException, IllegalAccessException
	{
		// colour iterator
		HashMap<Colour,UserInterface> players = new HashMap<Colour,UserInterface>();
		Iterator<Colour> ac = Colour.allColours().iterator();
		for (String iname: a)
		{
			Colour c = ac.next(); // happy to throw an exception if too many arguments specified.
			
			UserInterface ui = getInterface(iname);
			// System.out.println("" + ui.getClass().getName());
			players.put(c,getInterface(iname));
		}
		//System.out.println("size: " + players.size());
		return players;
	}

}
