package com.flashcard;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createSetView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();
        System.out.println("tworze nowy set :)");
//        stage.setTitle("lol");
    }



    @FXML
    private void exit(){
//        Platform.exit();
    }
}