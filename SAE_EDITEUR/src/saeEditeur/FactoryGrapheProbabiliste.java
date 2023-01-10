/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;

/**
 *
 * @author amine.daamouch
 */
public class FactoryGrapheProbabiliste implements AbstractFactoryGraphe{

    
    public FactoryGrapheProbabiliste(){
        
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
