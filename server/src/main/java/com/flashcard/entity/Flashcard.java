package com.flashcard.entity;

import jakarta.persistence.*;

@Entity
@Table(name= "flashcard")
public class Flashcard {

    @Id
    @SequenceGenerator(name="flashcard_generator", sequenceName = "flashcard_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flashcard_generator")
    @Column(name="id")
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name="set_id")
    private Set set;
    @Column(name="first_sentence")
    private String firstSentence;
    @Column(name="second_sentence")
    private String secondSentence;
    @Column(name="first_correct")
    private int firstCorrect;
    @Column(name="second_correct")
    private int secondCorrect;


    public Flashcard() {
    }

    public Flashcard(String firstSentence, String secondSentence) {
        this.firstSentence = firstSentence;
        this.secondSentence = secondSentence;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public String getFirstSentence() {
        return firstSentence;
    }

    public void setFirstSentence(String firstSentence) {
        this.firstSentence = firstSentence;
    }

    public String getSecondSentence() {
        return secondSentence;
    }

    public void setSecondSentence(String secondSentence) {
        this.secondSentence = secondSentence;
    }

    public int getFirstCorrect() {
        return firstCorrect;
    }

    public void setFirstCorrect(int firstCorrect) {
        this.firstCorrect = firstCorrect;
    }

    public int getSecondCorrect() {
        return secondCorrect;
    }

    public void setSecondCorrect(int secondCorrect) {
        this.secondCorrect = secondCorrect;
    }

    @Override
    public String toString() {
        return "Flashcard{" +
                "id=" + id +
                ", set=" + set +
                ", firstSentence='" + firstSentence + '\'' +
                ", secondSentence='" + secondSentence + '\'' +
                ", firstCorrect=" + firstCorrect +
                ", secondCorrect=" + secondCorrect +
                '}';
    }
}

