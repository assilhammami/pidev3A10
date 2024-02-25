package com.esprit.tests;

import com.esprit.models.*;
import com.esprit.services.UserService;

import java.sql.Blob;

public class MainProg {
    public static void main(String[] args) {
        UserService us= new UserService();
        Admin admin=new Admin("C:\\Users\\user\\Downloads\\Prosits\\pidev2\\src\\main\\java\\com\\esprit\\image\\téléchargé (1).jpg","aa","aaaaa","aaaaaaaaa","a","a",1,"a");
        Artiste artiste=new Artiste("C:\\Users\\user\\Downloads\\Prosits\\pidev2\\src\\main\\java\\com\\esprit\\image\\téléchargé (1).jpg","aa","aaaaa","aaaaaaaaa","a","a",1,"a");
        Client client= new Client("C:\\Users\\user\\Downloads\\Prosits\\pidev2\\src\\main\\java\\com\\esprit\\image\\téléchargé (1).jpg","aa","aaaaa","aaaaaaaaa","a","a",1,"a");
        Admin admin1=new Admin("C:\\Users\\user\\Downloads\\Prosits\\pidev2\\src\\main\\java\\com\\esprit\\image\\téléchargé (1).jpg","aa","aaaaa","aaaaaaaaa","123","weli159",1,"a");
  //us.ajouter(artiste)
        //us.modifier(client);
        Artiste art1 =new Artiste("C:\\Users\\user\\Desktop\\zoro.jpg","zoro","zoro","zoro","zoro","zoro",1,"zoro");
        us.ajouter(art1);
        //Blob profileImageBlob = us.convertFileToBlob("\"C:\\Users\\user\\Desktop\\Batman_(black_background).jpg\"");
        //art1.setPhoto_de_profile(profileImageBlob);
        //us.modifier(art1);

        //System.out.println(us.afficher());

    }
}
