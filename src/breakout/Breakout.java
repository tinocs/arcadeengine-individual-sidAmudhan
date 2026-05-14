package breakout;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Breakout extends Application {
	
	private Stage primaryStage;	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		HBox topBox = new HBox();
		topBox.setSpacing(10);
		primaryStage.setTitle("Breakout");
		// Title
		Text titleText = new Text("BREAKOUT");
		titleText.setFont(Font.font(60));
		String path = getClass().getClassLoader().getResource("breakoutresources/breakouttitle.png").toString();
		Image logoImg = new Image(path);
		ImageView logoView = new ImageView(logoImg);
		logoView.setFitWidth(300);
		logoView.setFitHeight(200);
		logoView.setPreserveRatio(true);
		topBox.getChildren().add(logoView);
		// playButton
		Button playButton = new Button("PLAY");
		playButton.setPrefWidth(100);
		playButton.setPrefHeight(50);
		// Menu
		VBox menuBox = new VBox(20);
		menuBox.setAlignment(Pos.CENTER);
		menuBox.getChildren().addAll(logoView, playButton);
		BorderPane menuRoot = new BorderPane();
		menuRoot.setCenter(menuBox);
		
		BorderPane root = new BorderPane();
		Scene scene = new Scene(menuRoot, 700, 650);
		primaryStage.setScene(scene);
		primaryStage.show();
		// playButton handler
		playButton.setOnAction(e -> {
			BorderPane gameRoot = new BorderPane();
			BallWorld world = new BallWorld();
			gameRoot.setCenter(world);
			Scene gameScene = new Scene(gameRoot, 750, 650);
			primaryStage.setScene(gameScene);
			world.start();
			world.requestFocus();
		});
	}
	
	
	
}
