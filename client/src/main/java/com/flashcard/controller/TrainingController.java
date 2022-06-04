package com.flashcard.controller;

import com.flashcard.dto.FlashcardDTO;
import com.flashcard.event.ShowViewEvent;
import com.flashcard.listener.ShowViewListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

    public void createFlashcardView(int setId, String setName) throws IOException {
        sentenceNumber = 0;
        flashcards = serverConnectionController.getFlashcardsForSet(String.valueOf(setId));
        Collections.shuffle(flashcards);
        System.out.println(flashcards);
        setNameLabel.setText(setName);
        sentenceLabel.setText(choseFirstSentence());

    }

    @FXML
    public void nextSentence(){
        if(sentenceNumber >= 0 && sentenceNumber < flashcards.size()-1) {
            sentenceNumber++;
            sentenceLabel.setText(choseFirstSentence());
            correctWrongHBox.setVisible(false);
            correctWrongHBox.setManaged(false);
        }
        System.out.println("sentenceNumber="+sentenceNumber + " flashcards.size()-1=" + (flashcards.size()-1));

    }

    @FXML
    public void previousSentence(){
        System.out.println("sentenceNumber="+sentenceNumber + " flashcards.size()-1=" + (flashcards.size()-1));
        if(sentenceNumber > 0 && sentenceNumber < flashcards.size()) {
            sentenceNumber--;
            sentenceLabel.setText(choseFirstSentence());
            correctWrongHBox.setVisible(false);
            correctWrongHBox.setManaged(false);
        }
        System.out.println("sentenceNumber="+sentenceNumber + " flashcards.size()-1=" + (flashcards.size()-1));

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
//                serverConnectionController.updateCorrectSentence("first",flashcards.get(sentenceNumber).getId());
            }else{
                flashcards.get(sentenceNumber).setSecondCorrect(1);
//                serverConnectionController.updateCorrectSentence("second",flashcards.get(sentenceNumber).getId());

            }
            System.out.println(flashcards);
            if (sentenceNumber < flashcards.size()-1){
                nextSentence();
            }
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


}
