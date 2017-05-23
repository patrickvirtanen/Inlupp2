
/**
 * Created by tildas on 2017-04-2scale.
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KartPanel extends JPanel {

	//TODO: gå igenom vilka attribut vi behöver här
	private JFrame f;
	ImageIcon bild;
	int scale = 1;
	private Position position;
	int x, y;
	private boolean marked = true;
	private TriangleObject triangle;
	private Map<Position, Place> markedPlacePerPosition = new HashMap<>();
	private Map<TriangleObject, Place> markedPlacePerPosition2 = new HashMap<>();

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

	public void paintTriangle(Position p, Category cat, Place pla) { // public Place
		position = p;
		triangle = new TriangleObject(this, p, cat, pla);
		triangle.addMouseListener(new TriangelLyss());
		add(triangle);
		validate();
		repaint();
		System.out.println("TRIANGEL");
	}

	class TriangelLyss extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent mev) {
			triangle = (TriangleObject) mev.getSource(); //för att se vilken triangel som är klickad
			triangle.setMarked();


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

				if (marked == true) {
					markedPlacePerPosition.put(triangle.position, triangle.place);
					markedPlacePerPosition2.put(triangle, triangle.place);
					provMark();
				} else {
					markedPlacePerPosition.remove(triangle.position, triangle.place);
					markedPlacePerPosition2.remove(triangle, triangle.place);
					System.out.println(marked);
				}

				marked = !marked;
			}

		}

		public void provMark() {

			for (Map.Entry<Position, Place> entry : markedPlacePerPosition.entrySet()) {
				System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());

				System.out.println(marked);
			}
		}
	}
	
	public void removeAllMarked(){
		
		//System.out.println(markedPlacePerPosition.toString());

		System.out.println("********************");
		//System.out.println(markedPlacePerPosition.toString());

		for (Map.Entry<TriangleObject, Place> entry : markedPlacePerPosition2.entrySet()) {
			this.remove(entry.getKey());
		}
		this.repaint();
		markedPlacePerPosition2.clear();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bild.getImage(), 0, 0, bild.getIconWidth() / scale, bild.getIconHeight() / scale, this);

	} // paintComponent

}
