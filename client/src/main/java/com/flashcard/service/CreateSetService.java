package com.flashcard.service;


import com.flashcard.controller.ServerConnectionController;
import com.flashcard.dto.SetDTO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreateSetService {

    private final Gson gson;
    private final ServerConnectionController serverConnectionController;


    public CreateSetService(ServerConnectionController serverConnectionController) {
        this.serverConnectionController = serverConnectionController;
        gson = new Gson();
    }



    public boolean saveSet(TextField setNameTextField, boolean edit, int colorId, int setToEditId,
                        VBox addSentencesVBox) throws IOException {
        String setName = setNameTextField.getText();
        Type setsListType = new TypeToken<ArrayList<SetDTO>>(){}.getType();
        ArrayList<SetDTO> sets = gson.fromJson(serverConnectionController.getSets(),setsListType);
        boolean theSameName = false;
        if(!edit){
            theSameName = duplicateSetName(sets, setName);
        }

        JsonObject setJson = setJsonToSend(setName, colorId, setToEditId, edit, addSentencesVBox);

        boolean dontSend = addSentencesVBox.getChildren().size() == 1;
       if (checkIfFieldIsEmpty(addSentencesVBox)){
           dontSend = true;
       }
        if((dontSend || setNameTextField.getText().equals("")) && !theSameName ){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Illegal argument");
            alert.setContentText("No field can be empty!");
            alert.showAndWait();
        }else if(!theSameName){
            serverConnectionController.sendNewSet(setJson.toString());
            return true;
        }
        //TODO add user
    return false;
    }

    private boolean duplicateSetName(ArrayList<SetDTO> sets, String setName) {
        for (SetDTO set : sets) {
            if (set.getName().equals(setName)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Illegal argument");
                alert.setContentText("There is already set with name " + setName);
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }

    private JsonObject setJsonToSend(String setName, int color, int setToEditId, boolean edit,
                                     VBox addSentencesVBox){
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
            if (node.getStyleClass().contains("sentencesHBox")){
                HBox hBox = (HBox) node;
                for(Node children: hBox.getChildren()){
                    if(children.getStyleClass().contains("firstSentenceTextField")){
                        OneSentences.put("firstSentence",((TextField) children).getText());
                    }
                    if(children.getStyleClass().contains("removeButtonHBox")){
                        HBox removeButtonHBox = (HBox) children;
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
        return setJson;
    }

    private boolean checkIfFieldIsEmpty(VBox addSentencesVBox){
        for(Node node: addSentencesVBox.getChildren()){
            if (node.getStyleClass().contains("sentencesHBox")){
                HBox hBox = (HBox) node;
                for(Node children: hBox.getChildren()){
                    if(children.getStyleClass().contains("firstSentenceTextField")){
                        if(((TextField) children).getText().equals("")) return true;
                    }
                    if(children.getStyleClass().contains("removeButtonHBox")){
                        HBox removeButtonHBox = (HBox) children;
                        if(((TextField)  removeButtonHBox.getChildren().get(0)).getText().equals("")) return true;
                    }
                }
            }
        }
        return false;
    }

}
