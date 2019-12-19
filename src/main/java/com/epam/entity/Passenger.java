package com.epam.entity;

public class Passenger {

    private String name;
    private String destinationGoal;

    public Passenger(String name, String destinationGoal) {
        this.name = name;
        this.destinationGoal = destinationGoal;
    }

    public String getDestinationGoal() {
        return destinationGoal;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + name + '\'' +
                '}';
    }
}
