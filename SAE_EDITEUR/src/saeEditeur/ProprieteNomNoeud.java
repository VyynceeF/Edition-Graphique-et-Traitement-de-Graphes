/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 *
 * @author vincent.faure
 */
public class ProprieteNomNoeud extends Propriete {
    
    /**
     * Construit une proprietee
     * @param n nom propriete
     * @param sdl mode de saisie
     * @param elg element du graphe
     */
    ProprieteNomNoeud(ElementGraphe elg){
        super("Nom",false,elg);
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

        for (int i = 0; i < elementGraphe.g.noeuds.size() ; i++) {
            if(valeur.equals(elementGraphe.g.noeuds.get(i).nom)){
                return false;
            }
        }
        return true;
        
    }
    
    @Override
    public void setObject(Object o){
        TextField field = (TextField) o;
        Noeud n = (Noeud) elementGraphe;
        field.setText(n.nom);
    }
    
    /**
     * Convertit valeur au type approprie 
     * et l'affecte a l'attribut associeNoeud
     * @param valeur 
     */
    @Override
    public void setValeur(String valeur, Object o, AnchorPane zoneDessin){
        TextField field = (TextField) o;
        Noeud n = (Noeud) elementGraphe;
        
        if(validerSaisie(field.getText())){
            n.setNom(field.getText());
            n.nomText.setText(field.getText());
        }else{
            field.setText(n.nom);
        }
    }
}
