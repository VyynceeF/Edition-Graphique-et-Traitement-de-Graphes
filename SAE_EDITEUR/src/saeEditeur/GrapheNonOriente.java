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
public class GrapheNonOriente extends Graphe {
    
    
    public GrapheNonOriente(){
    }
    
    public String toString() {
        
        return "GrapheSimple";
    }
    
    @Override
    public String getFactory() {
        
        return "Graphe non orienté";
    }
    
    @Override
    public void ajouterNoeud(Noeud n) throws NoeudException {
        if (n instanceof NoeudGrapheNonOriente){
            n.setNom(nomNoeud(n.nom,0));
            noeuds.add(n);
        }else{
            // thorw l'exception 
        }
       
    }

    @Override
    public Lien ajouterLien(Lien l)throws LienException{
        if(!(l instanceof Arete)){
           // TODO Lève l'exception
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
                   && l.source == aTester.source)
                  || (l.destinataire == aTester.source
                      && l.source == aTester.destinataire)) {      
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
