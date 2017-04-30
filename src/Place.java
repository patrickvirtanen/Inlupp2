/**
 * Created by tildas on 2017-04-30.
 */
public class Place {
	private String name;
	private Position coordinates;
	private Category.Type category;

	public Place (String name, int coordinateX, int coordinateY, Category.Type category) {
		this.name = name;
		coordinates = new Position(coordinateX, coordinateY);
		this.category = category;
	}

	public Place (String name, int coordinateX, int coordinateY) {
		this.name = name;
		coordinates = new Position(coordinateX, coordinateY);
		this.category = Category.Type.None;
	}

	public String getName() {
		return name;
	}

}
