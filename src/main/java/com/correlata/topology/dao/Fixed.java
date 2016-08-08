package com.correlata.topology.dao;

/**
 * Created by Denis on 15/12/13.
 */
public class Fixed {
    boolean x;
    boolean y;

    public Fixed() {
    }

    public Fixed(boolean x, boolean y) {
        this.x = x;
        this.y = y;
    }

    public boolean isX() {
        return x;
    }

    public void setX(boolean x) {
        this.x = x;
    }

    public boolean isY() {
        return y;
    }

    public void setY(boolean y) {
        this.y = y;
    }
}
