package com.lawriecate.apps.easyeats2;

/**
 * Created by lawrie on 18/11/2016.
 */

public class Grocery {
    private String name;
    private Boolean bought;
    private int id;

    public Grocery(int id,String name,Boolean bought) {
        this.id = id;
        this.name = name;
        this.bought = bought;
    }

    public Grocery(String name) {
        this.name = name;
        this.bought = false;
    }

    public int getID()
    {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getBought() {
        return bought;
    }

    public void setBought(Boolean bought) {
        this.bought = bought;
    }
}

