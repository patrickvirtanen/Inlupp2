import javax.swing.*;
import java.awt.*;

public class TriangleObject extends JComponent{

	Polygon p;
	Position position;
	Color col;

	int[] xes;  // = {0, 25, 50};
	int[] yes;  // = {0, 50, 0};

	int x, x1, x2, y, y1, y2;
	int triangelRadie = 25;

	public TriangleObject(KartPanel kp, Position pos, Category cat) {
		p = new Polygon();

		position = pos;

		x = position.getPositionX();
		y = position.getPositionY();

		System.out.println(x + ", " + y);

		x1 = x-triangelRadie;
		x2 = x+triangelRadie;
		y1 = y-(triangelRadie*2);
		y2 = y-(triangelRadie*2);

		xes = new int[] {x1, x, x2};
		yes = new int[] {y1, y, y2};


		p.xpoints = xes;
		p.ypoints = yes;
		p.npoints = 3;
		this.col = cat.getColor();

		int panelX = kp.getWidth();
		int panelY = kp.getHeight();

		//setBounds säger inom vilken ruta vi vill rita i.
		// När layouten är null (se KartPanel) så har alla komponenter storleken 0,0 och försöker
		// en rita utanför komponentens storlek så kommer den ignorera det.
		// Problemet var kanske alltså att det var någon konstig layout i KartPanel som gjorde
		// triangeln så liten att den vägrade rita ut den ?
		setBounds(0, 0, panelX, panelY);
	}

	@Override
	protected void paintComponent(Graphics g){

		super.paintComponent(g);
		g.setColor(col);
		g.fillPolygon(xes, yes, 3);
		g.drawPolygon(p);
		System.out.println("triangelobjekt");               //testutskrift
		System.out.println(x1 + ", " + x + ", " + x2);      //testutskrift
		System.out.println(y1 + ", " + y + ", " + y2);      //testutskrift
	}
}
