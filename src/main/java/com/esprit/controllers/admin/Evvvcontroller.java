package com.esprit.controllers.admin;
import com.esprit.models.Event;

import com.esprit.models.Reservation;
import com.esprit.objects.Post;
import com.esprit.objects.Reactions;
import com.esprit.services.LikesService;
import com.esprit.services.MyListener1;
import com.esprit.services.UserDataManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;

import java.net.URL;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class Evvvcontroller implements Initializable {
    @FXML
    private Label nom;
    private Event e;
    private Reservation r;
    private MyListener1 myListener;
    @FXML
    private ImageView img;
    private Reactions currentReaction;
    Event data;
    @FXML
    public VBox all;


    @FXML
    private ImageView imgProfile;

    @FXML
    private Label username;

    @FXML
    private ImageView imgVerified;


    @FXML
    private ImageView audience;

    @FXML
    private Label caption;

    @FXML
    private ImageView imgPost;

    @FXML
    private Label nbReactions;

    @FXML
    private Label nbComments;

    @FXML
    private Label nbShares;

    @FXML
    private HBox share;
    @FXML
    private HBox reactionsContainer;

    @FXML
    private ImageView imgLike;

    @FXML
    private ImageView imgLove;

    @FXML
    private ImageView imgCare;

    @FXML
    private ImageView imgHaha;

    @FXML
    private ImageView imgWow;

    @FXML
    private ImageView imgSad;

    @FXML
    private ImageView imgAngry;

    @FXML
    private HBox likeContainer;

    @FXML
    private ImageView imgReaction;

    @FXML
    private Label reactionName;
    @FXML
    public ImageView mostliked1;

    @FXML
    public ImageView mostliked2;

    @FXML
    public ImageView mostliked3;
    private LikesService LS = new LikesService();
    private int user_liked = 0;
    private Post post = new Post();
    private int userId;
    private long startTime = 0;


    public void setData(Event e, MyListener1 myListener) throws SQLException {
        this.e = e;
        data = e;
        userId = UserDataManager.getInstance().getUserId();
        if (LS.hasUserLikedPost(data.getId(), userId)) {
            Image image = new Image("file:src/main/java/com/esprit/" + Reactions.valueOf(LS.currentRection(data.getId(), userId)).getImgSrc());


            imgReaction.setImage(image);
            user_liked = 1;
        }
        setmost_rection(e.getId());

        post.setTotalReactions(LS.getTotalLikesForPost(data.getId()));
        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        //  nbComments.setText(post.getNbComments() + " comments");
        //  nbShares.setText(post.getNbShares()+" shares");

        currentReaction = Reactions.LIKE;
        this.myListener = myListener;
        nom.setText(e.getNom());
        ImageView imageView = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.minHeight(300);
        imageView.maxHeight(300);
        imageView.maxWidth(300);
        imageView.minHeight(300);
        imageView.prefHeight(300);

        imageView.setPreserveRatio(true);
        try {

            File file = new File("src/main/resources/images/" + e.getImage());
            String imageUrl = file.toURI().toURL().toExternalForm();
            Image image = new Image(imageUrl);


            img.minHeight(200);
            img.maxHeight(200);
            img.maxWidth(200);
            img.minHeight(200);
            img.prefHeight(200);
            img.prefWidth(200);
            img.setFitHeight(200);
            img.setFitWidth(200);
            img.setImage(image);

            System.out.println(e.getImage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }

    @FXML
    public VBox hakimvbox;

    @FXML
    private void click(MouseEvent event) {
        myListener.onClickListener(e);
    }

    @FXML
    public void onLikeContainerMouseReleased(MouseEvent me) throws SQLException {
        if (System.currentTimeMillis() - startTime > 500) {
            reactionsContainer.setVisible(true);
        } else {
            if (reactionsContainer.isVisible()) {
                reactionsContainer.setVisible(false);
            }
            if (currentReaction == Reactions.NON) {
                setReaction(Reactions.LIKE);
                if (user_liked == 0) {
                    LS.addLike(data.getId(), userId, "LIKE");
                }//kifkif
                post.setTotalReactions(LS.getTotalLikesForPost(data.getId()));
                nbReactions.setText(String.valueOf(post.getTotalReactions()));
                setmost_rection(data.getId());

            } else {
                setReaction(Reactions.NON);
                LS.deleteLike(data.getId(), userId);// badlha

                user_liked = 0;

            }
            // badel lina
            post.setTotalReactions(LS.getTotalLikesForPost(data.getId()));
            nbReactions.setText(String.valueOf(post.getTotalReactions()));
            setmost_rection(data.getId());

        }
    }

    @FXML
    public void onReactionImgPressed(MouseEvent me) throws SQLException {
        switch (((ImageView) me.getSource()).getId()) {
            case "imgLove":
                setReaction(Reactions.LOVE);
                if (user_liked == 0) {
                    LS.addLike(data.getId(), userId, "LOVE");
                } else {
                    LS.updateLike(data.getId(), userId, "LOVE");
                }

                break;
            case "imgCare":
                setReaction(Reactions.CARE);
                if (user_liked == 0) {
                    LS.addLike(data.getId(), userId, "CARE");
                } else {
                    LS.updateLike(data.getId(), userId, "CARE");
                }
                break;
            case "imgHaha":
                setReaction(Reactions.HAHA);
                if (user_liked == 0) {
                    LS.addLike(data.getId(), userId, "HAHA");
                } else {
                    LS.updateLike(data.getId(), userId, "HAHA");
                }
                break;
            case "imgWow":
                setReaction(Reactions.WOW);
                if (user_liked == 0) {
                    LS.addLike(data.getId(), userId, "WOW");
                } else {
                    LS.updateLike(data.getId(), userId, "WOW");
                }
                break;
            case "imgSad":
                setReaction(Reactions.SAD);
                if (user_liked == 0) {
                    LS.addLike(data.getId(), userId, "SAD");
                } else {
                    LS.updateLike(data.getId(), userId, "SAD");
                }
                break;
            case "imgAngry":
                setReaction(Reactions.ANGRY);
                if (user_liked == 0) {
                    LS.addLike(data.getId(), userId, "ANGRY");
                } else {
                    LS.updateLike(data.getId(), userId, "ANGRY");
                }
                break;
            default:
                setReaction(Reactions.LIKE);
                if (user_liked == 0) {
                    LS.addLike(data.getId(), userId, "LIKE");
                } else {
                    LS.updateLike(data.getId(), userId, "LOVE");
                }
                break;
        }
        reactionsContainer.setVisible(false);
        user_liked = 1;
        setmost_rection(data.getId());
        post.setTotalReactions(LS.getTotalLikesForPost(data.getId()));
        nbReactions.setText(String.valueOf(post.getTotalReactions()));
    }

    public void setReaction(Reactions reaction) throws SQLException {
        Image image = new Image("file:src/main/java/com/esprit/" + reaction.getImgSrc());
        System.out.println(reaction.getImgSrc());

        imgReaction.setImage(image);
        reactionName.setText(reaction.getName());
        reactionName.setTextFill(Color.web(reaction.getColor()));
// code hatha ma3jbnich akel 7aja ow arj3

        if (currentReaction == Reactions.NON) {
            //  post.setTotalReactions(post.getTotalReactions() + 1);
        }
// as i said
        currentReaction = reaction;

        if (currentReaction == Reactions.NON) {
            // post.setTotalReactions(post.getTotalReactions() - 1);
        }
//badel lina
        post.setTotalReactions(LS.getTotalLikesForPost(data.getId()));
        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        setmost_rection(data.getId());
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValues(Map<K, V> map) {
        List<Map.Entry<K, V>> entries = new LinkedList<>(map.entrySet());

        // Sort the list based on values
        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Populate a LinkedHashMap to maintain the insertion order
        Map<K, V> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public void setmost_rection(int id) throws SQLException {


        Map<String, Integer> sortedMap = new TreeMap<>(LS.readLikesForPost(id));
        TreeMap<Integer, String> sortedByValueMap = new TreeMap<>();
        sortedMap = sortByValues(sortedMap);

        // System.out.println( sortedByValueMap.toString() );


        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            if (count >= 3) {
                break;
            }
            Image image;


            String imgSrc = "file:src/main/java/com/esprit/" + Reactions.valueOf(entry.getKey()).getImgSrc();

            switch (count + 1) {
                case 1:
                    mostliked1.setImage(new Image(imgSrc));
                    break;
                case 2:
                    mostliked2.setImage(new Image(imgSrc));
                    break;
                case 3:
                    mostliked3.setImage(new Image(imgSrc));
                    break;


            }

            count++;
        }
        String imgSrc = "file:src/main/java/com/esprit/img/abyeth_mini.jpg";

        for (int i = count; i < 3; i++) {
            switch (count + 1) {
                case 1:
                    mostliked1.setImage(new Image(imgSrc));
                    break;
                case 2:
                    mostliked2.setImage(new Image(imgSrc));
                    break;
                case 3:
                    mostliked3.setImage(new Image(imgSrc));
                    break;


            }
        }
    }


    public void onLikeContainerPressed(MouseEvent me) {
        startTime = System.currentTimeMillis();

    }
    // Assuming this is inside the EvvController class


    public void share() {
        // Retrieve the description and image URL from the data object
        String description = data.getNom();
        String imageUrl = data.getImage();

        // Encode the description and image URL
        try {
            description = URLEncoder.encode(description, "UTF-8");
            imageUrl = URLEncoder.encode(imageUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Construct the Facebook share URL with the encoded description and image URL
        String shareUrl = "https://www.facebook.com/sharer/sharer.php?u=" + imageUrl + "&quote=" + description;

        // Open the share URL in a web browser
        openWebPage(shareUrl);
    }



    // Méthode pour télécharger l'image sur un serveur accessible par Facebook




    // Method to open a web page in the default system browser
    private void openWebPage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


   /*@FXML
    void share(MouseEvent event) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        String description = data.getDescription();
       String image =  data.getImage();
       System.out.println(image);

        // Encode the description and image URL
        try {
            description = URLEncoder.encode(description, "UTF-8");
            image = URLEncoder.encode(image, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Construct the Facebook share URL with the encoded description and image URL
        String shareUrl = "https://www.facebook.com/sharer/sharer.php?u=" + image + "&quote=" + description;
        webEngine.load(shareUrl);
        openWebPage(shareUrl);
        post.setNbShares(post.getNbShares()+1);


}*/}
