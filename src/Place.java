import javax.swing.JComponent;

/**
 * Created by tildas on 2017-04-30.
 */
public class Place extends JComponent {
	
	private String name;
	private Position coordinates;
	private Category category;
	private int x,y;

	public Place(String name){
		this.name = name;
	}
	public Place(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Place (String name, int coordinateX, int coordinateY, Category category) {
		this.name = name;
		this.coordinates = new Position(coordinateX, coordinateY);
		this.category = category;
	}

	public String getName() {
		return name;
	}

}
