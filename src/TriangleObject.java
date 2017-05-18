import java.awt.*;

public class TriangleObject {

	Position pos;
	Polygon p;
	Category c; 
	
	int[] xes = {0,50,100};
	int[] yes = {0, 100,0};
	
	//Kanske bara ska ha detta i subklasserna?
	// KOMMENTAR: tänk på att färgerna finns lagrade redan i Category.java !

	public TriangleObject() {
		p = new Polygon();
		p.xpoints = xes;
		p.ypoints = yes;
		p.npoints = 3;
		
	}
	
	public void drawPolygon(Graphics g){
		g.setColor(Color.BLACK);
		g.fillPolygon(xes,yes,3);
//		g.drawPolygon(p);
	}
	
	
}
