package breakout;

import engine.Actor;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Paddle extends Actor {

	public Paddle() {
		String path = getClass().getClassLoader().getResource("breakoutresources/paddle.png").toString();
		Image img = new Image(path);
		setImage(img);
	}
	
	@Override
	public void act(long now) {
		BallWorld world = (BallWorld) getWorld();
		double speed = 9;
		if (world.isKeyPressed(KeyCode.RIGHT)) {
			double dx = speed;
			setX(getX() + dx);
			boolean isScrolling = false;
			if (getX() > world.getWidth() / 2.0) {
				isScrolling = true;
				world.scroll(dx);
			} else {
				isScrolling = false;
			}
		}
		if (world.isKeyPressed(KeyCode.LEFT)) {
			double dx = -speed;
			setX(getX() + dx);
			boolean isScrolling = false;
			if (getX() < world.getWidth() / 2.0) {
				isScrolling = true;
				world.scroll(dx);
			} else {
				isScrolling = false;
			}
		}
	}
	
}
