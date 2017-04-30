/**
 * Created by tildas on 2017-04-30.
 */
public class DescribedPlace extends Place {
	private String description;

	public DescribedPlace (String description, String name, int coordinateX, int coordinateY, Category.Type category) {
		super(name, coordinateX, coordinateY, category);
		this.description = description;

	}
}
