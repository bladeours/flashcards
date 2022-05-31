package com.flashcard.controller;

import com.flashcard.event.ShowViewEvent;
//import com.flashcard.listener.ShowCreateSetListener;
import com.flashcard.listener.ShowViewListener;
import javafx.application.Platform;
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


    private final ShowViewListener stageListener;
    private final ServerConnectionController serverConnectionController;
    public MenuController(ShowViewListener stageListener, ServerConnectionController serverConnectionController) {
        this.stageListener = stageListener;
        System.out.println("MenuController constructor");
        this.serverConnectionController = serverConnectionController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                serverConnectionController.test();
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

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    static class StageReadyEvent extends ApplicationEvent {

        public StageReadyEvent(Stage source){
            super(source);
        }

        public Stage getStage(){
            return (Stage) getSource();
        }
    }
}