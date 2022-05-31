package com.flashcard.controller;


import com.google.gson.JsonObject;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
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
        return null;
    }

    private void sendToServer(BufferedWriter bw,String request) throws IOException {
        bw.write(request);
        bw.newLine();
        bw.flush();
    }

}
