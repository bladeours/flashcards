package com.flashcard.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="color")
public class Color {
    @Id
    @SequenceGenerator(name="color_generator", sequenceName = "color_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "color_generator")
    @Column(name="id")
    private int id;
    @Column(name="code")
    private String code;
    @OneToMany(mappedBy = "color",
            cascade = {CascadeType.PERSIST,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    private List<Set> sets;

    public Color() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }

    public void add(Set tempSet){
        if(sets == null){
            sets = new ArrayList<>();
        }
        sets.add(tempSet);
        tempSet.setColor(this);
    }
}
