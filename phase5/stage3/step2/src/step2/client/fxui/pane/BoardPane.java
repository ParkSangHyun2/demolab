package step2.client.fxui.pane;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	private BoardEventHelper boardEvents;
	private Map<String, Button> buttons;

	public BoardPane(TravelClubDto travelClubDto) {
		//
		this.travelClubDto = travelClubDto;
		this.boardEvents = new BoardEventHelper();
		this.buttons = new LinkedHashMap<String, Button>();
	}

	private void resetBtnLayout() {
		//
		this.setVisibleBtns(true, false, false, false);
	}

	private void initializePostList(TableView<PostingDto> postingTable) {
		//
		boardEvents.setPostList(postingTable,travelClubDto);
	}

	private void setPostEvents(TableView<PostingDto> postingTable, TextField titleField, TextArea articleField) {
		buttons.get("new").setOnAction(e -> {
			this.setVisibleBtns(false, true, false, false);
			titleField.setText(null);
			articleField.setText(null);
		});
		
		buttons.get("post").setOnAction(e ->{
			String title = titleField.getText();
			String article = articleField.getText();
			boardEvents.createPosting(title, article,travelClubDto);
			this.resetBtnLayout();
			this.initializePostList(postingTable);
		});

		postingTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
			//
			ObservableList<PostingDto> selectedItem
			= postingTable.getSelectionModel().getSelectedItems();
			int selectedIndex;
			
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 1 && !(postingTable.getItems().isEmpty())
						&& !selectedItem.isEmpty()) {
					setVisibleBtns(true, false, true, true);
					titleField.setText(selectedItem.iterator().next().getTitle());
					articleField.setText(selectedItem.iterator().next().getContents());
					

					/**
					 * If you click the mouse, you should increase the number in the 'readCount' column of TableView.
					 * sanghyun..
					 */
					PostingDto posting = selectedItem.iterator().next(); 
					selectedIndex = postingTable.getSelectionModel().getSelectedIndex();
					posting.setReadCount(
							posting.getReadCount()+1
							);
					boardEvents.increaseReadCount(posting);
					initializePostList(postingTable);
					postingTable.getSelectionModel().select(selectedIndex);
				}
			}

		});

		buttons.get("modify").setOnAction(e -> {
			this.setVisibleBtns(true, false, true, true);
			ObservableList<PostingDto> selectedItem;
			selectedItem = postingTable.getSelectionModel().getSelectedItems();
			boardEvents.modifyPosting(selectedItem.iterator().next(),titleField, articleField);

			this.showBoard();
		});

		buttons.get("delete").setOnAction(e -> {
			this.setVisibleBtns(true, false, true, true);
			ObservableList<PostingDto> allItem, selectedItem;
			allItem = postingTable.getItems();
			selectedItem = postingTable.getSelectionModel().getSelectedItems();

			if (ConfirmBox.display("DELETE", "sure to Withdrawal?")) {
				boardEvents.deletePosting(selectedItem.iterator().next());
				selectedItem.forEach(allItem::remove);
			}
			this.showBoard();
		});
	}
	
	/*
	Set the visible of the button
	Sequence : (newBtn, postBtn, modifyBtn, deleteBtn)
	*/
	private void setVisibleBtns(boolean...switchs) {
		//
		Iterator<String> btnSequence = buttons.keySet().iterator();
		for(int i=0; i<switchs.length; i++) {
			buttons.get(btnSequence.next()).setVisible(switchs[i]);
		}
	}
	
	@SuppressWarnings("unchecked")
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

		postListLayout.prefWidthProperty().bind(mainPostLayout.widthProperty().divide(1.5));
		postListLayout.getChildren().add(postingTable);

		HBox btnLayout = new HBox();
		
		Button newBtn = new Button("New Posting");
		Button modifyBtn = new Button("Modify");
		
		StackPane stackBtns = new StackPane();
		Button deleteBtn = new Button("Delete");
		Button postBtn = new Button("Post");
		
		buttons.put("new", newBtn);
		buttons.put("post", postBtn);
		buttons.put("modify", modifyBtn);
		buttons.put("delete", deleteBtn);
		
		
		stackBtns.getChildren().addAll(deleteBtn, postBtn);

		btnLayout.getChildren().addAll(newBtn, modifyBtn, stackBtns);
		btnLayout.setSpacing(10);
		btnLayout.setAlignment(Pos.BASELINE_RIGHT);
		

		GridPane postingLayout = new GridPane();
		Label subTitle = new Label("Title");
		TextField titleField = new TextField();
		Label article = new Label("Article");
		TextArea articleField = new TextArea();

		this.setPostEvents(postingTable, titleField, articleField);
		this.resetBtnLayout();

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
}
