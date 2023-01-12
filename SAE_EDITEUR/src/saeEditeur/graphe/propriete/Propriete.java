/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur.graphe.propriete;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import saeEditeur.graphe.ElementGraphe;

/**
 *
 * @author vincent.faure
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


















