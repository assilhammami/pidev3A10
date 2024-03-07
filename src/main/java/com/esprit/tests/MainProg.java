package com.esprit.tests;

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

