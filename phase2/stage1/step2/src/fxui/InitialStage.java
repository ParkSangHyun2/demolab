package fxui;

import fxui.pane.LoginPane;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class InitialStage {
	//
	private Stage window;
	private BorderPane mainLayout;
	
	MenuBar menuBar;
	public Menu clubMenu = new Menu("Club");
	public MenuItem myClubs;
	public MenuItem findClubs;
	public MenuItem createClub;	
	
	public InitialStage(Stage window, BorderPane mainLayout) {
		//
		this.window = window;
		this.mainLayout = mainLayout;
	}

	public Parent createInitialStage() {
		//	
		LoginPane loginPane = new LoginPane(window, mainLayout);
		
		clubMenu.getItems().addAll(
				myClubs = new MenuItem("My Clubs"),
				findClubs = new MenuItem("Find Clubs"),
				createClub = new MenuItem("Create Club")
				);
		
		menuBar = new MenuBar();
		menuBar.getMenus().addAll(clubMenu);
		
		this.setMenuEvent(myClubs);
		this.setMenuEvent(findClubs);
		this.setMenuEvent(createClub);
		
		mainLayout.setTop(menuBar);
		mainLayout.setCenter(loginPane.initLoginPane());
		mainLayout.getTop().setDisable(true);
		
		menuBar.setDisable(true);
		
		return mainLayout;
	}
	
	private void setMenuEvent(MenuItem menu) {
		//
		menu.setOnAction(e ->{
			
		});
	}

}
