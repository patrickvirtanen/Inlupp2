/**
 * Created by tildas on 2017-04-30.
 */
public class DescribedPlace extends Place {
	private String description;

	public DescribedPlace (String description, String name, Position p, Category category) {
		super(name, p, category);
		this.description = description;
	}

	public String toString() {
		return  "Described," + getCategory() + "," + getCoordinates() + "," + getName() + "," + description;
	}
}
