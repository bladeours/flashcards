package com.flashcard.controller;

import com.flashcard.event.ShowViewEvent;
import com.flashcard.listener.ShowViewListener;
import com.flashcard.service.EditService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


@Component
public class CreateSetController implements Initializable {

    @FXML
    Pane root;
    @FXML
    ScrollPane addSentencesScrollPane;
    @FXML
    Button addSentenceButton;
    @FXML
    VBox addSentencesVBox;
    @FXML
    ComboBox<String> colorComboBox;
    @FXML
    TextField setNameTextField;

    private final ShowViewListener showViewListener;
    private final ServerConnectionController serverConnectionController;
    private final EditService editService;
    private boolean edit = false;
    private int setToEditId;


    public CreateSetController(ShowViewListener showViewListener, ServerConnectionController serverConnectionController,
                               EditService editService) {
        this.showViewListener = showViewListener;
        this.serverConnectionController = serverConnectionController;
        this.editService = editService;
        System.out.println("CreateSetController constructor");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> colors = null;
        try {
            colors = serverConnectionController.getColors();
        } catch (IOException e) {
            e.printStackTrace();
        }

        colorComboBox.setItems(colors);
        colorComboBox.setValue(colors.get(0));
    }

    @FXML
    public void addSentence() {
        Label firstSentenceLabel = new Label("FirstSentence");
        firstSentenceLabel.getStyleClass().add("firstSentenceLabel");

        TextField firstSentenceTextField = new TextField();
        firstSentenceTextField.getStyleClass().add("firstSentenceTextField");

        Label secondSentenceLabel = new Label("SecondSentence");
        secondSentenceLabel.getStyleClass().add("secondSentenceLabel");

        TextField secondSentenceTextField = new TextField();
        secondSentenceTextField.getStyleClass().add("secondSentenceTextField");

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
        hbox.getChildren().add(firstSentenceLabel);
        hbox.getChildren().add(firstSentenceTextField);
        hbox.getChildren().add(secondSentenceLabel);
        hbox.getChildren().add(secondSentenceTextField);
        hbox.getChildren().add(removeButton);

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

        String setName = setNameTextField.getText();
        String color = colorComboBox.getValue();
        Gson gson = new Gson();
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

        for(Node node: addSentencesVBox.getChildren()){
            HBox hBox = (HBox) node;
            for(Node hboxChildren: hBox.getChildren()){
                if(hboxChildren.getClass().equals(TextField.class)){
                    if(hboxChildren.getStyleClass().contains("firstSentenceTextField")){
                        OneSentences.put("firstSentence",((TextField) hboxChildren).getText());
                    }
                    if(hboxChildren.getStyleClass().contains("secondSentenceTextField")){
                        OneSentences.put("secondSentence",((TextField) hboxChildren).getText());
                    }
                }
            }
            if(edit){
                OneSentences.put("flashcardId",String.valueOf(hBox.getProperties().get("flashcardId")));
            }
            AllSentences.add(new HashMap<>(OneSentences));
            OneSentences.clear();
        }

        String allSentencesJson = gson.toJson(AllSentences,ArrayList.class);
        JsonElement jsonElement = JsonParser.parseString(allSentencesJson).getAsJsonArray();

        setJson.add("sentences",jsonElement);
        System.out.println(setJson);

        serverConnectionController.sendNewSet(setJson.toString());

        //TODO add user

        backToMenu();
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public void setSetToEditId(int setToEditId) {
        this.setToEditId = setToEditId;
    }

    public void createEditLayout(int setId, String name, String color) throws IOException {
        setNameTextField.setText(name);
        colorComboBox.setValue(color);
        editService.addSentencesToLayout(addSentencesVBox, setId);
    }
}
