/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import static saeEditeur.ArcProbabiliste.FLECHE_LONGUEUR;

/**
 *
 * @author amine.daamouch
 */
public class Arc extends Lien {
    
    /** Ligne (courbee) de la fleche */
    private QuadCurve quadCurve = null;
    
    /** Extremite de la fleche */
    private Line arrow1 = null;
    
    /** Extremite de la fleche */
    private Line arrow2 = null;
    
    private double xControl, yControl;
  
    public Arc(Noeud source,Noeud destinataire){
        super(source,destinataire);
    }
    
    public Arc() {
    }
    
   
    @Override
    public void dessiner(AnchorPane zoneDessin){
        
        final double EPSILON = 10E-15;
        double xPrimeSource;
        double yPrimeSource;
        double xPrimeDes;
        double yPrimeDes;
        double xVectDirect;
        double yVectDirect; 
        double xVectOrtho; 
        double yVectOrtho;
        double distance;
        double longueurFleche; 
        int multiplicateurPointControl;
       
        if (quadCurve != null) {
            
            zoneDessin.getChildren().remove(quadCurve);
            zoneDessin.getChildren().remove(arrow1);
            zoneDessin.getChildren().remove(arrow2);
        }
        
        quadCurve = new QuadCurve();

        
        distance = Math.sqrt(Math.pow(destinataire.position.x - source.position.x, 2) + Math.pow(destinataire.position.y - source.position.y, 2));
        
        if (distance != 0.0) {
            xPrimeSource = source.position.x + (destinataire.position.x - source.position.x) / (distance + EPSILON) * Noeud.RAYON;
            yPrimeSource = source.position.y + (destinataire.position.y - source.position.y) / (distance+ EPSILON) * Noeud.RAYON;
            xPrimeDes = destinataire.position.x - (destinataire.position.x - source.position.x) / (distance+ EPSILON) * Noeud.RAYON;
            yPrimeDes = destinataire.position.y - (destinataire.position.y - source.position.y) / (distance + EPSILON) * Noeud.RAYON;
            quadCurve.setStartX(xPrimeSource); 
            quadCurve.setStartY(yPrimeSource); 
            quadCurve.setEndX(xPrimeDes); 
            quadCurve.setEndY(yPrimeDes);
            multiplicateurPointControl = 80;
        } else {
            xPrimeSource = source.position.x;
            yPrimeSource = source.position.y - Noeud.RAYON; 
            xPrimeDes = source.position.x - Noeud.RAYON * Math.sqrt(2)/2;
            yPrimeDes = source.position.y - Noeud.RAYON * Math.sqrt(2)/2;
            quadCurve.setStartX(xPrimeSource);
            quadCurve.setStartY(yPrimeSource);
            quadCurve.setEndX(xPrimeDes);
            quadCurve.setEndY(yPrimeDes);
            multiplicateurPointControl = 70; 
        }

        xVectDirect = xPrimeDes - xPrimeSource; 
        yVectDirect = yPrimeDes - yPrimeSource; 

        xVectOrtho = - yVectDirect * (1 / Math.sqrt(yVectDirect * yVectDirect + xVectDirect * xVectDirect));
        yVectOrtho =   xVectDirect* (1 / Math.sqrt(yVectDirect * yVectDirect + xVectDirect * xVectDirect));

        xControl = xVectOrtho * multiplicateurPointControl + (xPrimeSource + xPrimeDes) / 2; 
        yControl = yVectOrtho * multiplicateurPointControl + (yPrimeSource + yPrimeDes) / 2;
        
        /* Longueur flèche*/
        longueurFleche = FLECHE_LONGUEUR / Math.sqrt(Math.pow(xPrimeDes - xControl, 2) + Math.pow(yPrimeDes - yControl, 2));
        
        /* Fleche coté Gauche */
        arrow1 = new Line();
        arrow1.setStartX(xPrimeDes);
        arrow1.setStartY(yPrimeDes);
        arrow1.setEndX(xPrimeDes + longueurFleche * ((xControl - xPrimeDes) * Math.cos(Math.PI/8)-(yControl - yPrimeDes) * Math.sin(Math.PI/8)));
        arrow1.setEndY(yPrimeDes + longueurFleche * ((xControl - xPrimeDes) * Math.sin(Math.PI/8)+(yControl - yPrimeDes) * Math.cos(Math.PI/8)));
        
        /** Fleche coté droit */
        arrow2 = new Line();
        arrow2.setStartX(xPrimeDes);
        arrow2.setStartY(yPrimeDes);
        arrow2.setEndX(xPrimeDes + longueurFleche * ((xControl - xPrimeDes) * Math.cos(-Math.PI/8)-(yControl - yPrimeDes) * Math.sin(-Math.PI/8)));
        arrow2.setEndY(yPrimeDes + longueurFleche * ((xControl - xPrimeDes) * Math.sin(-Math.PI/8)+(yControl - yPrimeDes) * Math.cos(-Math.PI/8)));
        
        quadCurve.setControlX(xControl); 
        quadCurve.setControlY(yControl);       
        quadCurve.setFill(null);
        quadCurve.setStroke(Color.BLACK);
        
        zoneDessin.getChildren().addAll(arrow1,arrow2, quadCurve); 
    }

