import javax.swing.*;
public class ArgumentsGui {

	Hack caller; // this just feels wrong

	public ArgumentsGui(Hack h) { caller=h; }

	public void show() 
	{
		JPanel main;
		JFrame frame;

		frame = new JFrame();
        frame.setTitle("Hack");

        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
	}

}
