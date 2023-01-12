/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author amine.daamouch
 */
public class FactoryGrapheNonOriente implements AbstractFactoryGraphe {
    
    public FactoryGrapheNonOriente(){
        
    }
    
    @Override
    public Graphe creerGraphe() {
        return new GrapheNonOriente();
    }

    @Override
    public Noeud creerNoeud(double x, double y, Graphe g) throws NoeudException{
       
        return new NoeudGrapheNonOriente(x, y, g) ;
    }

    @Override
    public Lien creerLien(Noeud source, Noeud destinataire, Graphe g) throws LienException{
        return new Arete(source, destinataire, g);
    }
    
    
}
