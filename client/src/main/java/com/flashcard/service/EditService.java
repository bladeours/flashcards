package com.flashcard.service;

import com.flashcard.controller.ServerConnectionController;
import com.flashcard.dto.FlashcardDTO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class EditService {

    private final ServerConnectionController serverConnectionController;

    public EditService(ServerConnectionController serverConnectionController) {
        this.serverConnectionController = serverConnectionController;
    }

    public void addSentencesToLayout(VBox addSentencesVBox, int setId) throws IOException {

        ArrayList<FlashcardDTO> flashcards = serverConnectionController.getFlashcardsForSet(String.valueOf(setId));
        System.out.println(flashcards);
        addSentencesVBox.getChildren().clear();

        HBox headerHBox = new HBox();
        headerHBox.prefWidth(1000);
        headerHBox.setAlignment(Pos.TOP_CENTER);
        headerHBox.setSpacing(20);

        Label firstSentenceLabel = new Label("First sentence");
        Label secondSentenceLabel = new Label("Second sentence");

        headerHBox.getChildren().addAll(firstSentenceLabel,secondSentenceLabel);
        addSentencesVBox.getChildren().add(headerHBox);

        for (FlashcardDTO flashcard : flashcards) {

            TextField firstSentenceTextField = new TextField();
            firstSentenceTextField.getStyleClass().add("firstSentenceTextField");
            firstSentenceTextField.setText(flashcard.getFirstSentence());

            TextField secondSentenceTextField = new TextField();
            secondSentenceTextField.getStyleClass().add("secondSentenceTextField");
            secondSentenceTextField.setText(flashcard.getSecondSentence());

            Button removeButton = new Button("X");
            removeButton.getStyleClass().add("removeButton");

            removeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                   removeRow(actionEvent);
                }
            });

            HBox smallerHBox =  new HBox();
            smallerHBox.getStyleClass().add("removeButtonHBox");

            HBox hbox = new HBox();
            hbox.setAlignment(Pos.TOP_CENTER);
            hbox.setPrefWidth(1000.0);
            hbox.getStyleClass().add("sentencesHBox");
            hbox.getProperties().put("flashcardId", flashcard.getId());
            smallerHBox.getChildren().addAll(secondSentenceTextField,removeButton);
            hbox.getChildren().addAll(firstSentenceTextField,smallerHBox);
            addSentencesVBox.getChildren().add(hbox);
        }


    }

    public void removeRow(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        HBox hBox = (HBox) button.getParent();
        HBox hBoxParent = (HBox) hBox.getParent();
        VBox vBox = (VBox) hBoxParent.getParent();
        vBox.getChildren().remove(hBoxParent);
    }

}
