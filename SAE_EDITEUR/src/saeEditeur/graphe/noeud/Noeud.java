/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * Noeud.java                     16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
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
 * Représentation d'un noeud pour un graphe
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
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
    
    @Override
    public String toString() {
        
        return nom;
    }
    
    /**
     * Modifie la position d'un noeud et sa représentation
     * @param x Abscisse du noeud
     * @param y Ordonnée du noeud
     * @param zoneDessin Zone de dessin
     */
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
    
    /**
     * Dessine le noeud avec un cercle et son nom à l'intérieur
     * @param zoneDessin Zone de dessin
     * @return Le cercle représentant le noeud
     */
    public abstract Circle dessiner(AnchorPane zoneDessin);
    
    /**
     * Prédicat vérifiant si le point (x, y) est situé dans le cercle 
     * représentant le noeud
     * @param x Abscisse du point
     * @param y Ordonnée du point
     * @return true si le point est dans le cercle, false sinon
     */
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
     * Augmente l'epaisseur de la bordure du cercle représentant le noeud
     */
    public void noeudSelectionne() {
        
        c.setStrokeWidth(3);
    }
    
    /**
     * Remet l'epaisseur de la bordure du cercle representant le noeud par defaut
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
