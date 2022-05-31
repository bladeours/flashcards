package com.flashcard.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class createSetController implements Initializable {

    @FXML
    Pane root;
    @FXML
    ScrollPane addSentencesScrollPane;
    @FXML
    Button addSentenceButton;
    @FXML
    VBox addSentencesVBox;
    @FXML
    ComboBox<String> colorComboBox;

    @Autowired
    private DatabaseController databaseController;

    public createSetController(DatabaseController databaseController) {
        System.out.println("loefaefael");
        this.databaseController = databaseController;
    }



    public createSetController() {
        System.out.println("bez niczego");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initialize");
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "1",
                        "2",
                        "3"
                );
        colorComboBox.setItems(options);
    }

    @FXML
    public void addSentence() {
        Label firstSentenceLabel = new Label("FirstSentence");
        firstSentenceLabel.getStyleClass().add("firstSentenceLabel");

        TextField firstSentenceTextField = new TextField();
        firstSentenceTextField.getStyleClass().add("firstSentenceTextField");

        Label secondSentenceLabel = new Label("SecondSentence");
        secondSentenceLabel.getStyleClass().add("secondSentenceLabel");

        TextField secondSentenceTextField = new TextField();
        secondSentenceTextField.getStyleClass().add("secondSentenceTextField");

        Button removeButton = new Button("X");
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button button = (Button) actionEvent.getSource();
                HBox hBox = (HBox) button.getParent();
                VBox vBox = (VBox) hBox.getParent();
                vBox.getChildren().remove(hBox);
            }
        });

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().add(firstSentenceLabel);
        hbox.getChildren().add(firstSentenceTextField);
        hbox.getChildren().add(secondSentenceLabel);
        hbox.getChildren().add(secondSentenceTextField);
        hbox.getChildren().add(removeButton);

        addSentencesVBox.getChildren().add(hbox);
    }

    @FXML
    public void backToMenu() throws IOException {
        Stage stage = (Stage) root.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/flashcard/view/menuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void saveSet(){
        System.out.println("saveSet() method");
        databaseController.test();

        //TODO

    }


}
