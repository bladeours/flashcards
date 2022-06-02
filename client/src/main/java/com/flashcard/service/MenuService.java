package com.flashcard.service;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    public MenuService() {
        System.out.println("MenuService constructor");
    }

//    public boolean createNewHBoxSet(VBox rootRightVBox){
//
//    }

    public VBox createSetTile(String name, String color){
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
        Button addButton = new Button("Add");
        addButton.getStyleClass().add("setButtons");
        Button removeButton = new Button("Remove");
        removeButton.getStyleClass().add("setButtons");

        setButtonsHBox.getChildren().addAll(editButton, addButton, removeButton);

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
        System.out.println(size);
        if(size %2 == 0){
            rowSentenceHBox = new HBox();
            rowSentenceHBox.getStyleClass().add("rowSentenceHBox");
            rootRightVBox.getChildren().add(rowSentenceHBox);
        }else{
            rowSentenceHBox = (HBox) rootRightVBox.getChildren().get(rootSize-1);
        }

        return  rowSentenceHBox;
    }
}
