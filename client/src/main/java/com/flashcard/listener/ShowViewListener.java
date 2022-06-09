package com.flashcard.listener;

import com.flashcard.FlashcardAppJavaFX;
import com.flashcard.event.ShowViewEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ShowViewListener implements ApplicationListener<ShowViewEvent> {

//    private final Resource fxml;



    private final ApplicationContext applicationContext;


    public ShowViewListener(ApplicationContext ac) {
        this.applicationContext = ac;
    }

    @Override
    public void onApplicationEvent(ShowViewEvent showViewEvent) {
        try {
            Stage stage = showViewEvent.getStage();
            FXMLLoader fxmlLoader = new FXMLLoader(FlashcardAppJavaFX.class.getResource(showViewEvent.getClassPath()));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1000, 600);
            stage.getIcons().add(new Image("file:flashcard.png"));
            stage.setScene(scene);
            stage.setTitle("Flashcards");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
