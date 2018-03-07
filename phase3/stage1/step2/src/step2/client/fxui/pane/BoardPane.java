package step2.client.fxui.pane;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import step1.share.domain.entity.dto.PostingDto;
import step1.share.domain.entity.dto.TravelClubDto;
import step2.client.fxui.PrimaryScene;
import step2.client.fxui.event.BoardEventHelper;
import step2.client.fxui.util.ConfirmBox;

public class BoardPane {
	//
	private TravelClubDto travelClubDto;
	BoardEventHelper boardEvents;

	public BoardPane(TravelClubDto travelClubDto) {
		//
		this.travelClubDto = travelClubDto;
		boardEvents = new BoardEventHelper(travelClubDto);
	}

	/**
	 * 
	 * 1. 빈테이블에서 선택할시 테이블내의 항목이 없는데도 항목에 관한 버튼이 표시됨 V
	 * 2. ReadCount 안올라감.
	 * 3. 
	 * 
	 */
	public void showBoard() {
		//
		VBox mainPostLayout = new VBox();

		HBox titleLayout = new HBox();
		Label title = new Label("Posting");
		title.prefWidthProperty().bind(titleLayout.widthProperty().divide(2));
		title.setAlignment(Pos.BASELINE_LEFT);
		title.setFont(new Font(20));
		titleLayout.getChildren().addAll(title);

		StackPane postListLayout = new StackPane();
		TableView<PostingDto> postingTable = new TableView<PostingDto>();
		TableColumn<PostingDto, String> titleColumn = new TableColumn<PostingDto, String>("Title");
		titleColumn.prefWidthProperty().bind(postListLayout.widthProperty().divide(3));
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		
		TableColumn<PostingDto, String> writerEmailColumn = new TableColumn<PostingDto, String>("Writer E-mail");
		writerEmailColumn.prefWidthProperty().bind(postListLayout.widthProperty().divide(3));
		writerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("writerEmail"));
		
		TableColumn<PostingDto, String> writtenDateColumn = new TableColumn<PostingDto, String>("Written Date");
		writtenDateColumn.prefWidthProperty().bind(postListLayout.widthProperty().divide(6));
		writtenDateColumn.setCellValueFactory(new PropertyValueFactory<>("writtenDate"));
		
		TableColumn<PostingDto, String> readCountColumn = new TableColumn<PostingDto, String>("Read Count");
		readCountColumn.prefWidthProperty().bind(postListLayout.widthProperty().divide(6));
		readCountColumn.setCellValueFactory(new PropertyValueFactory<>("readCount"));
		postingTable.getColumns().addAll(titleColumn, writerEmailColumn, writtenDateColumn, readCountColumn);
		
		this.initializePostList(postingTable);
		postingTable.getSelectionModel().selectFirst();

		postListLayout.prefWidthProperty().bind(mainPostLayout.widthProperty().divide(1.5));
		postListLayout.getChildren().add(postingTable);

		HBox btnLayout = new HBox();
		StackPane postBtnBox = new StackPane();
		Button newBtn = new Button("New Posting");
		
		postBtnBox.setAlignment(Pos.BASELINE_LEFT);
		postBtnBox.getChildren().addAll(newBtn);

		HBox btnBox = new HBox();
		Button modifyBtn = new Button("Modify");
		StackPane btnPane = new StackPane();
		Button deleteBtn = new Button("Delete");
		Button postBtn = new Button("Post");
		btnPane.getChildren().addAll(deleteBtn, postBtn);
		btnBox.getChildren().addAll(modifyBtn, btnPane);
		btnBox.setAlignment(Pos.BASELINE_RIGHT);

		btnLayout.getChildren().addAll(postBtnBox, btnBox);
		btnLayout.setSpacing(10);
		btnLayout.setAlignment(Pos.BASELINE_RIGHT);
		

		GridPane postingLayout = new GridPane();
		Label subTitle = new Label("Title");
		TextField titleField = new TextField();
		Label article = new Label("Article");
		TextArea articleField = new TextArea();

		this.setPostEvents(postingTable, titleField, articleField, newBtn, postBtn, modifyBtn, deleteBtn);
		this.resetBtnLayout(newBtn, postBtn, modifyBtn, deleteBtn);

