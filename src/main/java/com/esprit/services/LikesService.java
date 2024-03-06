package com.esprit.services;



import com.esprit.utils.DataSource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikesService  {
    private Connection connection;
    public LikesService() {
        connection = DataSource.getInstance().getConnection();
    }

    // Method to insert a new like
    public  void addLike(int post_id, int user_id, String reaction_type) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO likes (post_id, user_id, reaction_type) VALUES (?, ?, ?)") ;
        preparedStatement.setInt(1, post_id);
        preparedStatement.setInt(2, user_id);
        preparedStatement.setString(3, reaction_type);
        preparedStatement.executeUpdate();

    }

    // Method to get total likes with a specific reaction for a specific post
    public  int getTotalLikesForPost(int post_id) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT COUNT(*) AS total_likes FROM likes WHERE post_id = ? ");
        preparedStatement.setInt(1, post_id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("total_likes");

        }

        return 0;
    }

    public  String currentRection(int post_id,int user_id ) throws SQLException {

        String userLikes=new String() ;

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT reaction_type FROM likes WHERE post_id = ? AND user_id = ?") ;
        preparedStatement.setInt(1, post_id);
        preparedStatement.setInt(2, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String reactionType = resultSet.getString("reaction_type");
            userLikes=reactionType;
        }


        return userLikes;


    }
    // Method to delete a like
    public  void deleteLike(int post_id, int user_id) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM likes WHERE post_id = ? AND user_id = ? ");
        preparedStatement.setInt(1, post_id);
        preparedStatement.setInt(2, user_id);

        preparedStatement.executeUpdate();

    }



    public  void updateLike(int post_id, int user_id ,String type) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE likes SET reaction_type = ? WHERE post_id = ? AND user_id = ?");
        preparedStatement.setString(1,type);
        preparedStatement.setInt(2, post_id);
        preparedStatement.setInt(3, user_id);

        preparedStatement.executeUpdate();

    }





    public  Map<String, Integer>  readLikesForPost(int post_id) throws SQLException {
        Map<String, Integer> likesInfo = new HashMap<>();

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT reaction_type, COUNT(*) AS total_likes FROM likes WHERE post_id = ? GROUP BY reaction_type");
        preparedStatement.setInt(1, post_id);
        ResultSet resultSet = preparedStatement.executeQuery() ;
        while (resultSet.next()) {
            String reactionType = resultSet.getString("reaction_type");
            int totalLikes = resultSet.getInt("total_likes");
            likesInfo.put(reactionType, totalLikes);
        }


        return likesInfo;
    }
    public  boolean hasUserLikedPost(int post_id, int user_id) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT COUNT(*) AS like_count FROM likes WHERE post_id = ? AND user_id = ?");
        preparedStatement.setInt(1, post_id);
        preparedStatement.setInt(2, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int likeCount = resultSet.getInt("like_count");
            return likeCount > 0;
        }



        return false;
    }

    // Example usage
    public static void main(String[] args) {
        // Example usage goes here
        // Add likes, retrieve likes, delete likes, etc.
    }

}


