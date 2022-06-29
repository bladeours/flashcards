package com.flashcard.service;


import com.flashcard.controller.ServerConnectionController;
import com.flashcard.dto.ScoreDTO;
import com.flashcard.event.ShowViewEvent;
import com.flashcard.listener.ShowViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class ScoresService {

    private final ServerConnectionController serverConnectionController;
    private final ShowViewListener showViewListener;

    public ScoresService(ServerConnectionController serverConnectionController, ShowViewListener showViewListener) {
        this.serverConnectionController = serverConnectionController;
        this.showViewListener = showViewListener;
    }


    public void createScoreView(VBox scoresVBox, Pane root) throws IOException {
        ArrayList<ScoreDTO> scores = createScoreArrayFromJson(serverConnectionController.getAllScores());
        Collections.sort(scores);

        for(ScoreDTO score: scores){
            String color = score.getColorCode();
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.TOP_CENTER);
            Label setName = new Label(score.getSetName());
            setName.getStyleClass().add("setNameLabel");
            setName.setStyle("-fx-text-fill: " + color + ";");

            Button resetFirstButton = new Button("X");
            resetFirstButton.getStyleClass().add("resetButton");

            resetFirstButton.setOnMouseClicked(mouseEvent -> {
                try {
                    serverConnectionController.resetFirstScore(score.getSetId());
                    showViewListener.getApplicationContext().publishEvent(new ShowViewEvent((Stage) root.getScene().getWindow()
                            , "/com/flashcard/view/scoresView.fxml"));
                    createScoreView(scoresVBox, root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Label firstScore = new Label(score.getScoreFirst() + "/" + score.getMaxScore());
            firstScore.getStyleClass().add("firstScoreLabel");
            firstScore.setStyle("-fx-text-fill: " + color + ";");

            Label secondScore = new Label(score.getScoreSecond() + "/" + score.getMaxScore());
            secondScore.getStyleClass().add("secondScoreLabel");
            secondScore.setStyle("-fx-text-fill: " + color + ";");

            Button resetSecondButton = new Button("X");
            resetSecondButton.getStyleClass().add("resetButton");
            resetSecondButton.hoverProperty();

            resetSecondButton.setOnMouseClicked(mouseEvent -> {
                try {
                    serverConnectionController.resetSecondScore(score.getSetId());
                    showViewListener.getApplicationContext().publishEvent(new ShowViewEvent((Stage) root.getScene().getWindow()
                            ,"/com/flashcard/view/scoresView.fxml"));
                    createScoreView(scoresVBox, root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            hBox.getChildren().addAll(setName, resetFirstButton, firstScore, secondScore, resetSecondButton);
            scoresVBox.getChildren().add(hBox);
        }
    }

    public ArrayList<ScoreDTO> createScoreArrayFromJson(String json){
        Type scoreListType = new TypeToken<ArrayList<com.flashcard.dto.ScoreDTO>>(){}.getType();
        return new Gson().fromJson(json,scoreListType);
    }

}
