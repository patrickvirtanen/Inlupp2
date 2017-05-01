import javax.swing.*;

/**
 * Created by tildas on 2017-05-01.
 */
public class NameForm extends JPanel {
	private JTextField nameField = new JTextField(20);

	public NameForm() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel row1 = new JPanel();
		add(row1);
		row1.add(new JLabel("Name: "));
		row1.add(nameField);
	}

	public String getName() {
		return nameField.getText();
	}
}
