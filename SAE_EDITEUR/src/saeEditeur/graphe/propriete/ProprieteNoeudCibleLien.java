/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * ProprieteNoeudCibleLien.java   16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe.propriete;

import java.util.ArrayList;
import saeEditeur.graphe.lien.Arc;
import saeEditeur.graphe.lien.Arete;
import saeEditeur.graphe.lien.Lien;
import saeEditeur.graphe.lien.ArcProbabiliste;
import saeEditeur.graphe.noeud.Noeud;
import saeEditeur.graphe.typegraphe.GrapheNonOriente;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import saeEditeur.graphe.ElementGraphe;
import saeEditeur.graphe.lien.ArcPondere;

/**
 * Représentation des propriétés d'un Noeud
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public class ProprieteNoeudCibleLien extends Propriete {
    
    /**
     * Construit une propriete
     * @param elg element du graphe
     */
    public ProprieteNoeudCibleLien(ElementGraphe elg){
        super("Cible",true,elg);
    }
    
    /**
     * Fournit la liste des valeurs pour la ChoiceBox
     * @return Liste valeur si saisiDansListe = vrai, 
     *         null si saisiDansListe = false
     */
    @Override
    public String[] getListeChoix(){
        
        Lien l = (Lien) elementGraphe; //Lien select
        String[] liste = new String[l.g.noeuds.size()];//Creer la liste a return
        
        for(int i = 0; i < l.g.noeuds.size(); i++) {//Boucle sur les noeuds
            liste[i] = l.g.noeuds.get(i).nom;//Ajoute dans la liste tous les nom
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
            Arc a = new Arc(l.source, n, l.g);
            lienExiste = l.g.estLienValide(a);
        }
        
        if(l instanceof Arete){
            Arete a = new Arete(l.source, n, l.g);
            lienExiste = l.g.estLienValide(a);
        }
        
        if(l instanceof ArcProbabiliste){
            ArcProbabiliste a = new ArcProbabiliste(l.source, n, l.g);
            lienExiste = l.g.estLienValide(a);
        }
        
        if(l instanceof ArcPondere){
            ArcPondere a = new ArcPondere(l.source, n, l.g);
            lienExiste = l.g.estLienValide(a);
        }
        
        //Cas pour un graphe non orienté (Boucle)
        if(l.g instanceof GrapheNonOriente && valeur.equals(l.source.nom)){
            return false;
        }
        
        if(!lienExiste){
            return false;
        }
        
        return true; //Si aucune erreur
    }
    
    @Override
    public void setObject(Object o){
        ChoiceBox box = (ChoiceBox) o;
        Lien l = (Lien) elementGraphe;
        box.setValue(l.destinataire.nom);
    }
    
    @Override
    public void setValeur(String valeur, Object o, AnchorPane zoneDessin){
        
        ChoiceBox box = (ChoiceBox) o; //o est instance de ChoiceBox
        Lien l = (Lien) elementGraphe; //Lien select
        
        if(validerSaisie(valeur)){
            
            //Recuperation du noeud grace au nom
            Noeud n = getNoeud(valeur);
            
            // Ajout dans la pile d'action
            ArrayList<Object> ancienPosition = new ArrayList<>();
            ancienPosition.add(l.g.lienSelectionne);
            ancienPosition.add(l.destinataire);
            ancienPosition.add(2);
            l.g.ajouterPileUndo(ancienPosition);
            //Appel a la fonction de modification
            l.g.changementExtremiteLien(l.g.lienSelectionne, n, 2, zoneDessin);
            
        }else{
            //Si la saisie n'est pas correcte (on set o a la valeur initiale)
            box.setValue(l.destinataire.nom);                                             
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
