module com.flashcard.clientflashcard {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.flashcard to javafx.fxml;
    exports com.flashcard;
    exports com.flashcard.controller;
    opens com.flashcard.controller to javafx.fxml;
}