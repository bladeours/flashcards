package com.flashcard.controller;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

@Service
public class DatabaseController {

    private Socket socket;
    private BufferedWriter bw;
    private BufferedReader br;
    private int i = 0;

    public DatabaseController() {
        System.out.println("database constructor");
    }

    public  void test(){
        i++;
        System.out.println("udalo sie! i=" + i);
    }
    //       Socket socket = new Socket("localhost",7080);
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//        JsonObject request = new JsonObject();
//        request.addProperty("action","test");
//        bw.write(request.toString());
//        bw.newLine();
//        bw.flush();
//        System.out.println(br.readLine());

}
