package com.correlata.topology.dao;

import java.util.Comparator;
import java.util.Set;

/**
 * Created by Denis on 15/12/13.
 */
public class Node{
    String id;
    String name;
    String shape;
    int x;
    int y;
    Fixed fixed;
    String label;
    String type;
    String title;
    String group;
    String index;
    Set<String> hiddenGroup;
    String color;
    String image;
    int size;
    /*Set<String> groupSet;

    public Set<String> getGroupSet() {
        return groupSet;
    }

    public void setGroupSet(Set<String> groupSet) {
        this.groupSet = groupSet;
    }*/

    public Node() {
    }

    public Node(String id) {
        this.id = id;
    }

    public Node(String id, String name, String label) {
        this.id = id;
        this.name = name;
        this.label = label;
    }

    public Node(String id, int x, int y, Fixed fixed) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.fixed = fixed;
    }

    public Node(String id, String shape, String color, String image, int x, int y, Fixed fixed) {
        this.id = id;
        this.shape = shape;
        /*this.color = color;*/
        //this.image = image;
        this.x = x;
        this.y = y;
        this.fixed = fixed;
    }

   /* public static Node createNode(String id, String label, String type, String title, String group, String index, String hiddenGroup){
        Node node = new Node();
        node.setId(id);
        node.setLabel(label);
        node.setType(type);
        node.setTitle(title);
        node.setGroup(group);
        node.setIndex(index);
        node.setHiddenGroup(hiddenGroup);
        return node;
    }*/

    public Node(String id, String label, String type, String title, String color, Set<String> hiddenGroup) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.title = title;
        this.color = color;
        this.hiddenGroup = hiddenGroup;
    }

    public Node(String id, String label, String type, String title, String color) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.title = title;
        this.color = color;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Fixed getFixed() {
        return fixed;
    }

    public void setFixed(Fixed fixed) {
        this.fixed = fixed;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

   /* public String getHiddenGroup() {
        return hiddenGroup;
    }

    public void setHiddenGroup(String hiddenGroup) {
        this.hiddenGroup = hiddenGroup;
    }*/

    public Set<String> getHiddenGroup() {
        return hiddenGroup;
    }

    public void setHiddenGroup(Set<String> hiddenGroup) {
        this.hiddenGroup = hiddenGroup;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", shape='" + shape + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", fixed=" + fixed +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", group='" + group + '\'' +
                ", index='" + index + '\'' +
                ", hiddenGroup='" + hiddenGroup + '\'' +
                ", color='" + color + '\'' +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        Node node = (Node) obj;
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        if(!id.equalsIgnoreCase(node.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
