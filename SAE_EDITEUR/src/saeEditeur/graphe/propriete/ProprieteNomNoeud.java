/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur.graphe.propriete;

import saeEditeur.graphe.noeud.Noeud;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import saeEditeur.graphe.ElementGraphe;

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
    public ProprieteNomNoeud(ElementGraphe elg){
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
     * Verifie si valeur est valide
     * @param valeur valeur de la saisie
     * @return true si valide sinon false
     */
    @Override
    public boolean validerSaisie(String valeur){

        for (int i = 0; i < elementGraphe.g.noeuds.size() ; i++) { //Boucle sur les noeuds
            //Si la valeur est equale à un nom de noeud
            if(valeur.equals(elementGraphe.g.noeuds.get(i).nom)){
                return false;
            }
        }
        return true;
        
    }
    
    /**
     * Set la Valeur de o
     * @param o En fonction de l'object en parametre (TextField ou BoxChoice)
     */
    @Override
    public void setObject(Object o){
        TextField field = (TextField) o;
        Noeud n = (Noeud) elementGraphe;
        field.setText(n.nom);
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
        
        TextField field = (TextField) o; // o instance de TextField
        Noeud n = (Noeud) elementGraphe; //Noeud select
        
        if(validerSaisie(field.getText())){
            
            n.setNom(field.getText());//Set du nom de n
            n.nomText.setText(field.getText()); //Set du TextField nomText
            
        }else{
            //Si la saisie n'est pas valide, valeur du TextField reviens à l'initiale
            field.setText(n.nom);
        }
    }
}
