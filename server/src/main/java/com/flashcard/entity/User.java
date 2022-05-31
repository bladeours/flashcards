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
    private List<Score> scores ;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    private List<Set> sets;
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

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


    public void add(Score tempScore){
        if(scores == null){
            scores = new ArrayList<>();
        }
        scores.add(tempScore);
        tempScore.setUser(this);
    }

    public void add(Set tempSet){
        if(sets == null){
            sets = new ArrayList<>();
        }
        sets.add(tempSet);
        tempSet.setUser(this);
    }
}
