package fxui.pane;

import java.util.LinkedHashMap;
import java.util.Map;

import entity.dto.TravelClubDto;
import fxui.PrimaryScene;
import fxui.event.BoardEventHelper;
import fxui.event.ClubEventHelper;
import fxui.util.ConfirmBox;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClubPane {
	//
	private String loggedInMemberName;
	private String loggedInMemberEmail;
	
	private ClubEventHelper clubEvents;
	private BoardEventHelper boardEvents;
	
	public ClubPane(String email, String name) {
		//
		this.loggedInMemberEmail = email;
		this.loggedInMemberName = name;
		
		this.clubEvents = new ClubEventHelper();
		this.boardEvents = new BoardEventHelper();
	}

	public void showMyClubScene() {
		//내가 가입한 클럽목록 -> 클럽 더블클릭 -> 게시판이용
		VBox mainLayout = new VBox();
		
		StackPane titleLayout = new StackPane();
		Label titleLabel = new Label("My Clubs");
		titleLabel.setFont(new Font(30));
		titleLayout.getChildren().add(titleLabel);
		
		StackPane tableLayout = new StackPane();
		TableView<TravelClubDto> myClubTable = new TableView<TravelClubDto>();
		TableColumn<TravelClubDto, String> nameColumn =
				new TableColumn<TravelClubDto, String>("Name");
		nameColumn.prefWidthProperty().bind(myClubTable.widthProperty().divide(4));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<TravelClubDto, String> introduceColumn =
				new TableColumn<TravelClubDto, String>("Introduce");
		introduceColumn.prefWidthProperty().bind(myClubTable.widthProperty().divide(2));
		introduceColumn.setCellValueFactory(new PropertyValueFactory<>("intro"));
		
		TableColumn<TravelClubDto, String> foundationDayColumn = 
				new TableColumn<TravelClubDto, String>("FoundationDay");
		foundationDayColumn.prefWidthProperty().bind(myClubTable.widthProperty().divide(4));
		foundationDayColumn.setCellValueFactory(new PropertyValueFactory<>("foundationDay"));
		
		myClubTable.getColumns().addAll(nameColumn, introduceColumn, foundationDayColumn);
		//테이블내용 가지고와야함
		//myClubTable.setItems();
		
		tableLayout.getChildren().add(myClubTable);
		
		StackPane buttonLayout = new StackPane();
		Button withdrawalBtn = new Button("Withdrawal");
		buttonLayout.getChildren().add(withdrawalBtn);
		buttonLayout.setAlignment(Pos.BOTTOM_RIGHT);
		
		mainLayout.getChildren().addAll(titleLayout, tableLayout, buttonLayout);
		mainLayout.setPadding(new Insets(10));
		mainLayout.setSpacing(10);
		
		this.setMyClubEvent(myClubTable, withdrawalBtn);

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
		Button searchAllBtn = new Button("Search All");
		searchLayout.setSpacing(10);
		searchLayout.setAlignment(Pos.BASELINE_RIGHT);
		searchLayout.getChildren().addAll(searchField, searchBtn, searchAllBtn);
		
		StackPane tableLayout = new StackPane();
		TableView<TravelClubDto> clubTable = new TableView<TravelClubDto>();
		TableColumn<TravelClubDto, String> nameColumn =
				new TableColumn<TravelClubDto, String>("Name");
		nameColumn.prefWidthProperty().bind(clubTable.widthProperty().divide(4));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<TravelClubDto, String> introduceColumn =
				new TableColumn<TravelClubDto, String>("Introduce");
		introduceColumn.prefWidthProperty().bind(clubTable.widthProperty().divide(2));
		introduceColumn.setCellValueFactory(new PropertyValueFactory<>("intro"));
		
		TableColumn<TravelClubDto, String> foundationDayColumn = 
				new TableColumn<TravelClubDto, String>("FoundationDay");
		foundationDayColumn.prefWidthProperty().bind(clubTable.widthProperty().divide(4));
		foundationDayColumn.setCellValueFactory(new PropertyValueFactory<>("foundationDay"));
		
		clubTable.getColumns().addAll(nameColumn, introduceColumn, foundationDayColumn);
		//테이블 내용 불러와야함.
		//myClubTable.setItems(value);
		
		tableLayout.getChildren().add(clubTable);
		
		StackPane buttonLayout = new StackPane();
		Button joinBtn = new Button("Join");
		buttonLayout.getChildren().add(joinBtn);
		buttonLayout.setAlignment(Pos.BOTTOM_RIGHT);
		
		mainLayout.getChildren().addAll(titleLayout, searchLayout, tableLayout, buttonLayout);
		mainLayout.setPadding(new Insets(10));
		mainLayout.setSpacing(10);
		
		this.setSearchClubEvent(clubTable, searchField,searchBtn, searchAllBtn, joinBtn);
		
		PrimaryScene.changeScene(mainLayout);
	}
	
	public void showCreateClubScene() {
		// 팝업창.
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
	
	private void setMyClubEvent(TableView<TravelClubDto> myClubTable,Button withdrawalBtn ) {
		//
		withdrawalBtn.setOnAction(e ->{
			ObservableList<TravelClubDto> selectedItem, allItem;
			allItem = myClubTable.getItems();
			selectedItem = myClubTable.getSelectionModel().getSelectedItems();
			
			if (ConfirmBox.display("DELETE", "sure to Withdrawal?")) {
				clubEvents.withdrawalClub(selectedItem.iterator().next());
				selectedItem.forEach(allItem::remove);
			}
		});
		
		myClubTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//
				if(event.getClickCount() > 1) {
					ObservableList<TravelClubDto> selectedItem;
					selectedItem = myClubTable.getSelectionModel().getSelectedItems();
					TravelClubDto travelClub= selectedItem.iterator().next();
					boardEvents.showPostings(travelClub);
				}
			}
			
		});
	}
	
	private void setSearchClubEvent(TableView<TravelClubDto> clubTable, 
			TextField searchField, Button searchBtn, Button searchAllBtn, Button joinBtn) {
		//
		String clubName = searchField.getText();
		
		searchBtn.setOnAction(e ->{
			clubEvents.searchClub(clubName);
		});
		
		searchAllBtn.setOnAction(e ->{
			clubEvents.searchAllClub();
		});
		
		joinBtn.setOnAction(e ->{
			ObservableList<TravelClubDto> selectedItem, allItem;
			allItem = clubTable.getItems();
			selectedItem = clubTable.getSelectionModel().getSelectedItems();
			
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
			
			//클럽생성시 보드도함께 생성해야함.
		});
	}
	

}
