import java.awt.*;

/**
 * Created by tildas on 2017-04-30.
 */
public class Category {
	public enum Type {Bus, Underground, Train, None}

	public static Color getCategoryColor(Type type) {
		switch (type) {
			case Bus:
				return Color.RED;
			case Underground:
				return Color.BLUE;
			case Train:
				return Color.GREEN;
			default:
				return Color.BLACK;
		}
	}
	//Kan göras en liknande metod som ovan för beskrivning för kategori etc etc

}
