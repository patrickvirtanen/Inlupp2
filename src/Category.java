import java.awt.*;

/**
 * Created by tildas on 2017-04-30.
 */

public enum Category {
	// En kan se det som fyra konstruktorer (nedan), alltså har enum flera värden av typen
	Bus, Train, Underground, None;

	public Color getColor() {
		switch(this) {
			case Bus:
				return Color.RED;
			case Train:
				return Color.GREEN;
			case Underground:
				return Color.BLUE;
			default:
				return Color.BLACK;
		}
	}

	public static Category parseCategory(String str){
		switch(str) {
			case "Bus":
				return Category.Bus;
			case "Train":
				return Category.Train;
			case "Underground":
				return Category.Underground;
			default:
				return Category.None;
		}
	}

}

// ovan kan sedan skapas med typ
// Category luma = Category.Bus;
// och färg kan då hämtas med
// luma.getColor();
