package com.esprit.tests;

<<<<<<< HEAD
import com.esprit.models.Archive;
import com.esprit.models.Travail;
import com.esprit.services.ArchiveService2;
import com.esprit.services.TravailService2;
import java.sql.Date;
import java.time.LocalDate;

import static com.esprit.models.StatusTravail.Attente;

public class MainProg {

    public static void main(String[] args) {
        TravailService2 ps = new TravailService2();
        ArchiveService2 As = new ArchiveService2();
        // Convertir les dates de chaînes de caractères à des objets Date
      // Date dateDemande = Date.valueOf("2040-10-06");
     //  Date dateFin = Date.valueOf("2030-08-01");
      //  Travail t = new Travail(32,"DD",12,"DDDDD",Attente,dateDemande,dateFin,"FFFFF");
     //   t.setIdp(10);
      //  Archive a = new Archive(10,"FFFFFsSF","C:\\ddd",t);
      //  Date d = Date.valueOf(LocalDate.now());
     //   a.setDateCreation(d);
      //  ArchiveService2 service2 = new ArchiveService2();
      //  service2.modifier(a);
        System.out.println(ps.afficherbyid(10));
        //ps.ajouter(t);
        // Utiliser les objets Date dans la création du Travail
        //ps.ajouter(new Travail("tableau art", 21, "arte", Attente, dateDemande, dateFin));




        //Date date_acceptation = Date.valueOf("2030-03-01");
        //As.supprimer(new Archive(3,"artistique", 70, date_acceptation));
    }
}
=======
import com.esprit.models.Publication;
import com.esprit.models.commentaire;
import com.esprit.services.PublicationService;
import com.esprit.services.commentaireService;


public class MainProg {
    public static void main (String[] args) {
        PublicationService ps = new PublicationService();
        Publication pub = new Publication("url","l art","c est joli");
        ps.ajouter(pub);
       // ps.modifier();
       // pub.setId(11);
     //  pub.setTitre("art");
      //  ps.modifier(pub);
       //  ps.supprimer(pub);
       // System.out.println(ps.afficher());
        commentaireService cs = new commentaireService();
       // commentaire com =new commentaire("c est beau",  4  );
        // cs.ajouter(com);
       // System.out.println(cs.afficher());
        //cs.supprimer();



    }
}

>>>>>>> 2a5c9c04de20e210453822606aa0fa2e3fa3f9d9
