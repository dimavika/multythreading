package com.epam.entity;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*package-private*/ int getX() {
        return x;
    }

    /*package-private*/ int getY() {
        return y;
    }
}
