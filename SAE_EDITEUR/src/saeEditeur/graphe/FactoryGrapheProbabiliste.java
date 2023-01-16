/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * FactoryGrapheProbabiliste.java 16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe;

import saeEditeur.graphe.lien.LienException;
import saeEditeur.graphe.lien.Lien;
import saeEditeur.graphe.lien.ArcProbabiliste;
import saeEditeur.graphe.noeud.Noeud;
import saeEditeur.graphe.noeud.NoeudGrapheProbabiliste;
import saeEditeur.graphe.noeud.NoeudException;
import saeEditeur.graphe.typegraphe.GrapheProbabiliste;
import saeEditeur.graphe.typegraphe.Graphe;

/**
 * Classe permettant la création d'un graphe probabiliste, 
 * de noeud probabiliste et de lien probabiliste
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public class FactoryGrapheProbabiliste implements AbstractFactoryGraphe {

    
    public FactoryGrapheProbabiliste() {
    }
    
    @Override
    public Graphe creerGraphe() {
        return new GrapheProbabiliste();
    }

    @Override
    public Noeud creerNoeud(double x, double y, Graphe g) throws NoeudException {
        return new NoeudGrapheProbabiliste(x, y, g);
    }

    public Lien creerLien(Noeud source, Noeud destinataire, Graphe g) throws LienException {
        return new ArcProbabiliste(source,destinataire,g);
    }

    

   
   
    
}
