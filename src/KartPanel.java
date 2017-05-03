
/**
 * Created by tildas on 2017-04-2scale.
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class KartPanel extends JPanel {

	ImageIcon bild;
	int scale = 1;

	public KartPanel(String filnamn) {
		bild = new ImageIcon(filnamn);
		int w = bild.getIconWidth();
		int h = bild.getIconHeight();

		setPreferredSize(new Dimension(w / scale, h / scale));
		setMaximumSize(new Dimension(w / scale, h / scale));
		setMinimumSize(new Dimension(w / scale, h / scale));
		addMouseListener(new MusLyss());

	}

	class MusLyss extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e){
			int x = e.getX();
			int y = e.getY();
			Position p = new Position(x,y);
			p.getPosition();
			
			
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bild.getImage(), 0, 0, bild.getIconWidth() / scale, bild.getIconHeight() / scale, this);
	}

}
