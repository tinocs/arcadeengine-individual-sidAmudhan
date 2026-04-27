package breakout;

import java.io.File;
import java.util.Scanner;
import javafx.scene.paint.Color;
import engine.World;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BallWorld extends World {

	private Ball ball;
	private Paddle paddle;
	private Score score;
	private Lives lives;
	private int level = 1;
	private boolean isPaused = true;
	private Text pauseText;
	
	public BallWorld() {
		setPrefSize(800, 800);
	}

	public Score getScore() {
		return score;
	}
	
	@Override
	public void act(long now) {
		// TODO Auto-generated method stub
		if (isKeyPressed(javafx.scene.input.KeyCode.SPACE) && isPaused) {
			setPaused(false);
			if (pauseText != null) {
				getChildren().remove(pauseText);
				pauseText = null;
			}
		}
		if (isPaused) {
			return;
		}
		if (getObjects(Brick.class).isEmpty()) {
			level++;
			if (level == 2) {
				loadTextFileLevel("level2.txt");
				setPaused(true);
				double ballX = paddle.getX() + paddle.getWidth() / 2.0 - ball.getWidth() / 2.0;
				double ballY = paddle.getY() - ball.getHeight() - 2;
				ball.setX(ballX);
				ball.setY(ballY);
				pauseText();
			} else if (level == 3) {
				
			}
		}
	}

	@Override
	public void onDimensionsInitialized() {
		// TODO Auto-generated method stub
		ball = new Ball();
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
		
		lives = new Lives();
		lives.setX(10);
		lives.setY(60);
		getChildren().add(lives);
		loadTextFileLevel("level1.txt");
		setPaused(true);
		ballX = paddle.getX() + paddle.getWidth() / 2.0 - ball.getWidth() / 2.0;
		ballY = paddle.getY() - ball.getHeight() - 2;
		ball.setX(ballX);
		ball.setY(ballY);	
		setPaused(true);
		ballX = paddle.getX() + paddle.getWidth() / 2.0 - ball.getWidth() / 2.0;
		ballY = paddle.getY() - ball.getHeight() - 2;
		ball.setX(ballX);
		ball.setY(ballY);
		pauseText();
	}
	
	public void pauseText() {
		if (pauseText != null && getChildren().contains(pauseText)) {
			return;
		}
		pauseText = new Text("Press SPACE to start");
		pauseText.setFont(new Font(30));
		pauseText.setX(getWidth() / 2.0 - pauseText.getLayoutBounds().getWidth() / 2.0);
		pauseText.setY(getHeight() / 2.0);
		getChildren().add(pauseText);
	}
	
	public void loadTextFileLevel(String filename) {
		try (Scanner in = new Scanner(new File(filename))) {
			int rows = in.nextInt();
			int cols = in.nextInt();
			in.nextLine();
			Brick temp = new Brick();
			for (int r = 0; r < rows; r++) {
				String line = in.nextLine();
				for (int c = 0; c < cols; c++) {
					char ch = line.charAt(c);
					if (ch == '0') {
						continue;
					}
					Brick b = createBrick(ch, 30 + c * (temp.getWidth() + 4), 30 + r * (temp.getHeight() + 4));
					if (b != null) {
						add(b);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// helper method for each brick
	private Brick createBrick(char ch, double x, double y) {
		Color color;
		if (ch == '1') {
			color = Color.BLUE;
		} else if (ch == '2') {
			color = Color.GREEN;
		} else {
			return null;
		}
		Brick b = new Brick();
		b.setX(x);
		b.setY(y);
		return b;
	}
	
	public Lives getLives() {
		return lives;
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public void setPaused(boolean paused) {
		this.isPaused = paused;
	}
	
}
