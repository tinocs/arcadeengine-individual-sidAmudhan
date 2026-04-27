package breakout;

import engine.Actor;
import engine.Sound;
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
		BallWorld world = (BallWorld) getWorld();
		if (world.isPaused()) {
			Paddle paddle = world.getObjects(Paddle.class).get(0);
			double ballX = paddle.getX() + paddle.getWidth() / 2.0 - getWidth() / 2.0;
			double ballY = paddle.getY() - getHeight() - 2;
			setX(ballX);
			setY(ballY);
			// dont move while paused
			return;
		}
		move(dx, dy);
		// bounce off edges 
		double width = getWorld().getWidth();
		double height = getWorld().getHeight();
		if (getX() <= 0) {
			setX(0);
			dx = -dx;
			new Sound("ballbounceresources/ball_bounce.wav").play();
		}
		if (getY() <= 0) {
			setY(0);
			dy = -dy;
			new Sound("ballbounceresources/ball_bounce.wav").play();
		}
		if (getX() + getWidth() >= getWorld().getWidth()) {
			setX(getWorld().getWidth() - getWidth());
			dx = -dx;
			new Sound("ballbounceresources/ball_bounce.wav").play();
		}
		if (getY() + getHeight() >= getWorld().getHeight()) {
			setY(getWorld().getHeight() - getHeight());
			dy = -dy;
			BallWorld ballworld = (BallWorld) getWorld();
			Score score = ballworld.getScore();
			score.setValue(score.getValue() - 1000);
			Lives lives = ballworld.getLives();
			lives.setValue(lives.getValue() - 1);
			new Sound("ballbounceresources/lose_life.wav").play();
			if (lives.getValue() <= 0) {
				new Sound("ballbounceresources/game_lost.wav").play();
				System.out.println("You lose");
			} else {
				ballworld.setPaused(true);
				Paddle paddle = ballworld.getObjects(Paddle.class).get(0);
				setX(paddle.getX() + paddle.getWidth() / 2.0 - getWidth() / 2.0);
				setY(paddle.getY() - paddle.getHeight() - 2);
				dx = 4;
				dy = -5;
				ballworld.pauseText();
			}
			return;
		}
		Paddle paddle = getOneIntersectingObject(Paddle.class);
		if (paddle != null) {
			dy = -dy;
			setY(paddle.getY() - getHeight());
			new Sound("ballbounceresources/ball_bounce.wav").play();
	}
		Brick brick = getOneIntersectingObject(Brick.class);
		if (brick != null) {
			if (getX() + getWidth() / 2.0 >= brick.getX() && getX() + getWidth() / 2.0 <= brick.getX() + brick.getWidth()) {
				dy = -dy;
			} else if (getY() + getHeight() / 2.0 >= brick.getY() && getY() + getHeight() / 2.0 <= brick.getY() + brick.getHeight()) {
				dx = -dx;
			} else {
				dx = -dx;
				dy = -dy;
			}
			new Sound("ballbounceresources/brick_hit.wav").play();
			getWorld().remove(brick);
	}
}
}
