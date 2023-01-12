/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author amine.daamouch
 */
public class FactoryGrapheOriente implements AbstractFactoryGraphe{
    
    public FactoryGrapheOriente(){
        
    }

    @Override
    public Graphe creerGraphe() {
        return new GrapheOriente();
    }

    @Override
    public Noeud creerNoeud(double x, double y, Graphe g) throws NoeudException{
        return new NoeudGrapheOriente(x, y, g) ;
    }

    @Override
    public Lien creerLien(Noeud source, Noeud destinataire, Graphe g) throws LienException{
        return new Arc(source, destinataire, g);
    }
    
}
