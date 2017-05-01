import javax.swing.*;

/**
 * Created by tildas on 2017-05-01.
 */
public class DescriptionForm extends JPanel {
	private JTextField nameField = new JTextField(20);
	private JTextField descriptionField = new JTextField(30);

	public DescriptionForm() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel row1 = new JPanel();
		add(row1);
		row1.add(new JLabel("Name: "));
		row1.add(nameField);
		JPanel row2 = new JPanel();
		add(row2);
		row2.add(new JLabel("Decription: "));
		row2.add(descriptionField);
	}

	public String getName() {
		return nameField.getText();
	}
}
