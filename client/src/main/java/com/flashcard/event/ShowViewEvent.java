package com.flashcard.event;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class ShowViewEvent extends ApplicationEvent {

    private String classPath;

    public ShowViewEvent(Stage source, String classPath ){
        super(source);
        this.classPath = classPath;
    }

    public Stage getStage(){
        return (Stage) getSource();
    }
    public String getClassPath(){
        return classPath;
    }
}