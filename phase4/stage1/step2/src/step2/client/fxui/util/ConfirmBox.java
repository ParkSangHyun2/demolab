package step2.client.fxui.util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
	//
	static boolean answer;	
	
	public static boolean display(String title, String message) {
	//
	Stage stage;
	Scene scene;
	VBox layout;
	Button yesBtn,noBtn;
		
	stage = new Stage();
	stage.setMinWidth(100);
	stage.setMinHeight(100);
	stage.initModality(Modality.APPLICATION_MODAL);
	
	stage.setTitle(title);
	Label label = new Label(message);
	StackPane stackPane = new StackPane();
	stackPane.getChildren().add(label);
	stackPane.setAlignment(Pos.BASELINE_CENTER);
	
	yesBtn = new Button("Yes");
	noBtn = new Button("No");
	HBox hbox = new HBox(30);
	hbox.getChildren().addAll(yesBtn, noBtn);
	hbox.setAlignment(Pos.BASELINE_CENTER);
	
	yesBtn.setOnAction(e ->{
		answer = true;
		stage.close();
	});

	noBtn.setOnAction(e ->{
		answer = false;
		stage.close();
	});
	
	layout  = new VBox(10);
	layout.getChildren().addAll(stackPane, hbox);
	layout.setAlignment(Pos.BOTTOM_LEFT);
	layout.setPadding(new Insets(20));
		
	scene = new Scene(layout, 170, 80);
	
	stage.setScene(scene);
	stage.showAndWait(); // 이전 씬에서 있는이벤트를 잠시닫고 현재 메소드의 이벤트를 우선적으로 처리!
	return answer;
	}
}
