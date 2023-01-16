/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * FactoryGrapheOriente.java      16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe;

import saeEditeur.graphe.lien.LienException;
import saeEditeur.graphe.lien.Arc;
import saeEditeur.graphe.lien.Lien;
import saeEditeur.graphe.noeud.Noeud;
import saeEditeur.graphe.noeud.NoeudGrapheOriente;
import saeEditeur.graphe.noeud.NoeudException;
import saeEditeur.graphe.typegraphe.GrapheOriente;
import saeEditeur.graphe.typegraphe.Graphe;

/**
 * Classe permettant la création d'un graphe orienté, 
 * de noeud orienté et de lien orienté
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public class FactoryGrapheOriente implements AbstractFactoryGraphe {
    
    public FactoryGrapheOriente() { 
    }

    @Override
    public Graphe creerGraphe() {
        return new GrapheOriente();
    }

    @Override
    public Noeud creerNoeud(double x, double y, Graphe g) throws NoeudException {
        return new NoeudGrapheOriente(x, y, g) ;
    }

    @Override
    public Lien creerLien(Noeud source, Noeud destinataire, Graphe g) throws LienException {
        return new Arc(source, destinataire, g);
    }
    
}
