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
//    exports com.flashcard.service to spring.beans;

//    opens com.flashcard.service to spring.core;
    opens com.flashcard.controller to javafx.fxml;
    opens com.flashcard.repository to com.google.gson;
    exports com.flashcard.listener;
    opens com.flashcard.listener to javafx.fxml;
}