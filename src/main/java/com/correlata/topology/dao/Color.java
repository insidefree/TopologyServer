package com.correlata.topology.dao;

//import com.sun.org.apache.xpath.internal.operations.String;

/**
 * Created by Denis on 15/12/13.
 */
public class Color {

    String inherit;
    float opacity;

    public Color() {
    }

    public Color(String inherit) {
        this.inherit = inherit;
    }

    public String getInherit() {
        return inherit;
    }

    public void setInherit(String inherit) {
        this.inherit = inherit;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

}
