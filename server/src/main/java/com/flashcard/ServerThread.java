package com.flashcard;

import com.flashcard.entity.Score;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread{
        private final Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String request = br.readLine();
//                System.out.println(request);
                JsonObject requestJson = new Gson().fromJson(request,JsonObject.class);
                switch (requestJson.get("action").toString().replace("\"","")){
                    case "test":
                            System.out.println("Tescior udany!");
                            break;
//                    case "lol" -> System.out.println("Tescior udany!");
//                    default -> System.out.println("default");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToClient(BufferedWriter bw,String result) throws IOException {
        bw.write(result);
        bw.newLine();
        bw.flush();
    }
}
