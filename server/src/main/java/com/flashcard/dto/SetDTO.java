package com.flashcard.dto;

public class SetDTO {
    private int id;
    private String name;
    private String color;

    public SetDTO(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.color = code;
    }

    @Override
    public String toString() {
        return "SetDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + color + '\'' +
                '}';
    }
}
