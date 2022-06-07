package com.flashcard.dto;

public class ScoreDTO implements Comparable<ScoreDTO> {
    private int id;
    private int setId;
    private String setName;
    private int scoreFirst;
    private int scoreSecond;
    private int maxScore;
    private String colorCode;

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

    public String getColorCode() {
        return colorCode;
    }

    @Override
    public int compareTo(ScoreDTO newScore) {
        return setName.compareToIgnoreCase(newScore.setName);
    }
}
