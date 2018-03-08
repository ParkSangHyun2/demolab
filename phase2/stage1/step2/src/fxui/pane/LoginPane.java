package fxui.pane;

import java.util.LinkedHashMap;
import java.util.Map;

import fxui.PrimaryScene;
import fxui.Session;
import fxui.event.LoginEventHelper;
import fxui.util.AlertBox;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginPane {

	private BorderPane mainLayout;

	LoginEventHelper loginEventHelper;

	public LoginPane(BorderPane mainLayout) {
		//
		this.mainLayout = mainLayout;
		loginEventHelper = new LoginEventHelper();
	}

	public Pane initLoginPane() {
		//
		Label titleLabel = new Label("Login");
		Label emailLabel = new Label("E-mail");
		Label nameLabel = new Label("Name");
		TextField emailField = new TextField();
		TextField nameField = new TextField();
		Button loginBtn = new Button("Login");
		Button signupBtn = new Button("SignUp");

		Map<String, Node> valueNodes = new LinkedHashMap<String, Node>();
		valueNodes.put("emailField", emailField);
		valueNodes.put("nameField", nameField);

		StackPane titlePane = new StackPane();
		titlePane.getChildren().add(titleLabel);
		titlePane.setAlignment(Pos.BASELINE_CENTER);

		titleLabel.setFont(Font.font(30));

		GridPane contentsLayout = new GridPane();
		contentsLayout.add(titlePane, 1, 0);
		contentsLayout.add(emailLabel, 0, 1);
		contentsLayout.add(nameLabel, 0, 2);
		contentsLayout.add(emailField, 1, 1);
		contentsLayout.add(nameField, 1, 2);
		contentsLayout.setHgap(20);
		contentsLayout.setVgap(10);
		contentsLayout.setAlignment(Pos.BASELINE_CENTER);

		HBox btnBox = new HBox();
		btnBox.getChildren().addAll(loginBtn, signupBtn);
		btnBox.setSpacing(20);
		btnBox.setAlignment(Pos.CENTER);

		VBox mainLayout = new VBox();
		mainLayout.getChildren().addAll(titlePane, contentsLayout, btnBox);
		mainLayout.setAlignment(Pos.CENTER);
		mainLayout.setPadding(new Insets(10));
		mainLayout.setSpacing(10);

		this.setLoginEvents(valueNodes, loginBtn, signupBtn);

		return mainLayout;
	}

	public void createSignupStage() {
		//
		Label titleLabel = new Label("Sign Up");
		titleLabel.setFont(new Font(30));
		Label emailLabel = new Label("E-mail");
		Label nameLabel = new Label("Name");
		Label phoneLabel = new Label("PhoneNumber");
		TextField emailField = new TextField();
		TextField nameField = new TextField();
		TextField phoneField = new TextField();
		Button confirmBtn = new Button("Confirm");
		Button cancelBtn = new Button("Cancel");

		Map<String, Node> valueNodes = new LinkedHashMap<String, Node>();

		valueNodes.put("emailField", emailField);
		valueNodes.put("nameField", nameField);
		valueNodes.put("phoneField", phoneField);

		StackPane titleLayout = new StackPane();
		titleLayout.getChildren().add(titleLabel);

		GridPane contentsLayout = new GridPane();
		contentsLayout.setVgap(10);
		contentsLayout.setHgap(10);
		contentsLayout.add(emailLabel, 0, 0);
		contentsLayout.add(nameLabel, 0, 1);
		contentsLayout.add(phoneLabel, 0, 2);
		contentsLayout.add(emailField, 1, 0);
		contentsLayout.add(nameField, 1, 1);
		contentsLayout.add(phoneField, 1, 2);

		HBox btnLayout = new HBox();
		btnLayout.setSpacing(10);
		btnLayout.setAlignment(Pos.BASELINE_CENTER);
		btnLayout.getChildren().addAll(confirmBtn, cancelBtn);

		VBox mainLayout = new VBox();
		mainLayout.setSpacing(20);
		mainLayout.setPadding(new Insets(10));
		mainLayout.getChildren().addAll(titleLayout, contentsLayout, btnLayout);

		Scene scene = new Scene(mainLayout);

		Stage popupStage = new Stage();

		this.setSignupEvents(mainLayout, popupStage, valueNodes, confirmBtn, cancelBtn);

		popupStage.setScene(scene);
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setResizable(false);
		popupStage.show();
	}

	private void setLoginEvents(Map<String, Node> valueNodes, Button loginBtn, Button signupBtn) {
		//
		this.setEnterEvent(mainLayout, loginBtn);
		
		loginBtn.setOnAction(e -> {
			//
			String email = ((TextField) valueNodes.get("emailField")).getText();
			String name = ((TextField) valueNodes.get("nameField")).getText();

			if (loginEventHelper.login(email, name)) {
				Session.putMemberInSession(email, name);
				mainLayout.getTop().setDisable(false);

				ClubPane clubPane = new ClubPane();
				PrimaryScene.defineLoggedInUser(Session.loggedInMemberName);
				clubPane.showMyClubScene();
			} else {
				mainLayout.getTop().setDisable(true);
				AlertBox.alert("Login Failed", "Please check");
			}
		});

		signupBtn.setOnAction(e -> {
			this.createSignupStage();
		});
	}

	private void setSignupEvents(Pane pane,Stage stage, Map<String, Node> valueNodes, Button confirmBtn, Button cancelBtn) {
		//
		this.setEnterEvent(pane, confirmBtn);

		confirmBtn.setOnAction(e -> {
			//
			String email = ((TextField) valueNodes.get("emailField")).getText();
			String name = ((TextField) valueNodes.get("nameField")).getText();
			String phoneNumber = ((TextField) valueNodes.get("phoneField")).getText();

			loginEventHelper.signupMember(email, name, phoneNumber);
			stage.close();
		});

		cancelBtn.setOnAction(e -> {
			stage.close();
		});
	}

	private void setEnterEvent(Pane pane, Button button) {
		//
		pane.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				//
				if (event.getCode() == KeyCode.ENTER) {
					button.fire();
				}
			}
		});
	}
}
