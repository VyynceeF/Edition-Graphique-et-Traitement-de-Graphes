/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

import java.util.ArrayList;
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
    public static Button ajouterBoutonVerification(Pane pane, Pane zoneDessin, GrapheProbabiliste g) {
        
        Button btnVerification = new Button("Vérifier le graphe");
        
        // Position et CSS du bouton
        btnVerification.setLayoutX(19);
        btnVerification.setLayoutY(207);
        btnVerification.setPrefSize(168, 25);
        
        // Action du bouton - Verifier que le graphe est bien probabiliste
        btnVerification.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (g.verifierGraphe()) {   // Verifie si le graphe est probabiliste
                                            // Et change les couleurs sur les noeuds errones
                    
                    g.regroupementEtat(g.regroupementClasse(zoneDessin));
                }
            }
        });
        pane.getChildren().add(btnVerification);
        return btnVerification;
    }
    
    /**
     * Verifie que le graphe est probabiliste,
     * Pour chaque sommet, la somme des valeurs des arcs ayant comme extrémité 
     * finale ce sommet doit être égale à 1
     * @param g Graphe a verifier
     * @return true si le graphe respecte les conditions du grapghe probabiliste, 
     *         false sinon
     */
    public boolean verifierGraphe() {
        
        boolean ttOk = true; // Tous les noeuds respectent les proprietes
        
        // Pour chaque sommet, la somme des valeurs des arcs ayant comme extrémité finale ce
        // sommet doit être égale à 1
        for (int noAVerifier = 0 ; noAVerifier < noeuds.size() ; noAVerifier++) {
            
            if (!verifierNoeud((NoeudGrapheProbabiliste) noeuds.get(noAVerifier))) {
                
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
    public boolean verifierNoeud(NoeudGrapheProbabiliste n) {
        
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
    
    /**
     * Predicat permettant de savoir s'il existe un chemin entre le noeud source
     * et le noeud destinataire dans le graphe probabiliste
     * @param listeVisitees Liste des noeuds deja visites
     * @param source        Noeud depart
     * @param destinataire  Noeud arrive
     * @return true s'il existe un chemin entre source et destinataire, false sinon
     */
    public boolean estChemin(ArrayList<NoeudGrapheProbabiliste> listeVisitees,NoeudGrapheProbabiliste source , NoeudGrapheProbabiliste destinataire){   
     
        for (int i = 0; i < source.successeurs.size() ; i++) {
            if(!listeVisitees.contains(source.successeurs.get(i).destinataire) ){
                /* cas où le chemin est minimun de poids 1 */
                if(source.successeurs.get(i).destinataire == destinataire){
                    return true;   
                }
                listeVisitees.add((NoeudGrapheProbabiliste) source.successeurs.get(i).destinataire);
                /* cas d'une boucle */
                if(source.successeurs.get(i).destinataire == source && source.successeurs.size() == 1 ){
                    return false;
                }
                /* Cas où le chemin est minimun de poids 2 */
                if(estChemin(listeVisitees, (NoeudGrapheProbabiliste) source.successeurs.get(i).destinataire, destinataire)){
                    return true;
                }
  
            }
        }
        /* Arrivé , aucun successeur trouvé pour aller du noeud source au destinataire*/ 
        return false;
    }
    
    /**
     * Regroupement des sommets du graphe par classe
     * C'est a dire regrouper les états d’un graphe probabiliste en classes 
     * d’équivalence : une classe d’équivalence regroupe tous les états qui 
     * communiquent entre eux.
     * Affiche le numero de la classe dans le noeud
     * @return Une liste contenant les differentes classes
     */
    public ArrayList<ArrayList<Noeud>> regroupementClasse(Pane zoneDessin) {
        
        int noClasse = 0 ; // Numero de la classe actuelle
        
        // Liste des noeuds pas encore dans une classe
        ArrayList<Noeud> ensembleNoeud = (ArrayList<Noeud>) noeuds.clone();
        
        ArrayList<ArrayList<Noeud>> classes = new ArrayList<>();
        ArrayList<Noeud> classe;
        
        NoeudGrapheProbabiliste noeudActuel,
                                noeudClasse; 
        
        while (ensembleNoeud.size() != 0) {
            
            noeudActuel = (NoeudGrapheProbabiliste) ensembleNoeud.get(0);
            classe = new ArrayList<>();
            // Ajout dans la classe
            classe.add(ensembleNoeud.get(0));
            // Comme dans une classe alors suppression dans l'ensemble des noeuds
            ensembleNoeud.remove(ensembleNoeud.get(0));
            // Affichage du nom de sa classe dans le cercle
            noeudActuel.ajouterNomClasse(zoneDessin, "C" + noClasse);
            for (int noATester = 0 ; noATester < ensembleNoeud.size() ; noATester++) {
                
                if (estTransitifSysmetrique((NoeudGrapheProbabiliste) noeudActuel, 
                                            (NoeudGrapheProbabiliste) ensembleNoeud.get(noATester))) {
                    
                    noeudClasse = (NoeudGrapheProbabiliste) ensembleNoeud.get(noATester);
                    classe.add(noeudClasse);
                    ensembleNoeud.remove(noeudClasse);
                    noeudClasse.ajouterNomClasse(zoneDessin, "C" + noClasse);
                    noATester--;
                }
            }
            // Ajout de la classe dans la liste de classes
            classes.add(classe);
            noClasse++; // Changement de classe
        }
        
        return classes;
    }
    
    /**
     * Verifie qu'il y est un chemin entre n1 et n2 
     * et verifie qu'il y est un chemin entre n2 et n1
     * @param n1 Noeud a tester
     * @param n2 Noeud a tester
     * @return true s'il y a un chemin entre n1 et n2, et qu'il y a un chemin entre
     *         n2 et n1, false sinon
     */
    public boolean estTransitifSysmetrique(NoeudGrapheProbabiliste n1, NoeudGrapheProbabiliste n2) {
        
        return estChemin(new ArrayList<NoeudGrapheProbabiliste>(), n1, n2)
               && estChemin(new ArrayList<NoeudGrapheProbabiliste>(), n2, n1);
    }
    
    public void regroupementEtat(ArrayList<ArrayList<Noeud>> listeClasse) {
        
        ArrayList<ArrayList<Noeud>> listeClasseATester;
        
        boolean estTransitoire;
        
        for (int noClasse = 0 ; noClasse < listeClasse.size() ; noClasse++) {
            
            estTransitoire = false;
            
            listeClasseATester = (ArrayList<ArrayList<Noeud>>) listeClasse.clone();
            listeClasseATester.remove(noClasse);
            // Transitoire
            for (int noClasseATester = 0 ; noClasseATester < listeClasseATester.size() && !estTransitoire ; noClasseATester++) {
                
                if (estChemin(new ArrayList<NoeudGrapheProbabiliste>(), 
                              (NoeudGrapheProbabiliste) listeClasse.get(noClasse).get(0),       // Classe que l'on verifie
                              (NoeudGrapheProbabiliste) listeClasseATester.get(noClasseATester).get(0) // Classe de comparaison
                   )) {
                    
                    // Changement couleur noeud en couleur etat transitoire
                    changementCouleurTransitoire(listeClasse.get(noClasse));
                    estTransitoire = true;
                }
            }
            
            // Ergodique
            if (!estTransitoire) {
                changementCouleurErgodique(listeClasse.get(noClasse));
            }
        }
    }
    
    /**
     * Changement de la couleur de la bordure de tous les noeuds dans la classe
     * @param classe Ensemble des noeuds a modifier
     */
    public void changementCouleurTransitoire(ArrayList<Noeud> classe) {
        
        NoeudGrapheProbabiliste noeud;
        
        for (int aChanger = 0 ; aChanger < classe.size() ; aChanger++) {
            
            noeud = (NoeudGrapheProbabiliste) classe.get(aChanger);
            noeud.changementCouleurTransitoire();
        }
    }
    
    /**
     * Changement de la couleur de la bordure de tous les noeuds dans la classe
     * @param classe Ensemble des noeuds a modifier
     */
    public void changementCouleurErgodique(ArrayList<Noeud> classe) {
        
        NoeudGrapheProbabiliste noeud;
        
        for (int aChanger = 0 ; aChanger < classe.size() ; aChanger++) {
            
            noeud = (NoeudGrapheProbabiliste) classe.get(aChanger);
            noeud.changementCouleurErgodique();
        }
    }
}
