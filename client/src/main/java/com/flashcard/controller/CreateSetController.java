package com.flashcard.controller;

import com.flashcard.dto.ColorDTO;
import com.flashcard.dto.ScoreDTO;
import com.flashcard.dto.SetDTO;
import com.flashcard.event.ShowViewEvent;
import com.flashcard.listener.ShowViewListener;
import com.flashcard.service.EditService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;


@Component
public class CreateSetController implements Initializable {

    @FXML
    private Pane root;
    @FXML
    private ScrollPane addSentencesScrollPane;
    @FXML
    private Button addSentenceButton;
    @FXML
    private VBox addSentencesVBox;
    @FXML
    private HBox colorHBox;
    @FXML
    private TextField setNameTextField;

    private final ShowViewListener showViewListener;
    private final ServerConnectionController serverConnectionController;
    private final EditService editService;
    private boolean edit = false;
    private int setToEditId;
    private int colorId = 1;
    private ArrayList<ColorDTO> colors;


    public CreateSetController(ShowViewListener showViewListener, ServerConnectionController serverConnectionController,
                               EditService editService) {
        this.showViewListener = showViewListener;
        this.serverConnectionController = serverConnectionController;
        this.editService = editService;
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
        Gson gson = new Gson();
        String setName = setNameTextField.getText();
        Type setsListType = new TypeToken<ArrayList<SetDTO>>(){}.getType();
        ArrayList<SetDTO> sets = gson.fromJson(serverConnectionController.getSets(),setsListType);
        boolean theSameName = false;
        for(SetDTO set: sets){
            if(set.getName().equals(setName) && !edit){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Illegal argument");
                alert.setContentText("There is already set with name " + setName);
                alert.showAndWait();
                theSameName = true;
                break;
            }
        }

        int color = colorId;

        JsonObject setJson = new JsonObject();
        setJson.addProperty("setName",setName);
        setJson.addProperty("color",color);
        if(edit){
            setJson.addProperty("action","editSet");
            setJson.addProperty("setToEditId",setToEditId);
        }else{
            setJson.addProperty("action","createSet");
        }
        Map<String,String> OneSentences = new HashMap<>();
        ArrayList<Map<String,String>> AllSentences = new ArrayList<>();

        boolean dontSend = addSentencesVBox.getChildren().size() == 1;
        for(Node node: addSentencesVBox.getChildren()){
            if (node.getStyleClass().contains("sentencesHBox")){
                 HBox hBox = (HBox) node;
                for(Node children: hBox.getChildren()){
                    if(children.getStyleClass().contains("firstSentenceTextField")){
                        if(((TextField) children).getText().equals("")) dontSend = true;
                        OneSentences.put("firstSentence",((TextField) children).getText());
                    }
                    if(children.getStyleClass().contains("removeButtonHBox")){
                        HBox removeButtonHBox = (HBox) children;
                        if(((TextField)  removeButtonHBox.getChildren().get(0)).getText().equals("")) dontSend = true;
                        OneSentences.put("secondSentence",((TextField)  removeButtonHBox.getChildren().get(0)).getText());
                    }
                }
                if(edit){
                    OneSentences.put("flashcardId",String.valueOf(hBox.getProperties().get("flashcardId")));
                }
                AllSentences.add(new HashMap<>(OneSentences));
                OneSentences.clear();
            }

        }

        String allSentencesJson = gson.toJson(AllSentences,ArrayList.class);
        JsonElement jsonElement = JsonParser.parseString(allSentencesJson).getAsJsonArray();

        setJson.add("sentences",jsonElement);
        if((dontSend || setNameTextField.getText().equals("")) && !theSameName ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Illegal argument");
            alert.setContentText("No field can be empty!");
            alert.showAndWait();
        }else if(!theSameName){

            serverConnectionController.sendNewSet(setJson.toString());
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
