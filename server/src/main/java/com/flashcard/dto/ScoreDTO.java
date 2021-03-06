package com.flashcard.dto;

public class ScoreDTO {
    private int id;
    private int setId;
    private String setName;
    private int scoreFirst;
    private int scoreSecond;
    private int maxScore;
    private String colorCode;

    public ScoreDTO(int id, int setId, String setName, int scoreFirst, int scoreSecond, int maxScore, String colorCode) {
        this.id = id;
        this.setId = setId;
        this.setName = setName;
        this.scoreFirst = scoreFirst;
        this.scoreSecond = scoreSecond;
        this.maxScore = maxScore;
        this.colorCode = colorCode;
    }
}
