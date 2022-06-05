package com.flashcard.controller;

import com.flashcard.dto.ColorDTO;
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
import javafx.scene.input.MouseEvent;
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


    public CreateSetController(ShowViewListener showViewListener, ServerConnectionController serverConnectionController,
                               EditService editService) {
        this.showViewListener = showViewListener;
        this.serverConnectionController = serverConnectionController;
        this.editService = editService;
        System.out.println("CreateSetController constructor");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<ColorDTO> colors = null;
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
                }
            });
            colorHBox.getChildren().add(colorButton);

        }

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
                removeRow(actionEvent);
            }
        });

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().addAll(firstSentenceLabel,firstSentenceTextField,secondSentenceLabel,secondSentenceTextField,
                removeButton);

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
//        String color = colorComboBox.getValue();
        int color = colorId;
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

        boolean dontSend = addSentencesVBox.getChildren().size() == 0;

        for(Node node: addSentencesVBox.getChildren()){
            HBox hBox = (HBox) node;

            for(Node hboxChildren: hBox.getChildren()){
                if(hboxChildren.getClass().equals(TextField.class)){
                    if(((TextField) hboxChildren).getText().equals("")){
                        dontSend = true;
                        break;
                    }
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

        if(dontSend || setNameTextField.getText().equals("") ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Illegal argument");
            alert.setContentText("No field can be empty!");
            alert.showAndWait();
        }else{
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
//        colorComboBox.setValue(color);
        editService.addSentencesToLayout(addSentencesVBox, setId);
    }

    public void removeRow(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        HBox hBox = (HBox) button.getParent();
        VBox vBox = (VBox) hBox.getParent();
        vBox.getChildren().remove(hBox);
    }
}
