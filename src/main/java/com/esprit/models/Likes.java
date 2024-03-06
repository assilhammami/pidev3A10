package com.esprit.models;

import java.util.Date;

public class Likes {
    private int like_id;
    private int post_id;
    private  int      user_id;
    private String  reaction_type;

    private Date created_at;

    public Likes(int post_id, int user_id, String reaction_type) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.reaction_type = reaction_type;
    }

    public Likes(int like_id, int post_id, int user_id, String reaction_type) {
        this.like_id = like_id;
        this.post_id = post_id;
        this.user_id = user_id;
        this.reaction_type = reaction_type;
    }

    public int getLike_id() {
        return like_id;
    }

    public void setLike_id(int like_id) {
        this.like_id = like_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getReaction_type() {
        return reaction_type;
    }

    public void setReaction_type(String reaction_type) {
        this.reaction_type = reaction_type;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}