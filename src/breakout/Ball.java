package breakout;

import engine.Actor;
import engine.Sound;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Ball extends Actor {
	
	private double dx;
	private double dy;
	private Text overText;
	
	public Ball() {
		java.net.URL url = getClass().getClassLoader().getResource("breakoutresources/ball.png");
		if (url == null) {
			System.out.println("Not working");
		} else {
			Image img = new Image(url.toString());
			setImage(img);
		}
		dx = 2;
		dy = 2;
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
				setY(0);
				setX(0);
				new Sound("ballbounceresources/game_lost.wav").play();
				overText = new Text("GAME OVER!");
				overText.setFont(new Font(30));
				overText.setX(getWidth() / 2.0 - overText.getLayoutBounds().getWidth() / 2.0);
				overText.setY(getHeight() / 2.0);
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
			FadeTransition ft = new FadeTransition(Duration.millis(150), brick);
			ft.setToValue(0.0);
			ft.setCycleCount(4);
			ft.setAutoReverse(true);
			ft.play();
			getWorld().remove(brick);
	}
}
}
