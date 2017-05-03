/**
 * Created by tildas on 2017-04-2scale.
 */
import javax.swing.*;



import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class KartPanel extends JPanel{
	ImageIcon bild;
	int scale = 2;


	public KartPanel(String filnamn){
		bild = new ImageIcon(filnamn);
		int w = bild.getIconWidth();
		int h = bild.getIconHeight();
		
		setPreferredSize(new Dimension(w/scale, h/scale));
		setMaximumSize(new Dimension(w/scale, h/scale));
		setMinimumSize(new Dimension(w/scale, h/scale));

		

	}
	
	

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(bild.getImage(), 0, 0, bild.getIconWidth()/scale, bild.getIconHeight()/scale, this);
	}
	
//	public void getPosition(int x, int y){
//		Position p = new Position(x, y);
//		System.out.println(x);
//		
//	}
}
