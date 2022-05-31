package com.flashcard;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class HelloApplication extends Application {

    private ConfigurableApplicationContext applicationContext;


    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/flashcard/view/menuView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(DemoApplication.class).run();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
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