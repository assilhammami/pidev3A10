package com.esprit.models;

public final class Admin extends Personne{
    public Admin(int id,String profilepicture, String name, String lastname, String email, String password, String username) {
        super(id,profilepicture, name, lastname, email, password, username);
        this.type=UserType.ADMIN;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "profilepicture='" + profilepicture + '\'' +
                ", username='" + username + '\'' +
                ", type=" + type +
                '}';
    }
}
