/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * Propriete.java                 16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe.propriete;

import javafx.scene.layout.AnchorPane;
import saeEditeur.graphe.ElementGraphe;

/**
 * Représentation des propriétés pour un élément du graphe
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public abstract class Propriete {
    
    /** nom de la propriete (associe à un attribut) du noeud ou lien */
    public String nom;
    
    /** 
     * Vrai si la valeur doit etre saisie dans une ChoiceBox, 
     * Faux si la valeur doit etre saisie dans un TextField 
     */
    public boolean saisieDansListe;
    
    /** Noeud ou Lien concerne par la propriete */
    public ElementGraphe elementGraphe;
    
    /**
     * Construit une proprietee
     * @param n nom propriete
     * @param sdl mode de saisie
     * @param elg element du graphe
     */
    Propriete(String n, boolean sdl, ElementGraphe elg){
        nom = n;
        saisieDansListe = sdl;
        elementGraphe = elg;
    }
    
    /**
     * Fournit la liste des valeurs pour la ChoiceBox
     * @return Liste valeur si saisiDansListe = vrai, 
     *         null si saisiDansListe = false
     */
    public abstract String[] getListeChoix();
    
    /**
     * Verifie si  valeur est valide
     * @param valeur valeur de la saisie
     * @return true si valide sinon false
     */
    public abstract boolean validerSaisie(String valeur);
    
    /**
     * Set la Valeur de o
     * @param o En fonction de l'object en parametre (TextField ou BoxChoice)
     */
    public abstract void setObject(Object o);
    
    /**
     * Si saisie valide, applique les modification sur l'elementGraphe 
     * (Aussi sur la zone de dessin)
     * @param valeur valeur saisie
     * @param o En fonction de l'object en parametre (TextField ou BoxChoice)
     * @param zoneDessin
     */
    public abstract void setValeur(String valeur, Object o, AnchorPane zoneDessin);

}



















