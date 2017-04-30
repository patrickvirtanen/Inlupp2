import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

// Har lite svårt att lista ut positionen, men exprementerar lite.

public class Position extends JComponent {

	public Position(int x, int y){
		setBounds(x, y, 50, 50);
		addMouseListener(new MusLyss());
	}
	
	class BildPosition extends Position{
		private String filnamn;
		private ImageIcon bild;
		public BildPosition(int x, int y, String filnamn){
			super(x,y);
			this.filnamn = filnamn;
			bild = new ImageIcon(filnamn);
		}
//		protected void visa(Graphics g){
//			g.drawImage(bild.getImage(), 0, 0, getWidth(), getHeight(), this);
//		}
	}
	
	class MusLyss extends MouseAdapter{
		public void MouseCLicked(MouseEvent mev){
			int x = mev.getX();
			int y = mev.getY();
			System.out.println("" + x + y);
		}
	}
}
