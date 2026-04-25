package breakout;

import engine.World;
import engine.Actor;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;

public class BallWorld extends World {
	
	private Ball ball;
	private Paddle paddle;
	private Score score;
	
	public BallWorld() {
		setPrefSize(800, 800);
	}

	public Score getScore() {
		return score;
	}
	
	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDimensionsInitialized() {
		// TODO Auto-generated method stub
		Ball ball = new Ball();
		add(ball);
		double x = getWidth() / 2.0;
		double y = getHeight() / 2.0;
		double ballX = x - ball.getWidth() / 2.0;
		double ballY = y - ball.getHeight() / 2.0;
		ball.setX(ballX);
		ball.setY(ballY);
		
		paddle = new Paddle();
		add(paddle);
		double paddleY = getHeight() - 80;
		paddle.setY(paddleY);
		double paddleX = getWidth() / 2.0 - paddle.getWidth() / 2.0;
		paddle.setX(paddleX);
		setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// Add your code here
				double mouseX = event.getX();
				double PaddleX = mouseX - paddle.getWidth() / 2.0;
				paddle.setX(paddleX);
			}});
		score = new Score();
		score.setX(10);
		score.setY(30);
		getChildren().add(score);
		
		int rows = 6;
		int cols = 6;
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				Brick b = new Brick();
				b.setX(30 + c * (b.getWidth() + 4));
				b.setY(30 + r * (b.getHeight() + 4));
				add(b);
			}
		}
	}
	
}
