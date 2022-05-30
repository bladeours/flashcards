package com.flashcard.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "user", schema = "public")
public class User {
    @Id
    @SequenceGenerator(name="user_generator", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @Column(name="id", unique = true, nullable = false)
    private int id;
    @Column(name="name")
    private String name;
    @OneToMany(mappedBy = "user",
    cascade = {CascadeType.PERSIST,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    private List<Flashcard> flashcards;
    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    private List<Score> scores ;
    public User() {
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public User(String name) {
        this.name = name;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", flashcards=" + flashcards +
                '}';
    }

    public void add(Flashcard tempFlashcard){
        if(flashcards == null){
            flashcards = new ArrayList<>();
        }
        flashcards.add(tempFlashcard);
        tempFlashcard.setUser(this);
    }

    public void add(Score tempScore){
        if(scores == null){
            scores = new ArrayList<>();
        }
        scores.add(tempScore);
        tempScore.setUser(this);
    }
}
