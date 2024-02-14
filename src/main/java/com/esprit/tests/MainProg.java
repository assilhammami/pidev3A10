package com.esprit.tests;

import com.esprit.models.Event;
import com.esprit.models.Reservation;
import com.esprit.services.EventService;

import com.esprit.services.ReservationService;
import com.esprit.utils.DataSource;

public class MainProg {

    public static void main(String[] args) {
        EventService ev = new  EventService();
        ReservationService re = new ReservationService();

     // ev.ajouter(new Event("painting", "85566","ggg",50,"tunis"));
     // ev.supprimer(new Event(2,"painting", "85566","ggg",50,"tunis"));
       // ev.modifier(new Event(3,"painting", "77777","ggg5555",50,"tunis"));
   //   System.out.println(ev.afficher());
       // re.ajouter(new Reservation( "85566","ggg",50));
        // re.supprimer(new Reservation(2, "85566","ggg",50));
       // re.modifier(new Reservation(3, "77777","ggg5555",50));
        //   System.out.println(re.afficher());
    }
}
