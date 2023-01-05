/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;


import java.io.Serializable;
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
    
    /** Début de l'appelation du Noeud */
    public final static String NOM = "N" ;
    
    /**
     * noNoeud commence à 0 et qui est incrémenté de 1
     * à chaque crétion de Noeud 
     */
    public static int noNoeud = 0 ;
    
    public Noeud(double x, double y){
        position = new Point(x,y);
        nom = NOM + noNoeud;
        noNoeud++;
        
    }
    
    public Noeud(double x, double y, String nom){
        position = new Point(x,y);
        this.nom = nom ; 
    }
    
    public String toString() {
        
        return nom;
    }
    
    public Circle dessiner(AnchorPane zoneDessin){
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
   
}
