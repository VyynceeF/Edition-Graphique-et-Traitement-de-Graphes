/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;

import java.io.Serializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

/**
 *
 * @author amine.daamouch
 */
public class Arete extends Lien {
    
    /** Valeur de a et b dans la droite d'equation y = a x + b */
    private double a, b;
    
    private Line line = null;
    
    public Arete(Noeud source , Noeud destinataire, Graphe g){
        super(source,destinataire, g);
    }
    
    public Arete() {
        
    }
    
    
    @Override
    public void dessiner(AnchorPane zoneDessin){
        
        double xPrimeSource;
        double yPrimeSource;
        double xPrimeDes;
        double yPrimeDes;
        
        double distance;
        
        if (line != null) {
            zoneDessin.getChildren().remove(line);
        }
        
        distance = Math.sqrt(Math.pow(destinataire.position.x - source.position.x, 2) + Math.pow(destinataire.position.y - source.position.y, 2));
        xPrimeSource = source.position.x + (destinataire.position.x - source.position.x) / distance * Noeud.RAYON;
        yPrimeSource = source.position.y + (destinataire.position.y - source.position.y) / distance * Noeud.RAYON;
        xPrimeDes = destinataire.position.x - (destinataire.position.x - source.position.x) / distance * Noeud.RAYON;
        yPrimeDes = destinataire.position.y - (destinataire.position.y - source.position.y) / distance * Noeud.RAYON;
        
        line = new Line(xPrimeSource, yPrimeSource, xPrimeDes, yPrimeDes);
        
        line.setStrokeWidth(1);
        
        zoneDessin.getChildren().add(line);
        
        a = (yPrimeDes - yPrimeSource) / (xPrimeDes - xPrimeSource);
        b = -(a * xPrimeDes) + yPrimeDes;
    }

    public Noeud getSource() {
        return source;
    }

    public void setSource(Noeud source) {
        this.source = source;
    }

    public Noeud getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Noeud destinataire) {
        this.destinataire = destinataire;
    }
    
    /**
     * Predicat verifiant si le point (x, y) est sur le lien)
     * @param x Abscisse du point a tester
     * @param y Ordonnee du point a tester
     * @return Le lien s'il existe un lien sur la position (x, y), false sinon
     */
    @Override
    public boolean estClique(double x, double y) {
        
        
        if (Math.abs((a * x + b) - y) <= 5) {
            return true;
        }
        return false;
    }
    
    /**
     * Augmente l'epaisseur du lien
     */
    @Override
    public void lienSelectionne() {
        
        line.setStrokeWidth(3);
    }
    
    
    
    /**
     * Augmente l'epaisseur du lien
     */
    @Override
    public void lienDeselectionne() {
        
        line.setStrokeWidth(1);
    }
}
