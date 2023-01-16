/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * ProprieteNoeudPositionY.java   16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe.propriete;

import java.util.ArrayList;
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
public class ProprieteNoeudPositionY extends Propriete {
    
    /**
     * Construit une proprietee
     * @param n nom propriete
     * @param sdl mode de saisie
     * @param elg element du graphe
     */
    public ProprieteNoeudPositionY(ElementGraphe elg){
        super("Y",false,elg);
    }
    
    @Override
    public String[] getListeChoix(){
        return null;
    }
    
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
    
    @Override
    public void setObject(Object o){
        TextField field = (TextField) o;
        Noeud n = (Noeud) elementGraphe;
        field.setText(Double.toString(n.position.y));
    }
    
    @Override
    public void setValeur(String valeur, Object o, AnchorPane zoneDessin){
        
        TextField field = (TextField) o; //o est instance de TextField
        Noeud n = (Noeud) elementGraphe; //Noeud select
        
        if(validerSaisie(valeur)){ //Valeur est ok
            
            // Ajout dans la pile d'action
            ArrayList<Object> ancienPosition = new ArrayList<>();
            ancienPosition.add(n.g.noeudSelectionne);
            ancienPosition.add(n.position.x);
            ancienPosition.add(n.position.y);
            n.g.ajouterPileUndo(ancienPosition);
            
            n.modifierPosition(n.position.x, Double.parseDouble(valeur), zoneDessin);
        }else{
            field.setText(Double.toString(n.position.y));
        }
    }
}
