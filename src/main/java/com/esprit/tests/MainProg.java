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
=======

import com.esprit.models.*;
import com.esprit.services.Encryptor;
import com.esprit.services.UserService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;

public class MainProg {
    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        byte[] encryptionKey = {65, 12, 12, 12, 12, 12, 12, 12, 12,
                12, 12, 12, 12, 12, 12, 12 };
        UserService us= new UserService();
        //Admin admin=new Admin("C:\\Users\\user\\Downloads\\Prosits\\pidev2\\src\\main\\java\\com\\esprit\\image\\téléchargé (1).jpg","aa","aaaaa","aaaaaaaaa","a","a",1,"a");
        //Artiste artiste=new Artiste("C:\\Users\\user\\Downloads\\Prosits\\pidev2\\src\\main\\java\\com\\esprit\\image\\téléchargé (1).jpg","aa","aaaaa","aaaaaaaaa","a","a",1,"a");
        //Client client= new Client("C:\\Users\\user\\Downloads\\Prosits\\pidev2\\src\\main\\java\\com\\esprit\\image\\téléchargé (1).jpg","aa","aaaaa","aaaaaaaaa","a","a",1,"a");
        //Admin admin1=new Admin("C:\\Users\\user\\Downloads\\Prosits\\pidev2\\src\\main\\java\\com\\esprit\\image\\téléchargé (1).jpg","aa","aaaaa","aaaaaaaaa","123","weli159",1,"a");
  //us.ajouter(artiste)
        //us.modifier(client);
        //Artiste art1 =new Artiste("C:\\Users\\user\\Desktop\\zoro.jpg","zoro","zoro","zoro","zoro","zoro",1,"zoro");
        //us.ajouter(art1);
        //Blob profileImageBlob = us.convertFileToBlob("\"C:\\Users\\user\\Desktop\\Batman_(black_background).jpg\"");
        //art1.setPhoto_de_profile(profileImageBlob);
        //us.modifier(art1);

        //System.out.println(us.afficher());
        Encryptor encryptor = new Encryptor();
        String encrypted = encryptor.encrypt("123", encryptionKey);
        String decrypted = encryptor.decrypt("123", encryptionKey);
        System.out.println(encrypted);
        System.out.println(decrypted);


import com.esprit.models.Commande;
import com.esprit.models.Produits;
import com.esprit.service.CommandeService;
import com.esprit.service.ProduitsService;

import java.sql.SQLException;

public class MainProg {
    public static void main(String[] args) throws SQLException {
        ProduitsService ps = new ProduitsService();

        // ******* PRODUITS ********
        Produits p = new Produits(8,"NAV","bbibvsrvbduvbdvbdrbv s",500,1,"Janvier 1978","Tableaux");
        //ps.ajouterProduits(p);
        //ps.modifierProduits(p);
        //ps.supprimerProduits(8);
        //System.out.println(ps.afficherProduit());

        // ******* COMMANDES ********
        CommandeService cs = new CommandeService();
        Commande c = new Commande(3,"13/02/2024",2500,1,1,21);

        // cs.ajouterCommande(c);
        //cs.modifierCommande(c);
        //cs.supprimerCommande(2);
        // System.out.println(cs.afficherCommande());


    }
}
