package com.flashcard.dto;

public class FlashcardDTO {
    private String firstSentence;
    private String secondSentence;
    private int id;
    private int firstCorrect;
    private int secondCorrect;

    public FlashcardDTO(int id, String firstSentence, String secondSentence,  int firstCorrect, int secondCorrect) {
        this.firstSentence = firstSentence;
        this.secondSentence = secondSentence;
        this.id = id;
        this.firstCorrect = firstCorrect;
        this.secondCorrect = secondCorrect;
    }




}
