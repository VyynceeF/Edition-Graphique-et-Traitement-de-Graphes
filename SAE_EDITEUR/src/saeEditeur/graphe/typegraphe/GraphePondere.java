/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * GraphePondere.java             16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe.typegraphe;

import java.util.ArrayList;
import saeEditeur.graphe.lien.ArcPondere;
import saeEditeur.graphe.lien.Lien;
import saeEditeur.graphe.lien.LienException;
import saeEditeur.graphe.noeud.Noeud;
import saeEditeur.graphe.noeud.NoeudException;
import saeEditeur.graphe.noeud.NoeudGraphePondere;

/**
 * Représentation d'un graphe pondéré et ses fonctionnalités
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public class GraphePondere extends Graphe {

    public GraphePondere() {
    }
    
    public String toString() {
        
        return "GraphePondere";
    }
    
    @Override
    public String getFactory() {
        
        return "Graphe orienté pondéré";
    }
    
    @Override
    public void ajouterNoeud(Noeud n) throws NoeudException {
        if (n instanceof NoeudGraphePondere){
            n.setNom(nomNoeud(n.nom,0));
            noeuds.add(n);
        }else{
            throw new NoeudException("Impossible de créer un Noeud");
        }
       
    }

    @Override
    public Lien ajouterLien(Lien l)throws LienException{
        if(!(l instanceof ArcPondere)){
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
    
    @Override
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

