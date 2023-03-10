package com.epf.rentmanager.model;

public class Vehicle {
    private long id;
    private String constructor;
    private String model;
    private int nb_places;

    public Vehicle(long id, String constructor, int nb_places, String model) {
        this.id = id;
        this.constructor = constructor;
        this.nb_places = nb_places;
        this.model = model;
    }
    public Vehicle(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConstructor() {
        return constructor;
    }

    public void setConstructor(String constructor) {
        this.constructor = constructor;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }


    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", constructor='" + constructor + '\'' +
                ", model='" + model + '\'' +
                ", nb_places=" + nb_places +
                '}';
    }
}
