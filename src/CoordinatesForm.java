import javax.swing.*;

/**
 * Created by tildas on 2017-05-22.
 */
public class CoordinatesForm extends JPanel {
	private JTextField x, y;

	public CoordinatesForm() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel row1 = new JPanel();
		add(row1);
		row1.add(new JLabel("X: "));
		row1.add(x);
		row1.add(new JLabel("Y: "));
		row1.add(y);
	}

	public int getCoordinateX() {
		return Integer.parseInt(x.getText());
	}

	public int getCoordinateY() {
		return Integer.parseInt(y.getText());
	}
}
