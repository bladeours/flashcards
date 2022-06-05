package com.flashcard.controller;


import com.flashcard.dto.ColorDTO;
import com.flashcard.dto.FlashcardDTO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;

@Service
public class ServerConnectionController {

    private Socket socket;
    private BufferedWriter bw;
    private BufferedReader br;
    private int i = 0;

    public ServerConnectionController() {
        System.out.println("ServerConnectionController constructor");
        try {
            socket = new Socket("localhost",7080);
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected properly");

        } catch (IOException e) {
            e.printStackTrace();
            //TODO handle connection error
        }

    }


    public ArrayList<ColorDTO> getColors() throws IOException {
        JsonObject request = new JsonObject();
        request.addProperty("action","giveColors");
        sendToServer(bw,request.toString());
        String respond = br.readLine();
        Gson gson = new Gson();
        Type colorsListType = new TypeToken<ArrayList<ColorDTO>>(){}.getType();
        ArrayList<ColorDTO> colorsArrayList = gson.fromJson(respond,colorsListType);
        return colorsArrayList;
    }

    public String getSets() throws IOException {
        JsonObject request = new JsonObject();
        request.addProperty("action","giveSets");
        sendToServer(bw,request.toString());

        return br.readLine();
    }

    public void sendNewSet(String setJson) throws IOException {
        sendToServer(bw,setJson);
    }


    private void sendToServer(BufferedWriter bw,String request) throws IOException {
        bw.write(request);
        bw.newLine();
        bw.flush();
    }

    public ArrayList<FlashcardDTO> getFlashcardsForSet(String id) throws IOException {
        JsonObject request = new JsonObject();
        request.addProperty("action","giveFlashcardsForSet");
        request.addProperty("set_id",Integer.valueOf(id));
        sendToServer(bw,request.toString());

        String respond = br.readLine();

        Type flashcardListType = new TypeToken<ArrayList<FlashcardDTO>>(){}.getType();
        ArrayList<FlashcardDTO> flashcardArrayList = new Gson().fromJson(respond,flashcardListType);

        return flashcardArrayList;
    }

    public void updateCorrectSentence(String whichSentence, int id) throws IOException {
        JsonObject request = new JsonObject();
        request.addProperty("action","updateCorrectSentence");
        request.addProperty("flashcard_id", id);
        request.addProperty("whichSentence", whichSentence);

        sendToServer(bw,request.toString());
    }

    public void removeSet(String id) throws IOException {
        JsonObject request = new JsonObject();
        request.addProperty("action","removeSet");
        request.addProperty("set_id",Integer.valueOf(id));
        sendToServer(bw,request.toString());
    }

    public String getAllScores() throws IOException {
        JsonObject request = new JsonObject();
        request.addProperty("action","giveAllScores");
        sendToServer(bw,request.toString());
        return br.readLine();

    }

    public void resetFirstScore(int setId) throws IOException {
        JsonObject request = new JsonObject();
        request.addProperty("action","resetFirstScore");
        request.addProperty("setId",setId);
        sendToServer(bw,request.toString());
    }

    public void resetSecondScore(int setId) throws IOException {
        JsonObject request = new JsonObject();
        request.addProperty("action","resetSecondScore");
        request.addProperty("setId",setId);
        sendToServer(bw,request.toString());
    }
}
