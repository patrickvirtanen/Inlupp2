
/**
 * Created by tildas on 2017-04-2scale.
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class KartPanel extends JPanel {

	//TODO: gå igenom vilka attribut vi behöver här
	private JFrame f;
	ImageIcon bild;
	int scale = 1;

	//Ändrade till en lista istället för HashMap för de markerade trianglarna
	private List<Place> markedPlaces = new ArrayList<>();
	private List<Place> allPlaces = new ArrayList<>();

	public KartPanel(String filnamn) {
		bild = new ImageIcon(filnamn);
		int w = bild.getIconWidth();
		int h = bild.getIconHeight();

		setLayout(null);

		setPreferredSize(new Dimension(w / scale, h / scale));
		setMaximumSize(new Dimension(w / scale, h / scale));
		setMinimumSize(new Dimension(w / scale, h / scale));
		setOpaque(false);
	}

	public void paintTriangle(Place pla) {
		Position position = pla.getPosition();
		pla.addMouseListener(new TriangelLyss());
		add(pla);
		allPlaces.add(pla);
		validate();
		repaint();
	}

	class TriangelLyss extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent mev) {
			Place place = (Place) mev.getSource(); //för att se vilken triangel som är klickad
			boolean markerad = place.getMarked();

			if (SwingUtilities.isRightMouseButton(mev)) {
				if (place instanceof NamedPlace) {
					String meddelande = place.getName() + " {" + place.getCoordinates() + "}";
					JOptionPane.showMessageDialog(null, meddelande, "Platsinfo", JOptionPane.OK_OPTION);
				} else if (place instanceof DescribedPlace) {
					String meddelande = "Name: " + place.getName() + " {" + place.getCoordinates()
						+ "} \nDescription: " + ((DescribedPlace) place).getDescription();
					JOptionPane.showMessageDialog(null, meddelande, "Platsinfo", JOptionPane.OK_OPTION);
				}

			} else if (SwingUtilities.isLeftMouseButton(mev)) {
				if (markerad == false) {
					markedPlaces.add(place);
					System.out.println(markerad);
				} else {
					markedPlaces.remove(place);
					System.out.println(markerad);
				}
				place.setMarked(!markerad);
			}

		}
	}

	public List<Place> removeAllMarked() {
		for (Place place : markedPlaces) {
			this.remove(place);
		}
		List<Place> removedPlaces = new ArrayList<>(markedPlaces); //gör en kopia på markedPlaces så att
		markedPlaces.clear(); // den kan clearas och den kopian returneras
		this.repaint();

		return removedPlaces;
	}
	
	public void unMarkAllTriangles(){
		for (Place place : allPlaces) {
			place.setMarked(false);
		}

		// TODO: måste ta bort även från listan marked places
		
		this.repaint();
	}

	public void unMark() {
		
		for (Place place : markedPlaces) {
			place.setMarked(false);
		}

		// TODO: måste ta bort även från listan marked places
//				markedTriangles.clear(); //För att min funktion catagoryLyss i main
		// skulle fungera var jag tvungen att blocka denna
		// Jag vet att du sa att den fuckar upp saker, men
		// Hittade ingen annan lösning :/
		this.repaint();
	}

	public void mark(Place place) {
		place.setMarked(true);
		allPlaces.add(place);
		markedPlaces.add(place);
		this.repaint();
	}

	public void hideTriangle() {
		for (Place place : markedPlaces) {
			place.setVisible(false);
		}
//		this.unMark();
		this.repaint();
	}

	public void hideCatTriangle(Category c) {

		for (Place place : allPlaces) {

			if (place.getCategory() == c) {   // if (c.getColor().equals(place.col)) {
				place.setVisible(false);
				System.out.println("Slutet på for-loopen");
				markedPlaces.add(place);
				//Göm trianglarna i catTriangle
				//Rensa listan
			}
	
		}
		this.unMarkAllTriangles();
		this.repaint();
	}

	public void showTriangle(Category c) {
		for (Place place : markedPlaces) {
			if (c == place.getCategory()) {
				place.setVisible(true);
			}
		}
		this.unMark();
		this.repaint();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bild.getImage(), 0, 0, bild.getIconWidth() / scale, bild.getIconHeight() / scale, this);

	} // paintComponent

}
