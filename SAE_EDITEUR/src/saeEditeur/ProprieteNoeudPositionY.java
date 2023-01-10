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
     * Convertit valeur au type approprie et verifie si elle est valide
     * @param valeur valeur de la saisie
     * @return true si valide sinon false
     */
    @Override
    public boolean validerSaisie(String valeur){
        
        Noeud n = (Noeud) elementGraphe;
        try {        
            if(Double.parseDouble(valeur) > 56.0 
               && Double.parseDouble(valeur) < 1945.0
               && n.g.estNoeudValideApresDeplacement(n.position.x, Double.parseDouble(valeur), n)){
                return true;
            }
        } catch(NumberFormatException e){};
        
        return false;
    }
    
    @Override
    public void setObject(Object o){
        TextField field = (TextField) o;
        Noeud n = (Noeud) elementGraphe;
        field.setText(Double.toString(n.position.y));
    }
    
    /**
     * Convertit valeur au type approprie 
     * et l'affecte a l'attribut associeNoeud
     * @param valeur 
     * @param field 
     */
    @Override
    public void setValeur(String valeur, Object o, AnchorPane zoneDessin){
        
        TextField field = (TextField) o;
        Noeud n = (Noeud) elementGraphe;
        
        if(validerSaisie(valeur)){ //Valeur est ok
            n.modifierPosition(n.position.x, Double.parseDouble(valeur), zoneDessin);
        }else{
            field.setText(Double.toString(n.position.y));
        }
    }
    
}
