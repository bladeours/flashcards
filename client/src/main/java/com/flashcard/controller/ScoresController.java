package com.flashcard.controller;

import com.flashcard.event.ShowViewEvent;
import com.flashcard.listener.ShowViewListener;
import com.flashcard.service.ScoresService;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScoresController {
    @FXML
    private Pane root;
    @FXML
    private VBox scoresVBox;

    private final ServerConnectionController serverConnectionController;
    private final ShowViewListener showViewListener;
    private final ScoresService scoresService;

    public ScoresController(ServerConnectionController serverConnectionController, ShowViewListener showViewListener,
                            ScoresService scoresService) {
        this.serverConnectionController = serverConnectionController;
        this.showViewListener = showViewListener;
        this.scoresService = scoresService;
    }

    public void createScoreView() throws IOException {
        scoresService.createScoreView(scoresVBox, root);
    }

    @FXML
    public void backToMenu() {
        showViewListener.getApplicationContext().publishEvent(new ShowViewEvent((Stage) root.getScene().getWindow(),
                "/com/flashcard/view/menuView.fxml"));
    }
}
