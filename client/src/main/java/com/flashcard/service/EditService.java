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

        for(int i=0;i<flashcards.size();i++){
            Label firstSentenceLabel = new Label("First sentence");
            firstSentenceLabel.getStyleClass().add("firstSentenceLabel");


            TextField firstSentenceTextField = new TextField();
            firstSentenceTextField.getStyleClass().add("firstSentenceTextField");
            firstSentenceTextField.setText(flashcards.get(i).getFirstSentence());

            Label secondSentenceLabel = new Label("Second sentence");
            secondSentenceLabel.getStyleClass().add("secondSentenceLabel");

            TextField secondSentenceTextField = new TextField();
            secondSentenceTextField.getStyleClass().add("secondSentenceTextField");
            secondSentenceTextField.setText(flashcards.get(i).getSecondSentence());

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
            hbox.setPrefWidth(1000.0);
            hbox.getProperties().put("flashcardId",flashcards.get(i).getId());
            hbox.getChildren().add(firstSentenceLabel);
            hbox.getChildren().add(firstSentenceTextField);
            hbox.getChildren().add(secondSentenceLabel);
            hbox.getChildren().add(secondSentenceTextField);
            hbox.getChildren().add(removeButton);
            addSentencesVBox.getChildren().add(hbox);
        }


    }


}
