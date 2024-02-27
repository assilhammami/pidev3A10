package com.esprit.controllers;
import com.esprit.models.User;
import com.esprit.services.Encryptor;
import com.esprit.services.UserDataManager;
import com.esprit.services.UserService;
import com.esprit.utils.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

public class UpdateAccController implements Initializable {

    @FXML
    private Button CoursesButton;

    @FXML
    private TextField Emailused;

    @FXML
    private Button EventsButton;

    @FXML
    private Button JobsButton;


    @FXML
    private ImageView Photo_de_profil;

    @FXML
    private Button PostsButton;

    @FXML
    private Button ProfileButton;

    @FXML
    private Button Submit;

    @FXML
    private Button Uploadbutton;


    @FXML
    private TextField weakPasswod;

    @FXML
    private TextField birthdate;

    @FXML
    private Button show;

    @FXML
    private Button show1;
    @FXML
    private Button hide;

    @FXML
    private Button hide1;
    @FXML
    private ImageView eyeclosed;

    @FXML
    private ImageView eyeclosed1;

    @FXML
    private ImageView eyeopen;

    @FXML
    private ImageView eyeopen1;

    @FXML
    private TextField email;

    @FXML
    private TextField emailinv;

    @FXML
    private TextField fieldserr;

    @FXML
    private PasswordField hiddenpassword1;

    @FXML
    private PasswordField hiddenpassword2;

    @FXML
    private Button logoutButton;

    @FXML
    private TextField nom;

    @FXML
    private TextField num_telephone;

    @FXML
    private TextField passerr;

    @FXML
    private TextField password1;

    @FXML
    private TextField password2;

    @FXML
    private TextField phone_err;

    @FXML
    private ImageView photoProfile;

    @FXML
    private TextField prenom;

    @FXML
    private Label UserType;

    @FXML
    private TextField urlimage;

    @FXML
    private TextField username;

    @FXML
    private Label usernameOld;

    @FXML
    private TextField usernametaken;
    @FXML
    private Button Refreshbutton;
    @FXML
    private TextField ok;
    @FXML
    private DatePicker birthdate1;
    @FXML
    private TextField alphabeticalErr;


    @FXML
    private Button DeleteButton;

    UserService us=new UserService();


   // int CurrentUserId;
    private UserDataManager userDataManager = UserDataManager.getInstance();

   /* public UpdateAccController(int currentUserId) {
        CurrentUserId = currentUserId;
    }*/
   int CurrentUserId = userDataManager.getUserId();
    User currentUser = us.getUser(CurrentUserId);
    public String old_email;
    public String old_username;

    public UpdateAccController() throws SQLException {
    }

    //public void setCurrentUserId(int loginid){this.CurrentUserId=loginid;}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



                nom.setText(currentUser.getNom());
                prenom.setText(currentUser.getPrenom());
                num_telephone.setText(String.valueOf(currentUser.getNum_telephone()));
                username.setText(currentUser.getUsername());
                email.setText(currentUser.getEmail());
        ((TextField)birthdate1.getEditor()).setText(currentUser.getDate_de_naissance().toString());
                usernameOld.setText(currentUser.getUsername());
        Image photo_profile=us.loadImage(currentUser.getPhoto_de_profile());
        photoProfile.setImage(photo_profile);
        urlimage.setText(currentUser.getPhoto_de_profile());

        /*try (InputStream inputStream = profileImageBlob.getBinaryStream()) {
            Image profileImage = new Image(inputStream);
            photoProfile.setImage(profileImage);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show a default image)
        }*/
                //Image profileImage =  (currentUser.getPhoto_de_profile());

