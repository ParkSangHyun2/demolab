package step2.client.fxui.util;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);

		stage.setTitle(title);
		Label label = new Label(message);
		closeBtn = new Button("close");
		closeBtn.setAlignment(Pos.CENTER);
		closeBtn.setOnAction(e -> stage.close());

		layout = new VBox();
		layout.getChildren().addAll(label, closeBtn);
		layout.setAlignment(Pos.BOTTOM_CENTER);
		layout.setSpacing(10);
		layout.setPadding(new Insets(10));

		scene = new Scene(layout);

		scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				//
				if (event.getCode() == KeyCode.ENTER) {
					closeBtn.fire();
				}
			}
		});

		stage.setScene(scene);
		stage.showAndWait();

	}
}
