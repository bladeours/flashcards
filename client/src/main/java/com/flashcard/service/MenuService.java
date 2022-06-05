package com.flashcard.service;

import com.flashcard.controller.CreateSetController;
import com.flashcard.controller.ServerConnectionController;
import com.flashcard.controller.TrainingController;
import com.flashcard.dto.SetDTO;
import com.flashcard.event.ShowViewEvent;
import com.flashcard.listener.ShowViewListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class MenuService {

    private final CreateSetController createSetController;
    private final ServerConnectionController serverConnectionController;
    private final TrainingController trainingController;
    private final ShowViewListener stageListener;
    private Pane root;
    private VBox rootRightVBox;



    public MenuService(CreateSetController createSetController, ServerConnectionController serverConnectionController, TrainingController trainingController,
                       ShowViewListener stageListener) {
        this.createSetController = createSetController;
        this.serverConnectionController = serverConnectionController;
        this.trainingController = trainingController;
        this.stageListener = stageListener;
        System.out.println("MenuService constructor");
    }

    public void setPane(Pane root) {
        this.root = root;
    }

    public void setRootRightVBox(VBox rootRightVBox) {
        this.rootRightVBox = rootRightVBox;
    }

    public ArrayList<SetDTO> createSetArrayFromJson(String json){
        Type setListType = new TypeToken<ArrayList<SetDTO>>(){}.getType();
        ArrayList<SetDTO> setDTOArrayList = new Gson().fromJson(json,setListType);
        return setDTOArrayList;
    }

    public VBox createSetTile(String name, String color, String id){
        VBox rootSetsVBox = new VBox();
        rootSetsVBox.setAlignment(Pos.TOP_CENTER);
        rootSetsVBox.getStyleClass().add("rootSetsVBox");
        rootSetsVBox.setStyle("-fx-background-color:"+ color +";");

        Label setNameLabel = new Label(name);
        setNameLabel.getStyleClass().add("setNameLabel");

        HBox setButtonsHBox = new HBox();
        setButtonsHBox.getStyleClass().add("setButtonsHBox");
        rootSetsVBox.getChildren().addAll(setNameLabel,setButtonsHBox);

        Button editButton = new Button("Edit");
        editButton.getStyleClass().add("setButtons");
        editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                stageListener.getApplicationContext().publishEvent(new ShowViewEvent((Stage) root.getScene().getWindow()
                        ,"/com/flashcard/view/createSetView.fxml"));
                createSetController.setEdit(true);
                createSetController.setSetToEditId(Integer.parseInt(id));
                try {
                    createSetController.createEditLayout(Integer.parseInt(id),name,color);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        Button removeButton = new Button("Remove");
        removeButton.getStyleClass().add("setButtons");
        removeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Remove set");
                alert.setHeaderText("Warning!");
                alert.setContentText("Are you sure that you want to remove " + name +"?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    System.out.println("usuwam set o id "+ id);
                    try {
                        serverConnectionController.removeSet(id);
                        refreshSetTiles();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        setButtonsHBox.getChildren().addAll(editButton, removeButton);

        rootSetsVBox.getProperties().put("set_id",id);
        rootSetsVBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Select language");
                alert.setHeaderText("Which language do you want?");
                alert.setContentText("");

                ButtonType buttonTypeOne = new ButtonType("First");
                ButtonType buttonTypeTwo = new ButtonType("Second");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

                Optional<ButtonType> result = alert.showAndWait();
                boolean showFlashcards = false;
                if (result.get() == buttonTypeOne){
                    trainingController.setFromFirst(true);
                    showFlashcards = true;
                } else if (result.get() == buttonTypeTwo) {
                    trainingController.setFromFirst(false);
                    showFlashcards = true;
                }

                if(showFlashcards){
                    stageListener.getApplicationContext().publishEvent(new ShowViewEvent((Stage) root.getScene().getWindow()
                            ,"/com/flashcard/view/trainingView.fxml"));
                    try {
                        trainingController.createFlashcardView(Integer.parseInt(id),name,color);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return rootSetsVBox;
    }

    public HBox chooseHBoxToSetTiles(VBox rootRightVBox){
        HBox rowSentenceHBox;

        int rootSize = rootRightVBox.getChildren().size();
        HBox tempHBox = new HBox();
        if(rootSize>0) {
            tempHBox = (HBox) rootRightVBox.getChildren().get(rootSize - 1);
        }
        int size = tempHBox.getChildren().size();
        if(size %2 == 0){
            rowSentenceHBox = new HBox();
            rowSentenceHBox.getStyleClass().add("rowSentenceHBox");
            rootRightVBox.getChildren().add(rowSentenceHBox);
        }else{
            rowSentenceHBox = (HBox) rootRightVBox.getChildren().get(rootSize-1);
        }

        return  rowSentenceHBox;
    }

    public void refreshSetTiles(){
        rootRightVBox.getChildren().clear();
        try {
            ArrayList<SetDTO> setsDTO = createSetArrayFromJson(serverConnectionController.getSets());
            for (SetDTO setDTO : setsDTO){
                HBox rowSentenceHBox = chooseHBoxToSetTiles(rootRightVBox);

                VBox vBox = createSetTile(setDTO.getName(), setDTO.getColor(),String.valueOf(setDTO.getId()));

                rowSentenceHBox.getChildren().add(vBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
