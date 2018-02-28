package fxui.pane;

import java.util.LinkedHashMap;
import java.util.Map;

import entity.dto.MemberDto;
import fxui.PrimaryScene;
import fxui.Session;
import fxui.event.MemberEventHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MemberPane {
	//
	MemberEventHelper memberEvent;

	public MemberPane() {
		memberEvent = new MemberEventHelper();
	}

	public void showMyInfomation() {
		//
		Label titleLabel = new Label("My Account");
		titleLabel.setFont(new Font(30));
		Label emailLabel = new Label("E-mail");
		Label nameLabel = new Label("Name");
		Label phoneLabel = new Label("PhoneNumber");
		Label nickNameLabel = new Label("NickName");
		Label birthDayLabel = new Label("BirthDay");
		TextField emailField = new TextField();
		emailField.setDisable(true);
		TextField nameField = new TextField();
		nameField.setDisable(true);
		TextField phoneField = new TextField();
		TextField nickNameField = new TextField();
		TextField birthDayField = new TextField();
		Button modifyBtn = new Button("Modify");
		Button cancelBtn = new Button("Cancel");

		Map<String, Node> valueNodes = new LinkedHashMap<String, Node>();

		valueNodes.put("email", emailField);
		valueNodes.put("name", nameField);
		valueNodes.put("phoneNumber", phoneField);
		valueNodes.put("nickName", nickNameField);
		valueNodes.put("birthDay", birthDayField);

		this.setMyInfo(valueNodes);

		StackPane titleLayout = new StackPane();
		titleLayout.getChildren().add(titleLabel);

		GridPane contentsLayout = new GridPane();
		contentsLayout.setVgap(10);
		contentsLayout.setHgap(10);
		contentsLayout.add(emailLabel, 0, 0);
		contentsLayout.add(nameLabel, 0, 1);
		contentsLayout.add(phoneLabel, 0, 2);
		contentsLayout.add(nickNameLabel, 0, 3);
		contentsLayout.add(birthDayLabel, 0, 4);
		contentsLayout.add(emailField, 1, 0);
		contentsLayout.add(nameField, 1, 1);
		contentsLayout.add(phoneField, 1, 2);
		contentsLayout.add(nickNameField, 1, 3);
		contentsLayout.add(birthDayField, 1, 4);
		contentsLayout.setAlignment(Pos.BASELINE_CENTER);

		HBox btnLayout = new HBox();
		btnLayout.setSpacing(10);
		btnLayout.setAlignment(Pos.BASELINE_CENTER);
		btnLayout.getChildren().addAll(modifyBtn, cancelBtn);

		VBox mainLayout = new VBox();
		mainLayout.setSpacing(20);
		mainLayout.setPadding(new Insets(10));
		mainLayout.getChildren().addAll(titleLayout, contentsLayout, btnLayout);
		mainLayout.setAlignment(Pos.CENTER);

		this.setMemberEvent(valueNodes, modifyBtn, cancelBtn);

		PrimaryScene.changeScene(mainLayout);
	}

	private void setMyInfo(Map<String, Node> valueNodes) {
		//
		MemberDto member = memberEvent.getMemberDto(Session.loggedInMemberName);

		((TextField) valueNodes.get("email")).setText(member.getEmail());
		((TextField) valueNodes.get("name")).setText(member.getName());
		((TextField) valueNodes.get("phoneNumber")).setText(member.getPhoneNumber());
		((TextField) valueNodes.get("nickName")).setText(member.getNickName());
		((TextField) valueNodes.get("birthDay")).setText(member.getBirthDay());

	}

	private void setMemberEvent(Map<String, Node> valueNodes, Button modifyBtn, Button cancelBtn) {
		//
		modifyBtn.setOnAction(e -> {
			String phoneNumber = ((TextField) valueNodes.get("phoneNumber")).getText();
			String nickName = ((TextField) valueNodes.get("nickName")).getText();
			String birthDay = ((TextField) valueNodes.get("birthDay")).getText();
			String memberName = Session.loggedInMemberName;
			
			memberEvent.modifyMember(memberName, phoneNumber, nickName, birthDay);
			ClubPane clubPane = new ClubPane(Session.loggedInMemberEmail,Session.loggedInMemberName);
			clubPane.showMyClubScene();
		});

		cancelBtn.setOnAction(e -> {
			ClubPane clubPane = new ClubPane(Session.loggedInMemberEmail, Session.loggedInMemberName);
			clubPane.showMyClubScene();
		});
	}

}
