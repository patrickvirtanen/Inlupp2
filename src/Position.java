import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

// Har lite svårt att lista ut positionen, men exprementerar lite.

public class Position extends JComponent {

	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getPositionX() {
		return x;
	}

	public int getPositionY() {
		return y;
	}

	//TODO: kan vi ta bort dessa metoder nedan?
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getPosition() {
		return x + "," + y;
	}
}