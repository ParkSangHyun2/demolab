package fxui.pane;


import fxui.event.LoginEventHelper;
import fxui.util.AlertBox;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginPane {
	//
	private Stage window;
	private BorderPane mainLayout;
	
	private Label titleLabel;
	private 	Label emailLabel;
	private Label nameLabel;
	private TextField emailField;
	private TextField nameField;
	private Button loginBtn;
	private Button signupBtn;
	
	LoginEventHelper loginEventHelper;
	
	public LoginPane(Stage window, BorderPane mainLayout){
		//
		this.window = window;
		this.mainLayout = mainLayout;
		loginEventHelper = new LoginEventHelper();
		
		titleLabel = new Label("Login");
		emailLabel = new Label("E-mail");
		nameLabel = new Label("Name");
		emailField = new TextField();
		nameField = new TextField();
		loginBtn = new Button("Login");
		signupBtn = new Button("SignUp");
	}
	
	public Pane initLoginPane() {
		//
		GridPane contentsLayout = new GridPane();
			
		HBox btnBox = new HBox();
		btnBox.getChildren().addAll(loginBtn, signupBtn);
		btnBox.setSpacing(20);
		btnBox.setAlignment(Pos.CENTER);
		
		StackPane titlePane = new StackPane();
		titlePane.getChildren().add(titleLabel);
		titlePane.setAlignment(Pos.CENTER);
		
		titleLabel.setFont(Font.font(30));
		
		contentsLayout.add(titlePane, 1, 0);
		contentsLayout.add(emailLabel, 0, 1);
		contentsLayout.add(nameLabel, 0, 2);
		contentsLayout.add(emailField, 1, 1);
		contentsLayout.add(nameField, 1, 2);
		contentsLayout.add(btnBox, 1, 3);
		
		contentsLayout.setHgap(20);
		contentsLayout.setVgap(10);

		contentsLayout.setAlignment(Pos.CENTER);
		
		this.evokeEvent();
		
		return contentsLayout;
	}
	
	private void evokeEvent() {
		loginBtn.setOnAction(e ->{
			//
			if(loginEventHelper.login()) {
				mainLayout.getTop().setDisable(false);
				//다음씬으로 이동-> 첫화면 : 나의클럽정보
			}else {
				AlertBox.alert("Login Failed", "Please check");
			}
		});
		
		signupBtn.setOnAction(e ->{
			// 회원가입팝업스테이지작성.
		});
	}
}
