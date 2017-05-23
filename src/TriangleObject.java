import javax.swing.*;
import java.awt.*;

public class TriangleObject extends JComponent {

	Polygon p;
	Position position;
	Color col;
	Place place;
	boolean oMarkerad = true;

	int[] xes; // = {0, 25, 50};
	int[] yes; // = {0, 50, 0};

	int x, x1, x2, y, y1, y2;
	int triangelRadie = 15;

	public TriangleObject(KartPanel kp, Position pos, Category cat, Place pla) {
		p = new Polygon();
		place = pla;

		position = pos;

		x = position.getPositionX();
		y = position.getPositionY();

		System.out.println(x + ", " + y);

		//Övre vänstra hörnet
		x1 = x - triangelRadie;
		y1 = y - (triangelRadie * 2);

		//setBounds säger inom vilken ruta vi vill rita i.
		// När layouten är null (se KartPanel) så har alla komponenter storleken 0,0 och försöker
		// en rita utanför komponentens storlek så kommer den ignorera det.
		// Problemet var kanske alltså att det var någon konstig layout i KartPanel som gjorde
		// triangeln så liten att den vägrade rita ut den ?
		setBounds(x1, y1, triangelRadie * 2, triangelRadie * 2);

		xes = new int[] { 0, triangelRadie, triangelRadie * 2 };
		yes = new int[] { 0, triangelRadie * 2, 0 };

		p.xpoints = xes;
		p.ypoints = yes;
		p.npoints = 3;
		this.col = cat.getColor();

	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (oMarkerad) {
			g.setColor(col);
			g.fillPolygon(xes, yes, 3);
			g.drawPolygon(p);

		} else {
			g.setColor(col);
			g.fillPolygon(xes, yes, 3);
			g.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(3));
			g.drawPolygon(xes, yes, 3);

		}
	}
	

	public void setMarked(boolean marked) {
		oMarkerad = !marked;
		repaint();
	}

	public boolean getMarked() {
		return oMarkerad;
	}
}
