/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur.graphe.propriete;

import saeEditeur.graphe.lien.Arc;
import saeEditeur.graphe.lien.Arete;
import saeEditeur.graphe.lien.Lien;
import saeEditeur.graphe.lien.ArcProbabiliste;
import saeEditeur.graphe.noeud.Noeud;
import saeEditeur.graphe.typegraphe.GrapheNonOriente;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import saeEditeur.graphe.ElementGraphe;

/**
 *
 * @author vincent.faure
 */
public class ProprieteNoeudSourceLien extends Propriete {
    
    /**
     * Construit une proprietee
     * @param n nom propriete
     * @param sdl mode de saisie
     * @param elg element du graphe
     */
    public ProprieteNoeudSourceLien(ElementGraphe elg){
        super("Source",true,elg);
    }
    
    /**
     * Fournit la liste des valeurs pour la ChoiceBox
     * @return Liste valeur si saisiDansListe = vrai, 
     *         null si saisiDansListe = false
     */
    @Override
    public String[] getListeChoix(){

        Lien l = (Lien) elementGraphe;
        String[] liste = new String[l.g.noeuds.size()];
        
        for(int i = 0; i < l.g.noeuds.size(); i++) {
            liste[i] = l.g.noeuds.get(i).nom;
        }
                
        return liste;
    }
    
    /**
     * Verifie si valeur est valide
     * @param valeur valeur de la saisie
     * @return true si valide sinon false
     */
    @Override
    public boolean validerSaisie(String valeur){
        
        Lien l = (Lien) elementGraphe; //Lien selectionner
        
        //Recuperation du noeud grace au nom
        Noeud n = getNoeud(valeur);
        
        boolean lienExiste = false;
                
        if(l instanceof Arc){
            Arc a = new Arc(n, l.destinataire, l.g);
            lienExiste = l.g.estLienValide(a);
        }
        
        if(l instanceof Arete){
            Arete a = new Arete(n, l.destinataire, l.g);
            lienExiste = l.g.estLienValide(a);
        }
        
        if(l instanceof ArcProbabiliste){
            ArcProbabiliste a = new ArcProbabiliste(n, l.destinataire, l.g);
            lienExiste = l.g.estLienValide(a);
        }
        
        //Cas pour un graphe non orienté (Boucle)
        if(l.g instanceof GrapheNonOriente && valeur.equals(l.destinataire.nom)){
            return false;
        }
        
        if(!lienExiste){
            System.out.println("lien source => " + lienExiste);
            return false;
        }
        
        return true; //Si aucune erreur
    }
    
    /**
     * Set la Valeur de o
     * @param o En fonction de l'object en parametre (TextField ou BoxChoice)
     */
    @Override
    public void setObject(Object o){
        ChoiceBox box = (ChoiceBox) o;
        Lien l = (Lien) elementGraphe;
        box.setValue(l.source.nom);
    }
    
    /**
     * Si saisie valide, applique les modification sur le Lien 
     * (Aussi sur la zone de dessin)
     * @param valeur 
     * @param o En fonction de l'object en parametre (TextField ou BoxChoice)
     * @param zoneDessin
     */
    @Override
    public void setValeur(String valeur, Object o, AnchorPane zoneDessin){
        
        ChoiceBox box = (ChoiceBox) o; //o instance de ChoiceBox
        Lien l = (Lien) elementGraphe; //Lien select
        
        if(validerSaisie(valeur)){
            
            //Recuperation du noeud grace au nom
            Noeud n = getNoeud(valeur);
            
            //Appel a la fonction de modification
            l.g.changementExtremiteLien(n, 1, zoneDessin);

        }else{
            //Si la saisie n'est pas correcte (on set o a la valeur initiale)
            box.setValue(l.source.nom);
        } 
    }

    /**
     * Recuperer un Noeud grace au nom du noeud
     * @param nom Nom du noeud
     * @return null si le noeud n'est pas trouver sinon il renvoie le noeud
     */
    private Noeud getNoeud(String nom){
        Lien l = (Lien) elementGraphe; //Lien select
        
        for (int i = 0; i < l.g.noeuds.size(); i++) { //On boucle sur les noeuds
            if (l.g.noeuds.get(i).nom.equals(nom)) { //On verifie si un noeud
                return l.g.noeuds.get(i);            //correspond au nom rentrer
            }
        }
        
        return null; // Return null si aucun noeud trouver
    }
}
