/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur.graphe.noeud;


import saeEditeur.graphe.typegraphe.Graphe;
import java.util.ArrayList; 
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import saeEditeur.graphe.ElementGraphe;
import saeEditeur.graphe.lien.Lien;
import saeEditeur.Point;


/**
 *
 * @author amine.daamouch
 */
public abstract class Noeud extends ElementGraphe {
    
    /** Position du point sur l'axe(x,y) */
    public Point position ; 
    
    /** nom du Noeud */
    public String nom ; 
    
    /** Nom du Noeud a créer*/
    public Text nomText = null; 
    
    /** Cercle pour dessiner le noeud */
    public Circle c = null ; 
    
    /** Liste des successeurs  */
    public ArrayList<Lien> successeurs ;
    
    /** Liste des predecesseurs  */
    public ArrayList<Lien> predecesseurs ;
    
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
    
    public abstract Circle dessiner(AnchorPane zoneDessin);
    
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

    /**
     * Permet de supprimer un noeud
     * @param zoneDessin Zone de dessin
     */
    public abstract void supprimer(AnchorPane zoneDessin);
    
    

}
