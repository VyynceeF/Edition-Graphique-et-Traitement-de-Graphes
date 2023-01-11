/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;


import java.io.Serializable;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;


/**
 *
 * @author amine.daamouch
 */
public abstract class Noeud extends ElementGraphe {
    
    /** Position du point sur l'axe(x,y) */
    Point position ; 
    
    /** nom du Noeud */
    String nom ; 
    
    /** Nom du Noeud a créer*/
    Text nomText = null; 
    
    /** Cercle pour dessiner le noeud */
    Circle c = null ; 
    
    /** Liste des successeurs  */
    ArrayList<Lien> successeurs ;
    
    /** Liste des predecesseurs  */
    ArrayList<Lien> predecesseurs ;
    
    /** Rayon du cercle représentant le noeud */
    public static final double RAYON = 50; 

    public Noeud() {
    }
    
    /** Début de l'appelation du Noeud */
    public final static String NOM = "N" ;
    
    /**
     * noNoeud commence à 0 et qui est incrémenté de 1
     * à chaque crétion de Noeud 
     */
    public static int noNoeud = 0 ;
    
    public Noeud(double x, double y, Graphe g){
        position = new Point(x,y);
        nom = NOM + noNoeud;
        noNoeud++;
        successeurs = new ArrayList<>();
        predecesseurs = new ArrayList<>();
        this.g = g;
    }
    
    public Noeud(double x, double y, String nom, Graphe g){
        position = new Point(x,y);
        this.nom = nom ; 
        successeurs = new ArrayList<>();
        predecesseurs = new ArrayList<>();
        this.g = g;
    }
    
    public String toString() {
        
        return nom;
    }
    
    public void modifierPosition(double x, double y, AnchorPane zoneDessin) {
        
        position.setX(x);
        position.setY(y);
        
        for (int noSuccesseur = 0 ; noSuccesseur < successeurs.size() ; noSuccesseur++) {
            
            successeurs.get(noSuccesseur).dessiner(zoneDessin);
        }
        for (int noPredecesseur = 0 ; noPredecesseur < predecesseurs.size() ; noPredecesseur++) {
            
            predecesseurs.get(noPredecesseur).dessiner(zoneDessin);
        }
        dessiner(zoneDessin);
    }
    
    public Circle dessiner(AnchorPane zoneDessin){
        
        if (c != null) {
            zoneDessin.getChildren().remove(c);
            zoneDessin.getChildren().remove(nomText);
        }
        c = new Circle();
        c.setCenterX(position.x);
        c.setCenterY(position.y);
        c.setRadius(RAYON);
        c.setFill(Color.WHITE);
        c.setStroke(Color.BLACK);
        c.setStrokeWidth(1);
        zoneDessin.getChildren().add(c);
        
        // Nom du noeud
        nomText = new Text(position.x,position.y,this.nom);
        nomText.setFont(new Font(12));
        
        nomText.setBoundsType(TextBoundsType.VISUAL);
        nomText.setX(position.x - (nomText.getLayoutBounds().getWidth() / 2));
        
        
        zoneDessin.getChildren().add(nomText);
        return c;
    }
    
    public boolean estClique(double x, double y){
        return Math.sqrt((x - position.x )*(x - position.x )
                + (y - position.y )*(y - position.y)) <= RAYON;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Text getNomText() {
        return nomText;
    }

    public void setNomText(Text nomText) {
        this.nomText = nomText;
    }

    public Circle getC() {
        return c;
    }

    public void setC(Circle c) {
        this.c = c;
    }

    public static int getNoNoeud() {
        return noNoeud;
    }

    public static void setNoNoeud(int noNoeud) {
        Noeud.noNoeud = noNoeud;
    }
    
    /**
     * Augmente l'epaisseur du cercle representant le noeud
     */
    public void noeudSelectionne() {
        
        c.setStrokeWidth(3);
    }
    
    /**
     * Remet l'epaisseur du cercle representant le noeud par defaut
     */
    public void noeudDeselectionne() {
        
        c.setStrokeWidth(1);
    }

    void supprimer(AnchorPane zoneDessin) {
        
        /* Suppression de tous les liens */
        
        // Suppression des liens successeurs
        for (int i = 0; i < successeurs.size() ; i++) {
            
            successeurs.get(i).supprimer(zoneDessin);
        }
        // Suppression des liens predecesseurs
        for (int i = 0; i < predecesseurs.size() ; i++) {
            
            predecesseurs.get(i).supprimer(zoneDessin);
        }
        zoneDessin.getChildren().removeAll(c, nomText);
    }
    
    

}
