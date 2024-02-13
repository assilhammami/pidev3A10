package com.esprit.tests;

import com.esprit.models.Admin;
import com.esprit.models.Artiste;
import com.esprit.services.ServiceAdmin;
import com.esprit.services.ServiceArtiste;

public class MainProg {
    public static void main(String[] args) {
        ServiceAdmin sa=new ServiceAdmin();
        ServiceArtiste sar=new ServiceArtiste();
     Admin ad1=new Admin(1,"aa","admin","admin","aaaaa","admin00","admin1");
        Admin ad2=new Admin(2,"bb","admin2","admin2","bbbb","admin00","admin2");
        Artiste ar1= new Artiste(23,"cc","admin3","admin3","cccc","admin00","admin3","photoshop","2003");
       Artiste ar2=new Artiste(13,"aa","admin","admin","aaaaa","admin00","admin1","dessin","2004");
     sar.modifier(new Artiste(24,"ok","ok","ok","ok","ok","ko","photoshop","2003"));

    }
}
