package com.flashcard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/menuView.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("view/menuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();
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
}