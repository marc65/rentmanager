package com.epf.rentmanager.model;

//import jdk.vm.ci.meta.Local;

import java.time.LocalDate;

public class Reservation {

    private int id;
    private Client client_id;
    private Vehicle vehicle_id;
    private LocalDate debut;
    private LocalDate fin;

    public Reservation (int id, Client client_id, Vehicle vehicle_id, LocalDate debut, LocalDate fin){
        this.id = id;
        this.client_id = client_id;
        this.vehicle_id =vehicle_id;
        this.debut=debut;
        this.fin = fin;

    }
    public Reservation(){

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient_id() {
        return client_id;
    }

    public void setClient_id(Client client_id) {
        this.client_id = client_id;
    }

    public Vehicle getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(Vehicle vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", vehicle_id=" + vehicle_id +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }
}
