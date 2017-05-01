/**
 * Created by tildas on 2017-04-30.
 */
public class Place {
	private String name;
	private Position coordinates;
	private Category category;

	public Place (String name, int coordinateX, int coordinateY, Category category) {
		this.name = name;
		coordinates = new Position(coordinateX, coordinateY);
		this.category = category;
	}

	public String getName() {
		return name;
	}

}
