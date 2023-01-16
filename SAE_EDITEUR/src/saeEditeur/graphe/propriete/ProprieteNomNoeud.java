/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * ProprieteNomNoeud.java         16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe.propriete;

import saeEditeur.graphe.noeud.Noeud;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import saeEditeur.graphe.ElementGraphe;

/**
 * Représentation des propriétés d'un Noeud
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public class ProprieteNomNoeud extends Propriete {
    
    /**
     * Construit une proprietee
     * @param elg element du graphe
     */
    public ProprieteNomNoeud(ElementGraphe elg){
        super("Nom",false,elg);
    }
    
    @Override
    public String[] getListeChoix(){
        return null;
    }
    
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
    
    @Override
    public void setObject(Object o){
        TextField field = (TextField) o;
        Noeud n = (Noeud) elementGraphe;
        field.setText(n.nom);
    }
    
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
