package com.epam.autocode.kafka.game_rating;

public class Game {

    private String name;
    private Integer numberOfPreorders;

    public Game(String name, Integer numberOfPreorders) {
        this.name = name;
        this.numberOfPreorders = numberOfPreorders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfPreorders() {
        return numberOfPreorders;
    }

    public void setNumberOfPreorders(Integer numberOfPreorders) {
        this.numberOfPreorders = numberOfPreorders;
    }
}