/**
 * Created by tildas on 2017-04-26.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FilValjare extends JPanel{
	ImageIcon bild;

	public FilValjare(String filnamn){
		bild = new ImageIcon(filnamn);
		int w = bild.getIconWidth();
		int h = bild.getIconHeight();
		setPreferredSize(new Dimension(w/6, h/6));
		setMaximumSize(new Dimension(w/6, h/6));
		setMinimumSize(new Dimension(w/6, h/6));
	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(bild.getImage(), 0, 0, getWidth(), getHeight(), this);
	}
}
