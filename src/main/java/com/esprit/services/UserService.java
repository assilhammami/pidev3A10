package com.esprit.services;

import com.esprit.models.Admin;
import com.esprit.models.Artiste;
import com.esprit.models.Client;
import com.esprit.models.User;
import com.esprit.utils.DataSource;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.constant.ConstantDescs.NULL;
import javafx.scene.image.Image;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;

public class UserService implements IService<User> {
    private Connection connection;
    Encryptor encryptor = new Encryptor();

    byte[] encryptionKey = {65, 12, 12, 12, 12, 12, 12, 12, 12,
            12, 12, 12, 12, 12, 12, 12 };

    public UserService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(User a) {
        String req = "INSERT into user(photo_de_profile,nom,prenom,email,password,username,num_telephone,UserType,date_de_naissance,Active ) values (?,?,?,?,?,?, ?,?,?,?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, a.getPhoto_de_profile());
            pst.setString(2, a.getNom());
            pst.setString(3, a.getPrenom());
            pst.setString(4, a.getEmail());
            pst.setString(5, encryptor.encrypt(a.getMot_de_passe(),encryptionKey));
            pst.setString(6, a.getUsername());
            pst.setInt(7, a.getNum_telephone());
            pst.setString(8, a.getType());
            pst.setString(9, a.getDate_de_naissance());
            pst.setBoolean(10,a.getActive());


            pst.executeUpdate();
            System.out.println("User ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void modifier(User a) {
        String req = "UPDATE user set photo_de_profile = ?, nom = ?,prenom=?,email=?,password=?,username=?,num_telephone=?,UserType=?,date_de_naissance=?,Active=? where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, a.getPhoto_de_profile());
            pst.setString(2, a.getNom());
            pst.setString(3, a.getPrenom());
            pst.setString(4, a.getEmail());
            pst.setString(5,  encryptor.encrypt(a.getMot_de_passe(),encryptionKey));
            pst.setString(6, a.getUsername());
            pst.setInt(7, a.getNum_telephone());
            pst.setString(8, a.getType().toString());
            pst.setString(9, a.getDate_de_naissance());
            pst.setBoolean(10,a.getActive());
            pst.setInt(11, a.getId());

            pst.executeUpdate();
            System.out.println("User modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void supprimer(User a) {
        String req = "DELETE from user where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, a.getId());
            pst.executeUpdate();
            System.out.println("User supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<User> afficher() {
        List<User> users = new ArrayList<>();

        String req = "SELECT * FROM user ";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String userType = rs.getString("UserType").trim();
                System.out.println("UserType from database: " + userType);

                if ("ADMIN".equals(userType)) {
                    users.add(new Admin(rs.getInt("id"), rs.getString("photo_de_profile"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), encryptor.decrypt(rs.getString("password"),encryptionKey), rs.getString("username"), rs.getInt("num_telephone"), rs.getString("date_de_naissance")));
                } else if ("ARTISTE".equals(userType)) {
                    users.add(new Artiste(rs.getInt("id"), rs.getString("photo_de_profile"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),encryptor.decrypt(rs.getString("password"),encryptionKey), rs.getString("username"), rs.getInt("num_telephone"), rs.getString("UserType"), rs.getString("date_de_naissance"),rs.getBoolean("Active")));
                } else if ("CLIENT".equals(userType)) {
                    users.add(new Client(rs.getInt("id"), rs.getString("photo_de_profile"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), encryptor.decrypt(rs.getString("password"),encryptionKey), rs.getString("username"), rs.getInt("num_telephone"), rs.getString("UserType"), rs.getString("date_de_naissance"),rs.getBoolean("Active")));
                }

            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public boolean verifierLogin(String username, String password) throws SQLException {

        String req = "SELECT * FROM user WHERE username=? AND password=? ;";
        int id = -1;
        try{
        PreparedStatement pst = connection.prepareStatement(req);
        pst.setString(1, username);
        pst.setString(2, encryptor.encrypt(password,encryptionKey));
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            id = rs.getInt(1);

        }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return id != -1;
    }
    public String getPassword(TextField Visiblepassword, PasswordField Hiddenpassword ){
        if(Visiblepassword.isVisible()){
            return Visiblepassword.getText();
        } else {
            return Hiddenpassword.getText();
        }
    }
    public  boolean isUsernameAvailable(String username) throws SQLException {
        int id=-1;
            String req = "SELECT * FROM user WHERE username = ?";
           PreparedStatement pst = connection.prepareStatement(req) ;
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            id = rs.getInt(1);

        }
        return id== -1;
    }
    public  boolean isEmailAvailable(String email) throws SQLException {
        int id=-1;
        String req = "SELECT * FROM user WHERE email = ?";
        try{
        PreparedStatement pst = connection.prepareStatement(req) ;
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            id = rs.getInt(1);

        } } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id== -1;


    }
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$";

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(EMAIL_REGEX);

    public  boolean validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public  boolean areFieldsNotEmpty(List<String> fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return false;
            }
        }
        return true;
}
    public boolean isAlpha(String chaine) {
        return chaine.matches("[a-zA-Z- -]+");
    }
    public boolean isMdp(String chaine) {
        return chaine.length() < 6 ? false : chaine.matches("[a-zA-Z-0-9]+");
    }
public  boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("[2459]\\d{7}");
}
    public  boolean isValidBirthDate(String birthdate) {
        return birthdate.matches("^(\\d{4})-(\\d{2})-(\\d{2})$");
    }
    public User getUser(int i) throws SQLException {
        User connectedUser = null;
        String req = "SELECT * FROM user WHERE id = ?";
        try{
        PreparedStatement pst = connection.prepareStatement(req);
        pst.setInt(1, i);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            if (rs.getString("UserType").equals("ADMIN")) {
                connectedUser = new Admin(rs.getInt("id"), rs.getString("photo_de_profile"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),encryptor.decrypt(rs.getString("password"),encryptionKey), rs.getString("username"), rs.getInt("num_telephone"), rs.getString("date_de_naissance"));
            } else if (rs.getString("UserType").equals("ARTISTE")) {
                connectedUser = new Artiste(rs.getInt("id"), rs.getString("photo_de_profile"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), encryptor.decrypt(rs.getString("password"),encryptionKey), rs.getString("username"), rs.getInt("num_telephone"), rs.getString("UserType"), rs.getString("date_de_naissance"),rs.getBoolean("Active"));
            } else {
                connectedUser = new Client(rs.getInt("id"), rs.getString("photo_de_profile"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), encryptor.decrypt(rs.getString("password"),encryptionKey), rs.getString("username"), rs.getInt("num_telephone"), rs.getString("UserType"), rs.getString("date_de_naissance"),rs.getBoolean("Active"));
            }

        } } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return connectedUser;
    }

    public int getUserId(String username) throws SQLException {
        String req = "SELECT id FROM user WHERE username = ?";
        int i = 0;

        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                i = rs.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return i;
    }
    public void ActivateAccount(User user){user.setActive(true);
    }
    public void DesactivateAccount(User user){user.setActive(false);}
    public  Image loadImage(String filePath) {
        try {
            File file = new File(filePath);
            return new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }}
    public User getUtilisateurUsernameouEmail(String username) throws SQLException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        User connectedUser=null;

        String req="SELECT * FROM user WHERE Nom_Utilisateur=? OR email=? ;";

        PreparedStatement pst =connection.prepareStatement(req);
        pst.setString(1,username);
        pst.setString(2,username);

        ResultSet rs=pst.executeQuery();
        if (rs.next()) {
            if (rs.getString("UserType").equals("ADMIN")) {
                connectedUser = new Admin(rs.getInt("id"), rs.getString("photo_de_profile"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"),encryptor.decrypt(rs.getString("password"),encryptionKey), rs.getString("username"), rs.getInt("num_telephone"), rs.getString("date_de_naissance"));
            } else if (rs.getString("UserType").equals("ARTISTE")) {
                connectedUser = new Artiste(rs.getInt("id"), rs.getString("photo_de_profile"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), encryptor.decrypt(rs.getString("password"),encryptionKey), rs.getString("username"), rs.getInt("num_telephone"), rs.getString("UserType"), rs.getString("date_de_naissance"),rs.getBoolean("Active"));
            } else {
                connectedUser = new Client(rs.getInt("id"), rs.getString("photo_de_profile"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), encryptor.decrypt(rs.getString("password"),encryptionKey), rs.getString("username"), rs.getInt("num_telephone"), rs.getString("UserType"), rs.getString("date_de_naissance"),rs.getBoolean("Active"));
            }

        }
    {return connectedUser;}}
    public int generer(){
        Random rand = new Random();
        int code = rand.nextInt((9999 - 1000) + 1) + 1000;
        return code;
    }

}


