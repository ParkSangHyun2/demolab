package fxui;

import fxui.pane.ClubPane;
import fxui.pane.LoginPane;
import fxui.pane.MemberPane;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PrimaryScene {
	//
	private static Stage window;
	private static BorderPane mainLayout;
	
	MenuBar menuBar;

	
	public static void changeScene(Pane pane) {mainLayout.setCenter(pane);}
	public static void defineLoggedInUser(String userName) {mainLayout.setBottom(markOnUser(userName));}
	public static void resetScene() {mainLayout.getTop().setDisable(true);
	mainLayout.setCenter(new LoginPane(window, mainLayout).initLoginPane());}
	
	@SuppressWarnings("static-access")
	public PrimaryScene(Stage window, BorderPane mainLayout) {
		//
		this.window = window;
		this.mainLayout = mainLayout;
	}

	public Parent createInitialPane() {
		//	
		Menu userMenu = new Menu("User");
		MenuItem loggedUserMenu = new MenuItem("My Account");
		MenuItem logoutMenu = new MenuItem("Logout");
		
		Menu clubMenu = new Menu("Club");
		MenuItem myClubsMenu = new MenuItem("My Clubs");
		MenuItem findClubsMenu = new MenuItem("Find Clubs");
		MenuItem createClubMenu = new MenuItem("Create Club");
		
		LoginPane loginPane = new LoginPane(window, mainLayout);
		
		userMenu.getItems().addAll(loggedUserMenu,logoutMenu);	
		clubMenu.getItems().addAll(myClubsMenu, findClubsMenu, createClubMenu);
		
		menuBar = new MenuBar();
		menuBar.getMenus().addAll(userMenu, clubMenu);
		
		this.setUserMenuEvent(loggedUserMenu,logoutMenu);
		this.setClubMenuEvent(myClubsMenu, findClubsMenu, createClubMenu);
		
		mainLayout.setTop(menuBar);
		mainLayout.setCenter(loginPane.initLoginPane());
		mainLayout.getTop().setDisable(true);
		mainLayout.setBottom(markOnUser("Undefined User"));
		
		menuBar.setDisable(true);
		
		return mainLayout;
	}
	
	private static Pane markOnUser(String name) {
		StackPane bottomLayout = new StackPane();
		Label loggedUser = new Label(name);
		loggedUser.setTextFill(Color.GRAY);
		bottomLayout.getChildren().add(loggedUser);
		return bottomLayout;
	}
	
	private void setUserMenuEvent(MenuItem loggedUserMenu, MenuItem logoutMenu) {
		//
		loggedUserMenu.setOnAction(e ->{
			MemberPane memberPane = new MemberPane();
			memberPane.showMyInfomation();
		});
		
		logoutMenu.setOnAction(e ->{
			PrimaryScene.resetScene();
		});
		
	}
	private void setClubMenuEvent(MenuItem myClubsMenu, MenuItem findClubsMenu, MenuItem createClubMenu) {
		//
		ClubPane clubPane = new ClubPane(Session.loggedInMemberEmail, Session.loggedInMemberName);
		PrimaryScene.defineLoggedInUser(Session.loggedInMemberName);
		
		myClubsMenu.setOnAction(e ->{
			clubPane.showMyClubScene();
		});
		
		findClubsMenu.setOnAction(e ->{
			clubPane.showSearchClubScene();
		});
		
		createClubMenu.setOnAction(e ->{
			clubPane.showCreateClubScene();
		});
	}

}
