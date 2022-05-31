package com.flashcard.entity;

import jakarta.persistence.*;

@Entity
@Table(name= "score")
public class Score {
    @Id
    @SequenceGenerator(name="score_generator", sequenceName = "score_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "score_generator")
    @Column(name="id")
    private int id;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;
    @Column(name="score_first")
    private int scoreFirst;
    @Column(name="score_second")
    private int scoreSecond;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="set_id")
    private Set set;

    public Score() {
    }

    public Score(int userId, int scoreFirst, int scoreSecond, int setId) {
        this.scoreFirst = scoreFirst;
        this.scoreSecond = scoreSecond;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScoreFirst() {
        return scoreFirst;
    }

    public void setScoreFirst(int scoreFirst) {
        this.scoreFirst = scoreFirst;
    }

    public int getScoreSecond() {
        return scoreSecond;
    }

    public void setScoreSecond(int scoreSecond) {
        this.scoreSecond = scoreSecond;
    }



    @Override
    public String toString() {
        return "Scores{" +
                "id=" + id +
                ", scoreFirst=" + scoreFirst +
                ", scoreSecond=" + scoreSecond +
                '}';
    }


}
