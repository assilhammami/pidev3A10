package com.esprit.models;

public final class Client extends Personne{
    private String adresse;
    public Client(int id,String profilepicture, String name, String lastname, String email, String password, String username,String adresse) {
        super(id,profilepicture, name, lastname, email, password, username);
        this.adresse=adresse;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Client{" +
                "adresse='" + adresse + '\'' +
                ", profilepicture='" + profilepicture + '\'' +
                ", username='" + username + '\'' +
                ", type=" + type +
                '}';
    }
}
