/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.BreakNode;

/**
 *
 * @author amine.daamouch
 */
public class GrapheProbabiliste extends Graphe {
    
    /** Ensemble des noeuds finaux du graphe */
    ArrayList<Noeud> noeudsFinales;
    
    /** Ensemble des noeuds transitoires du graphe */
    ArrayList<Noeud> noeudsTransitoires;
    
    /** Ordre des noeuds du graphe */
    ArrayList<Noeud> noeudsOrdones;
    
    public GrapheProbabiliste() {
        super();
        noeudsFinales = new ArrayList<>();
        noeudsTransitoires = new ArrayList<>();
        noeudsOrdones = new ArrayList<>();
        
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
                    
                    g.regroupementEtat(g.regroupementClasse());
                }
            }
        });
        pane.getChildren().add(btnVerification);
        return btnVerification;
    }
    
    public static Menu ajouterMenuNavBar(MenuBar menuBar, GrapheProbabiliste g, Pane zoneDessin) {
        
        Menu editMenu = new Menu("Edition");
        
        /* Matrice de transition */
        MenuItem itemMatriceTransition = new MenuItem("Matrice de transition");
        itemMatriceTransition.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    
                    // Création de l'objet de la popup
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    // Définition du titre de la popup
                    alert.setTitle("Matrice de transition");

                    // Définition du contenu de la popup
                    alert.setContentText("Matrice de transition\n\n"
                                         + g.matriceTransitoireCanoniqueToString());

                    // Affichage de la popup
                    alert.show();
                }
            }
        );
        
        MenuItem itemRegroupementClasse = new MenuItem("Regroupement par classe");
        itemRegroupementClasse.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    
                    System.out.println("Regroupement");
                    g.regroupementClasseEtAffiche(zoneDessin);
                    Stage popUp = new Stage();
                    popUp.initModality(Modality.APPLICATION_MODAL);
                    StackPane pane = new StackPane();
                    pane.getChildren().add(new Button("Fermer"));
                    popUp.setScene(new Scene(pane, 200, 200));
                    popUp.showAndWait();
                }
            }
        );
        
        MenuItem itemClassificationSommets = new MenuItem("Classification des sommets");
        itemClassificationSommets.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    
                    System.out.println("Classification");
                    g.regroupementEtatEtChangementCouleur(g.regroupementClasse());
                }
            }
        );
        
        Menu itemInterpretation = new Menu("Interprétation");
        //Sous menu de interpretation
        MenuItem itemProbaSommetASommet = new MenuItem("Probabilité de passer d’un sommet à un autre");
        itemProbaSommetASommet.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    
                    Stage popUp = new Stage();
                    popUp.initModality(Modality.APPLICATION_MODAL);
                    StackPane pane = new StackPane();
                    GridPane gridPane = new GridPane();
                    
                    // Resultat
                    Text resultat = new Text();
                    
                    // Noeud source
                    Text textSource = new Text("Noeud source - ");
                    ComboBox comboBoxSource = new ComboBox();
                    for (int i = 0 ; i < g.noeuds.size() ; i++) {
                        comboBoxSource.getItems().add(g.noeuds.get(i));
                    }
                    
                    // Noeud destinataire
                    Text textDestinataire = new Text("Noeud destinataire - ");
                    ComboBox comboBoxDestinataire = new ComboBox();
                    for (int i = 0 ; i < g.noeuds.size() ; i++) {
                        comboBoxDestinataire.getItems().add(g.noeuds.get(i));
                    }
                    
                    // Nombre de transition
                    Text textTransition = new Text("Nombre de transition - ");
                    TextField inputTransition = new TextField("1");
                    
                    // Button calcul
                    Button btCalcul = new Button("Calculer");
                    btCalcul.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            
                            Noeud nSource, nDestinataire;
                            
                            int nbTransition = 0;
                            
                            System.out.println("Pass");
                            // Verification validiter nombre de transition
                            try {
                                
                                nbTransition = Integer.parseInt(inputTransition.getText());
                                inputTransition.setStyle("-fx-border-color: black;");
                                if (nbTransition < 1) {
                                    inputTransition.setStyle("-fx-border-color: red;");
                                    nbTransition = 0;
                                }
                            } catch (NumberFormatException erreur) {
                                inputTransition.setStyle("-fx-border-color: red;");
                                nbTransition = 0;
                            }
                            
                            // Verification noeud source
                            if (nbTransition != 0) {
                                nSource = (NoeudGrapheProbabiliste) comboBoxSource.getValue();
                                nDestinataire = (NoeudGrapheProbabiliste) comboBoxDestinataire.getValue();
                                
                                resultat.setText("La probabilité d'atteindre le noeud " + nDestinataire.nom
                                                 + " depuis le noeud " + nSource.nom + " en " + nbTransition + " transition(s) est de "
                                                 + g.probaEntreNoeudEnNTransition(nSource, nDestinataire, nbTransition));
                            }
                        }
                    });
                    
                    // Ajout dans la grid pane
                    gridPane.add(textSource, 0, 0);
                    gridPane.add(comboBoxSource, 1, 0);
                    gridPane.add(textDestinataire, 0, 1);
                    gridPane.add(comboBoxDestinataire, 1, 1);
                    gridPane.add(textTransition, 0, 2);
                    gridPane.add(inputTransition, 1, 2);
                    gridPane.add(btCalcul, 1, 3);
                    gridPane.add(resultat, 0, 4);
                    gridPane.setColumnSpan(resultat,2);
                    gridPane.setAlignment(Pos.CENTER);
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);
                    pane.getChildren().add(gridPane);
                    popUp.setScene(new Scene(pane, 600, 400));
                    popUp.showAndWait();
                }
            }
        );
        
        MenuItem itemProbaTransition = new MenuItem("Loi de probabilité atteinte après un nombre de transition(s) donné");
        itemInterpretation.getItems().addAll(itemProbaSommetASommet, itemProbaTransition);
        
        // Ajout des menus de edition
        editMenu.getItems().addAll(itemMatriceTransition, 
                                   itemRegroupementClasse,
                                   itemClassificationSommets,
                                   itemInterpretation);
        
        // Ajout dans la navbar
        menuBar.getMenus().add(editMenu);
        return editMenu;
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
        if (Math.abs(somme - 1) >= 10e-10) {
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
    public ArrayList<ArrayList<Noeud>> regroupementClasseEtAffiche(Pane zoneDessin) {
        
        noeudsFinales.clear();
        noeudsTransitoires.clear();
        noeudsOrdones.clear();
        
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
     * Regroupement des sommets du graphe par classe
     * C'est a dire regrouper les états d’un graphe probabiliste en classes 
     * d’équivalence : une classe d’équivalence regroupe tous les états qui 
     * communiquent entre eux.
     * @return Une liste contenant les differentes classes
     */
    public ArrayList<ArrayList<Noeud>> regroupementClasse() {
        
        noeudsFinales.clear();
        noeudsTransitoires.clear();
        noeudsOrdones.clear();
        
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
            for (int noATester = 0 ; noATester < ensembleNoeud.size() ; noATester++) {
                
                if (estTransitifSysmetrique((NoeudGrapheProbabiliste) noeudActuel, 
                                            (NoeudGrapheProbabiliste) ensembleNoeud.get(noATester))) {
                    
                    noeudClasse = (NoeudGrapheProbabiliste) ensembleNoeud.get(noATester);
                    classe.add(noeudClasse);
                    ensembleNoeud.remove(noeudClasse);
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
    
    /**
     * Regroupe les etats ergodiques et les etats transitoires
     * Et change la couleur des noeuds
     * @param listeClasse 
     */
    public void regroupementEtatEtChangementCouleur(ArrayList<ArrayList<Noeud>> listeClasse) {
        
        noeudsFinales.clear();
        noeudsTransitoires.clear();
        noeudsOrdones.clear();
        
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
                    // Ajout de tous les etats transitoire dans la liste 
                    ajouterListeTransitoire(listeClasse.get(noClasse));
                    estTransitoire = true;
                }
            }
            
            // Ergodique
            if (!estTransitoire) {
                changementCouleurErgodique(listeClasse.get(noClasse));
                ajouterListeFinale(listeClasse.get(noClasse));
            }
        }
    }
    
    /**
     * Regroupe les etats ergodiques et les etats transitoires
     * @param listeClasse 
     */
    public void regroupementEtat(ArrayList<ArrayList<Noeud>> listeClasse) {
        
        noeudsOrdones.clear();
        
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
                    
                    // Ajout de tous les etats transitoire dans la liste 
                    ajouterListeTransitoire(listeClasse.get(noClasse));
                    estTransitoire = true;
                }
            }
            
            // Ergodique
            if (!estTransitoire) {
                ajouterListeFinale(listeClasse.get(noClasse));
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

    private void ajouterListeTransitoire(ArrayList<Noeud> classe) {
        
        for (int aAjouter = 0 ; aAjouter < classe.size() ; aAjouter++) {
            noeudsTransitoires.add(classe.get(aAjouter));
        }
    }

    private void ajouterListeFinale(ArrayList<Noeud> classe) {
        
        for (int aAjouter = 0 ; aAjouter < classe.size() ; aAjouter++) {
            noeudsFinales.add(classe.get(aAjouter));
        }
    }
    
    /**
     * Creer la matice de transition du graphe sous la forme canonique
     */
    public double[][] matriceTransitoireCanonique() {
        
        double[][] m = new double[noeuds.size()][noeuds.size()];
        
        // Ajout de noeuds ergodiques
        for (int noErgo = 0 ; noErgo < noeudsFinales.size() ; noErgo++) {
            noeudsOrdones.add(noeudsFinales.get(noErgo));
        }
        // Ajout de noeuds transitoire
        for (int noErgo = 0 ; noErgo < noeudsTransitoires.size() ; noErgo++) {
            noeudsOrdones.add(noeudsTransitoires.get(noErgo));
        }
        // Ajout des valeurs dans la matrice
        for (int x = 0 ; x < noeudsOrdones.size() ; x++) {
            
            for (int y = 0 ; y < noeudsOrdones.size() ; y++) {
                
                m[x][y] = valeurEntreDeuxNoeud((NoeudGrapheProbabiliste) noeudsOrdones.get(x),
                                               (NoeudGrapheProbabiliste) noeudsOrdones.get(y));
            }
        }
        
        return m ;
    }
    
    /**
     * Renvoie la matice de transition du graphe sous la forme canonique en string
     * @return la matice de transition
     */
    public String matriceTransitoireCanoniqueToString() {
        
        regroupementEtat(regroupementClasse());
        
        String m = "   ";
        double[][] matrice = matriceTransitoireCanonique();
        
        // Nom Noeuds
        for (int i = 0 ; i < noeudsOrdones.size() ; i++) {
            
            m += " " + noeudsOrdones.get(i);
        }
        m += "\n";
        for (int i = 0 ; i < matrice.length ; i++) {
            m += noeudsOrdones.get(i) + "[";
            for (int y = 0 ; y < matrice[i].length ; y++) {
                
                m += matrice[i][y];
                if (y != matrice[i].length - 1) {
                    m += " , ";
                }
            }
            m += "]\n";
        }
        return m;
    }
    
    public double valeurEntreDeuxNoeud(NoeudGrapheProbabiliste n1, NoeudGrapheProbabiliste n2) {
        
        for (int i = 0; i < n1.successeurs.size() ; i++) {
            
            if (n1.successeurs.get(i).destinataire == n2) {
                
                return n1.successeurs.get(i).coefficient;
            } 
        }
        return 0.0;
    }
    
    /**
     * Multiplie la matrice carree m par elle meme
     * @param m Matrice a multiplier
     * @return La multiplication de la matrice m par elle meme
     */
    public static double[][] multiplicationMatrice(double[][] m1, double[][] m2){
        
        // Créer une matrice pour stocker la multiplication
        double resultat[][] = new double[m1.length][m1.length];  
        
        // Multiplication
        for (int i=0 ; i < m1.length ; i++){
            for (int j=0 ; j < m1.length ; j++){ 
                resultat[i][j] = 0;    
                for (int k=0 ; k < m1.length ; k++) { 
                    resultat[i][j] += m1[i][k] * m2[k][j];    
                }
            }
        }  
        return resultat;
    }
    
    /**
     * Calcul la matrice m elevee a l'exposant exp
     * @param m   Matrice a elevee
     * @param exp Exposant
     * @return La matrice m elevee a l'exposant exp
     */
    public static double[][] exposantMatrice(double[][] m, int exp) {
        
        double[][] resultat = m ;
        
        while (exp > 1) {
            
            resultat = multiplicationMatrice(resultat, m);
            exp--;
        }
        
//        Affichage dans la console
//        for (int i=0 ; i < m.length ; i++){
//            
//            for (int j=0 ; j < m.length ; j++){ 
//                System.out.print(" " + resultat[i][j]);
//            }
//            System.out.println("");
//        }
        return resultat ;
    }
    
    public double probaEntreNoeudEnNTransition(Noeud source, Noeud destinataire, int nbTransition) {
        
        regroupementEtat(regroupementClasse());  
        
        int ligne   = 0 ;   // Ligne du noeud source dans la matrice de transition
        int colonne = 0 ;   // Ligne du noeud destinataire dans la matrice de transition
        
        // Matrice de transition du graphe
        double[][] matrice = matriceTransitoireCanonique();
        
        matrice = exposantMatrice(matrice, nbTransition);
        
        // Recherche de source dans la matrice
        for (int i = 0 ; i < noeudsOrdones.size() ; i++) {
            if (noeudsOrdones.get(i) == source) {
                ligne = i;
            }
        }
        
        // Recherche de destinataire dans la matrice
        for (int i = 0 ; i < noeudsOrdones.size() ; i++) {
            if (noeudsOrdones.get(i) == destinataire) {
                colonne = i;
            }
        }
        
        return matrice[ligne][colonne];
    }

    public ArrayList<Noeud> getNoeudsFinales() {
        return noeudsFinales;
    }

    public void setNoeudsFinales(ArrayList<Noeud> noeudsFinales) {
        this.noeudsFinales = noeudsFinales;
    }

    public ArrayList<Noeud> getNoeudsTransitoires() {
        return noeudsTransitoires;
    }

    public void setNoeudsTransitoires(ArrayList<Noeud> noeudsTransitoires) {
        this.noeudsTransitoires = noeudsTransitoires;
    }

    public ArrayList<Noeud> getNoeudsOrdones() {
        return noeudsOrdones;
    }

    public void setNoeudsOrdones(ArrayList<Noeud> noeudsOrdones) {
        this.noeudsOrdones = noeudsOrdones;
    }

    public ArrayList<Noeud> getNoeuds() {
        return noeuds;
    }

    public void setNoeuds(ArrayList<Noeud> noeuds) {
        this.noeuds = noeuds;
    }

    public ArrayList<Lien> getLiens() {
        return liens;
    }

    public void setLiens(ArrayList<Lien> liens) {
        this.liens = liens;
    }
    
    
}
