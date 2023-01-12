/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author amine.daamouch
 */
public class FactoryGraphePondere implements AbstractFactoryGraphe{
    
    public FactoryGraphePondere(){
        
    }

    @Override
    public Graphe creerGraphe() {
        return new GraphePondere();
    }

    @Override
    public Noeud creerNoeud(double x, double y, Graphe g) throws NoeudException{
        return new NoeudGraphePondere(x, y, g) ;
    }

    @Override
    public Lien creerLien(Noeud source, Noeud destinataire, Graphe g) throws LienException{
        return new ArcPondere(source, destinataire, g);
    }
    
}
