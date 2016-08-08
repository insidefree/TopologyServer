package com.correlata.topology.dao;

/**
 * Created by Denis on 15/12/13.
 */
public class Smooth {
    String type;
    String forceDirection;
    float roundness;

    public Smooth() {
    }

    public Smooth(String type, String forceDirection, float roundness) {
        this.type = type;
        this.forceDirection = forceDirection;
        this.roundness = roundness;
    }

    public Smooth(String type) {
        this.type = type;
    }

    /*public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }*/

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
