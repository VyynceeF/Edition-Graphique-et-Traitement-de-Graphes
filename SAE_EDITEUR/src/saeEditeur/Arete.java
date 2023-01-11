/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;

import java.io.Serializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author amine.daamouch
 */
public class Arete extends Lien {
    
    private Line line = null;
    
    public Arete(Noeud source , Noeud destinataire){
        super(source,destinataire);
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
    }
    
    /**
     * Permet de deplacer l'extremite en position x, y
     * @param x Nouvelle abscisse
     * @param y Nouvelle ordonnee
     * @param extremite 1 -> Premiere extremite | 2 -> Derniere extremite
     * @param zoneDessin Zone de dessin
     */
    public void modifierPosition(double x, double y, int extremite, AnchorPane zoneDessin){
        
        if (extremite == 1) {
                      
            line.setStartX(x);
            line.setStartY(y);
            pointDepart.setCenterX(x);
            pointDepart.setCenterY(y);
            
        } else if (extremite == 2) {
                      
            line.setEndX(x);
            line.setEndY(y);
            pointArrive.setCenterX(x);
            pointArrive.setCenterY(y);
            
        }
        
    }
    
    public void remiseDefaut() {
        
        double distance = Math.sqrt(Math.pow(destinataire.position.x - source.position.x, 2) + Math.pow(destinataire.position.y - source.position.y, 2));
        double xPrimeSource = source.position.x + (destinataire.position.x - source.position.x) / distance * Noeud.RAYON;
        double yPrimeSource = source.position.y + (destinataire.position.y - source.position.y) / distance * Noeud.RAYON;
        double xPrimeDes = destinataire.position.x - (destinataire.position.x - source.position.x) / distance * Noeud.RAYON;
        double yPrimeDes = destinataire.position.y - (destinataire.position.y - source.position.y) / distance * Noeud.RAYON;
        line.setStartX(xPrimeSource);
        line.setStartY(yPrimeSource);
        line.setEndX(xPrimeDes);
        line.setEndY(yPrimeDes);
    }
    
    @Override
    public void changementExtremite(Noeud nouveauNoeud, int extremite, AnchorPane zoneDessin) {
        
        if (extremite == 1) {
        /* Noeud source */
                      
            // Le noeud source n'a plus ce lien comme predecesseur
            source.successeurs.remove(this);
            
            // Modification de l'extremite du lien
            source = nouveauNoeud;
            source.successeurs.add(this);
            
            // On redessine correctement le lien
            this.dessiner(zoneDessin);
            
        } else if (extremite == 2) {
        /* Noeud destinataire */
                      
            // Le noeud source n'a plus ce lien comme predecesseur
            destinataire.predecesseurs.remove(this);
            
            // Modification de l'extremite du lien
            destinataire = nouveauNoeud;
            destinataire.predecesseurs.add(this);
            
            // On redessine correctement le lien
            this.dessiner(zoneDessin);
        }
    }
    
    
    @Override
    public void supprimer(AnchorPane zoneDessin) {
        
        source.successeurs.remove(this);
        destinataire.predecesseurs.remove(this);
        zoneDessin.getChildren().removeAll(line);
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
     * Predicat verifiant si le point (x, y) est sur le lien
     * @param x Abscisse du point a tester
     * @param y Ordonnee du point a tester
     * @return Le lien s'il existe un lien sur la position (x, y), false sinon
     */
    @Override
    public boolean estClique(double x, double y) {
        
        return line.intersects(x, y, 1, 1);
    }
    
    /**
     * Augmente l'epaisseur du lien
     */
    @Override
    public void lienSelectionne(AnchorPane zoneDessin) {
        
        pointDepart = new Circle(line.getStartX(), line.getStartY(), 5);
        pointArrive = new Circle(line.getEndX(), line.getEndY(), 5);
        zoneDessin.getChildren().addAll(pointDepart, pointArrive);
        line.setStrokeWidth(3);
    }
    
    @Override
    public int estExtremite(double x, double y) {
        
        if (Math.sqrt((x - line.getStartX())*(x - line.getStartX())
                      + (y - line.getStartY())*(y - line.getStartY())) <= 5) {
            
            return 1;
        } else if (Math.sqrt((x - line.getEndX())*(x - line.getEndX())
                             + (y - line.getEndY())*(y - line.getEndY())) <= 5) {
            
            return 2;
        }
        return 0;
    }
    
    /**
     * Augmente l'epaisseur du lien
     */
    @Override
    public void lienDeselectionne(AnchorPane zoneDessin) {
        
        line.setStrokeWidth(1);
        zoneDessin.getChildren().removeAll(pointDepart, pointArrive);
    }
}
