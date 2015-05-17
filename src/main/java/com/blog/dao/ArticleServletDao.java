//vue
package com.blog.dao;

import java.util.ArrayList;

import com.blog.Article;

public class ArticleServletDao<T extends Article> implements ArticleDBService<T> {
    ArrayList<T> storage;//storage est un attribut, Arraylist (c'est une classe) c'est le type de l'objet storage
 
    public ArticleServletDao() {
        storage = new ArrayList<T>();
    }
 
    public boolean create(T entity) {
        return storage.add(entity);//on ajoute entity ds storage et la valeur de retour c'est le resultat de add et add retourne un booleen
    }//add retourne un booleen qui create retourne

    public T readOne(int id) {
        return storage.get(id);
    }
 
    public ArrayList<T> readAll() {
        return storage; //
    }
 
    public boolean update(int id, String title, String summary, String content) {
        T entity = storage.get(id);
 
        entity.setSummary(summary);
        entity.setTitle(title);
        entity.setContent(content);
 
        return true;
    }
 
    public boolean delete(int id) {
        storage.get(id).delete();
        return true;
    }

	public int count() {
		return storage.size();
	}
}
