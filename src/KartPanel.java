
/**
 * Created by tildas on 2017-04-2scale.
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class KartPanel extends JPanel {

	private JFrame f;
	ImageIcon bild;
	int scale = 1;
	private Position p;
	int x, y;

	public KartPanel(String filnamn) {
		bild = new ImageIcon(filnamn);
		int w = bild.getIconWidth();
		int h = bild.getIconHeight();

		setPreferredSize(new Dimension(w / scale, h / scale));
		setMaximumSize(new Dimension(w / scale, h / scale));
		setMinimumSize(new Dimension(w / scale, h / scale));
		//addMouseListener(new MusLyss());

	}

	// koppling mellan newPlace och MusLyss är knasig
//	public Position newPlace() {

		//this.addMouseListener(new MusLyss());
//		System.out.println("jkhk");


//		return p;

//	}



	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bild.getImage(), 0, 0, bild.getIconWidth() / scale, bild.getIconHeight() / scale, this);
	} // paintComponent

	public void paintTraiangle(Graphics g) {
		int[] x = new int[3];
		int[] y = new int[3];
		int n; // count of points

		x[0] = 25;
		x[1] = 145;
		x[2] = 30;
		y[0] = 25;
		y[1] = 25;
		y[2] = 145;
		n = 3;
		Polygon myTri = new Polygon(x, y, n);

		g.drawPolygon(myTri);
		g.fillPolygon(myTri);
	}

}
