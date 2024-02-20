package com.esprit.tests;

import com.esprit.models.Archive;
import com.esprit.models.Travail;
import com.esprit.services.ArchiveService2;
import com.esprit.services.TravailService2;
import java.sql.Date;

import static com.esprit.models.StatusTravail.Attente;

public class MainProg {

    public static void main(String[] args) {
        TravailService2 ps = new TravailService2();
        ArchiveService2 As = new ArchiveService2();
        // Convertir les dates de chaînes de caractères à des objets Date
       Date dateDemande = Date.valueOf("2040-10-06");
       Date dateFin = Date.valueOf("2030-08-01");

        // Utiliser les objets Date dans la création du Travail
        ps.ajouter(new Travail("tableau art", 21, "arte", Attente, dateDemande, dateFin));




        //Date date_acceptation = Date.valueOf("2030-03-01");
        //As.supprimer(new Archive(3,"artistique", 70, date_acceptation));
    }
}
