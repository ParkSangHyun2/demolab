package fxui;

import fxui.pane.ClubPane;
import fxui.pane.LoginPane;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PrimaryScene {
	//
	private static Stage window;
	private static BorderPane mainLayout;
	
	MenuBar menuBar;

	
	public static void changeScene(Pane pane) {mainLayout.setCenter(pane);}
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
		Menu clubMenu = new Menu("Club");
		MenuItem myClubsMenu;
		MenuItem findClubsMenu;
		MenuItem createClubMenu;	
		
		LoginPane loginPane = new LoginPane(window, mainLayout);
		
		clubMenu.getItems().addAll(
				myClubsMenu = new MenuItem("My Clubs"),
				findClubsMenu = new MenuItem("Find Clubs"),
				createClubMenu = new MenuItem("Create Club")
				);
		
		menuBar = new MenuBar();
		menuBar.getMenus().addAll(clubMenu);
		
		this.setMenuEvent(myClubsMenu, findClubsMenu, createClubMenu);
		
		mainLayout.setTop(menuBar);
		mainLayout.setCenter(loginPane.initLoginPane());
		mainLayout.getTop().setDisable(true);
		
		menuBar.setDisable(true);
		
		return mainLayout;
	}
	
	private void setMenuEvent(MenuItem myClubsMenu, MenuItem findClubsMenu, MenuItem createClubMenu) {
		//
		ClubPane clubPane = new ClubPane(Session.loggedInMember);
		
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
