package com.esprit.tests;

import com.esprit.models.avis;
import com.esprit.models.cours;
import com.esprit.services.AvisService;
import com.esprit.services.CoursService;

import java.sql.Date;

import static com.esprit.models.NoteCours.*;

public class MainProg {
    public static void main(String[] args) {
        CoursService cs = new CoursService();
        Date d = new Date(15000000);
        //cours cours1=new cours("cours4","ajdhdhdh",01/03/2020,"azazazaz");
        //cs.ajouter(new cours("dtgd", "cours4",d,"https://i.pinimg.com/originals/bf/cc/8c/bfcc8cd948d23f32b7c28ada46810648.jpg"));
        //cs.modifier(new cours(25, "cours3","MOUADH"));
        //cs.supprimer(new cours(7, "Med","MOUADH"));
        //System.out.println(cs.getByIDUser(101));
        //System.out.println(cs.afficher());
        AvisService as = new AvisService();
        System.out.println(as.getByIDCours(101));
        //avis a = new avis(QUATRE,"jddh");
        //a.setIdc(101);
       // a.setIdu(8);
       //as.ajouter(a);
       // as.modifier(new avis(11,DEUX,"fffff"));
        //as.supprimer(new avis(20).getId());
       // System.out.println(as.afficher());
        //as.ajouter(new avis(2,4,"jddh","yyyyy"));
        //as.modifier(new avis(2,4,"omar","abidi"));
        //.supprimer(new avis(2,4,"jddh","yyyyy"));
        //System.out.println(as.afficher());


}
}
