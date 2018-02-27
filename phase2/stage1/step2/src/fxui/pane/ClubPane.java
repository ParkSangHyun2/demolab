package fxui.pane;

import java.util.LinkedHashMap;
import java.util.Map;

import fxui.PrimaryScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClubPane {
	//
	String loggedInMemberName;
	String loggedInMemberEmail;
	
	public ClubPane(Map<String, String> values) {
		//
		this.loggedInMemberEmail = values.get("email");
		this.loggedInMemberName = values.get("name");
	}

	public void showMyClubScene() {
		//내가 가입한 클럽목록 -> 클럽 더블클릭 -> 게시판이용
		VBox mainLayout = new VBox();
		
		StackPane titleLayout = new StackPane();
		Label titleLabel = new Label("My Clubs");
		titleLabel.setFont(new Font(30));
		titleLayout.getChildren().add(titleLabel);
		
		StackPane tableLayout = new StackPane();
		TableView myClubTable = new TableView();
		tableLayout.getChildren().add(myClubTable);
		
		StackPane buttonLayout = new StackPane();
		Button withdrawalBtn = new Button("Withdrawal");
		buttonLayout.getChildren().add(withdrawalBtn);
		buttonLayout.setAlignment(Pos.BOTTOM_RIGHT);
		
		mainLayout.getChildren().addAll(titleLayout, tableLayout, buttonLayout);
		mainLayout.setPadding(new Insets(10));
		mainLayout.setSpacing(10);

		PrimaryScene.changeScene(mainLayout);
		
	}
	
	public void showSearchClubScene() {
		//전체 리스트와 검색창.
		VBox mainLayout = new VBox();
		
		StackPane titleLayout = new StackPane();
		Label titleLabel = new Label("Find Clubs");
		titleLabel.setFont(new Font(30));
		titleLayout.getChildren().add(titleLabel);
		
		HBox searchLayout = new HBox();
		TextField searchField = new TextField();
		Button searchBtn = new Button("Search");
		searchLayout.getChildren().addAll(searchField, searchBtn);
		
		StackPane tableLayout = new StackPane();
		TableView myClubTable = new TableView();
		tableLayout.getChildren().add(myClubTable);
		
		StackPane buttonLayout = new StackPane();
		Button withdrawalBtn = new Button("Withdrawal");
		buttonLayout.getChildren().add(withdrawalBtn);
		buttonLayout.setAlignment(Pos.BOTTOM_RIGHT);
		
		mainLayout.getChildren().addAll(titleLayout, searchLayout, tableLayout, buttonLayout);
		mainLayout.setPadding(new Insets(10));
		mainLayout.setSpacing(10);
		
		PrimaryScene.changeScene(mainLayout);
	}
	
	public void showCreateClubScene() {
		// 팝업창.
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
		
		Map<String,Node> valueNodes = new LinkedHashMap<String, Node>();
		
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
		
		//this.setSignupEvents(popupStage, valueNodes, confirmBtn, cancelBtn);
		
		popupStage.setScene(scene);
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setResizable(false);
		popupStage.show();
	}

}
