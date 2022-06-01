package com.flashcard.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "set")
public class Set {
    @Id
    @SequenceGenerator(name="set_generator", sequenceName = "set_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "set_generator")
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @OneToMany(mappedBy = "set",
            cascade = {CascadeType.PERSIST,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    private List<Flashcard> flashcards;
    @OneToMany(mappedBy = "set",
            cascade = {CascadeType.PERSIST,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    private List<Score> scores;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="color_id")
    private Color color;

    public Set() {
    }


    public Set(String name) {
        this.name = name;
    }

    public Set(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Set{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                '}';
    }

    public void add(Flashcard tempFlashcard){
        if(flashcards == null){
            flashcards = new ArrayList<>();
        }
        flashcards.add(tempFlashcard);
        tempFlashcard.setSet(this);
    }

    public void add(Score tempScore){
        if(scores == null){
            scores = new ArrayList<>();
        }
        scores.add(tempScore);
        tempScore.setSet(this);
    }
}
