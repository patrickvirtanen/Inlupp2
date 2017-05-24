import javax.swing.JComponent;
import java.awt.*;

/**
 * Created by tildas on 2017-04-30.
 */
public class Place extends JComponent {
	
	private String name;
	private Position coordinates;
	private Category category;
	private boolean markerad = false;

	int triangelRadie = 15;

	// Göra en type också (Named eller Described) som en enum och lägga in i konstruktorerna automatisk?
	// Eller går det att lösa på något snyggare sätt? - HA EN TOSTRING-METOD I VARJE SUBKLASS
	
	public Place (String name, Position p, Category category) {
		this.name = name;
		this.coordinates = p;
		this.category = category;

		//Övre vänstra hörnet
		int x1 = p.getPositionX() - triangelRadie;
		int y1 = p.getPositionY() - (triangelRadie * 2);

		//setBounds säger inom vilken ruta vi vill rita i.
		// När layouten är null (se KartPanel) så har alla komponenter storleken 0,0 och försöker
		// en rita utanför komponentens storlek så kommer den ignorera det.
		// Problemet var kanske alltså att det var någon konstig layout i KartPanel som gjorde
		// triangeln så liten att den vägrade rita ut den ?
		setBounds(x1, y1, triangelRadie * 2, triangelRadie * 2);
	}

	public String getName() {
		return name;
	}

	public Category getCategory() {
		return category;
	}

	public Position getPosition() {
		return coordinates;
	}

	public String getCoordinates() {
		return coordinates.getPosition();
	}

	//Skickar en in true så blir triangeln markerad, och vice versa
	public void setMarked(boolean marked) {
		markerad = marked;
		repaint();
	}

	public boolean getMarked() {
		return markerad;
	}


	@Override
	protected void paintComponent(Graphics g) {
		int[] xes = new int[] { 0, triangelRadie, triangelRadie * 2 };
		int[] yes = new int[] { 0, triangelRadie * 2, 0 };

		Color col = getCategory().getColor();

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if (markerad) {
			g.setColor(col);
			g.fillPolygon(xes, yes, 3);
			g.setColor(Color.DARK_GRAY);
			g2.setStroke(new BasicStroke(3));
			g.drawPolygon(xes, yes, 3);
		} else {
			g.setColor(col);
			g.fillPolygon(xes, yes, 3);
			g.drawPolygon(xes, yes, 3);
		}
	}

}
