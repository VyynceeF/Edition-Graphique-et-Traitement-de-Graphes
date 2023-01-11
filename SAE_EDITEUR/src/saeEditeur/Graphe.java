/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    
    public abstract String toString();
    
    public abstract String getFactory();
    
    public abstract void ajouterNoeud(Noeud n) throws NoeudException;
    
    public abstract Lien ajouterLien(Lien l) throws LienException;
    
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
     * Prédicat vérifiant si un noeud peut être créer ou non
     * @return true ssi la zone du noeud à créer est en dehors de la zone de chaque noeud déjà créer 
     */
    public boolean estNoeudValideApresDeplacement(double x, double y, Noeud n) {
    	
        ArrayList<Noeud> noeudsTests = (ArrayList<Noeud>) noeuds.clone();
        noeudsTests.remove(n);
        
    	for (int noATester = 0 ; noATester < noeudsTests.size() ; noATester++) {
    		
    		// Coin haut droit
    		if (noeudsTests.get(noATester).position.x - DECALLAGE <= x + DECALLAGE
                    && x + DECALLAGE <= noeudsTests.get(noATester).position.x + DECALLAGE
                    && noeudsTests.get(noATester).position.y - DECALLAGE <= y + DECALLAGE
                    && y + DECALLAGE <= noeudsTests.get(noATester).position.y + DECALLAGE) {
                    
                    return false;                    
    		}

    		// Coin haut gauche
    		if (noeudsTests.get(noATester).position.x - DECALLAGE <= x - DECALLAGE
                    && x - DECALLAGE <= noeudsTests.get(noATester).position.x + DECALLAGE
                    && noeudsTests.get(noATester).position.y - DECALLAGE <= y + DECALLAGE
                    && y + DECALLAGE <= noeudsTests.get(noATester).position.y + DECALLAGE) {

                    return false;                    
    		}

    		// Coin bas gauche
    		if (noeudsTests.get(noATester).position.x - DECALLAGE <= x - DECALLAGE
                    && x - DECALLAGE <= noeudsTests.get(noATester).position.x + DECALLAGE
                    && noeudsTests.get(noATester).position.y - DECALLAGE <= y - DECALLAGE
                    && y - DECALLAGE <= noeudsTests.get(noATester).position.y + DECALLAGE) {

                    return false;                    
    		}

    		// Coin bas droit
    		if (noeudsTests.get(noATester).position.x - DECALLAGE <= x + DECALLAGE
                    && x + DECALLAGE <= noeudsTests.get(noATester).position.x + DECALLAGE
                    && noeudsTests.get(noATester).position.y - DECALLAGE <= y - DECALLAGE
                    && y - DECALLAGE <= noeudsTests.get(noATester).position.y + DECALLAGE) {

                    return false;                    
    		}
    	}    	
        
        if (DECALLAGE > x 
            || 2000 - DECALLAGE < x
            ||  DECALLAGE > y
            || 2000 - DECALLAGE < y) {

            return false;                    
        }
        return true;
    }
    
    /**
     * Permet d'enregistrer le graphe dans un fichier
     * @param chemin Chemin et nom du fichier
     */
    public void enregistrer(String chemin) throws FileNotFoundException {
        
        // Sérialisation XML d'un noeud dans fichier essai.xml*
        System.out.println(chemin + ".xml");
        XMLEncoder encoder = new XMLEncoder(new FileOutputStream(chemin + ".xml"));
        encoder.writeObject(this);
        encoder.close();
           
    }
    
    /**
     * Permet d'ouvrir un graphe contenu dans le fichier 
     * @param chemin Chemin et nom du fichier
     */
    public static Graphe ouvrir(String chemin) throws FileNotFoundException {
        
        // Déserialisation et affichage du noeud pour véfification
        try {
            // Le fichier est bien un enregistrement d'un graphe
            XMLDecoder decoder = new XMLDecoder(new FileInputStream(chemin));

            Graphe graphe = (Graphe) decoder.readObject();
            
            return graphe;
            
        } catch (Exception e) {
            
            throw new FileNotFoundException();
        }
        
        
           
    }
    
    /**
     * Dessine sur la zone de dessin tous les elements du graphe (après ouverture)
     * @param zoneDessin Zone de dessin du graphe
     */
    public void dessiner(AnchorPane zoneDessin) {
        
        /* Dessin des noeuds */
        for (int noNoeud = 0 ; noNoeud < noeuds.size() ; noNoeud++) {
            
            noeuds.get(noNoeud).dessiner(zoneDessin);
        }
        
        /* Dessin des liens */
        for (int noLien = 0 ; noLien < liens.size() ; noLien++) {
            
            liens.get(noLien).dessiner(zoneDessin);
        }
        
        /* Precedente selection */
        if (noeudSelectionne != null) {
            
            noeudSelectionne.noeudSelectionne();
        }
        if (lienSelectionne != null) {
            
            lienSelectionne.lienSelectionne(zoneDessin);
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
    public void lienSelectionne(Lien lienSelectionne, AnchorPane zoneDessin) {
        
        lienSelectionne.lienSelectionne(zoneDessin);
        this.lienSelectionne = lienSelectionne;
    }
    
    /**
     * Deselectionne tous les elements du graphe
     */
    public void deselectionnerAll(AnchorPane zoneDessin) {
        
        if (noeudSelectionne != null) {
            
            noeudSelectionne.noeudDeselectionne();
            noeudSelectionne = null;
        }
        if (lienSelectionne != null) {
            
            lienSelectionne.lienDeselectionne(zoneDessin);
            lienSelectionne = null;
        }
    }

    void supprimerLien(Lien lienSelectionne, AnchorPane zoneDessin) {
        
        liens.remove(lienSelectionne);
        lienSelectionne.supprimer(zoneDessin);
    }
    
    void supprmierNoeud(Noeud noeudSelectionne,AnchorPane zoneDessin) {
        
        noeuds.remove(noeudSelectionne);
        /* Suppression dans la zone de dessin */
        noeudSelectionne.supprimer(zoneDessin);
        
        deselectionnerAll(zoneDessin);
    }
    
    /**
     * Modifie l'extremite du lien selectionne par le nouveau noeud
     * @param nouveauNoeud Nouveau noeud extremite
     * @param extremite 1 -> Premiere extremite | 2 -> Derniere extremite
     * @param zoneDessin Zone de dessin
     */
    void changementExtremiteLien(Noeud nouveauNoeud, int extremite, AnchorPane zoneDessin) {
        
        lienSelectionne.changementExtremite(nouveauNoeud, extremite, zoneDessin);
    }
    
    
}