                UserType.setText(currentUser.getType().toString());
                 old_email= currentUser.getEmail();
                 old_username= currentUser.getUsername();
         }



    @FXML
    void DeleteMyAccount(ActionEvent event) throws IOException {
        Stage stage=(Stage) logoutButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VerificationDelete.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
    @FXML
    void EditCoursesbutton(ActionEvent event) {

    }

    @FXML
    void EditEventsbutton(ActionEvent event) {

    }

    @FXML
    void EditJobsbutton(ActionEvent event) {

    }

    @FXML
    void EditPostsbutton(ActionEvent event) {

    }

    @FXML
    void EditProfilebutton(ActionEvent event) {

    }

    @FXML
    void LogOut(ActionEvent event) throws IOException {
        userDataManager.logout();
        CurrentUserId =0;
        currentUser=null;
        Stage stage=(Stage) logoutButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }


    @FXML
    void SaveChanges(ActionEvent event) throws SQLException, IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String name=nom.getText();
        String lastname=prenom.getText();
        String num=num_telephone.getText();
        String username1=username.getText();
        String adresse=email.getText();
        String path=urlimage.getText();
        Image img=photoProfile.getImage();
        String datenaissance=((TextField)birthdate1.getEditor()).getText();
        boolean testemailold=!us.isEmailAvailable(adresse);
        boolean testusernameold=!us.isUsernameAvailable(username1);
        if(old_email.equals(adresse)){testemailold=false;};
        if(old_username.equals(username1)){testusernameold=false;};


        String pass1=us.getPassword(password1,hiddenpassword1);
        String pass2=us.getPassword(password2,hiddenpassword2);
        if(pass1!=""&&pass2!=""){
        List<String> fields=new ArrayList<>(Arrays.asList(name, lastname, num, username1, adresse, path, datenaissance, pass1, pass2));

        if(!us.areFieldsNotEmpty(fields)){
            fieldserr.setVisible(true);
        }
        else {fieldserr.setVisible(false);
            phone_err.setVisible(!us.isValidPhoneNumber(num));
            emailinv.setVisible(!us.validateEmail(adresse));
            Emailused.setVisible(testemailold);
            usernametaken.setVisible(testusernameold);
             weakPasswod.setVisible(!us.isMdp(pass1));
            //birthErr.setVisible(!us.isValidBirthDate(datenaissance));}
            passerr.setVisible(!(pass1.equals(pass2)));
            alphabeticalErr.setVisible(!us.isAlpha(name)&&!us.isAlpha(lastname));}
        if(us.isValidPhoneNumber(num)&&us.validateEmail(adresse)&&!testemailold&&!testusernameold&&(pass1.equals(pass2))&&/*us.isValidBirthDate(datenaissance)&&*/us.areFieldsNotEmpty(fields)&&us.isMdp(pass1)&&us.isAlpha(name)&&us.isAlpha(lastname))
        {currentUser.setEmail(adresse);currentUser.setNom(name);currentUser.setPrenom(lastname);currentUser.setDate_de_naissance(datenaissance);currentUser.setMot_de_passe(pass1);currentUser.setPhoto_de_profile(path);currentUser.setUsername(username1);currentUser.setNum_telephone(Integer.parseInt(num));
         us.modifier(currentUser);
         Submit.setVisible(false);
         ok.setVisible(true);
         Refreshbutton.setVisible(true);

        }}else {List<String> fields=new ArrayList<>(Arrays.asList(name, lastname, num, username1, adresse, path, datenaissance));

            if(!us.areFieldsNotEmpty(fields)){
                fieldserr.setVisible(true);
            }
            else {fieldserr.setVisible(false);
                phone_err.setVisible(!us.isValidPhoneNumber(num));
                emailinv.setVisible(!us.validateEmail(adresse));
                Emailused.setVisible(testemailold);
                usernametaken.setVisible(testusernameold);
                alphabeticalErr.setVisible(!us.isAlpha(name)&&!us.isAlpha(lastname));
                //birthErr.setVisible(!us.isValidBirthDate(datenaissance));}
            passerr.setVisible(!(pass1.equals(pass2)));
            alphabeticalErr.setVisible(!us.isAlpha(name)&&!us.isAlpha(lastname));}
            if(us.isValidPhoneNumber(num)&&us.validateEmail(adresse)&&!testemailold &&!testusernameold&&(pass1.equals(pass2))&&/*us.isValidBirthDate(datenaissance)&&*/us.areFieldsNotEmpty(fields)&&us.isAlpha(name)&&us.isAlpha(lastname))
            {currentUser.setEmail(adresse);currentUser.setNom(name);currentUser.setPrenom(lastname);currentUser.setDate_de_naissance(datenaissance);currentUser.setPhoto_de_profile(path);currentUser.setUsername(username1);currentUser.setNum_telephone(Integer.parseInt(num));
                us.modifier(currentUser);
                Submit.setVisible(false);
                ok.setVisible(true);
                Refreshbutton.setVisible(true);
                 }


    }}



    @FXML
    void Hidepassword11(ActionEvent event) {
        hiddenpassword2.setText(password2.getText());
        hiddenpassword2.setVisible(true);
        password2.setVisible(false);
        hide1.setVisible(false);
        show1.setVisible(true);
    }

    @FXML
    void ShowPassword(ActionEvent event) {
        password1.setText(hiddenpassword1.getText());
        password1.setVisible(true);
        hiddenpassword1.setVisible(false);
        hide.setVisible(true);
        show.setVisible(false);
    }
    @FXML
    void hidePassword(ActionEvent event) {
        hiddenpassword1.setText(password1.getText());
        hiddenpassword1.setVisible(true);
        password1.setVisible(false);
        hide.setVisible(false);
        show.setVisible(true);

    }

    @FXML
    void Showpassword11(ActionEvent event) {
        password2.setText(hiddenpassword2.getText());
        password2.setVisible(true);
        hiddenpassword2.setVisible(false);
        hide1.setVisible(true);
        show1.setVisible(false);
    }

    @FXML
    void UploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            urlimage.setText(imagePath);
            // Mettre à jour l'image dans l'interface graphique
            Image image = new Image(selectedFile.toURI().toString());
            Photo_de_profil.setImage(image);
            Photo_de_profil.setVisible(true);
            currentUser.setPhoto_de_profile(imagePath);
        } else {
            System.out.println("Aucun fichier sélectionné");
        }
    }
    @FXML
    void RefreshPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateProfile.fxml"));
        Parent root = loader.load();

        // Get the current stage
        Stage stage = (Stage) Refreshbutton.getScene().getWindow();
        UserDataManager.getInstance().setUserId(currentUser.getId());
        // Set the new FXML file on the scene
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }



}


