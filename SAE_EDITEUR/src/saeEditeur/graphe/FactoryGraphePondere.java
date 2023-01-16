/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * FactoryGraphePondere.java      16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe;

import saeEditeur.graphe.lien.LienException;
import saeEditeur.graphe.lien.ArcPondere;
import saeEditeur.graphe.lien.Lien;
import saeEditeur.graphe.noeud.Noeud;
import saeEditeur.graphe.noeud.NoeudException;
import saeEditeur.graphe.noeud.NoeudGraphePondere;
import saeEditeur.graphe.typegraphe.Graphe;
import saeEditeur.graphe.typegraphe.GraphePondere;

/**
 * Classe permettant la création d'un graphe pondéré, 
 * de noeud pondéré et de lien pondéré
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public class FactoryGraphePondere implements AbstractFactoryGraphe {
    
    public FactoryGraphePondere() {
    }

    @Override
    public Graphe creerGraphe() {
        return new GraphePondere();
    }

    @Override
    public Noeud creerNoeud(double x, double y, Graphe g) throws NoeudException {
        return new NoeudGraphePondere(x, y, g) ;
    }

    @Override
    public Lien creerLien(Noeud source, Noeud destinataire, Graphe g) throws LienException {
        return new ArcPondere(source, destinataire, g);
    }
    
}
