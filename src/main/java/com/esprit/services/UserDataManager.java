package com.esprit.services;

public class UserDataManager {
    private static UserDataManager instance;
    private int userId;

    private UserDataManager() {

    }

    public static UserDataManager getInstance() {
        if (instance == null) {
            instance = new UserDataManager();
        }
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public  void logout() {
        userId = 0;
    }

}

}

