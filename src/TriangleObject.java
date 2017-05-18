import javax.swing.*;
import java.awt.*;

public class TriangleObject extends JComponent{

	Polygon p;
	Color c; //Kanske bara ska ha detta i subklasserna?
	// KOMMENTAR: tänk på att färgerna finns lagrade redan i Category.java !

	int[] xes = {0, 50, 100};
	int[] yes = {0, 100, 0};

	public TriangleObject(KartPanel kp) {
		p = new Polygon();

		p.xpoints = xes;
		p.ypoints = yes;
		p.npoints = 3;
		//this.c = c;

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
		g.setColor(Color.BLACK);
		g.fillPolygon(xes, yes, 3);
		g.drawPolygon(p);
		System.out.println("triangelobjekt");
	}
}
