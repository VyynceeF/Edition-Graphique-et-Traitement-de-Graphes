/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

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
    public Noeud creerNoeud(double x, double y) throws NoeudException{
       
        return new NoeudGrapheNonOriente(x, y) ;
    }

    @Override
    public Lien creerLien(Noeud source, Noeud destinataire) throws LienException{
        return new Arete(source, destinataire);
    }
    
    
}
