package fxui.util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {//

	
	public static void alert(String title, String message) {
		//
	Stage stage;
	Scene scene;
	VBox layout;
	Button closeBtn;
		
	stage = new Stage();
	stage.setMinWidth(150);
	stage.setMaxHeight(90);
	stage.setMinHeight(150);
	stage.setMaxWidth(90);
	stage.initModality(Modality.APPLICATION_MODAL);
	
	stage.setTitle(title);
	Label label = new Label(message);
	closeBtn = new Button("close");
	closeBtn.setAlignment(Pos.CENTER);
	closeBtn.setOnAction(e -> stage.close());
	
	layout  = new VBox(10);
	layout.getChildren().addAll(label,closeBtn);
	layout.setAlignment(Pos.BOTTOM_CENTER);
	layout.setPadding(new Insets(10));

	scene = new Scene(layout, 150 ,90);
	
	stage.setScene(scene);
	stage.showAndWait();
		
	}
}
