
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
	private boolean marked = false;
	private ArrayList<TriangleObject> temp = new ArrayList<TriangleObject>();
	private Map<Position, Place> placePerPosition = new HashMap<>();

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
		TriangleObject tro = new TriangleObject(this, p, cat, pla);
		tro.addMouseListener(new TriangelLyss());
		add(tro);
		validate();
		repaint();
		System.out.println("TRIANGEL");
	}

	public void removePlaces() {
		for (TriangleObject t : temp)
			temp.remove(t);

	}

	class TriangelLyss extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent mev) {
			TriangleObject triangle = (TriangleObject) mev.getSource(); //för att se vilken triangel som är klickad
			triangle.setMarked();
			marked = true;
			if (marked = true) {
				placePerPosition.put(triangle.position, triangle.place);
				provMark();
			} else {
				placePerPosition.remove(triangle.position, triangle.place);
				System.out.println(triangle);
			}

			triangle.repaint();

			if (SwingUtilities.isRightMouseButton(mev)) {
				if (triangle.place instanceof NamedPlace) {
					String meddelande = triangle.place.getName() + " {" + triangle.place.getCoordinates() + "}";
					JOptionPane.showMessageDialog(null, meddelande, "Platsinfo", JOptionPane.OK_OPTION);
				} else if (triangle.place instanceof DescribedPlace) {
					String meddelande = "Name: " + triangle.place.getName() + " {" + triangle.place.getCoordinates()
						+ "} \nDescription: " + ((DescribedPlace) triangle.place).getDescription();
					JOptionPane.showMessageDialog(null, meddelande, "Platsinfo", JOptionPane.OK_OPTION);
				}

			}

		}

		public void provMark() {

			for (Map.Entry<Position, Place> entry : placePerPosition.entrySet()) {
				System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());
			}
		}
	}
	
	public void removeAllMarked(){
		
		System.out.println(placePerPosition.toString());
		placePerPosition.clear();
		System.out.println("********************");
		System.out.println(placePerPosition.toString());
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bild.getImage(), 0, 0, bild.getIconWidth() / scale, bild.getIconHeight() / scale, this);

	} // paintComponent

}
