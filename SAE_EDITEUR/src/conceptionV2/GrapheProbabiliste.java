/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author amine.daamouch
 */
public class GrapheProbabiliste extends Graphe{

    public GrapheProbabiliste() {
        super();
    }

    @Override
    public void ajouterNoeud(Noeud n) throws NoeudException {
         if (n instanceof NoeudGrapheProbabiliste){
            noeuds.add(n);
        }else{
            // thorw l'exception 
        }
    }

    @Override
    public Lien ajouterLien(Lien l) throws LienException {
        if(!(l instanceof ArcProbabiliste)){
            throw new LienException("impossible de créer un arcs probabiliste");
        }
        
        liens.add(l);
        NoeudGrapheProbabiliste noeudSource = (NoeudGrapheProbabiliste)l.source;
        noeudSource.successeurs.add((ArcProbabiliste) l);
        
        return l; 
       
    }
    
    /**
     * Permet d'ajouter le bouton de verification du graphe dans la pane selection
     * @param pane Pane dans laquelle sera le bouton
     * @param g    Graphe que l'on souhaite verifier
     */
    public static void ajouterBoutonVerification(Pane pane, Graphe g) {
        
        Button btnVerification = new Button("Vérifier le graphe");
        
        // Position et CSS du bouton
        btnVerification.setLayoutX(19);
        btnVerification.setLayoutY(207);
        btnVerification.setPrefSize(168, 25);
        
        // Action du bouton - Verifier que le graphe est bien probabiliste
        btnVerification.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                verifierGraphe((GrapheProbabiliste) g);
            }
        });
        pane.getChildren().add(btnVerification);
    }
    
    /**
     * Verifie que le graphe est probabiliste,
     * Pour chaque sommet, la somme des valeurs des arcs ayant comme extrémité 
     * finale ce sommet doit être égale à 1
     * @param g Graphe a verifier
     * @return true si le graphe respecte les conditions du grapghe probabiliste, 
     *         false sinon
     */
    public static boolean verifierGraphe(GrapheProbabiliste g) {
        
        boolean ttOk = true; // Tous les noeuds respectent les proprietes
        
        // Pour chaque sommet, la somme des valeurs des arcs ayant comme extrémité finale ce
        // sommet doit être égale à 1
        for (int noAVerifier = 0 ; noAVerifier < g.noeuds.size() ; noAVerifier++) {
            
            if (!verifierNoeud((NoeudGrapheProbabiliste) g.noeuds.get(noAVerifier))) {
                
                // Le noeud ne respecte pas les conditions donc il y a au moins un noeud errone
                ttOk = false;
            }
        } 
        return ttOk;
    }
    
    /**
     * Vérifie que le noeud est un noeud d'un graphe probabiliste
     * La somme des valeurs des arcs ayant comme extrémité 
     * finale ce sommet doit être égale à 1
     * Modifie la couleur de contour en rouge du noeud si erreur
     * @param n Noeud a verifier
     * @return true si le noeud respecte les conditions du noeud probabiliste, 
     *         false sinon
     */
    public static boolean verifierNoeud(NoeudGrapheProbabiliste n) {
        
        double somme; // Somme des valeurs sortant du noeud n
        
        somme = 0;
        for (int noLien = 0 ; noLien < n.successeurs.size() ; noLien++) {
            
            somme += n.successeurs.get(noLien).coefficient;
        }
        if (somme != 1) {
            n.c.setStroke(Color.RED);
            return false;
        }
        
        n.c.setStroke(Color.BLACK);
        return true;
    }
}
