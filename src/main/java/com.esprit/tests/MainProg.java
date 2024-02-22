package com.esprit.tests;

import com.esprit.models.cours;
import com.esprit.services.CoursService;

public class MainProg {
    public static void main(String[] args) {
        CoursService cs = new CoursService();
        //cours cours1=new cours("cours4","ajdhdhdh",01/03/2020,"azazazaz");
        //cs.ajouter(new cours(1, "cours4","ajdhdhdh"));
        //cs.modifier(new cours(25, "cours3","MOUADH"));
        //cs.supprimer(new cours(7, "Med","MOUADH"));
        System.out.println(cs.afficher());
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
