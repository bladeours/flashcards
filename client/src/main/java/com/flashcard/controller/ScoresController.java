package com.flashcard.controller;

import com.flashcard.dto.ScoreDTO;
import com.flashcard.event.ShowViewEvent;
import com.flashcard.listener.ShowViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

@Component
public class ScoresController {
    @FXML
    private Pane root;
    @FXML
    private ScrollPane scoresScrollPane;
    @FXML
    private VBox scoresVBox;

    private final ServerConnectionController serverConnectionController;
    private final ShowViewListener showViewListener;


    public ScoresController(ServerConnectionController serverConnectionController, ShowViewListener showViewListener) {
        this.serverConnectionController = serverConnectionController;
        this.showViewListener = showViewListener;
    }

    public void createScoreView() throws IOException {
        System.out.println();
        ArrayList<ScoreDTO> scores = createScoreArrayFromJson(serverConnectionController.getAllScores());
        for(ScoreDTO score: scores){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.TOP_CENTER);
            Label setName = new Label(score.getSetName());
            setName.getStyleClass().add("setNameLabel");

            Button resetFirstButton = new Button("x");
            resetFirstButton.getStyleClass().add("resetButton");
            resetFirstButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        serverConnectionController.resetFirstScore(score.getSetId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Label firstScore = new Label(score.getScoreFirst() + "/" + score.getMaxScore());
            firstScore.getStyleClass().add("firstScoreLabel");

            Label secondScore = new Label(score.getScoreSecond() + "/" + score.getMaxScore());
            secondScore.getStyleClass().add("secondScoreLabel");

            Button resetSecondButton = new Button("x");
            resetSecondButton.getStyleClass().add("resetButton");
            resetSecondButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        serverConnectionController.resetSecondScore(score.getSetId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            hBox.getChildren().addAll(setName, resetFirstButton, firstScore, secondScore, resetSecondButton);
            scoresVBox.getChildren().add(hBox);
        }




//        scoresVBox.getChildren().add();
    }

    public ArrayList<ScoreDTO> createScoreArrayFromJson(String json){
        Type scoreListType = new TypeToken<ArrayList<ScoreDTO>>(){}.getType();
        ArrayList<ScoreDTO> scoreDTOArrayList = new Gson().fromJson(json,scoreListType);
        return scoreDTOArrayList;
    }

    @FXML
    public void backToMenu() {
        showViewListener.getApplicationContext().publishEvent(new ShowViewEvent((Stage) root.getScene().getWindow(),
                "/com/flashcard/view/menuView.fxml"));
    }
}
