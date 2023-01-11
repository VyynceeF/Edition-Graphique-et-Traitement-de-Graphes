/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author vincent.faure
 */
public class ProprieteNoeudPositionY extends Propriete {
    
    /**
     * Construit une proprietee
     * @param n nom propriete
     * @param sdl mode de saisie
     * @param elg element du graphe
     */
    ProprieteNoeudPositionY(ElementGraphe elg){
        super("Y",false,elg);
    }
    
    /**
     * Fournit la liste des valeurs pour la ChoiceBox
     * @return Liste valeur si saisiDansListe = vrai, 
     *         null si saisiDansListe = false
     */
    @Override
    public String[] getListeChoix(){
        return null;
    }
    
    /**
     * Verifie si valeur est valide
     * @param valeur valeur de la saisie
     * @return true si valide sinon false
     */
    @Override
    public boolean validerSaisie(String valeur){
        
        Noeud n = (Noeud) elementGraphe; //Noeud select
        try {    
            /* 
             * Verifie que valeur est bien dans l'intervalle
             * Et que le noeud ne ce retrouve pas sur un autre noeud
             */
            if(n.g.estNoeudValideApresDeplacement(n.position.x, Double.parseDouble(valeur), n)){
                return true;
            }
        } catch(NumberFormatException e){}; //Catch si la valeur saisie n'est pas un double
        
        return false;
    }
    
    /**
     * Set la Valeur de o
     * @param o En fonction de l'object en parametre (TextField ou BoxChoice)
     */
    @Override
    public void setObject(Object o){
        TextField field = (TextField) o;
        Noeud n = (Noeud) elementGraphe;
        field.setText(Double.toString(n.position.y));
    }
    
    /**
     * Si saisie valide, applique les modification sur le Noeud 
     * (Aussi sur la zone de dessin)
     * @param valeur valeur saisie
     * @param o En fonction de l'object en parametre (TextField ou BoxChoice)
     * @param zoneDessin
     */
    @Override
    public void setValeur(String valeur, Object o, AnchorPane zoneDessin){
        
        TextField field = (TextField) o; //o est instance de TextField
        Noeud n = (Noeud) elementGraphe; //Noeud select
        
        if(validerSaisie(valeur)){ //Valeur est ok
            n.modifierPosition(n.position.x, Double.parseDouble(valeur), zoneDessin);
        }else{
            field.setText(Double.toString(n.position.y));
        }
    }
    
}
