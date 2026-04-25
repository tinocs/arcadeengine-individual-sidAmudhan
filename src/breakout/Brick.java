package breakout;

import engine.Actor;
import javafx.scene.image.Image;

public class Brick extends Actor {

	public Brick() {
		java.net.URL url = getClass().getClassLoader().getResource("breakoutresources/brick.png");
		if (url == null) {
			System.out.println("Not working");
		} else {
			Image img = new Image(url.toString());
			setImage(img);
		}
	}
	
	public void act(long now) {
		
	}
	
}
