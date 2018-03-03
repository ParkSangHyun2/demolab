package fxui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TravelClubFx extends Application {
	//
	private Stage window;
	private BorderPane mainLayout;
	private Scene scene;

	private PrimaryScene primaryScene;

	public TravelClubFx() {
		//
		window = new Stage();
		window.setTitle("TravelClub-Stage1");
		window.setResizable(false);
		window.setMinHeight(550);
		window.setMinWidth(600);
		mainLayout = new BorderPane();
		primaryScene = new PrimaryScene(window, mainLayout);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//
		scene = new Scene(primaryScene.createInitialPane());
		//scene.X
		
		window.setScene(scene);
		window.showAndWait();
		window.close();
		
	}

	public static void main(String[] args) {
		launch();
	}
}
