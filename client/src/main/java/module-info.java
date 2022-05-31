module com.flashcard {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.beans;
    requires spring.core;
    requires spring.context;
    exports com.flashcard;
    exports com.flashcard.controller;

    
    opens com.flashcard.controller to javafx.fxml;
}