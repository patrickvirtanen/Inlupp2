import javax.swing.*;

public class CoordinatesForm extends JPanel {
	private JTextField x = new JTextField(5);
	private JTextField y = new JTextField(5);

	public CoordinatesForm() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel row1 = new JPanel();
		add(row1);
		row1.add(new JLabel("X: "));
		row1.add(x);
		row1.add(new JLabel("Y: "));
		row1.add(y);
	}

	public String getXField() {
		return x.getText();
	}

	public String getYField() {
		return y.getText();
	}

	public int getCoordinateX() {
		return Integer.parseInt(x.getText());
	}

	public int getCoordinateY() {
		return Integer.parseInt(y.getText());
	}
}
