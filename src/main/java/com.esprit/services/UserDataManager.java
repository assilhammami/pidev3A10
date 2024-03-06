package com.esprit.services;

import java.util.HashSet;
import java.util.Set;

public class UserDataManager {
    private static UserDataManager instance;
    private int userId;
    private int selectedCoursId;
    private Set<Integer> likedCourses = new HashSet<>();
    private Set<Integer> dislikedCourses = new HashSet<>();
    private UserDataManager() {

    }

    public boolean addLike(int courseId) {
        return likedCourses.add(courseId);
    }

    public boolean addDislike(int courseId) {
        return dislikedCourses.add(courseId);
    }

    public boolean hasLiked(int courseId) {
        return likedCourses.contains(courseId);
    }

    public boolean hasDisliked(int courseId) {
        return dislikedCourses.contains(courseId);
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
    public int getSelectedCoursId() {
        return selectedCoursId;
    }
    public void setSelectedCoursId(int selectedCoursId) {
        this.selectedCoursId = selectedCoursId;
    }
    public  void logout() {
        userId = 0;
    }
}