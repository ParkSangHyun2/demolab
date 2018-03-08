/*
 * COPYRIGHT (c) Nextree Consulting 2018
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:shpark@nextree.co.kr">Park sanghyun</a>
 * @since 2018. 3. 4.
 */

package step2.client.fxui.pane;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import step1.share.domain.entity.dto.TravelClubDto;
import step2.client.fxui.PrimaryScene;
import step2.client.fxui.Session;
import step2.client.fxui.event.ClubEventHelper;
import step2.client.fxui.util.ConfirmBox;

public class ClubPane {
	//
	private ClubEventHelper clubEvents;
	private VBox mainLayout;
	private StackPane titleLayout;
	private StackPane tableLayout;
	private StackPane buttonLayout;
	private TableView<TravelClubDto> travelClubTable;
	
	public ClubPane(String email, String name) {
		//
		this.clubEvents = new ClubEventHelper();
		this.mainLayout = new VBox();
		this.titleLayout = new StackPane();
		this.tableLayout = new StackPane();
		this.buttonLayout = new StackPane();
		this.travelClubTable = drawTravelClubTable();
	}

	public void showMyClubScene() {
		setTitleLayout("My Clubs");
		Button withdrawalButton = new Button("Withdraw");
		setButtonLayout(withdrawalButton);
		setMyClubEvent(withdrawalButton);
		initializeMyClubs();
		setTableLayout();
		changeScene(titleLayout, tableLayout, buttonLayout);
	}
	
	public void showSearchClubScene() {
		// Search Pane
		HBox searchLayout = new HBox();
		TextField searchField = new TextField();
		Button searchBtn = new Button("Search");
		Button searchAllBtn = new Button("Search All");
		searchLayout.setSpacing(10);
		searchLayout.setAlignment(Pos.BASELINE_RIGHT);
		searchLayout.getChildren().addAll(searchField, searchBtn, searchAllBtn);

		setTitleLayout("Find Clubs");
		Button joinButton = new Button("Join");
		setButtonLayout(joinButton);
		travelClubTable.getItems().clear();
		setTableLayout();
		setSearchClubEvent(searchField, searchBtn, searchAllBtn, joinButton);
		changeScene(titleLayout, searchLayout, tableLayout, buttonLayout);
	}

	public void showCreateClubScene() {
		// Create Club PopUp
		Label titleLabel = new Label("New TravelClub");
		titleLabel.setFont(new Font(30));
		Label clubNameLabel = new Label("Club Name");
		Label clubIntroduceLabel = new Label("Club Introduce");
		TextField clubNameField = new TextField();
		TextField clubIntroduceField = new TextField();
		Button confirmBtn = new Button("Confirm");
		Button cancelBtn = new Button("Cancel");
		
		Map<String,Node> valueNodes = new LinkedHashMap<String, Node>();
		
		valueNodes.put("clubNameField", clubNameField);
		valueNodes.put("clubIntroduceField", clubIntroduceField);
		
		StackPane titleLayout = new StackPane();
		titleLayout.getChildren().add(titleLabel);
		
		GridPane contentsLayout = new GridPane();
		contentsLayout.setVgap(10);
		contentsLayout.setHgap(10);
		contentsLayout.add(clubNameLabel, 0, 0);
		contentsLayout.add(clubIntroduceLabel, 0, 1);
		contentsLayout.add(clubNameField, 1, 0);
		contentsLayout.add(clubIntroduceField, 1, 1);
		
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
		
		this.setCreateClubEvent(popupStage, confirmBtn, cancelBtn, valueNodes);
		
		popupStage.setScene(scene);
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setResizable(false);
		popupStage.show();
	}
	
	private void initializeMyClubs() {
		//
		travelClubTable.getItems().clear();
		clubEvents.searchMyClubs(travelClubTable, Session.loggedInMemberEmail);
	}
	
