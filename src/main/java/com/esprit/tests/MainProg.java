package com.esprit.tests;

import com.esprit.models.Travail;
import com.esprit.services.TravailService2;
import java.sql.Date;

import static com.esprit.models.StatusTravail.Attente;

public class MainProg {

    public static void main(String[] args) {
        TravailService2 ps = new TravailService2();

        // Convertir les dates de chaînes de caractères à des objets Date
        Date dateDemande = Date.valueOf("2023-06-02");
        Date dateFin = Date.valueOf("2024-01-01");

        // Utiliser les objets Date dans la création du Travail
        ps.ajouter(new Travail("jdid", 10.5, "art", Attente, dateDemande, dateFin));
    }
}
