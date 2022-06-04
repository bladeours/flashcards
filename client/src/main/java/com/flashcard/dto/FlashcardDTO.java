package com.flashcard.dto;

public class FlashcardDTO {
    private String firstSentence;
    private String secondSentence;
    private int id;
    private int firstCorrect;
    private int secondCorrect;

    public String getFirstSentence() {
        return firstSentence;
    }

    public String getSecondSentence() {
        return secondSentence;
    }

    public int getId() {
        return id;
    }

    public int getFirstCorrect() {
        return firstCorrect;
    }

    public int getSecondCorrect() {
        return secondCorrect;
    }

    public void setFirstCorrect(int firstCorrect) {
        this.firstCorrect = firstCorrect;
    }

    public void setSecondCorrect(int secondCorrect) {
        this.secondCorrect = secondCorrect;
    }

    @Override
    public String toString() {
        return "FlashcardDTO{" +
                "firstSentence='" + firstSentence + '\'' +
                ", secondSentence='" + secondSentence + '\'' +
                ", id=" + id +
                ", firstCorrect=" + firstCorrect +
                ", secondCorrect=" + secondCorrect +
                '}';
    }
}
