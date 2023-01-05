/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author amine.daamouch
 */
public abstract class Graphe {
    
    /** Noeud actuellement selectionne dans le graphe */
    public Noeud noeudSelectionne = null;
    
    /** Lien actuellement selectionne dans le graphe */
    public Lien lienSelectionne = null;
    
    ArrayList<Noeud> noeuds ;
    
    final double DECALLAGE = Noeud.RAYON + 5 ;
    
    ArrayList<Lien> liens ;
    
    public Graphe(){
        Noeud.noNoeud = 0 ;
        liens =  new ArrayList<>();
        noeuds = new ArrayList<>();
        noeudSelectionne = null;
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
     * Permet de savoir s'il y a un lien sur la position (x, y)
     * @param x Abscisse du point a tester
     * @param y Ordonnee du point a tester
     * @return Le lien s'il existe un lien sur la position (x, y), false sinon
     */
    public Lien estLien(double x, double y) {
        
        for (Lien aTester : liens) {
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
    
    /**
     * Permet d'enregistrer le graphe dans un fichier avec json
     */
    public void enregistrer() {
        
        try {
            // Sérialisation XML d'un noeud dans fichier essai.xml
            XMLEncoder encoder = new XMLEncoder(new FileOutputStream("essai.xml"));
            encoder.writeObject(this);
            encoder.close();
           
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Noeud> getNoeuds() {
        return noeuds;
    }

    public void setNoeuds(ArrayList<Noeud> noeuds) {
        this.noeuds = noeuds;
    }

    public ArrayList<Lien> getLiens() {
        return liens;
    }

    public void setLiens(ArrayList<Lien> liens) {
        this.liens = liens;
    }
    
    /**
     * Stocke le noeud selectionne (pour le deselectionner plus tard)
     * Et Augmente l'epaisseur de la bordure representant le noeud
     * @param noeudSelectionne Noeud selectionne
     */
    public void noeudSelectionne(Noeud noeudSelectionne) {
        
        noeudSelectionne.noeudSelectionne();
        this.noeudSelectionne = noeudSelectionne;
    }
    
    /**
     * Stocke le lien selectionne (pour le deselectionner plus tard)
     * Et Augmente l'epaisseur du lien
     * @param lienSelectionne Noeud selectionne
     */
    public void lienSelectionne(Lien lienSelectionne) {
        
        lienSelectionne.lienSelectionne();
        this.lienSelectionne = lienSelectionne;
    }
    
    /**
     * Deselectionne tous les elements du graphe
     */
    public void deselectionnerAll() {
        
        if (noeudSelectionne != null) {
            
            noeudSelectionne.noeudDeselectionne();
            noeudSelectionne = null;
        }
    }
}
