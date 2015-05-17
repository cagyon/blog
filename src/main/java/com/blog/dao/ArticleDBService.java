package com.blog.dao;

import java.util.ArrayList;

// Cette interface sert à découpler le contrôleur de la base de données
// Ceci évite d'avoir la moindre dépendance (SQL ou autre) dans le contrôleur

public interface ArticleDBService<T> {
    public boolean create(T entity);
    public T readOne(int id);
    public ArrayList<T> readAll();
    public boolean update(int id, String title, String summary, String content);
    public boolean delete(int id);
    public int count();
}