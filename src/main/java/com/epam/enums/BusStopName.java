package com.epam.enums;

public enum  BusStopName {

    FIRST_STOP("First_stop"),
    SECOND_STOP("Second_stop") ,
    THIRD_STOP("Third_stop"),
    FOURTH_STOP("Fourth_stop");

    private String name;

    BusStopName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
