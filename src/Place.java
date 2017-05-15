import javax.swing.JComponent;

/**
 * Created by tildas on 2017-04-30.
 */
public class Place extends JComponent {
	
	private String name;
	private Position coordinates;
	private Category category;
	private int x,y;

	// Göra en type också (Named eller Described) som en enum och lägga in i konstruktorerna automatisk?
	// Eller går det att lösa på något snyggare sätt? - HA EN TOSTRING-METOD I VARJE SUBKLASS
	
	public Place (String name, Position p, Category category) {
		this.name = name;
		this.coordinates = p;
		this.category = category;
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

}
