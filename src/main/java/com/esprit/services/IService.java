package com.esprit.services;

import java.util.List;

public interface IService<T> {

    public void ajouter(T t);
    public void modifier(T t);
    public void supprimer(int id);
    public List<T> afficher();
}
