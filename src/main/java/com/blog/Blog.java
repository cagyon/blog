package com.blog;

import static spark.Spark.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.freemarker.FreeMarkerRoute;

import java.util.*;

import com.blog.dao.ArticleDBService;
import com.blog.dao.ArticleMySqlDao;
import com.blog.dao.CommentDBService;
import com.blog.dao.CommentMySqlDao;
import com.blog.dao.UserDBService;
import com.blog.dao.UserMySqlDao;
 
//contrôleur//
public class Blog {

	// Create DAOs for Article, Comment and User
    private static UserDBService<User> userDbService = new UserMySqlDao<User>();
    private static ArticleDBService<Article> articleDbService = new ArticleMySqlDao<Article>();
    private static CommentDBService<Comment> commentDbService = new CommentMySqlDao<Comment>();
    
    private static User currentUser;
    
    public static void main(String[] args) {
    	
        get(new FreeMarkerRoute("/") {//freemarker = moteur de template
            @Override
            public ModelAndView handle(Request request, Response response) {//requete http sur le serveur qui vient du navigateur
            	//(get ou post) et reponse que  la page du navigateur renvoie
            	//methode handle associée à la route
                Map<String, Object> viewObjects = new HashMap<String, Object>();
                if (currentUser != null) {
                	viewObjects.put("currentUser", currentUser.getLogin());
                }
                viewObjects.put("templateName", "articleList.ftl");

                ArrayList<Article> articles = articleDbService.readAll();                
                if (articles.isEmpty()) {
                    viewObjects.put("hasNoArticles", "Welcome, please click \"Write Article\" to begin.");
                } else {
                	viewObjects.put("articles", articles);
                	viewObjects.put("articleCount", articles.size());//mets ce couple clef/valeur ds la hashmap
                }
 
                return modelAndView(viewObjects, "layout.ftl");
            }
        });
        
        get(new FreeMarkerRoute("/login") {
            @Override
            public ModelAndView handle(Request request, Response response) {
                Map<String, Object> viewObjects = new HashMap<String, Object>();
                if (currentUser != null) {
                	response.redirect("/"); 
                }                
                viewObjects.put("templateName", "loginForm.ftl");
                return modelAndView(viewObjects, "layout.ftl");
            }
        });
        
        post(new FreeMarkerRoute("/login") {
            @Override
            public Object handle(Request request, Response response) {
 
                String login = request.queryParams("login-login");
                String password = request.queryParams("login-password");
                
                // Call DAO to authenticate user
               currentUser = userDbService.authenticate(login, password);
               if (currentUser != null) {
            	   // Authentication successful
            	   response.status(200);
                   response.redirect("/"); 
               }
               else {
            	   // Authentication failed
            	   response.status(403);
                   response.redirect("/login");
               }
               return "";
             }
        });
        
        get(new FreeMarkerRoute("/logout") {
            @Override
            public Object handle(Request request, Response response) {
            	// Clear current user
                currentUser = null;
                response.status(200);
                response.redirect("/");   
                return "";
            }
        });
 
        get(new FreeMarkerRoute("/article/create") {
            @Override
            public Object handle(Request request, Response response) {
                
            	if (currentUser == null) {
            		response.status(403);
                    response.redirect("/login");
                    return "";
                }
            	else {
                	Map<String, Object> viewObjects = new HashMap<String, Object>();// Map 'viewObjects' utilisé pour passer des paramètres à la vue
                    viewObjects.put("currentUser", currentUser.getLogin());
                    viewObjects.put("templateName", "articleForm.ftl");                     
                    // couple clé-valeur stocké dans la Map : "templateName" (variable qui contient le nom du fichier template à utiliser), "articleForm.ftl" (le nom du fichier)
                    return modelAndView(viewObjects, "layout.ftl"); // générer la vue en utilisant les paramètres contenus dans la Map
            	}
            	
            }
        });
 
        post(new Route("/article/create") {
            @Override
            public Object handle(Request request, Response response) {
            	if (currentUser == null) {
            		response.status(403);
                    response.redirect("/login");
                }
            	else {
	                String title = request.queryParams("article-title");
	                String summary = request.queryParams("article-summary");
	                String content = request.queryParams("article-content");
	                
	                // Create a new Article (Java object)
	                Article article = new Article(title, summary, content, currentUser.getLogin());
	                // Use the articleDbService.create method to save the object in database
	                articleDbService.create(article);
	                response.status(201);
	                response.redirect("/");
            	}
                return "";
            }
        });
        
        post(new Route("/comment/create/") {
            @Override
            public Object handle(Request request, Response response) {
            	if (currentUser == null) {
            		response.status(403);
                    response.redirect("/login");
                }
            	else {
	                Integer articleId = Integer.parseInt(request.queryParams("article-id"));
	                String content = request.queryParams("comment-content");
	                
	                // Create a new Comment (Java object)
	                Comment comment = new Comment(articleId, content);
	                // Use the DAO create method to save the comment in database
	                commentDbService.create(comment);
	  
	                response.status(201);
	                response.redirect("/article/read/"+articleId);
            	}
                return "";
            }
        });
 
        get(new FreeMarkerRoute("/article/read/:id") {
            @Override
            public Object handle(Request request, Response response) {
                Map<String, Object> viewObjects = new HashMap<String, Object>();
                viewObjects.put("currentUser", currentUser);//utilisateur inscrit
                viewObjects.put("templateName", "articleRead.ftl");//template que l'on utilise pr afficher la page

                Integer id = Integer.parseInt(request.params(":id"));//request param ça retourne une string que l'on transforme en int
                Article a = articleDbService.readOne(id);//le dao caché derrière cette interface
                viewObjects.put("article", a);
                
                if (currentUser != null) {
                	if (a.getAuthor().equals(currentUser.getLogin()) || (currentUser.isAdmin())) {
                        viewObjects.put("isAllowed", true);
                	}
                    viewObjects.put("currentUser", currentUser.getLogin());
                }
                
                ArrayList<Comment> comments = commentDbService.readAll(id);
                if (comments.isEmpty()) {
                	viewObjects.put("hasNoComments", "No comments have been posted for this article.");//ce commentaire est stocké ds la Hshmap
                }
                else {
                	viewObjects.put("comments", comments);
                }
 
                return modelAndView(viewObjects, "layout.ftl");
            }
        });
 
        get(new FreeMarkerRoute("/article/update/:id") {
            @Override
            public Object handle(Request request, Response response) {
            	if (currentUser == null) {
            		response.status(403);
                    response.redirect("/login");
                    return "";
                }
            	else {
	            	Map<String, Object> viewObjects = new HashMap<String, Object>();
	                viewObjects.put("currentUser", currentUser.getLogin());
	                viewObjects.put("templateName", "articleForm.ftl");
	 
	                Integer id = Integer.parseInt(request.params(":id"));
	
	                // Use the readOne to fetch an article by its unique id
	                viewObjects.put("article", articleDbService.readOne(id));
	 
	                return modelAndView(viewObjects, "layout.ftl");
            	}
            }
        });
 
        post(new Route("/article/update/:id") {
            @Override
            public Object handle(Request request, Response response) {
            	if (currentUser == null) {
            		response.status(403);
                    response.redirect("/login");
                }
            	else {
	                Integer id      = Integer.parseInt(request.queryParams("article-id"));
	                String title    = request.queryParams("article-title");
	                String summary  = request.queryParams("article-summary");
	                String content  = request.queryParams("article-content");
	 
	                // The articleDbService handles all the updating once it has
	                // be provided with the correct data
	                articleDbService.update(id, title, summary, content);
	 
	                response.status(200);
	                response.redirect("/");
            	}
                return "";
            }
        });
 
        get(new Route("/article/delete/:id") {
            @Override
            public Object handle(Request request, Response response) {
            	if (currentUser == null) {
            		response.status(403);
                    response.redirect("/login");
                }
            	else {
	                Integer id = Integer.parseInt(request.params(":id"));
	 
	                // Provide the unique article Id and then its deleted
	                articleDbService.delete(id);
	 
	                response.status(200);
	                response.redirect("/");
            	}
                return "";
            }
        });
    }
}

    
