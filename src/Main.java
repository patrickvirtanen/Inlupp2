import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {

	private JMenuBar mb = new JMenuBar();
	private JMenu men = new JMenu("Archive");
	private JMenuItem nytt = new JMenuItem("Nytt Fönster");
	private JMenuItem stäng = new JMenuItem("Stäng");

	private JRadioButton named = new JRadioButton("Named", false);
	private JRadioButton described = new JRadioButton("Described", false);
	String[] categorieList = { "Train", "Bus", "Underground" };

	private void fonster() {
		setLayout(new BorderLayout());
		setJMenuBar(mb);
		mb.add(men);
		men.add(nytt);
		men.add(stäng);

		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		add(eastPanel, BorderLayout.EAST);

		JButton newKnapp = new JButton("New");
		topPanel.add(newKnapp);
		//visaKnapp.addActionListener(new VisaLyss());

		topPanel.add(named);
		topPanel.add(described);
		ButtonGroup bg = new ButtonGroup();
		bg.add(named);
		bg.add(described);

		JTextField text = new JTextField("Search", 10);
		topPanel.add(text);

		JButton searchKnapp = new JButton("Search");
		topPanel.add(searchKnapp);
		//visaKnapp.addActionListener(new VisaLyss());

		JButton hideKnapp = new JButton("Hide");
		topPanel.add(hideKnapp);
		//visaKnapp.addActionListener(new VisaLyss());

		JButton removeKnapp = new JButton("Remove");
		topPanel.add(removeKnapp);
		//visaKnapp.addActionListener(new VisaLyss());

		JButton coordinatesKnapp = new JButton("Coordinates");
		topPanel.add(coordinatesKnapp);
		//visaKnapp.addActionListener(new VisaLyss());

		JLabel categories = new JLabel("Categories");
		eastPanel.add(categories);

		JList<String> list = new JList<String>(categorieList);
		eastPanel.add(list);

		JScrollPane scroll = new JScrollPane(list);
		eastPanel.add(scroll);

		JButton hidecat = new JButton("Hide Categorie");
		eastPanel.add(hidecat);

		setSize(1000, 300);
		setLocation(300, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void run() {
		fonster();
	}

	public static void main(String[] args) {
		Main program = new Main();
		program.run();
	}

	static {
		Font f = new Font("Dialog", Font.BOLD, 20);
		String[] comps = { "Button", "Label", "RadioButton", "CheckBox", "ToggleButton", "TextArea", "TextField",
				"Menu", "MenuItem", "FileChooser", "Dialog", "OptionPane" };
		for (String s : comps) {
			UIManager.put(s + ".font", f);
		}
	}
}
