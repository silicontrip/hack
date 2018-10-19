import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.HashMap;
public class ArgumentsGui implements ActionListener {

	private Hack caller; // this just feels wrong
	private HashMap<Colour,JComboBox<String>> selector;
	private JFrame frame;

	public ArgumentsGui(Hack h) { caller=h; }

	public void show() 
	{
		JPanel main;

		frame = new JFrame();
        frame.setTitle("Hack");

        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));

        JButton go = new JButton("GO.");
    	go.addActionListener(this);

		selector = new HashMap<Colour,JComboBox<String>>();

		// need to make colour identifier for dropdown.
		for (Colour cl: Colour.allColours())
		{
			JComboBox<String> jcb = new JComboBox<String>(UIFactory.getAllInterfaceNames());
			selector.put(cl,jcb);
			JPanel tl = new JPanel();
			    tl.add(new JLabel(""+cl+":"));
			    tl.add(jcb);

			main.add(tl);
		}

		main.add(go);

        frame.getContentPane().add(main,BorderLayout.CENTER);
        frame.setSize(480,240);
        frame.setResizable(true);
        frame.setVisible(true);

	}
	public void actionPerformed(ActionEvent ev) {
		try{
			frame.setVisible(false);
			HashMap<Colour,UserInterface> p = new HashMap<Colour,UserInterface>(); 
			for (Colour cl: selector.keySet())
			{
				String uiname = (String)selector.get(cl).getSelectedItem();
				if (!uiname.equals("none"))
					p.put(cl,UIFactory.getInterface(uiname));
				//System.out.println("" + cl + ": " +uiname);
			}
			caller.start(p);
			frame.dispose();
		} catch (Exception e) {

            JOptionPane.showMessageDialog(this.frame,
                e.getMessage(),
                "Error Occured",
                JOptionPane.ERROR_MESSAGE);

            System.out.print ("Exception: ");
            System.out.println(e.getMessage());
            e.printStackTrace();
		}
	}

}
