package com.flashcard;

import com.flashcard.controller.DatabaseController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread{
        private final Socket socket;
        private final DatabaseController databaseController;
    public ServerThread(Socket socket) {
        this.socket = socket;
        databaseController = new DatabaseController();
    }

    @Override
    public void run() {
        try {

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Client connected");
            while (true) {
                String request = br.readLine();
                if(request == null){
                    continue;
                }
                JsonObject requestJson = new Gson().fromJson(request,JsonObject.class);
                switch (requestJson.get("action").toString().replace("\"","")){
                    case "giveColors":
                        sendToClient(bw,databaseController.getColors());
                        break;
                    case "createSet":
                        databaseController.createSet(requestJson);
                        break;
                    case "giveSets":
                        sendToClient(bw,databaseController.getSets());
                        break;
                    case "giveFlashcardsForSet":
                        sendToClient(bw,databaseController.getFlashcardsForSet(requestJson.get("set_id").getAsInt()));
                        break;
                    case "updateCorrectSentence":
                        databaseController.updateCorrectSentence(requestJson.get("flashcard_id").getAsInt(),
                                requestJson.get("whichSentence").getAsString());
                        break;
                    case "removeSet":
                        databaseController.removeSet(requestJson.get("set_id").getAsInt());
                        break;
                    case "editSet":
                        databaseController.editSet(requestJson);
                        break;
                    case "giveAllScores":
                        sendToClient(bw,databaseController.getAllScores());
                        break;
                    case "resetFirstScore":
                        databaseController.resetFirstScore(requestJson.get("setId").getAsInt());
                        break;
                    case "resetSecondScore":
                        databaseController.resetSecondScore(requestJson.get("setId").getAsInt());
                }
            }

        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }

    private void sendToClient(BufferedWriter bw,String result) throws IOException {
        bw.write(result);
        bw.newLine();
        bw.flush();
    }
}
