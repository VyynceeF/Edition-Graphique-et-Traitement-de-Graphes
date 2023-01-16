/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * ElementGraphe.java             16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe;

import saeEditeur.graphe.propriete.ProprieteNoeudSourceLien;
import saeEditeur.graphe.propriete.ProprieteNoeudCibleLien;
import saeEditeur.graphe.propriete.ProprieteNoeudPositionY;
import saeEditeur.graphe.propriete.ProprieteNoeudPositionX;
import saeEditeur.graphe.propriete.ProprieteNomNoeud;
import saeEditeur.graphe.propriete.Propriete;
import saeEditeur.graphe.noeud.Noeud;
import saeEditeur.graphe.typegraphe.Graphe;

/**
 * Représentation d'un élément du graphe
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public class ElementGraphe {
    
    /** Graphe auquel appartient l'élément */
    public Graphe g;
    
    /**
     * @return L'ensemble des propriétées de l'élément
     */
    public Propriete[] getPropriete(){
        
        Propriete[] proprietees;
        
        if(this instanceof Noeud){ // Si l'instance est un Noeud
            
            proprietees = new Propriete[3];
            //Proprietees d'un Noeud
            proprietees[0] = new ProprieteNomNoeud(this);
            proprietees[1] = new ProprieteNoeudPositionX(this);
            proprietees[2] = new ProprieteNoeudPositionY(this);
            
        }else{ // Si l'instance est un Lien
            
            proprietees = new Propriete[2];
            //Proprietees d'un Lien
            proprietees[0] = new ProprieteNoeudSourceLien(this);
            proprietees[1] = new ProprieteNoeudCibleLien(this);
            
        }
 
        return proprietees;
    }
    
}
