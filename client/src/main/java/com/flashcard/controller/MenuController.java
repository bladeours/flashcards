package com.flashcard.controller;

import com.flashcard.event.ShowViewEvent;
//import com.flashcard.listener.ShowCreateSetListener;
import com.flashcard.listener.ShowViewListener;
import com.flashcard.service.MenuService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuController implements Initializable {
    @FXML
    private ScrollPane rootRightScrollPane;
    @FXML
    private Pane root;
    @FXML
    private Button createNewButton;
    @FXML
    private Button scoresButton;
    @FXML
    private Button changeUserButton;
    @FXML
    private Button exitButton;
    @FXML
    private VBox rootRightVBox;

    private final MenuService menuService;

    private final CreateSetController createSetController;


    private final ShowViewListener stageListener;
    private final ServerConnectionController serverConnectionController;
    public MenuController(MenuService menuService, CreateSetController createSetController, ShowViewListener stageListener,
                          ServerConnectionController serverConnectionController) {
        this.menuService = menuService;
        this.createSetController = createSetController;
        this.stageListener = stageListener;
        System.out.println("MenuController constructor");
        this.serverConnectionController = serverConnectionController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void createNewSet() throws IOException {
        stageListener.getApplicationContext().publishEvent(new ShowViewEvent((Stage) root.getScene().getWindow()
                ,"/com/flashcard/view/createSetView.fxml"));
    }

    @FXML
    private void exit(){
        Platform.exit();
    }

    @FXML
    private void test(){

        HBox rowSentenceHBox;

        int rootSize = rootRightVBox.getChildren().size();
        HBox tempHBox = new HBox();
        if(rootSize>0) {
            tempHBox = (HBox) rootRightVBox.getChildren().get(rootSize - 1);
        }
        int size = tempHBox.getChildren().size();
        System.out.println(size);
        if(size %2 == 0){
            rowSentenceHBox = new HBox();
            rowSentenceHBox.getStyleClass().add("rowSentenceHBox");
            rootRightVBox.getChildren().add(rowSentenceHBox);
        }else{
            rowSentenceHBox = (HBox) rootRightVBox.getChildren().get(rootSize-1);
        }



        VBox rootSetsVBox = new VBox();
        rootSetsVBox.setAlignment(Pos.TOP_CENTER);
        rootSetsVBox.getStyleClass().add("rootSetsVBox");

        Label setNameLabel = new Label("Set name");
        setNameLabel.getStyleClass().add("setNameLabel");

        HBox setButtonsHBox = new HBox();
        setButtonsHBox.getStyleClass().add("setButtonsHBox");
        rootSetsVBox.getChildren().addAll(setNameLabel,setButtonsHBox);

        Button editButton = new Button("Edit");
        editButton.getStyleClass().add("setButtons");
        Button addButton = new Button("Add");
        addButton.getStyleClass().add("setButtons");
        Button removeButton = new Button("Remove");
        removeButton.getStyleClass().add("setButtons");

        setButtonsHBox.getChildren().addAll(editButton, addButton, removeButton);

        rowSentenceHBox.getChildren().add(rootSetsVBox);

        //        rootRightVBox.getChildren().add()
    }

}