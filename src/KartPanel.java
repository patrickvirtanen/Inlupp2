
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
	private List<TriangleObject> markedTriangles = new ArrayList<>();
	private List<TriangleObject> allTriangles = new ArrayList<>();

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

	public TriangleObject paintTriangle(Place pla) {
		Position position = pla.getPosition();
		TriangleObject triangle = new TriangleObject(pla);
		triangle.addMouseListener(new TriangelLyss());
		add(triangle);
		allTriangles.add(triangle);
		validate();
		repaint();
		return triangle;
	}

	class TriangelLyss extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent mev) {
			TriangleObject triangle = (TriangleObject) mev.getSource(); //för att se vilken triangel som är klickad
			boolean markerad = triangle.getMarked();

			if (SwingUtilities.isRightMouseButton(mev)) {
				if (triangle.place instanceof NamedPlace) {
					String meddelande = triangle.place.getName() + " {" + triangle.place.getCoordinates() + "}";
					JOptionPane.showMessageDialog(null, meddelande, "Platsinfo", JOptionPane.OK_OPTION);
				} else if (triangle.place instanceof DescribedPlace) {
					String meddelande = "Name: " + triangle.place.getName() + " {" + triangle.place.getCoordinates()
						+ "} \nDescription: " + ((DescribedPlace) triangle.place).getDescription();
					JOptionPane.showMessageDialog(null, meddelande, "Platsinfo", JOptionPane.OK_OPTION);
				}

			} else if (SwingUtilities.isLeftMouseButton(mev)) {
				if (markerad == false) {
					markedTriangles.add(triangle);
					System.out.println(markerad);
				} else {
					markedTriangles.remove(triangle);
					System.out.println(markerad);
				}
				triangle.setMarked(!markerad);
			}

		}
	}

	public List<TriangleObject> removeAllMarked() {
		for (TriangleObject triangle : markedTriangles) {
			this.remove(triangle);
		}
		List<TriangleObject> removedTriangles = new ArrayList<>(markedTriangles); //gör en kompia på markedTriangles så att
		markedTriangles.clear(); // den kan clearas och den kopian returneras
		this.repaint();

		return removedTriangles;
	}
	
	public void unMarkAllTriangles(){
		for (TriangleObject triangle : allTriangles) {
			triangle.setMarked(false);
		}
		
		this.repaint();
	}

	public void unMark() {
		
		for (TriangleObject triangle : markedTriangles) {
			triangle.setMarked(false);
		}
//				markedTriangles.clear(); //För att min funktion catagoryLyss i main
		// skulle fungera var jag tvungen att blocka denna
		// Jag vet att du sa att den fuckar upp saker, men
		// Hittade ingen annan lösning :/
		this.repaint();
	}

	public void mark(TriangleObject triangle) {
		triangle.setMarked(true);
		allTriangles.add(triangle);
		markedTriangles.add(triangle);
		this.repaint();
	}

	public void hideTriangle() {
		for (TriangleObject triangle : markedTriangles) {
			triangle.setVisible(false);
		}
//		this.unMark();
		this.repaint();
	}

	public void hideCatTriangle(Category c) {

		for (TriangleObject triangle : allTriangles) {

			if (c.getColor().equals(triangle.col)) {
				triangle.setVisible(false);
				System.out.println("Slutet på for-loopen");
				markedTriangles.add(triangle);
				//Göm trianglarna i catTriangle
				//Rensa listan
			}
	
		}
		this.unMarkAllTriangles();
		this.repaint();
	}

	public void showTriangle(Category c) {
		for (TriangleObject triangle : markedTriangles) {
			if (c.getColor() == triangle.col) {
				triangle.setVisible(true);
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
