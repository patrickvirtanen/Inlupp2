import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.*;
import java.util.List;

/*

KRITISKT
* [X] huruvida triangel är markerad eller inte är funktionalitet som ska bo i platsen!
* [X] användaren ska kunna söka på koordinater
    * [X] denna sökruta ska kontrollera att input är numerisk
    * [X] annars typ samma som sökning på namn?
* [X] felmeddelande ska visas om plats redan finns på position
* [X] knappen hide category ska gömma alla platser som hör till vald kategori
* [X] när kategori väljs i listan ska platser som hör till den kategorin visas

STRUNTSAKER
* [X] annan färg för att markera svarta platser? (ifsats för att sätta till vit kant om none)

 */

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

	private JTextField textSearch;

	private Category[] categoryList = { Category.Bus, Category.Train, Category.Underground };

	private JList<Category> list;
	// Skapa ny "ordbok" för att slå upp vilka platser som har en kategori
	private Map<Category, Set<Place>> categoryPlaces = new HashMap<>();

	//För sökning av Plats på namn, men går via platsens triangel (ersätter placePerName)
	private Map<String, List<Place>> placePerName = new HashMap<>();

	// För sökning av Plats på Position
	// TODO: fylls inte denna i med värden?
	private Map<Position, Place> placePerPosition = new HashMap<>();

	//TODO: ha en datastruktur för att se markerade platser? Eller kolla det på något annat sätt?

	private JPanel topPanel;
	private JFileChooser jfc = new JFileChooser();
	private KartPanel fv = null;
	private JScrollPane scroll = new JScrollPane();
	private JPanel mittPanel = new JPanel();

	private boolean existingMap = false;
	private boolean changed = false;
	private boolean loadedPlaces = false;

	private void fonster() {
		// I början har kategorin "buss" inga platser kopplad till sig (tom mängd)
		categoryPlaces.put(Category.Bus, new HashSet<>());
		// Samma gäller övriga kategorier
		categoryPlaces.put(Category.Underground, new HashSet<>());
		categoryPlaces.put(Category.Train, new HashSet<>());
		categoryPlaces.put(Category.None, new HashSet<>());

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
		mittPanel.addMouseListener(new ClickLyss());

		JButton newKnapp = new JButton("New");
		topPanel.add(newKnapp);
		newKnapp.addActionListener(new NewLyss());

		topPanel.add(named);
		topPanel.add(described);
		buttonGroup.add(named);
		buttonGroup.add(described);

		textSearch = new JTextField("Search", 10);
		topPanel.add(textSearch);

		JButton searchKnapp = new JButton("Search");
		topPanel.add(searchKnapp);
		searchKnapp.addActionListener(new SearchLyss());

		JButton hideKnapp = new JButton("Hide");
		topPanel.add(hideKnapp);
		hideKnapp.addActionListener(new HideLyss());

		JButton removeKnapp = new JButton("Remove");
		topPanel.add(removeKnapp);
		removeKnapp.addActionListener(new RemoveLyss());

		JButton coordinatesKnapp = new JButton("Coordinates");
		topPanel.add(coordinatesKnapp);
		coordinatesKnapp.addActionListener(new CoordinatesLyss());

		JLabel categories = new JLabel("Categories");
		eastPanel.add(categories);

		list = new JList<>(categoryList);
		eastPanel.add(list);
		list.addMouseListener(new CategoryLyss());

		JScrollPane scroll = new JScrollPane(list);
		eastPanel.add(scroll);

		JButton hidecat = new JButton("Hide Category");
		eastPanel.add(hidecat);
		hidecat.addActionListener(new HideCatLyss());
		
		

		setSize(1000, 500);
		//pack();
		setLocation(200, 200);
		setVisible(true);

		addWindowListener(new ExitLyss());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	class RemoveLyss implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<Place> removedPlaces = fv.removeAllMarked(); //Hämtar listan på borttagna trianglar

			for (Place place : removedPlaces) {
				removePlace(place); //Anropar removePlace-metoden och skickar med tringelns Place
			}
		}
	}

	class ResizeLyss extends ComponentAdapter {
		public void componentResized(ComponentEvent cve) {
			topPanel.revalidate();
			revalidate();
		}
	}

	class OpenLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

			//TODO: if-sats om det redan finns en karta inladdad
			//TODO fort: samt osparade ändringar skall användaren varnas och kunna bryta för att spara

			// Om man öppnar en andra karta måste den första avslutas först och platserna rensas ?
			if (changed || existingMap) {
				int svar = JOptionPane.showConfirmDialog(Main.this, "Unsaved changes, do you still want to continue?",
					"Warning", JOptionPane.OK_CANCEL_OPTION);
				if (svar != JOptionPane.OK_OPTION)
					return;
			}
			//TODO: tömma alla datastrukturer på platser

			//Har en precis laddat in en karta finns inga osparade platser
			changed = false; //TODO: ska den här ligga i en if-sats ?
			loadedPlaces = false;

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

	class HideLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			list.clearSelection();
			fv.hideTriangle();

		}
	}

	class ClickLyss extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			KartPanel kp = (KartPanel)e.getSource();
			if (fv.equals(kp)) {
				list.clearSelection();
				//om e.getSource är lika med fv rensa JList
		}
		}
	}
	//	public void clickedPic(){
	//		//Om man klickar på bilden rensa JList
	//		
	//	}

	class HideCatLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

			Category c = list.getSelectedValue();

			Set<Place> places = categoryPlaces.get(c);

			if (c == null) {
				places = categoryPlaces.get(Category.None);
			}

			fv.hideCatTriangle(places);
			list.clearSelection();
		}
	}

	//TODO: metod för att lägga till ny plats i alla datastrukturer som krävs
	private void addPlace(Place p) {
		// Addera till platser (med position som nyckel ?)
		placePerPosition.put(p.getPosition(), p);

		// Addera till placePerName (platser med samma namn)
		String name = p.getName();
		List<Place> sameName = placePerName.get(name);

		fv.paintTriangle(p);

		if (sameName == null) {
			sameName = new ArrayList<Place>();
			placePerName.put(name, sameName);

		}
		sameName.add(p);

		// Hämta ut mängden för kategorin, och lägg till p i den
		categoryPlaces.get(p.getCategory()).add(p);

		//Platserna är förändrade i jämförelse med den sparade filen (finns nya platser)
		changed = true;
		

	}

	//TODO: metod för att ta bort alla markerade platser från alla datastrukturer som behövs
	private void removePlace(Place p) {
		placePerPosition.remove(p.getPosition(), p);

		String name = p.getName();
		List<Place> sameName = placePerName.get(name);
		sameName.remove(p);

		if (sameName.isEmpty()) {
			placePerName.remove(name);
		}

		// Hämta ut mängden för kategorin, och ta bort p från den
		categoryPlaces.get(p.getCategory()).remove(p);

		//Platserna är förändrade i jämförelse med den sparade filen (platser tagits bort)
		changed = true;
	}

	class CategoryLyss extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			Category c = list.getSelectedValue();
			Set<Place> places = categoryPlaces.get(c);
			fv.showTriangle(places);
		}
	}

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

				//TODO: göra så det inte går att sätta trianglar utanför kartan? (går med hög upplösning)

				int x = e.getX();
				int y = e.getY();
				Position p = new Position(x, y);

				//TODO: felhantering för position
				// TODO: if-sats ifall positionen redan finns, annars komma upp formulär
				// anropa platskonstruktor efter formulär

				Place aPlace = checkPosition(p);
				if (aPlace != null) {
					JOptionPane.showMessageDialog(null, "Already an existing place with those coordinates!", "Error",
						JOptionPane.ERROR_MESSAGE);
					buttonGroup.clearSelection();
					list.clearSelection();
					return;
				}

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
						addPlace(namedPlace);
						System.out.println("nu är denna tillagd " + namedPlace);
						//						removePlace(namedPlace);
						//						System.out.println("nu är denna borttagen " + namedPlace);
						//Måste kunna skicka med namedPlace till removePlace på något sätt.

						System.out.println(namedPlace); //testutskrift
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
							JOptionPane.showMessageDialog(null, "Add a description", "Error",
								JOptionPane.ERROR_MESSAGE);
							continue;
						}

						String name = desForm.getName();
						String description = desForm.getDescription();

						describedPlace = new DescribedPlace(description, name, p, c);
						addPlace(describedPlace);

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

	class SearchLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

			String searchedPlace = textSearch.getText();

			List<Place> sameName = placePerName.get(searchedPlace);

			if (sameName == null) {
				JOptionPane.showMessageDialog(null, "No existing place with that name!", "Error",
					JOptionPane.ERROR_MESSAGE);
				textSearch.setText("Search");
				return;
			} else {
				//börja med att avmarkera alla ev markerade platser
				fv.unMark();
				for (Place place : sameName) {
					//anropa metod som visar platsen
					place.setVisible(true);

					//anropa metod som markerar platsen
					fv.mark(place);
				}
			}

		}
	}

	class CoordinatesLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			CoordinatesForm coordniatesForm = new CoordinatesForm();
			while (true) {
				int test = JOptionPane.showConfirmDialog(null, coordniatesForm, "New", JOptionPane.OK_CANCEL_OPTION);
				if (test == 2 || test == -1) {
					break;
				}
				try {
					if (coordniatesForm.getXField() == null || coordniatesForm.getXField().equals("")) {
						JOptionPane.showMessageDialog(null, "Choose your X-value", "Error", JOptionPane.ERROR_MESSAGE);
						continue;
					} else if (coordniatesForm.getYField() == null || coordniatesForm.getYField().equals("")) {
						JOptionPane.showMessageDialog(null, "Choose your Y-value", "Error", JOptionPane.ERROR_MESSAGE);
						continue;
					} else if (coordniatesForm.getCoordinateX() < 0) {
						JOptionPane.showMessageDialog(null, "Your X-value can't be less than zero", "Error",
							JOptionPane.ERROR_MESSAGE);
						continue;
					} else if (coordniatesForm.getCoordinateY() < 0) {
						JOptionPane.showMessageDialog(null, "Your Y-value can't be less than zero", "Error",
							JOptionPane.ERROR_MESSAGE);
						continue;
					}

					int xCoordinate = coordniatesForm.getCoordinateX();
					int yCoordinate = coordniatesForm.getCoordinateY();
					System.out.println(xCoordinate + ", " + yCoordinate); //felsök

					Position testPosition = new Position(xCoordinate, yCoordinate);

					Place thePlace = checkPosition(testPosition);
					System.out.println(thePlace); //felsök

					//TODO: den hittar inte platsen med den inskriva positionen !!!
					if (thePlace == null) {
						JOptionPane.showMessageDialog(null, "No existing place with those coordinates!", "Error",
							JOptionPane.ERROR_MESSAGE);
						break;
					} else {
						fv.unMark();
						//TODO objektet Position kan inte jämföras och vara sammalika??

						thePlace.setVisible(true);
						fv.mark(thePlace);

						/*
						 * for (TriangleObject triangle: samePosition) {
						 * //anropa metod som visar triangeln med samma namn och
						 * position if
						 * (thePlace.getPosition().equals(testPosition)) {
						 * triangle.setVisible(true);
						 * 
						 * //anropa metod som markerar platsen
						 * fv.mark(triangle); } }
						 */
						break;
					}

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Wrong format!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		}
	}

	public Place checkPosition(Position testPos) { //returnerar Positionsobjektet om det finns

		System.out.println("check " + testPos.getPosition()); //felsök

		return placePerPosition.get(testPos); //Returnerar null om det inte finns någon plats med den positionen
	}

	class LoadLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			Place loadedPlace = null;

			if (loadedPlaces && changed) {
				int answer = JOptionPane.showConfirmDialog(Main.this, "Unsaved changes, do you still want to continue?",
					"Warning", JOptionPane.OK_CANCEL_OPTION);
				if (answer != JOptionPane.OK_OPTION) {
					return;
				}
			}

			//Sökväg på min dator så kommer inte funka hos dig
			File mapp = new File("/Users/tildas/IdeaProjects/inlupp2_git");
			//File mapp = new File("C:/Users/patri/Downloads");
			jfc = new JFileChooser();
			jfc.setCurrentDirectory(mapp);

			int svar = jfc.showOpenDialog(Main.this);

			if (svar != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fil = jfc.getSelectedFile();
			String path = fil.getAbsolutePath();

			// måste ha try-catch när det handlar om filer
			try {
				FileReader infil = new FileReader(path);
				BufferedReader in = new BufferedReader(infil);
				String line;

				while ((line = in.readLine()) != null) {
					String[] tokens = line.split(","); //splitta upp strängen på kommatecken
					String type = tokens[0];
					Category category = Category.parseCategory(tokens[1]);
					int x = Integer.parseInt(tokens[2]);
					int y = Integer.parseInt(tokens[3]);
					Position p = new Position(x, y);
					String name = tokens[4];

					if (type.equals("Described")) {
						String description = tokens[5];
						loadedPlace = new DescribedPlace(description, name, p, category);

					} else if (type.equals("Named")) {
						loadedPlace = new NamedPlace(name, p, category);

					} else {
						JOptionPane.showMessageDialog(null, "Wrong file", "Error", JOptionPane.ERROR_MESSAGE);
						loadedPlace = null;
					}

					if (loadedPlace != null) {
						addPlace(loadedPlace); //en metod som lägger till platsen i alla datastrukturer
					}
					loadedPlaces = true;
				}

				changed = false;

				in.close();
				infil.close();

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(Main.this, "File cannot be opened");
			} catch (IOException ei) {
				JOptionPane.showMessageDialog(Main.this, "Error " + ei.getMessage());
			} // indexOutOfBounds ifall det inte finns en till token, eller om det är fel typ (numberExceptions)

		}
	}

	class SaveLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {

			//Sökväg på min dator så kommer inte funka hos dig
			File mapp = new File("/Users/tildas/IdeaProjects/inlupp2_git");
			//File mapp = new File("C:/Users/patri/Downloads");
			jfc.setCurrentDirectory(mapp);

			int svar = jfc.showSaveDialog(Main.this);

			if (svar != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fil = jfc.getSelectedFile();
			String path = fil.getAbsolutePath();

			// csv - kommaseparerade värden
			try {
				// spara på samma fil hela tiden
				FileWriter utfil = new FileWriter(path);
				PrintWriter out = new PrintWriter(utfil);

				// för att gå igenom när det är en Map = .values
				for (Place p : placePerPosition.values()) {
					out.println(p);
				}

				out.close();
				utfil.close();

				//Platserna är sparade och det finns inga förändringar i jämförelse med den sparade filen
				changed = false;

			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(Main.this, "File cannot be found");
			} catch (IOException ei) {
				JOptionPane.showMessageDialog(Main.this, "Error " + ei.getMessage());
			}
		}
	}

	class ExitLyss extends WindowAdapter implements ActionListener {
		//TODO: varnar användaren om det finns osparade ändringar när programmet avslutas

		private void doIt() {
			if (changed) {
				int answer = JOptionPane.showConfirmDialog(Main.this, "Unsaved changes, do you still want to continue?",
					"Warning", JOptionPane.OK_CANCEL_OPTION);
				if (answer == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			} else
				System.exit(0);
		}

		@Override
		public void windowClosing(WindowEvent wev) {
			doIt();
		}

		public void actionPerformed(ActionEvent ave) {
			doIt();
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
