/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * Point.java                     16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur;

/**
 * Représentation d'une position d'un élément
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public class Point {

    /** L'axe des abscisses */ 
    public double x;

    /** L'axe des ordonées */ 
    public double y;

    /**
     * Constructeur vide pour XMLDecoder
     */
    public Point() {
    }
    
    /** Initialise les coordonnées de l'élément */
    public Point(double x, double y) {
        this.x = x ;
        this.y = y ;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    
}
