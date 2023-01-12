/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author vincent.faure
 */
public class ElementGraphe {
    
    /** Graphe de l'element */
    public Graphe g;
    
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
