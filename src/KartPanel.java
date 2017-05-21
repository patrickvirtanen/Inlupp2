
/**
 * Created by tildas on 2017-04-2scale.
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class KartPanel extends JPanel {

	//TODO: gå igenom vilka attribut vi behöver här
	private JFrame f;
	ImageIcon bild;
	int scale = 1;
	private Position position;
	int x, y;

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

	public void paintTriangle(Position p, Category cat, Place pla) {
		position = p;
		TriangleObject tro = new TriangleObject(this, p, cat, pla);
		tro.addMouseListener(new TriangelLyss());
		add(tro);
		validate();
		repaint();
		System.out.println("TRIANGEL");
	}

	class TriangelLyss extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent mev) {
			TriangleObject triangle = (TriangleObject)mev.getSource();  //för att se vilken triangel som är klickad

			if (SwingUtilities.isRightMouseButton(mev)) {
				if (triangle.place instanceof NamedPlace) {
					String meddelande = triangle.place.getName() + " {" + triangle.place.getCoordinates()
							+ "}";
					JOptionPane.showMessageDialog(null, meddelande, "Platsinfo", JOptionPane.OK_OPTION);
				} else if (triangle.place instanceof DescribedPlace) {
					String meddelande = "Name: " + triangle.place.getName() + " {" + triangle.place.getCoordinates()
							+ "} \nDescription: " + ((DescribedPlace) triangle.place).getDescription();
					JOptionPane.showMessageDialog(null, meddelande, "Platsinfo", JOptionPane.OK_OPTION);
				}

			}

		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bild.getImage(), 0, 0, bild.getIconWidth() / scale, bild.getIconHeight() / scale, this);

	} // paintComponent

}
