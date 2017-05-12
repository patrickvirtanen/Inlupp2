import java.awt.*;

public class TriangleObject {

	Polygon p;
	Color c; //Kanske bara ska ha detta i subklasserna?

	public TriangleObject(int[] x, int[] y, Color c) {
		p = new Polygon();
		p.xpoints = x;
		p.ypoints = y;
		p.npoints = x.length;
		this.c = c;
	}
	
	public void drawPolygon(Graphics g){
		g.setColor(c);
		g.drawPolygon(p);
	}
	
	
}