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
public class ProprieteNoeudCibleLien extends Propriete {
    
    /**
     * Construit une proprietee
     * @param n nom propriete
     * @param sdl mode de saisie
     * @param elg element du graphe
     */
    ProprieteNoeudCibleLien(ElementGraphe elg){
        super("Cible",true,elg);
    }
    
    /**
     * Fournit la liste des valeurs pour la ChoiceBox
     * @return Liste valeur si saisiDansListe = vrai, 
     *         null si saisiDansListe = false
     */
    @Override
    public String[] getListeChoix(){
        
        Lien l = (Lien) elementGraphe;
        String[] liste = new String[l.g.noeuds.size()];
        
        for(int i = 0; i < l.g.noeuds.size(); i++) {
            liste[i] = l.g.noeuds.get(i).nom;
        }
                
        return liste;
    }
    
    /**
     * Convertit valeur au type approprie et verifie si elle est valide
     * @param valeur valeur de la saisie
     * @return true si valide sinon false
     */
    @Override
    public boolean validerSaisie(String valeur){

        return true;
    }
    
    @Override
    public void setObject(Object o){
        ChoiceBox box = (ChoiceBox) o;
        Lien l = (Lien) elementGraphe;
        box.setValue(l.destinataire.nom);
    }
    
    /**
     * Convertit valeur au type approprie 
     * et l'affecte a l'attribut associeNoeud
     * @param valeur 
     * @param field 
     */
    @Override
    public void setValeur(String valeur, Object o, AnchorPane zoneDessin){
        
        ChoiceBox box = (ChoiceBox) o;
        Lien l = (Lien) elementGraphe;
        
        if(validerSaisie(valeur)){
            
        }else{
            box.setValue(l.destinataire.nom);
        } 
    }
    
}
