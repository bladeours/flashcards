package com.flashcard.dto;

public class ScoreDTO {
    private int id;
    private int setId;
    private String setName;
    private int scoreFirst;
    private int scoreSecond;
    private int maxScore;

    public int getId() {
        return id;
    }

    public int getSetId() {
        return setId;
    }

    public String getSetName() {
        return setName;
    }

    public int getScoreFirst() {
        return scoreFirst;
    }

    public int getScoreSecond() {
        return scoreSecond;
    }

    public int getMaxScore() {
        return maxScore;
    }
}
