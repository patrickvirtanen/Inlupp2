/**
 * Created by tildas on 2017-04-30.
 */
public class NamedPlace extends Place {

	public NamedPlace (String name, Position p, Category category) {
		super(name, p, category);
	}

	public String toString() {
		return getName() + " " + getCategory() + " " + getCoordinates();
	}
}
