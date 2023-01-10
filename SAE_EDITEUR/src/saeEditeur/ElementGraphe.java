/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;

/**
 *
 * @author vincent.faure
 */
public class ElementGraphe {
    
    /** Graphe de l'element */
    Graphe g;
    
    public Propriete[] getPropriete(){
        
        Propriete[] proprietees;
        
        if(this instanceof Noeud){
            
            proprietees = new Propriete[3];
            
            proprietees[0] = new ProprieteNomNoeud(this);
            proprietees[1] = new ProprieteNoeudPositionX(this);
            proprietees[2] = new ProprieteNoeudPositionY(this);
            
        }else{
            
            proprietees = new Propriete[2];
            
            proprietees[0] = new ProprieteNoeudSourceLien(this);
            proprietees[1] = new ProprieteNoeudCibleLien(this);
            
        }
 
        return proprietees;
    }
    
}
