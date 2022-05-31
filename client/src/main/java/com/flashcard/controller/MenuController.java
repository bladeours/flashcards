package com.flashcard.controller;

import com.flashcard.event.ShowViewEvent;
//import com.flashcard.listener.ShowCreateSetListener;
import com.flashcard.listener.ShowViewListener;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuController implements Initializable {
    @FXML
    Pane root;
    @FXML
    Button createNewButton;
    @FXML
    Button scoresButton;
    @FXML
    Button changeUserButton;
    @FXML
    Button exitButton;
    private Stage stage;



    private ApplicationContext applicationContext;
    private final CreateSetController createSetController;


    private final ShowViewListener stageListener;
    private final ServerConnectionController serverConnectionController;
    public MenuController(CreateSetController createSetController, ShowViewListener stageListener,
                          ServerConnectionController serverConnectionController) {
        this.createSetController = createSetController;
        this.stageListener = stageListener;
        System.out.println("MenuController constructor");
        this.serverConnectionController = serverConnectionController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    createSetController.saveSet();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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


}