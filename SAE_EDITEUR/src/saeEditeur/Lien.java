/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

/**
 *
 * @author amine.daamouch
 */
public abstract class Lien {
    
    /** premier noeud partant du lien */
    Noeud source ;
    /** deuxieme noeud partant du lien */
    Noeud destinataire;
    
    /** Cercle représentant la premiere extremite du lien */
    Circle pointDepart;
    
    /** Cercle représentant la derniere extremite du lien */
    Circle pointArrive;
    
    /**
     * 
     * @param source
     * @param destinataire 
     */
    public Lien(Noeud source , Noeud destinataire){
        this.source = source;
        this.destinataire= destinataire;
    }

    public Lien() {
    }
    
    /**
     * Predicat verifiant si le point (x, y) est sur le lien)
     * @param x Abscisse du point a tester
     * @param y Ordonnee du point a tester
     * @return Le lien s'il existe un lien sur la position (x, y), false sinon
     */
    public abstract boolean estClique(double x, double y);
    
    public abstract void lienSelectionne(AnchorPane zoneDessin);
    
    /**
     * Vérifie si une des extremite du lien est en position x, y
     * @param x Abscisse du point
     * @param y Ordonnee du point
     * @return 0 si aucunes extremites
     *         1 si l'extremite est le depart du lien
     *         2 si l'extremite est l'arrive du lien
     */
    public abstract int estExtremite(double x, double y);
    
    public abstract void lienDeselectionne(AnchorPane zoneDessin);
  
    public abstract void dessiner(AnchorPane zoneDessin);
    
    public abstract void remiseDefaut();
    
    /**
     * Permet de deplacer l'extremite en position x, y
     * @param x Nouvelle abscisse
     * @param y Nouvelle ordonnee
     * @param extremite 1 -> Premiere extremite | 2 -> Derniere extremite
     * @param zoneDessin Zone de dessin
     */
    public abstract void modifierPosition(double x, double y, int extremite, AnchorPane zoneDessin);
    
    /**
     * Permet le changement du noeud extremite par un nouveauNoeud
     * 1 -> Premiere extremite | 2 -> Derniere extremite
     * @param nouveauNoeud Nouveau noeud pour l'extremite
     * @param extremite 1 -> Premiere extremite | 2 -> Derniere extremite
     * @param zoneDessin Zone de dessin
     */
    public abstract void changementExtremite(Noeud nouveauNoeud, int extremite, AnchorPane zoneDessin);
    
    public abstract void supprimer(AnchorPane zoneDessin);

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
        
    
}
