package com.esprit.tests;

import com.esprit.models.Num_chapitre;
import com.esprit.models.cours;
import com.esprit.services.CoursService;

public class MainProg {
    public static void main(String[] args) {
        CoursService cs = new CoursService();
        cs.ajouter(new cours(1, "cours","ajdhdhdh", Num_chapitre.CHAP3));
        //cs.modifier(new cours(7, "cours1","MOUADH", FormatCours.PRESENTIEL));
        //cs.supprimer(new cours(7, "Med","MOUADH", FormatCours.PRESENTIEL));
        //System.out.println(cs.afficher());
        //AvisService as = new AvisService();
        //as.ajouter(new avis(2,4,"jddh","yyyyy"));
        //as.modifier(new avis(2,4,"omar","abidi"));
        //as.supprimer(new avis(2,4,"jddh","yyyyy"));
        //System.out.println(as.afficher());
        //as.ajouter(new avis(2,4,"jddh","yyyyy"));
        //as.modifier(new avis(2,4,"omar","abidi"));
        //.supprimer(new avis(2,4,"jddh","yyyyy"));
        //System.out.println(as.afficher());


}
}
