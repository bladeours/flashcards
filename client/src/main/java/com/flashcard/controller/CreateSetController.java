package com.flashcard.controller;

import com.flashcard.dto.ColorDTO;
import com.flashcard.event.ShowViewEvent;
import com.flashcard.listener.ShowViewListener;
import com.flashcard.service.CreateSetService;
import com.flashcard.service.EditService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;


@Component
public class CreateSetController implements Initializable {

    @FXML
    private Pane root;
    @FXML
    private VBox addSentencesVBox;
    @FXML
    private HBox colorHBox;
    @FXML
    private TextField setNameTextField;

    private final ShowViewListener showViewListener;
    private final ServerConnectionController serverConnectionController;
    private final EditService editService;
    private final CreateSetService createSetService;
    private boolean edit = false;
    private int setToEditId;
    private int colorId = 1;
    private ArrayList<ColorDTO> colors;


    public CreateSetController(ShowViewListener showViewListener, ServerConnectionController serverConnectionController,
                               EditService editService, CreateSetService createSetService) {
        this.showViewListener = showViewListener;
        this.serverConnectionController = serverConnectionController;
        this.editService = editService;
        this.createSetService = createSetService;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colors = null;
        try {
            colors = serverConnectionController.getColors();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(ColorDTO color: colors){
            Button colorButton = new Button();
            colorButton.getStyleClass().add("colorButtons");
            colorButton.setStyle("-fx-background-color: " + color.getCode());
            colorButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    colorId = color.getId();
                    root.setStyle("-fx-new-color:"+ color.getCode() +";");
                }
            });
            colorHBox.getChildren().add(colorButton);

        }

    }

    @FXML
    public void addSentence() {

        TextField firstSentenceTextField = new TextField();
        firstSentenceTextField.getStyleClass().add("firstSentenceTextField");

        TextField secondSentenceTextField = new TextField();
        secondSentenceTextField.getStyleClass().add("secondSentenceTextField");

        Button removeButton = new Button("X");
        removeButton.getStyleClass().add("removeButton");
        removeButton.setOnAction(this::removeRow);

        HBox smallerHBox =  new HBox();
        smallerHBox.getStyleClass().add("removeButtonHBox");
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getStyleClass().add("sentencesHBox");

        smallerHBox.getChildren().addAll(secondSentenceTextField,removeButton);
        hbox.getChildren().addAll(firstSentenceTextField,smallerHBox);

        addSentencesVBox.getChildren().add(hbox);
    }

    @FXML
    public void backToMenu(){
        edit = false;
        showViewListener.getApplicationContext().publishEvent(new ShowViewEvent((Stage) root.getScene().getWindow(),
                "/com/flashcard/view/menuView.fxml"));
    }

    @FXML
    public void saveSet() throws IOException {
        if(createSetService.saveSet(setNameTextField,edit,colorId,setToEditId,addSentencesVBox)){
            backToMenu();
        }
        //TODO add user

    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public void setSetToEditId(int setToEditId) {
        this.setToEditId = setToEditId;
    }

    public void createEditLayout(int setId, String name, String color) throws IOException {
        setNameTextField.setText(name);
        for(ColorDTO tempColor: colors){
            if(tempColor.getCode().equals(color)){
                       root.setStyle("-fx-new-color:"+ tempColor.getCode() +";");
            }
        }
        editService.addSentencesToLayout(addSentencesVBox, setId);
    }

    public void removeRow(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        HBox hBox = (HBox) button.getParent();
        HBox hBoxParent = (HBox) hBox.getParent();
        VBox vBox = (VBox) hBoxParent.getParent();
        vBox.getChildren().remove(hBoxParent);
    }
}