    public Noeud getSource() {
        return source;
    }

    public Noeud getDestinataire() {
        return destinataire;
    }
    
    /**
     * Predicat verifiant si le point (x, y) est sur le lien)
     * @param x Abscisse du point a tester
     * @param y Ordonnee du point a tester
     * @return Le lien s'il existe un lien sur la position (x, y), false sinon
     */
    @Override
    public boolean estClique(double x, double y) {
        
        return quadCurve.intersects(x, y, 1, 1);
    }
    
    /**
     * Augmente l'epaisseur du lien
     */
    public void lienSelectionne(AnchorPane zoneDessin) {
        
        pointDepart = new Circle(quadCurve.getStartX(), quadCurve.getStartY(), 5);
        pointArrive = new Circle(quadCurve.getEndX(), quadCurve.getEndY(), 5);
        zoneDessin.getChildren().addAll(pointDepart, pointArrive);
        quadCurve.setStrokeWidth(3);
        arrow1.setStrokeWidth(3);
        arrow2.setStrokeWidth(3);
    }
    
    /**
     * Augmente l'epaisseur du lien
     */
    @Override
    public void lienDeselectionne(AnchorPane zoneDessin) {
        
        quadCurve.setStrokeWidth(1);
        arrow1.setStrokeWidth(1);
        arrow2.setStrokeWidth(1);
        zoneDessin.getChildren().removeAll(pointDepart, pointArrive);
    }
    
    @Override
    public void supprimer(AnchorPane zoneDessin) {
        
        source.successeurs.remove(this);
        destinataire.predecesseurs.remove(this);
        zoneDessin.getChildren().removeAll(quadCurve, arrow1, arrow2);
    }
    
    @Override
    public int estExtremite(double x, double y) {
        
        if (Math.sqrt((x - quadCurve.getStartX())*(x - quadCurve.getStartX())
                      + (y - quadCurve.getStartY())*(y - quadCurve.getStartY())) <= 10) {
            
            return 1;
        } else if (Math.sqrt((x - quadCurve.getEndX())*(x - quadCurve.getEndX())
                             + (y - quadCurve.getEndY())*(y - quadCurve.getEndY())) <= 10) {
            
            return 2;
        }
        return 0;
    }
    
    @Override
    public void changementExtremite(Noeud nouveauNoeud, int extremite, AnchorPane zoneDessin) {
        
    }
    
    public void remiseDefaut() {
        
        
    }
    
    /**
     * Permet de deplacer l'extremite en position x, y
     * @param x Nouvelle abscisse
     * @param y Nouvelle ordonnee
     * @param extremite 1 -> Premiere extremite | 2 -> Derniere extremite
     * @param zoneDessin Zone de dessin
     */
    public void modifierPosition(double x, double y, int extremite, AnchorPane zoneDessin){
        
        double xPrimeSource;
        double yPrimeSource;
        double xPrimeDes;
        double yPrimeDes;
        
        double distance;
        
        
        if (extremite == 1) {
            
            
        } else if (extremite == 2) {
            
           
            
        }
        
    }
}
