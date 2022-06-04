package com.flashcard.controller;

import com.flashcard.event.ShowViewEvent;
//import com.flashcard.listener.ShowCreateSetListener;
import com.flashcard.listener.ShowViewListener;
import com.flashcard.dto.SetDTO;
import com.flashcard.service.MenuService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
        menuService.setPane(root);
        menuService.setRootRightVBox(rootRightVBox);
        menuService.refreshSetTiles();
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
    private void test() throws IOException {


    }

}