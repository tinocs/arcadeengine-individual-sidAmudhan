package breakout;

import engine.Actor;
import javafx.scene.image.Image;

public class Ball extends Actor {
	
	private double dx;
	private double dy;
	
	public Ball() {
		java.net.URL url = getClass().getClassLoader().getResource("breakoutresources/ball.png");
		if (url == null) {
			System.out.println("Not working");
		} else {
			Image img = new Image(url.toString());
			setImage(img);
		}
		dx = 4;
		dy = 5;
	}
	
	@Override
	public void act(long now) {
		move(dx, dy);
		// bounce off edges 
		double width = getWorld().getWidth();
		double height = getWorld().getHeight();
		if (getX() <= 0) {
			setX(0);
			dx = -dx;
		}
		if (getY() <= 0) {
			setY(0);
			dy = -dy;
		}
		if (getX() + getWidth() >= getWorld().getWidth()) {
			setX(getWorld().getWidth() - getWidth());
			dx = -dx;
		}
		if (getY() + getHeight() >= getWorld().getHeight()) {
			setY(getWorld().getHeight() - getHeight());
			dy = -dy;
			BallWorld ballworld = (BallWorld) getWorld();
			Score score = ballworld.getScore();
			score.setValue(score.getValue() - 1000);
		}
	}

}
