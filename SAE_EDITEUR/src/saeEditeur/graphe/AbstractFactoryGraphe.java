/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * AbstractFactoryGraphe.java     16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe;

import saeEditeur.graphe.lien.LienException;
import saeEditeur.graphe.lien.Lien;
import saeEditeur.graphe.noeud.Noeud;
import saeEditeur.graphe.noeud.NoeudException;
import saeEditeur.graphe.typegraphe.Graphe;

/**
 * Classe abstraite permettant la création d'un graphe, de noeud et de lien
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public interface AbstractFactoryGraphe {
    
    /**
     * Créer un graphe typé
     * @return Le graphe typé
     */
    public Graphe creerGraphe() ;
    
    /**
     * Créer un noeud en fonction du type de graphe
     * @param x Abscisse du noeud
     * @param y Ordonnée du noeud
     * @param g Graphe auquel il appartient
     * @return Un noeud typé
     * @throws NoeudException Si le noeud est invalide dans le graphe g
     */
    public Noeud creerNoeud(double x, double y, Graphe g) throws NoeudException ;
    
    /**
     * Créer un lien typé
     * @param source Noeud source du lien
     * @param destinataire Noeud destinataire du lien
     * @param g Graphe auquel il appartient
     * @return Un lien typé
     * @throws LienException Si le lien est invalide dans le graphe g 
     */
    public Lien creerLien(Noeud source, Noeud destinataire, Graphe g) throws LienException;
    
    
}
