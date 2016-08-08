package com.correlata.topology.dao;

/**
 * Created by Denis on 15/12/13.
 */
public class Edge {

    String from;
    String to;
    String title;
    Smooth smooth;
    int width;
    Inherit inherit;
    Color color;

    public Edge() {
    }

    public Edge(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public Edge(String from, String to, Smooth smooth,int width, Color color, Inherit inherit) {
        this.from = from;
        this.to = to;
        this.smooth = smooth;
        this.color = color;
        this.width = width;
        this.inherit = inherit;
    }

    public Edge(String from, String to, Smooth smooth,int width, Color color) {
        this.from = from;
        this.to = to;
        this.smooth = smooth;
        this.width = width;
        this.color = color;
    }

    public static Edge createEdge(String from, String to, String title){
        Edge edge = new Edge();
        edge.setFrom(from);
        edge.setTo(to);
        edge.setTitle(title);
        return edge;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Smooth getSmooth() {
        return smooth;
    }

    public void setSmooth(Smooth smooth) {
        this.smooth = smooth;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Inherit getInherit() {
        return inherit;
    }

    public void setInherit(Inherit inherit) {
        this.inherit = inherit;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Edge{" + "from=" + from + ", to=" + to + ", title=" + title + ", smooth=" + smooth + ", color=" + color + '}';
    }
    @Override
    public boolean equals(Object obj) {
        Edge edge = (Edge) obj;
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        if(!from.equalsIgnoreCase(edge.from))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }
}
