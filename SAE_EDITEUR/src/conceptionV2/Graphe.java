/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;
import java.util.*;
/**
 *
 * @author amine.daamouch
 */
public abstract class Graphe {
    
    ArrayList<Noeud> noeuds ;
    
    final double DECALLAGE = Noeud.RAYON + 5 ;
    
    ArrayList<Lien> liens ;
    
    public Graphe(){
        liens =  new ArrayList<>();
        noeuds = new ArrayList<>();
    }
    
    public abstract void ajouterNoeud(Noeud n) throws NoeudException;
    
    public abstract Lien ajouterLien(Lien l ) throws LienException;
    
    public Noeud estNoeud(double x, double y) {
        
        for (Noeud aTester : noeuds) {
            // racine_carre((x_point - x_centre)² + (y_centre - y_point)) < rayon
            if (aTester.estClique(x, y)) {
                
                return aTester;
            }
        }
        return null;
    }
    
   
    /**
     * Prédicat vérifiant si un noeud peut être créer ou non
     * @return true ssi la zone du noeud à créer est en dehors de la zone de chaque noeud déjà créer 
     */
    public boolean estNoeudValide(double x, double y) throws NoeudException{
    	
    	for (int noATester = 0 ; noATester < noeuds.size() ; noATester++) {
    		
    		// Coin haut droit
    		if (noeuds.get(noATester).position.x - DECALLAGE <= x + DECALLAGE
                    && x + DECALLAGE <= noeuds.get(noATester).position.x + DECALLAGE
                    && noeuds.get(noATester).position.y - DECALLAGE <= y + DECALLAGE
                    && y + DECALLAGE <= noeuds.get(noATester).position.y + DECALLAGE) {
                    throw new NoeudException("Impossible de créer un noeud "
                            + "sur un noeud existant");
                    
    		}

    		// Coin haut gauche
    		if (noeuds.get(noATester).position.x - DECALLAGE <= x - DECALLAGE
                    && x - DECALLAGE <= noeuds.get(noATester).position.x + DECALLAGE
                    && noeuds.get(noATester).position.y - DECALLAGE <= y + DECALLAGE
                    && y + DECALLAGE <= noeuds.get(noATester).position.y + DECALLAGE) {

                    throw new NoeudException("Impossible de créer un noeud "
                            + "sur un noeud existant");
    		}

    		// Coin bas gauche
    		if (noeuds.get(noATester).position.x - DECALLAGE <= x - DECALLAGE
                    && x - DECALLAGE <= noeuds.get(noATester).position.x + DECALLAGE
                    && noeuds.get(noATester).position.y - DECALLAGE <= y - DECALLAGE
                    && y - DECALLAGE <= noeuds.get(noATester).position.y + DECALLAGE) {

                    throw new NoeudException("Impossible de créer un noeud "
                            + "sur un noeud existant");
    		}

    		// Coin bas droit
    		if (noeuds.get(noATester).position.x - DECALLAGE <= x + DECALLAGE
                    && x + DECALLAGE <= noeuds.get(noATester).position.x + DECALLAGE
                    && noeuds.get(noATester).position.y - DECALLAGE <= y - DECALLAGE
                    && y - DECALLAGE <= noeuds.get(noATester).position.y + DECALLAGE) {

                    throw new NoeudException("Impossible de créer un noeud "
                            + "sur un noeud existant");
    		}
    	}    	
        
        if (DECALLAGE > x 
            || 2000 - DECALLAGE < x
            ||  DECALLAGE > y
            || 2000 - DECALLAGE < y) {
            throw new NoeudException("Impossible de créer un noeud "
                            + "sur un noeud existant");
        }
        return true;
    }
    
}
