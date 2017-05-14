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
	
	private boolean existingMap = false;

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
		close.addActionListener(new ExitLyss());

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

			//TODO: if-sats om det redan finns en karta inladdad
			//TODO fort: samt osparade ändringar skall användaren varnas och kunna bryta för att spara

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
			existingMap = true;

			//pack();
			validate();
			repaint();
		}
	}

	// Dessa två metoder nedan leker jag med just nu. Ska skapa ett nytt program för att se om jag får till det.
	TriangleObject Poly1;
	public void poly(){
		Poly1 = new TriangleObject(new int[]{10,200,10}, new int[]{10, 200,400}, Color.BLACK);
		
	}
	public void paintComponent(Graphics g){
		Poly1.drawPolygon(g);
		
	}

	//TODO: metod för att se vilka platser som är markerade


	//TODO: metod för att lägga till ny plats i alla datastrukturer som krävs

	//TODO: metod för att ta bort alla markerade platser från alla datastrukturer som behövs


	//TODO: metod för att gömma alla markerade platser

	class NewLyss implements ActionListener {

		public void actionPerformed(ActionEvent actionEvent) {
			//Kontrollerar om det finns någon inläst karta
			if (existingMap) {
				Cursor cross = new Cursor(Cursor.CROSSHAIR_CURSOR);
				fv.setCursor(cross);

				//TODO: kontrollera att krysset verkligen dyker upp första gången? Eller med Described?
				//TODO fort: Kanske finns gömd bugg...

				fv.addMouseListener(new KartLyss());
			} else {
				JOptionPane.showMessageDialog(null, "Load a map first", "Error", JOptionPane.ERROR_MESSAGE);
				buttonGroup.clearSelection();
				list.clearSelection();
				return;
			}
		}

		class KartLyss extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent e) {
				fv.setCursor(Cursor.getDefaultCursor());
				fv.removeMouseListener(this);

				int x = e.getX();
				int y = e.getY();
				Position p = new Position(x, y);

				//TODO: välja datastruktur för position
				//TODO: felhantering för position
				// if-sats ifall positionen redan finns, annars komma upp formulär
				// anropa platskonstruktor efter formulär

				Category c = list.getSelectedValue();
				if (c == null) {
					c = Category.None;
				}

				NamedPlace namedPlace = null;
				DescribedPlace describedPlace = null;

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
						System.out.println(namedPlace);         //testutskrift
						break;
					}
				} else if (described.isSelected()) {
					DescriptionForm desForm = new DescriptionForm();
					while (true) {
						int test = JOptionPane.showConfirmDialog(null, desForm, "New", JOptionPane.OK_CANCEL_OPTION);
						if (test == 2 || test == -1) {
							break;
						}
						if (desForm.getName() == null || desForm.getName().equals("")) {
							JOptionPane.showMessageDialog(null, "Add a name", "Error", JOptionPane.ERROR_MESSAGE);
							continue;
						} else if (desForm.getDescription() == null || desForm.getDescription().equals("")) {
							JOptionPane.showMessageDialog(null, "Add a description", "Error", JOptionPane.ERROR_MESSAGE);
							continue;
						}

						String name = desForm.getName();
						String description = desForm.getDescription();

						describedPlace = new DescribedPlace(description, name, p, c);
						System.out.println(describedPlace);     //testutskrift
						break;
					}

				} else {
					JOptionPane.showMessageDialog(null, "Choose type!", "Wrong", JOptionPane.ERROR_MESSAGE);
				}

				buttonGroup.clearSelection();
				list.clearSelection();
			}
		}
	}

	class LoadLyss implements ActionListener{
		public void actionPerformed(ActionEvent ave){
			// måste ha try-catch när det handlar om filer
			try {

				FileReader infil = new FileReader("jarvafaltet.places");
				BufferedReader in = new BufferedReader(infil);
				String line;

				while ((line = in.readLine()) != null) {
					String[] tokens = line.split(",");  //splitta upp strängen på kommatecken
					String type = tokens[0];
					Category category = Category.parseCategory(tokens[1]);
					int x = Integer.parseInt(tokens[2]);
					int y = Integer.parseInt(tokens[3]);
					Position p = new Position(x, y);
					String name = tokens[4];

					if (type.equals("Described")) {
						String description = tokens[5];
						DescribedPlace describedPlace = new DescribedPlace(description, name, p, category);
					} else if (type.equals("Named")) {
						NamedPlace namedPlace = new NamedPlace(name, p, category);
					} else {
						JOptionPane.showMessageDialog(null, "Wrong file", "Error", JOptionPane.ERROR_MESSAGE);
					}

					//TODO: lägga till platsen beroende på typ ?
					//add(p); //en metod som lägger till platsen i alla datastrukturer

				}

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

				// För att få filen att spara något då vi inte har några datastrukturer valda än
				out.println("Named,Bus,485,335,514\n" +
						"Named,Underground,666,487,Kista\n" +
						"Named,Bus,311,147,514\n" +
						"Named,Bus,634,354,514\n" +
						"Named,Underground,450,350,Husby\n" +
						"Named,Bus,762,624,514\n" +
						"Named,Bus,365,235,514\n" +
						"Named,Underground,691,528,Kista\n" +
						"Named,Train,929,317,Hellenelund\n" +
						"Named,Bus,719,507,514\n" +
						"Named,Bus,818,382,514\n" +
						"Named,Bus,94,786,179\n" +
						"Named,Underground,224,169,Akalla\n" +
						"Named,Bus,527,259,514\n" +
						"Named,Bus,883,578,514\n" +
						"Named,Bus,728,511,179\n" +
						"Named,Underground,416,313,Husby\n" +
						"Named,Bus,760,391,514\n" +
						"Named,None,736,397,NOD\n" +
						"Named,Bus,137,717,514\n" +
						"Named,Underground,272,197,Akalla\n" +
						"Described,None,711,453,Forum,DSV was here until 2014\n" +
						"Named,Bus,915,475,514");

				out.close();
				utfil.close();

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(Main.this, "File cannot be found");
			} catch (IOException ei) {
				JOptionPane.showMessageDialog(Main.this, "Error " + ei.getMessage());
			}
		}
	}

	class ExitLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			//TODO: varnar användaren om det finns osparade ändringar när programmet avslutas
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
