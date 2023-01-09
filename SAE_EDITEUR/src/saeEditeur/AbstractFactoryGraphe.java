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
public interface AbstractFactoryGraphe {
    
    public Graphe creerGraphe() ;
    
    public Noeud creerNoeud(double x, double y) throws NoeudException ;
    
    public Lien creerLien(Noeud source, Noeud destinataire) throws LienException;
    
    
}
