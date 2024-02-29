package com.esprit.tests;

import com.esprit.models.Event;
import com.esprit.models.Reservation;
import com.esprit.models.Status;
import com.esprit.services.EventService;

import com.esprit.services.ReservationService;
import com.esprit.utils.DataSource;

public class MainProg {

    private static int b;
    private static int c;

    public static void main(String[] args) {
        EventService ev = new  EventService();
        ReservationService re = new ReservationService();

        Reservation e =new Reservation("aaabba", Status.attente,3);
        e.setEvent(37);
        e.setUser(10);
        System.out.println(e);
       re.ajouter(e);
     // ev.ajouter(new Event("painting", "85566","ggg",50,"tunis"));
      //ev.supprimer(new Event(5,"painting", "85566","ggg",50,"tunis"));
       // ev.modifier(new Event(13,"pain", "77777","ggg5555",50,"tunis","https://www.bakerross.co.uk/media/catalog/product/p/o/poster-paint-pots-aw251j.jpg"));
      //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //
        //System.out.println(ev.afficher());
       // re.ajouter(new Reservation( "85566","ggg",50));
        // re.supprimer(new Reservation(2, "85566","ggg",50));
       // re.modifier(new Reservation(3, "77777","ggg5555",50));
        //   System.out.println(re.afficher());
    }
}
