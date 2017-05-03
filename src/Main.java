import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame {

	private JMenuBar mb = new JMenuBar();
	private JMenu men = new JMenu("Archive");
	private JMenuItem nytt = new JMenuItem("New map");
	private JMenuItem stäng = new JMenuItem("Stäng");

	private JRadioButton named = new JRadioButton("Named", false);
	private JRadioButton described = new JRadioButton("Described", false);
	private ButtonGroup buttonGroup = new ButtonGroup();
	String[] categorieList = { "Train", "Bus", "Underground" };

	private JFileChooser jfc = new JFileChooser();
	private KartPanel fv = null;
	private JScrollPane scroll = new JScrollPane();
	private JPanel mittPanel = new JPanel();
	
	

	private void fonster() {
		setLayout(new BorderLayout());
		setJMenuBar(mb);
		mb.add(men);
		men.add(nytt);
		nytt.addActionListener(new OpenLyss());
		nytt.addMouseListener(new MusLyss());
		men.add(stäng);
		buttonGroup.add(named);
		buttonGroup.add(described);

		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		add(eastPanel, BorderLayout.EAST);

		mittPanel.setLayout(new BorderLayout());
		mittPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(mittPanel, BorderLayout.CENTER);

		JButton newKnapp = new JButton("New");
		topPanel.add(newKnapp);
		newKnapp.addActionListener(new NewLyss());

		topPanel.add(named);
		topPanel.add(described);
		buttonGroup.add(named);
		buttonGroup.add(described);

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
		public void actionPerformed(ActionEvent ave) {
			FileFilter ff = new FileNameExtensionFilter("Bilder", "jpg", "png", "gif");
			jfc.setFileFilter(ff);
			//Sökväg på min dator så kommer inte funka hos dig
			File mapp = new File("/Users/tildas/Pictures/bakgrundsbilder");
			//File mapp = new File("C:/Users/patri/Downloads");
			jfc.setCurrentDirectory(mapp);

			int svar = jfc.showOpenDialog(Main.this);

			if (svar != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fil = jfc.getSelectedFile();
			String path = fil.getAbsolutePath();
			if (fv != null) {
				mittPanel.remove(scroll);
			}
			fv = new KartPanel(path);
			fv.addMouseListener(new MusLyss());
			scroll = new JScrollPane(fv);
			mittPanel.add(scroll, BorderLayout.CENTER);
			
			

			pack();
			validate();
			repaint();
		}
	}

	class MusLyss extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e){
			int x = e.getX();
			int y = e.getY();
			System.out.println(x + " " + y);
			
		}
	}
	class NewLyss implements ActionListener {
		public void actionPerformed(ActionEvent actionEvent) {
			if (named.isSelected()) {
				NameForm nameForm = new NameForm();
				while (true) {
					int test = JOptionPane.showConfirmDialog(null, nameForm, "New", JOptionPane.OK_CANCEL_OPTION);
					if (test == 2 || test == -1) {
						break;
					}
					if (nameForm.getName() == null || nameForm.getName().equals("")) {
						JOptionPane.showMessageDialog(null, "Add a name", "Wrong", JOptionPane.ERROR_MESSAGE);
						continue;
					}

					String name = nameForm.getName();

					//Place aPlace = new Place(name, xx, yy, cat);

					break;
				}
			} else if (described.isSelected()) {
				DescriptionForm desForm = new DescriptionForm();
			} else {
				JOptionPane.showMessageDialog(null, "Choose type!", "Wrong", JOptionPane.ERROR_MESSAGE);
			}
			buttonGroup.clearSelection();
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
		Font f = new Font("Dialog", Font.BOLD, 25);
		String[] comps = { "Button", "Label", "RadioButton", "CheckBox", "ToggleButton", "TextArea", "TextField",
				"Menu", "MenuItem", "FileChooser", "Dialog", "OptionPane" };
		for (String s : comps) {
			UIManager.put(s + ".font", f);
		}
	}
}
