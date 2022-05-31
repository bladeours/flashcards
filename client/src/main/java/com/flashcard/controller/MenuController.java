package com.flashcard.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
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

    public MenuController() {
    }

    @FXML
    private void createNewSet() throws IOException {
        stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/flashcard/view/createSetView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }



    @FXML
    private void exit(){
        Platform.exit();
    }
}