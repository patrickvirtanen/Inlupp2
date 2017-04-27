import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame {

	private JMenuBar mb = new JMenuBar();
	private JMenu men = new JMenu("Archive");
	private JMenuItem nytt = new JMenuItem("New map");
	private JMenuItem stäng = new JMenuItem("Stäng");

	private JRadioButton named = new JRadioButton("Named", false);
	private JRadioButton described = new JRadioButton("Described", false);
	String[] categorieList = { "Train", "Bus", "Underground" };

	JFileChooser jfc = new JFileChooser();
	FilValjare fv = null;
	JScrollPane scroll = null;

	private void fonster() {
		setLayout(new BorderLayout());
		setJMenuBar(mb);
		mb.add(men);
		men.add(nytt);
		nytt.addActionListener(new OpenLyss());
		men.add(stäng);

		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		add(eastPanel, BorderLayout.EAST);

		JButton newKnapp = new JButton("New");
		topPanel.add(newKnapp);
		newKnapp.addActionListener(new OpenLyss());

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

	class OpenLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave){
			FileFilter ff = new FileNameExtensionFilter("Bilder", "jpg", "png", "gif");
			jfc.setFileFilter(ff);
			//Sökväg på min dator så kommer inte funka hos dig
			File mapp = new File("/Users/tildas/Pictures/bakgrundsbilder");
			jfc.setCurrentDirectory(mapp);

			int svar = jfc.showOpenDialog(Main.this);

			if (svar != JFileChooser.APPROVE_OPTION)
				return;
			File fil = jfc.getSelectedFile();
			String path = fil.getAbsolutePath();
			if (fv != null)
				remove(scroll);
			fv = new FilValjare(path);
			scroll = new JScrollPane(fv);
			add(scroll, BorderLayout.CENTER);
			pack();
			validate();
			repaint();
		}
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
