import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main extends JFrame {
	
	private JMenuBar mb = new JMenuBar();
	private JMenu men = new JMenu("Archive");
	private JMenuItem nytt = new JMenuItem("New map");
	private JMenuItem loadP = new JMenuItem("Load places");
	private JMenuItem saveP = new JMenuItem("Save");
	private JMenuItem close = new JMenuItem("Exit");

	private JRadioButton named = new JRadioButton("Named", false);
	private JRadioButton described = new JRadioButton("Described", false);
	private ButtonGroup buttonGroup = new ButtonGroup();

	Category[] categoryList = {Category.Bus, Category.Train, Category.Underground};
	JList<Category> list;

	JPanel topPanel;
	private JFileChooser jfc = new JFileChooser();
	private KartPanel fv = null;
	private JScrollPane scroll = new JScrollPane();
	private JPanel mittPanel = new JPanel();
	
	

	private void fonster() {
		setLayout(new BorderLayout());
		addComponentListener(new ResizeLyss());
		setJMenuBar(mb);
		mb.add(men);
		men.add(nytt);
		nytt.addActionListener(new OpenLyss());
		men.add(loadP);
		loadP.addActionListener(new LoadLyss());
		men.add(saveP);
		saveP.addActionListener(new SaveLyss());
		men.add(close);

		topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new WrapLayout(FlowLayout.RIGHT));

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
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

		list = new JList<>(categoryList);
		eastPanel.add(list);

		JScrollPane scroll = new JScrollPane(list);
		eastPanel.add(scroll);

		JButton hidecat = new JButton("Hide Categorie");
		eastPanel.add(hidecat);

		setSize(1000, 300);
		//pack();
		setLocation(200, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	class ResizeLyss extends ComponentAdapter {
		public void componentResized (ComponentEvent cve) {
			topPanel.revalidate();
			revalidate();
		}
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
			scroll = new JScrollPane(fv);
			mittPanel.add(scroll, BorderLayout.CENTER);

			//pack();
			validate();
			repaint();
		}
	}


	class NewLyss implements ActionListener {
		class KartLyss extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent e) {
				fv.setCursor(Cursor.getDefaultCursor());
				fv.removeMouseListener(this);

				int x = e.getX();
				int y = e.getY();
				Position p = new Position(x, y);

				Category c = list.getSelectedValue();

				NamedPlace namedPlace = null;
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

						namedPlace = new NamedPlace(name, p, c);
						System.out.println(namedPlace);
						break;
					}
				} else if (described.isSelected()) {
					DescriptionForm desForm = new DescriptionForm();
				} else {
					JOptionPane.showMessageDialog(null, "Choose type!", "Wrong", JOptionPane.ERROR_MESSAGE);
				}

				buttonGroup.clearSelection();
				System.out.println(namedPlace);
			}
		}

		public void actionPerformed(ActionEvent actionEvent) {
			Cursor cross = new Cursor(Cursor.CROSSHAIR_CURSOR);     //ska ligga i newPlace?
			fv.setCursor(cross);
			fv.addMouseListener(new KartLyss());

			// if-sats ifall positionen redan finns, annars komma upp formulär
			// anropa platskonstruktor efter formulär
		}
	}

	class LoadLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			// måste ha try-catch när det handlar om filer
			try {

				FileReader infil = new FileReader("jarvafaltet.places");
				BufferedReader in = new BufferedReader(infil);
				String line;

				/*while ((line = in.readLine()) != null) {
					String[] tokens = line.split(",");  //splitta upp strängen på kommatecken
					Persnr pnr = Persnr.parsePersnr(tokens[0]);
					String namn = tokens[1];
					int vikt = Integer.parseInt(tokens[2]);



					//kontrollera ifall det är en Named eller Described place och anropa rätt konstruktor
					Place p = new Place(pnr, namn, vikt);

					add(p); //en metod som lägger till platsen i alla datastrukturer

				}*/

				in.close();
				infil.close();

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(Main.this, "File cannot be opened");
			} catch (IOException ei){
				JOptionPane.showMessageDialog(Main.this, "Error " + ei.getMessage());
			}   // indexOutOfBounds ifall det inte finns en till token, eller om det är fel typ (numberExceptions)


		}
	}

	class SaveLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			// csv - kommaseparerade värden
			try {
				// spara på samma fil hela tiden
				FileWriter utfil = new FileWriter("jarvafaltet.places");
				PrintWriter out = new PrintWriter(utfil);

				// för att gå igenom när det är en Map = .values
				/*for(Place p : places.values()) {
					out.println(p.getPnr() + "," + p.getNamn() + "," + p.getVikt());
				} */

				out.close();
				utfil.close();

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(Main.this, "File cannot be found");
			} catch (IOException ei) {
				JOptionPane.showMessageDialog(Main.this, "Error " + ei.getMessage());
			}
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
		Font f = new Font("Dialog", Font.PLAIN, 22);
		String[] comps = { "Button", "Label", "RadioButton", "CheckBox", "ToggleButton", "TextArea", "TextField",
				"Menu", "MenuItem", "FileChooser", "Dialog", "OptionPane" };
		for (String s : comps) {
			UIManager.put(s + ".font", f);
		}
	}
}
