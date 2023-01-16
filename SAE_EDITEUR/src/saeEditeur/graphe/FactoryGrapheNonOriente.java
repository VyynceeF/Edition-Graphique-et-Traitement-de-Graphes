/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * FactoryGrapheNonOriente.java   16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe;

import saeEditeur.graphe.lien.LienException;
import saeEditeur.graphe.lien.Arete;
import saeEditeur.graphe.lien.Lien;
import saeEditeur.graphe.noeud.Noeud;
import saeEditeur.graphe.noeud.NoeudGrapheNonOriente;
import saeEditeur.graphe.noeud.NoeudException;
import saeEditeur.graphe.typegraphe.GrapheNonOriente;
import saeEditeur.graphe.typegraphe.Graphe;

/**
 * Classe permettant la création d'un graphe non orienté, 
 * de noeud non orienté et de lien non orienté
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public class FactoryGrapheNonOriente implements AbstractFactoryGraphe {
    
    public FactoryGrapheNonOriente() {
    }
    
    @Override
    public Graphe creerGraphe() {
        return new GrapheNonOriente();
    }

    @Override
    public Noeud creerNoeud(double x, double y, Graphe g) throws NoeudException {
       
        return new NoeudGrapheNonOriente(x, y, g) ;
    }

    @Override
    public Lien creerLien(Noeud source, Noeud destinataire, Graphe g) throws LienException {
        return new Arete(source, destinataire, g);
    }
    
    
}
