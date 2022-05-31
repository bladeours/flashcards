package com.flashcard.controller;

import com.flashcard.event.ShowViewEvent;
import com.flashcard.listener.ShowViewListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;


@Component
public class CreateSetController implements Initializable {

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

    private final ShowViewListener showViewListener;
    private final ServerConnectionController serverConnectionController;


    public CreateSetController(ShowViewListener showViewListener, ServerConnectionController serverConnectionController) {
        this.showViewListener = showViewListener;
        this.serverConnectionController = serverConnectionController;
        System.out.println("CreateSetController constructor");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    public void backToMenu(){
        showViewListener.getApplicationContext().publishEvent(new ShowViewEvent((Stage) root.getScene().getWindow(),
                "/com/flashcard/view/menuView.fxml"));


    }

    @FXML
    public void saveSet(){
        System.out.println("saveSet() method");
        serverConnectionController.test();
        //TODO
    }


}
