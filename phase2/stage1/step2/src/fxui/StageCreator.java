package fxui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StageCreator {
	//
	private Stage window;
	private BorderPane borderLayout;
	
	public StageCreator() {
		//
		
	}
	
	public void showLoginStage() {
		//
		Stage window = new Stage();
		window.setTitle("Login");
		window.setMinHeight(300);
		window.setMinWidth(300);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		
		Scene scene = new Scene(borderLayout,200,200);
		window.setScene(scene);
		
		window.show();
	}
}
