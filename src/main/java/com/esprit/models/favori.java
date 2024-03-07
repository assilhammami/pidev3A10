package com.esprit.models;

public class favori {
    private int idfavori;
    private int idpublication;
    private int iduser;

    public favori(int idfavori, int idpublication, int iduser) {
        this.idfavori = idfavori;
        this.idpublication = idpublication;
        this.iduser = iduser;
    }

    public favori() {
    }

    public int getIdfavori() {
        return idfavori;
    }

    public void setIdfavori(int idfavori) {
        this.idfavori = idfavori;
    }

    public int getIdpublication() {
        return idpublication;
    }

    public void setIdpublication(int idpublication) {
        this.idpublication = idpublication;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public favori(int idpublication, int iduser) {
        this.idpublication = idpublication;
        this.iduser = iduser;
    }
}
