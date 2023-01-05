/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

import javafx.scene.layout.AnchorPane;

/**
 *
 * @author amine.daamouch
 */
public abstract class Lien {
    
    /** premier noeud partant du lien */
    Noeud source ;
     /** deuxieme noeud partant du lien */
    Noeud destinataire;
    
    
    public Lien(Noeud source , Noeud destinataire){
        this.source = source;
        this.destinataire= destinataire;
    }
    
    /**
     * Predicat verifiant si le point (x, y) est sur le lien)
     * @param x Abscisse du point a tester
     * @param y Ordonnee du point a tester
     * @return Le lien s'il existe un lien sur la position (x, y), false sinon
     */
    public abstract boolean estClique(double x, double y);
    
    public abstract void lienSelectionne();
  
    public abstract void dessiner(AnchorPane zoneDessin);

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
