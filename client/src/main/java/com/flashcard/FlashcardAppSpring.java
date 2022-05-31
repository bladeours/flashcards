package com.flashcard;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlashcardAppSpring {

	public static void main(String[] args) {
		Application.launch(FlashcardAppJavaFX.class,args);
	}

}
