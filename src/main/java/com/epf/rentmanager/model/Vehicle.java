package com.epf.rentmanager.model;

public class Vehicle {
    private int id;
    public String constructeur;
    public String modele;
    public byte nb_places;

    public Vehicle(int id, String constructeur, String modele, byte nb_places) {
        this.id = id;
        this.constructeur = constructeur;
        this.modele = modele;
        this.nb_places = nb_places;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public String getModele() {
        return modele;
    }

    public byte getNbPlaces() {
        return nb_places;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setNbPlaces(byte nb_places) {
        this.nb_places = nb_places;
    }
    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", constructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                ", nb_places=" + nb_places +
                '}';
    }
}
