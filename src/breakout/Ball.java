package breakout;

import engine.Actor;
import javafx.scene.image.Image;

public class Ball extends Actor {
	
	private double dx;
	private double dy;
	
	public Ball() {
		String path = getClass().getClassLoader().getResource("breakoutresources/ball.png").toString();
		Image img = new Image(path);
		setImage(img);
		dx = 4;
		dy = 5;
	}
	
	@Override
	public void act(long now) {
		move(dx, dy);
		// still figuring out how to bounce off edges 
	}

}
