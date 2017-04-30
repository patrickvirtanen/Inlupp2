/**
 * Created by tildas on 2017-04-26.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class KartPanel extends JPanel{
	ImageIcon bild;

	public KartPanel(String filnamn){
		bild = new ImageIcon(filnamn);
		int w = bild.getIconWidth();
		int h = bild.getIconHeight();
		setPreferredSize(new Dimension(w, h));
//		setMaximumSize(new Dimension(w/6, h/6));
//		setMinimumSize(new Dimension(w/6, h/6)); //Ändrade dessa eftersom bilden blev förvrängd.
	}

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(bild.getImage(), 0, 0, getWidth(), getHeight(), this);
	}
	
//	public void getPosition(int x, int y){
//		Position p = new Position(x, y);
//		System.out.println(x);
//		
//	}
}