	private void setMyClubEvent(Button withdrawalBtn) {
		//
		withdrawalBtn.setOnAction(e ->{
			ObservableList<TravelClubDto> selectedItem, allItem;
			allItem = travelClubTable.getItems();
			selectedItem = travelClubTable.getSelectionModel().getSelectedItems();
			
			if (ConfirmBox.display("DELETE", "sure to Withdrawal?")) {
				clubEvents.withdrawalClub(selectedItem.iterator().next());
				selectedItem.forEach(allItem::remove);
			}
		});
		
		travelClubTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//
				if(event.getButton().equals(MouseButton.PRIMARY) 
						&& event.getClickCount() > 1) {
					if (!travelClubTable.getSelectionModel().isEmpty()) {
						ObservableList<TravelClubDto> selectedItem = travelClubTable.getSelectionModel().getSelectedItems();						
						TravelClubDto travelClub = selectedItem.iterator().next();
						BoardPane boardPane = new BoardPane(travelClub);
						boardPane.showBoard();						
					}
				}
			}
		});
	}
	
	private void setSearchClubEvent(TextField searchField, Button searchBtn, Button searchAllBtn, Button joinBtn) {
		//
		searchBtn.setOnAction(e ->{
			String clubName = searchField.getText();
			clubEvents.searchClub(clubName, travelClubTable);
		});
		
		searchAllBtn.setOnAction(e ->{
			clubEvents.searchAllClub(travelClubTable);
		});
		
		joinBtn.setOnAction(e ->{
			ObservableList<TravelClubDto> selectedItem;
			selectedItem = travelClubTable.getSelectionModel().getSelectedItems();
			
			if (ConfirmBox.display("Join", "sure to Join?")) {
				clubEvents.joinClub(selectedItem.iterator().next());
			}
		});

	}
	
	private void setCreateClubEvent(Stage popupStage, Button confirmBtn, Button cancelBtn, Map<String, Node> valueNodes){
		//
		cancelBtn.setOnAction(e ->{
			popupStage.close();
		});
		
		confirmBtn.setOnAction(e ->{
			String clubName = ((TextField)valueNodes.get("clubNameField")).getText();
			String clubIntroduce = ((TextField)valueNodes.get("clubIntroduceField")).getText();
			
			clubEvents.createClub(clubName, clubIntroduce);
			popupStage.close();
			showMyClubScene();
		});
	}

	private void setTitleLayout(String titleText) {
		titleLayout.getChildren().clear();
		Label titleLabel = new Label(titleText);
		titleLabel.setFont(new Font(30));
		titleLayout.getChildren().add(titleLabel);
	}
	
	private void setTableLayout() {
		tableLayout.getChildren().clear();
		tableLayout.getChildren().add(travelClubTable);
	}
	
	private void setButtonLayout(Button... buttons) {
		buttonLayout.getChildren().clear();
		buttonLayout.getChildren().addAll(buttons);
		buttonLayout.setAlignment(Pos.BOTTOM_RIGHT);		
	}

	private void changeScene(Pane... layouts) {
		mainLayout.getChildren().clear();
		mainLayout.getChildren().addAll(layouts);
		mainLayout.setPadding(new Insets(10));
		mainLayout.setSpacing(10);
		PrimaryScene.changeScene(mainLayout);
	}
	
	@SuppressWarnings("unchecked")
	private TableView<TravelClubDto> drawTravelClubTable() {
		travelClubTable = new TableView<TravelClubDto>();
		TableColumn<TravelClubDto, String> nameColumn =
				new TableColumn<TravelClubDto, String>("Name");
		nameColumn.prefWidthProperty().bind(travelClubTable.widthProperty().divide(4));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<TravelClubDto, String> introduceColumn =
				new TableColumn<TravelClubDto, String>("Introduce");
		introduceColumn.prefWidthProperty().bind(travelClubTable.widthProperty().divide(2));
		introduceColumn.setCellValueFactory(new PropertyValueFactory<>("intro"));
		
		TableColumn<TravelClubDto, String> foundationDayColumn = 
				new TableColumn<TravelClubDto, String>("FoundationDay");
		foundationDayColumn.prefWidthProperty().bind(travelClubTable.widthProperty().divide(4));
		foundationDayColumn.setCellValueFactory(new PropertyValueFactory<>("foundationDay"));
		
		travelClubTable.getColumns().addAll(nameColumn, introduceColumn, foundationDayColumn);
		return travelClubTable;
	}
	
}
