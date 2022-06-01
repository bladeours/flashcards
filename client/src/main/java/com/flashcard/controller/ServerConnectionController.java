package com.flashcard.controller;


import com.flashcard.repository.Color;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

    public  void test() {
        JsonObject request = new JsonObject();
        request.addProperty("action","test");
        try {
            bw.write(request.toString());
            bw.newLine();
            bw.flush();
            System.out.println(br.readLine());
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public ObservableList<String> getColors() throws IOException {
        System.out.println("getColors()");
        JsonObject request = new JsonObject();
        request.addProperty("action","giveColors");
        sendToServer(bw,request.toString());
        String respond = br.readLine();
        Gson gson = new Gson();
        Type colorsListType = new TypeToken<ArrayList<Color>>(){}.getType();
        ArrayList<Color> colorsArrayList = gson.fromJson(respond,colorsListType);
        ObservableList<String> colors =  FXCollections.observableArrayList();
        for(Color color: colorsArrayList){
            colors.add(color.getCode());
        }


        return colors;
    }

    public void sendNewSet(String setJson) throws IOException {
        sendToServer(bw,setJson);
    }


    private void sendToServer(BufferedWriter bw,String request) throws IOException {
        bw.write(request);
        bw.newLine();
        bw.flush();
    }

}
