package com.esprit.models;

public sealed class Personne permits Admin,Artiste,Client {

    private int id;
    public String profilepicture;
    private String name;
    private String Lastname;
    private String email;
    private String password;
    public String username;
    public UserType type;

    public Personne(int id,String profilepicture, String name, String lastname, String email, String password, String username) {
        this.id=id;
        this.profilepicture = profilepicture;
        this.name = name;
        Lastname = lastname;
        this.email = email;
        this.password = password;
        this.username = username;
        this.type = UserType.CLIENT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

    public void setProfilepicture(String profilepicture) {
        this.profilepicture = profilepicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", profilepicture='" + profilepicture + '\'' +
                ", name='" + name + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", type=" + type +
                '}';
    }
}
