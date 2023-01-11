/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author amine.daamouch
 */
public class GrapheOriente extends Graphe {

    public GrapheOriente() {
    }
    
    public String toString() {
        
        return "GrapheOriente";
    }
    
    @Override
    public String getFactory() {
        
        return "Graphe orienté";
    }
    
    @Override
    public void ajouterNoeud(Noeud n) throws NoeudException {
        if (n instanceof NoeudGrapheOriente){
            noeuds.add(n);
        }else{
            throw new NoeudException("Impossible de créer un Noeud");
        }
       
    }

    @Override
    public Lien ajouterLien(Lien l)throws LienException{
        if(!(l instanceof Arc)){
           throw new LienException("Impossible de créer un Lien");
        }
        if (l.destinataire == l.source) {
            throw new LienException("Impossible de créer un Lien");
        }
        
        l.source.successeurs.add(l);
        l.destinataire.predecesseurs.add(l);
        
        if (!estLienValide(l)) {
            throw new LienException("Impossible de créer un lien sur un lien");
        }
        
        liens.add(l);
        return l;
    }
    
    public boolean estLienValide(Lien l)  {
        for (Lien aTester : liens) {
            if ((l.destinataire == aTester.destinataire
                   && l.source == aTester.source)) {      
                return false;
            }
                     
        } 
        return true; 
    }

    public ArrayList<Noeud> getNoeuds() {
        return noeuds;
    }

    public void setNoeuds(ArrayList<Noeud> noeuds) {
        this.noeuds = noeuds;
    }

    public ArrayList<Lien> getLiens() {
        return liens;
    }

    public void setLiens(ArrayList<Lien> liens) {
        this.liens = liens;
    }
    
    
}

