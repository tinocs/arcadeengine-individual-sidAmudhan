package breakout;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Score extends Text {

	private int value;
	
	public Score() {
		value = 0;
		setFont(new Font(24));
		updateDisplay();
	}
	
	private void updateDisplay() {
		setText("Score " + value);
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int newValue) {
		value = newValue;
		updateDisplay();
	}
	
}