		postingLayout.add(subTitle, 0, 0);
		postingLayout.add(article, 0, 1);
		postingLayout.add(titleField, 1, 0);
		postingLayout.add(articleField, 1, 1);
		postingLayout.setVgap(5);
		postingLayout.setHgap(5);

		VBox postDetailLayout = new VBox();
		postDetailLayout.setSpacing(10);
		postDetailLayout.setAlignment(Pos.CENTER);
		postDetailLayout.prefHeightProperty().bind(mainPostLayout.heightProperty().divide(1.3));
		postDetailLayout.getChildren().addAll(postingLayout);

		mainPostLayout.setPadding(new Insets(10));
		mainPostLayout.setSpacing(10);
		mainPostLayout.getChildren().addAll(titleLayout, postListLayout, btnLayout, postDetailLayout);

		PrimaryScene.changeScene(mainPostLayout);
	}

	private void resetBtnLayout(Button newBtn, Button postBtn, Button modifyBtn, Button deleteBtn) {
		//
		newBtn.setVisible(true);
		postBtn.setVisible(false);
		modifyBtn.setVisible(false);
		deleteBtn.setVisible(false);
	}

	private void initializePostList(TableView<PostingDto> postingTable) {
		//
		boardEvents.setPostList(postingTable,travelClubDto);
	}

	private void setPostEvents(TableView<PostingDto> postingTable, TextField titleField, TextArea articleField,
			Button newBtn, Button postBtn, Button modifyBtn, Button deleteBtn) {
		newBtn.setOnAction(e -> {
			newBtn.setVisible(false);
			postBtn.setVisible(true);
			modifyBtn.setVisible(false);
			deleteBtn.setVisible(false);
			titleField.setText(null);
			articleField.setText(null);
		});
		
		postBtn.setOnAction(e ->{
			String title = titleField.getText();
			String article = articleField.getText();
			boardEvents.createPosting(title, article,travelClubDto);
			this.resetBtnLayout(newBtn, postBtn, modifyBtn, deleteBtn);
			this.initializePostList(postingTable);
		});

		postingTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				//
				if (event.getClickCount() == 1 && !(postingTable.getItems().isEmpty())) {
					newBtn.setVisible(true);
					postBtn.setVisible(false);
					modifyBtn.setVisible(true);
					deleteBtn.setVisible(true);
					
					ObservableList<PostingDto> selectedItem;
					selectedItem = postingTable.getSelectionModel().getSelectedItems();
					titleField.setText(selectedItem.iterator().next().getTitle());
					articleField.setText(selectedItem.iterator().next().getContents());

//					PostingDto posting= selectedItem.iterator().next();
//					posting.setReadCount(posting.getReadCount()+1);
//					boardEvents.modifyPosting(posting,titleField, articleField);
					
//					postingTable.getItems().clear();
//					boardEvents.setPostList(postingTable,travelClubDto);

				}
			}

		});

		modifyBtn.setOnAction(e -> {
			newBtn.setVisible(true);
			postBtn.setVisible(false);
			modifyBtn.setVisible(true);
			deleteBtn.setVisible(true);
			ObservableList<PostingDto> selectedItem;
			selectedItem = postingTable.getSelectionModel().getSelectedItems();
			boardEvents.modifyPosting(selectedItem.iterator().next(),titleField, articleField);

			postingTable.getItems().clear();
			boardEvents.setPostList(postingTable,travelClubDto);
		});

		deleteBtn.setOnAction(e -> {
			newBtn.setVisible(true);
			postBtn.setVisible(false);
			modifyBtn.setVisible(true);
			deleteBtn.setVisible(true);
			ObservableList<PostingDto> allItem, selectedItem;
			allItem = postingTable.getItems();
			selectedItem = postingTable.getSelectionModel().getSelectedItems();

			if (ConfirmBox.display("DELETE", "sure to Withdrawal?")) {
				boardEvents.deletePosting(selectedItem.iterator().next());
				selectedItem.forEach(allItem::remove);
			}
			
			postingTable.getItems().clear();
			boardEvents.setPostList(postingTable,travelClubDto);
		});
	}
}
