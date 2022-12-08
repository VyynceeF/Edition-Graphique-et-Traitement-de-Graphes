/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;


import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.event.*;


/**
 *
 * @author amine.daamouch
 */
public abstract class Noeud {
    
    
    /** Position du point sur l'axe(x,y) */
    Point position ; 
    
    /** nom du Noeud */
    String nom ; 
    
    /** Nom du Noeud a créer*/
    Text nomText ; 
    /** Cercle pour dessiner le noeud */
    Circle c  ; 
    
    /** Rayon du cercle représentant le noeud */
    public static final double RAYON = 50; 
    
    public Noeud(double x, double y){
        position = new Point(x,y);
    }
    
    public Noeud(double x, double y, String nom){
        position = new Point(x,y);
        this.nom = nom ; 
    }
    
    public Circle dessiner(AnchorPane zoneDessin){
        c = new Circle();
        c.setCenterX(position.x);
        c.setCenterY(position.y);
        c.setRadius(RAYON);
        c.setFill(Color.WHITE);
        c.setStroke(Color.BLACK);
        /* 
         * On suprime le noeud si celui si à déjà été créer
         * pour qu'il puisse passer en premier plan lors de la création
         * d'un lien
         */
        zoneDessin.getChildren().remove(c);
        zoneDessin.getChildren().add(c);
        nomText = new Text(position.x,position.y,this.nom);
        nomText.setFont(new Font(12));
        
        zoneDessin.getChildren().add(nomText);
        return c;
    }
    
    public boolean estClique(double x, double y){
        if(Math.sqrt((x - position.x )*(x - position.x )
           + (y - position.y )*(y - position.y)) <= RAYON){
            return true;
        }
        return false;
    }
    
    
   
}
