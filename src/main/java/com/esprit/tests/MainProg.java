package com.esprit.tests;

import com.esprit.models.Publication;
import com.esprit.models.badge;
import com.esprit.models.commentaire;
import com.esprit.services.PublicationService;
import com.esprit.services.badgeService;
import com.esprit.services.commentaireService;
import com.esprit.utils.DataSource;


public class MainProg {
    public static void main(String[] args) {
       // PublicationService ps = new PublicationService();
       // Publication pub = new Publication("aaa", "bbb", "20/2/2006" ,2  ,"3");
       // ps.ajouter(pub);
       // pub.setId(12);
       // pub.setTitre("le bozart");
        // ps.modifier(pub);
        commentaireService cs = new commentaireService();
        commentaire com =new commentaire("c est beau", "20/12/2022" , 4 , 5);
         cs.ajouter(com);
       // badgeService bs = new badgeService();
       // badge bg =new badge( badge.TypeBadge.EVENEMENT, "expert en art", "15/2/2023" , 4);
        // bs.ajouter(bg);


    }
}

