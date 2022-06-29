package com.flashcard;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlashcardAppSpring {

	public static void main(String[] args) {
		Application.launch(FlashcardAppJavaFX.class,args);
	}

}

//TODO add tests
//TODO add language to set
//TODO if "wrong" is clicked then point will be subtracted
//TODO add logging
//TODO bug after removing last sentenceHBOX in createSet view (EDIT view)
