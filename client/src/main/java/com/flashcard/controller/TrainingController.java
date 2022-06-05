package com.flashcard.controller;

import com.flashcard.dto.FlashcardDTO;
import com.flashcard.event.ShowViewEvent;
import com.flashcard.listener.ShowViewListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

@Component
public class TrainingController implements Initializable {
    @FXML
    private Label setNameLabel;
    @FXML
    private Label sentenceLabel;
    @FXML
    private HBox correctWrongHBox;
    @FXML
    private Pane root;
    @FXML
    private Label counterLabel;
    @FXML
    private VBox flashcardVBox;

    private int sentenceNumber;
    private boolean fromFirst = false;

    private final ShowViewListener showViewListener;
    private final ServerConnectionController serverConnectionController;
    ArrayList<FlashcardDTO> flashcards;

    public TrainingController(ShowViewListener stageListener, ServerConnectionController serverConnectionController) {
        this.showViewListener = stageListener;
        this.serverConnectionController = serverConnectionController;
        System.out.println("TrainingController constructor");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void createFlashcardView(int setId, String setName, String color) throws IOException {
        flashcardVBox.setStyle("-fx-background-color: " + color);
        sentenceNumber = 0;
        flashcards = serverConnectionController.getFlashcardsForSet(String.valueOf(setId));
        if(fromFirst){
            flashcards.removeIf(flashcard -> flashcard.getFirstCorrect() > 0);
        }else{
            flashcards.removeIf(flashcard -> flashcard.getSecondCorrect() > 0);

        }
        Collections.shuffle(flashcards);
        setNameLabel.setText(setName);
        sentenceLabel.setText(choseFirstSentence());
        counterLabel.setText("1/" + flashcards.size());

    }

    @FXML
    public void nextSentence(){
        if(sentenceNumber >= 0 && sentenceNumber < flashcards.size()-1) {
            counterLabel.setText((sentenceNumber+2)+ "/" + flashcards.size());
            sentenceNumber++;
            sentenceLabel.setText(choseFirstSentence());
            correctWrongHBox.setVisible(false);
            correctWrongHBox.setManaged(false);

        }else{
            showTheEndAlert();
        }

    }

    @FXML
    public void previousSentence(){
        if(sentenceNumber > 0 && sentenceNumber < flashcards.size()) {
            counterLabel.setText(sentenceNumber + "/" + flashcards.size());
            sentenceNumber--;
            sentenceLabel.setText(choseFirstSentence());
            correctWrongHBox.setVisible(false);
            correctWrongHBox.setManaged(false);
        }else{
            showTheEndAlert();
        }

    }

    @FXML
    public void showSecondSentence(){
        sentenceLabel.setText(choseSecondSentence());
        correctWrongHBox.setVisible(true);
        correctWrongHBox.setManaged(true);

    }

    @FXML
    public void backToMenu(){
        showViewListener.getApplicationContext().publishEvent(new ShowViewEvent((Stage) root.getScene().getWindow(),
                "/com/flashcard/view/menuView.fxml"));
    }

    @FXML
    public void correct() throws IOException {
        if(sentenceNumber >= 0 && sentenceNumber < flashcards.size()) {
            if(fromFirst){
                flashcards.get(sentenceNumber).setFirstCorrect(1);
                serverConnectionController.updateCorrectSentence("first",flashcards.get(sentenceNumber).getId());
            }else{
                flashcards.get(sentenceNumber).setSecondCorrect(1);
                serverConnectionController.updateCorrectSentence("second",flashcards.get(sentenceNumber).getId());

            }
            System.out.println(flashcards);
            if (sentenceNumber < flashcards.size()-1){
                nextSentence();
            }
        }else{
            showTheEndAlert();
        }
    }

    @FXML
    public void wrong() {
        nextSentence();
    }

    private String choseFirstSentence(){
        if (fromFirst) {
            return flashcards.get(sentenceNumber).getFirstSentence();
        }
        return flashcards.get(sentenceNumber).getSecondSentence();
    }

    private String choseSecondSentence() {
        if (fromFirst) {
            return flashcards.get(sentenceNumber).getSecondSentence();
        }
            return flashcards.get(sentenceNumber).getFirstSentence();
    }

    public void setFromFirst(boolean fromFirst) {
        this.fromFirst = fromFirst;
    }

    private void showTheEndAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("There is no more sentences.");

        alert.showAndWait();
    }

}
