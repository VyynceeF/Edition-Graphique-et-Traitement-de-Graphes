/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * GrapheProbabiliste.java        16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe.typegraphe;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import saeEditeur.graphe.lien.ArcProbabiliste;
import saeEditeur.graphe.lien.Lien;
import saeEditeur.graphe.lien.LienException;
import saeEditeur.graphe.noeud.Noeud;
import saeEditeur.graphe.noeud.NoeudException;
import saeEditeur.graphe.noeud.NoeudGrapheProbabiliste;

/**
 * Représentation d'un graphe probabiliste et ses fonctionnalités
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
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
    public String toString() {
        
        return "GrapheProbabiliste";
    }
    
    @Override
    public String getFactory() {
        
        return "Graphe probabiliste";
    }

    @Override
    public void ajouterNoeud(Noeud n) throws NoeudException {
         if (n instanceof NoeudGrapheProbabiliste){
            n.setNom(nomNoeud(n.nom,0));
            noeuds.add(n);
        }else{
            throw new NoeudException("Impossible de créer un Noeud");
        }
    }

    @Override
    public Lien ajouterLien(Lien l) throws LienException {
        if(!(l instanceof ArcProbabiliste)){
            throw new LienException("Impossible de créer un arcs probabiliste");
        }
        
        if (!estLienValide(l)) {
            throw new LienException("Impossible de créer un lien sur un lien");
        }
        
        NoeudGrapheProbabiliste tmp = (NoeudGrapheProbabiliste) l.source;
        tmp.successeurs.add(l);
        l.destinataire.predecesseurs.add(l);
        liens.add(l);
        
        return l; 
    }
    
    @Override
    public boolean estLienValide(Lien l)  {
        for (Lien aTester : liens) {
            if ((l.destinataire == aTester.destinataire
                   && l.source == aTester.source)) {      
                return false;
            }
                     
        } 
        return true; 
    }
    
    /**
     * Permet d'ajouter le bouton de verification du graphe dans la pane selection
     * @param pane Pane dans laquelle sera le bouton
     * @param zoneDessin Zone de dessin
     * @param g    Graphe que l'on souhaite verifier
     * @return Le bouton de vérification
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
    
    /**
     * Ajout d'une pane pour afficher la légende des noeuds
     * @param menu Menu de l'application
     * @return La pane contenant la légende
     */
    public static Pane ajouterLegende(Pane menu) {
        
        Pane legende = new Pane();
        
        Text textTransitoire = new Text("Etat transitoire");
        textTransitoire.relocate(28, 29);
        Circle circleTransitoire = new Circle(15, Color.rgb(157, 255, 117));
        circleTransitoire.relocate(162, 23);
        Text textErgodique = new Text("Etat ergodique");
        textErgodique.relocate(28, 66);
        Circle circleErgodique = new Circle(15, Color.rgb(117, 208, 255));
        circleErgodique.relocate(162, 60);
        Text textAbsorbant = new Text("Etat absorbant");
        textAbsorbant.relocate(28, 103);
        Circle circleAbsorbant = new Circle(15, Color.rgb(141, 117, 255));
        circleAbsorbant.relocate(162, 97);
        
        legende.getChildren().addAll(textAbsorbant, textErgodique, textTransitoire,
                                     circleAbsorbant, circleErgodique, circleTransitoire);
        legende.setLayoutX(0);
        legende.setLayoutY(450);
        menu.getChildren().add(legende);
        
        return legende;
    }
    
    /**
     * Ajout d'un menu édition dans la navbar pour le traitement de graphe
     * porbabiliste et associations de ces fonctionnalités à ce menu
     * @param menuBar Navbar
     * @param g Graphe probabiliste à traiter
     * @param zoneDessin Zone de dessin
     * @return Le menu de traitement
     */
    public static Menu ajouterMenuNavBar(MenuBar menuBar, GrapheProbabiliste g, Pane zoneDessin) {
        
        Menu editMenu = new Menu("Edition");
        
        /* Matrice de transition */
        MenuItem itemMatriceTransition = new MenuItem("Matrice de transition");
        itemMatriceTransition.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    
                    if (g.verifierGraphe()) {   // Verifie si le graphe est probabiliste
                                                // Et change les couleurs sur les noeuds errones
                        Stage popUp = new Stage();
                        popUp.getIcons().add(new Image("/saeEditeur/logo.png"));
                        popUp.initModality(Modality.APPLICATION_MODAL);
                        popUp.setTitle("Matrice de transition");
                        StackPane pane = new StackPane();
                        
                        GridPane gridPane = g.afficheMatrice();
                        gridPane.setAlignment(Pos.CENTER);
                        
                        pane.getChildren().add(gridPane);
                        StackPane.setAlignment(gridPane, Pos.CENTER);
                        popUp.setScene(new Scene(pane, 600, 400));
                        popUp.showAndWait();
                    }
                }
            }
        );
        
        MenuItem itemRegroupementClasse = new MenuItem("Regroupement par classe");
        itemRegroupementClasse.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (g.verifierGraphe()) {   // Verifie si le graphe est probabiliste
                                            // Et change les couleurs sur les noeuds errones
                        g.regroupementClasseEtAffiche(zoneDessin);
                    }
                }
            }
        );
        
        MenuItem itemClassificationSommets = new MenuItem("Classification des sommets");
        itemClassificationSommets.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (g.verifierGraphe()) {   // Verifie si le graphe est probabiliste
                                            // Et change les couleurs sur les noeuds errones
                        g.regroupementEtatEtChangementCouleur(g.regroupementClasse());
                        
                    }
                }
            }
        );
        
        Menu itemInterpretation = new Menu("Interprétation");
        //Sous menu de interpretation
        MenuItem itemProbaSommetASommet = new MenuItem("Probabilité de passer d’un sommet à un autre");
        itemProbaSommetASommet.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (g.verifierGraphe()) {   // Verifie si le graphe est probabiliste
                                            // Et change les couleurs sur les noeuds errones
                        Stage popUp = new Stage();
                        popUp.getIcons().add(new Image("/saeEditeur/logo.png"));
                        popUp.setTitle("Probabilité de passer d’un sommet à un autre");
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
            }
        );
        
        MenuItem itemProbaTransition = new MenuItem("Loi de probabilité atteinte après un nombre de transition(s) donné");
        itemProbaTransition.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (g.verifierGraphe()) {   // Verifie si le graphe est probabiliste
                                            // Et change les couleurs sur les noeuds errones
                    g.regroupementEtat(g.regroupementClasse());
                    g.matriceTransitoireCanonique();

                    Stage popUp = new Stage();
                    popUp.getIcons().add(new Image("/saeEditeur/logo.png"));
                    popUp.initModality(Modality.APPLICATION_MODAL);
                    popUp.setTitle("Loi de probabilité atteinte après un nombre de transition(s) donné");
                    StackPane pane = new StackPane();
                    GridPane gridPane = new GridPane();

                    // Resultat
                    Text resultat = new Text();

                    //Recuperation du nombre de noeuds du graphe
                    int nbNoeuds = g.noeudsOrdones.size();

                    //Tableau contenant tous les textfield pour les noeuds
                    TextField[] tabField = new TextField[nbNoeuds];
                    Text textNoeuds = new Text("Entrer une probabilité pour chaque Noeud -");
                    for (int i = 0 ; i <  nbNoeuds ; i++) {
                        Text noeuds = new Text();
                        noeuds.setText(g.noeudsOrdones.get(i).toString());
                        gridPane.add(noeuds,1,i);
                        noeuds.setId("noeuds" + i);
                        TextField Field = new TextField("0");
                        gridPane.add(Field,2,i);
                        tabField[i] = Field; // Ajout textField du noeuds i dans le tableau
                        Field.setId("Field" + i);
                    }

                    // Nombre de transition
                    Text textTransition = new Text("Nombre de transition(s) - ");
                    TextField inputTransition = new TextField("1");

                    // Button calcul
                    Button btCalcul = new Button("Calculer");
                    btCalcul.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {

                            //Recuperation du nombre de transition entrer
                            int nbTransition = 0;

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

                            //recuperation des proba pour chaque noeud
                            boolean tabErr = true; //True si pas d'erreur de probabilité des noeuds sinon false

                            double sommeProba = 0.0; //Somme des proba du vecteur initial

                            double[][] vecteurInitial = new double[1][nbNoeuds];
                            for(int i = 0; i < tabField.length; i++){
                                //Verifie les probabilitées et ajoute au tableau les valeurs
                                try{
                                    vecteurInitial[0][i] = Double.parseDouble(tabField[i].getText());
                                    tabField[i].setStyle("-fx-border-color: black;");
                                    if(vecteurInitial[0][i] < 0 || vecteurInitial[0][i] > 1){
                                        tabField[i].setStyle("-fx-border-color: red;");
                                        tabErr = false;
                                    }
                                    sommeProba += vecteurInitial[0][i];
                                } catch (NumberFormatException erreur) {
                                   tabField[i].setStyle("-fx-border-color: red;");
                                }
                            }

                            //Verifie que la somme des proba du vecteur initial est égal à 1.0
                            if(Math.abs(sommeProba - 1) >= 10e-10){
                                tabErr = false;
                                resultat.setText("Erreur ! La somme des probabilités du vecteur initial n'est pas égale à 1.0");
                                resultat.setFill(Color.RED);
                            }

                            if(nbTransition != 0 && tabErr){
                                double[][] tabVecteur = g.loiDeProbabiliteeEnNTransition(vecteurInitial,nbTransition);
                                String afficher = "";
                                double number;
                                //Mise en forme pour l'affichage
                                for(int i = 0; i < g.noeudsOrdones.size(); i++){ 
                                    number = Math.round(tabVecteur[0][i] * 1000.0) / 1000.0; //Arrondir le double
                                    afficher += g.noeudsOrdones.get(i).nom + " -> " + number + "\n";
                                }

                                //Affichage
                                resultat.setText("Le vecteur de probabilité qui représente les probabilités d'etre sur chaque sommet après " + nbTransition + " transitions :\n\n" 
                                                + afficher);
                                resultat.setFill(Color.BLACK);
                            }
                        }
                    });

                    //Ajout dans le grid pane
                    gridPane.add(textNoeuds,0,0);
                    gridPane.add(textTransition,1,nbNoeuds);
                    gridPane.add(inputTransition,2,nbNoeuds);
                    gridPane.add(btCalcul, 2, nbNoeuds + 1);
                    gridPane.setColumnSpan(btCalcul,3);
                    gridPane.add(resultat, 0, nbNoeuds + 2);
                    gridPane.setColumnSpan(resultat,3);
                    gridPane.setAlignment(Pos.CENTER);
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);
                    pane.getChildren().add(gridPane);
                    popUp.setScene(new Scene(pane, 800, 600));
                    popUp.showAndWait();
                }
            }
        });
        
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
        System.out.println("successeurs " + n.successeurs.size());
        for (int noLien = 0 ; noLien < n.successeurs.size() ; noLien++) {
            
            ArcProbabiliste tmp = (ArcProbabiliste) n.successeurs.get(noLien);
            
            // Si un lien de ce noeud est inférieur ou égal à 0, 
            // Les conditions sont fausses.
            if (tmp.coefficient <= 0.0) {
                System.out.println(tmp.coefficient + " => true");
                n.c.setStroke(Color.RED);
                return false;
            }
            
            somme += tmp.coefficient;
            System.out.println("somme = " + somme);
        }
        System.out.println("somme Totale = " + somme);
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
            
            System.out.println("EnsembleNoeud pas vide");
            
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
        
        System.out.println(classes);
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
                if (listeClasse.get(noClasse).size() == 1) {
                    changementCouleurErgodiqueAbsorbant(listeClasse.get(noClasse));
                } else {
                    changementCouleurErgodique(listeClasse.get(noClasse));
                }
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
     * Changement de la couleur de fond de tous les noeuds dans la classe
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
     * Changement de la couleur de fond de tous les noeuds dans la classe
     * @param classe Ensemble des noeuds a modifier
     */
    public void changementCouleurErgodique(ArrayList<Noeud> classe) {
        
        NoeudGrapheProbabiliste noeud;
        
        for (int aChanger = 0 ; aChanger < classe.size() ; aChanger++) {
            
            noeud = (NoeudGrapheProbabiliste) classe.get(aChanger);
            noeud.changementCouleurErgodique();
        }
    }
    
    /**
     * Changement de la couleur de fond de tous les noeuds dans la classe
     * @param classe Ensemble des noeuds a modifier
     */
    public void changementCouleurErgodiqueAbsorbant(ArrayList<Noeud> classe) {
        
        NoeudGrapheProbabiliste noeud;
        
        for (int aChanger = 0 ; aChanger < classe.size() ; aChanger++) {
            
            noeud = (NoeudGrapheProbabiliste) classe.get(aChanger);
            noeud.changementCouleurErgodiqueAbsorbant();
        }
    }

    /**
     * Ajouter les noeuds de la classe dans une grande liste de noeud transitoire
     * @param classe Liste de noeuds transitoires
     */
    private void ajouterListeTransitoire(ArrayList<Noeud> classe) {
        
        for (int aAjouter = 0 ; aAjouter < classe.size() ; aAjouter++) {
            noeudsTransitoires.add(classe.get(aAjouter));
        }
    }

    /**
     * Ajouter les noeuds de la classe dans une grande liste de noeud finaux
     * @param classe Liste de noeuds finaux
     */
    private void ajouterListeFinale(ArrayList<Noeud> classe) {
        
        for (int aAjouter = 0 ; aAjouter < classe.size() ; aAjouter++) {
            noeudsFinales.add(classe.get(aAjouter));
        }
    }
    
    /**
     * Creer la matice de transition du graphe sous la forme canonique
     * @return La matrice de transition du graphe sous la forme canonique
     *         [[N1, N2, N3]
     *          [N1, N2, N3]
     *          [N1, N2, N3]]
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
     * @return La matice de transition sous forme canonique
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
    
    /**
     * Recherche la valeur de l'arc entre le noeud n1 et n2
     * @param n1 Noeud source
     * @param n2 Noeud destinataire
     * @return La valeur entre les 2 noeuds s'il y a, 0.0 sinon
     */
    public double valeurEntreDeuxNoeud(NoeudGrapheProbabiliste n1, NoeudGrapheProbabiliste n2) {
        
        for (int i = 0; i < n1.successeurs.size() ; i++) {
            
            if (n1.successeurs.get(i).destinataire == n2) {
                
                ArcProbabiliste tmp = (ArcProbabiliste) n1.successeurs.get(i);
                return tmp.coefficient;
            } 
        }
        return 0.0;
    }
    
    /**
     * Multiplie la matrice carree m1 avec m2
     * @param m1 Matrice a multiplier
     * @param m2 Matrice a multiplier
     * @return La multiplication de la matrice m1 par m2
     */
    public static double[][] multiplicationMatrice(double[][] m1, double[][] m2){
        
        if(m1[0].length != m2.length){
            throw new IllegalArgumentException("Multiplication impossible !");
        }
        
        // Créer une matrice pour stocker la multiplication
        double resultat[][] = new double[m1.length][m2.length];  
        
        // Multiplication
        for (int i=0 ; i < m1.length ; i++){ //ligne matrice m1
            for (int j=0 ; j < m2.length ; j++){ //ligne matrice m2
                resultat[i][j] = 0;    
                for (int k=0 ; k < m2.length ; k++) { //colonne
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
        return resultat ;
    }
    
    /**
     * Calcul la probabilité de passer entre le noeud source et destinataire
     * en nbTransition transition
     * @param source Noeud source
     * @param destinataire Noeud destinataire
     * @param nbTransition Nombre de transition
     * @return La probabilité de passer entre le noeud source et destinataire
     */
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
    
    /**
     * Pour une loi de probabilité initiale sur l’ensemble des sommets du graphe, 
     * détermine la loi de probabilité atteinte après 
     * un nombre de transition(s) donné
     * @param vecteur Loi de probabilité initiale sur l’ensemble des sommets du graphe
     * @param nbTransition Nombre de transition
     * @return loi de probabilité atteinte après un nombre de transition(s) donné
     */
    public double[][] loiDeProbabiliteeEnNTransition(double[][] vecteur, int nbTransition){
        
        regroupementEtat(regroupementClasse());

        //Calcul de la loi de probabiliuté
            //Matrice de Transition exposant N
        double[][] matrice = matriceTransitoireCanonique();     
        matrice = exposantMatrice(matrice, nbTransition);
 
            //Mutiplier le vecteur de probabilité par la matrice de transition exposant N
        double[][] resultat =  multiplicationMatrice(vecteur, matrice);
            
        //Retourner le resultat
        return resultat;
    }
    
    /**
     * Affiche une matrice dans une GridPane
     * @return La matrice sous forme canonique dans un grid pane
     */
    public GridPane afficheMatrice(){
        
        regroupementEtat(regroupementClasse());
        double[][] matrice = matriceTransitoireCanonique();
        
        GridPane gridPane = new GridPane();
        
        // Affichage de la premiere ligne
        Text textNoeud = new Text("Noeuds");
        gridPane.add(textNoeud, 0, 0);
        GridPane.setValignment(textNoeud, VPos.CENTER);
        GridPane.setHalignment(textNoeud, HPos.CENTER);
        for (int noNoeud = 0 ; noNoeud < noeudsOrdones.size() ; noNoeud++) {
            
            gridPane.add(new Text(noeudsOrdones.get(noNoeud).toString()), noNoeud + 1, 0);
        }
        
        for (int x = 0 ; x < matrice.length ; x++) {
            
            Text nom = new Text(noeudsOrdones.get(x).toString());
            gridPane.add(nom, 0, x + 1);
            GridPane.setValignment(nom, VPos.CENTER);
            GridPane.setHalignment(nom, HPos.CENTER);
            for (int y = 0 ; y < matrice[x].length ; y++) {
                
                Text valeur = new Text(matrice[x][y] + "");
                gridPane.add(valeur, y + 1, x + 1);
                GridPane.setValignment(valeur, VPos.CENTER);
                GridPane.setHalignment(valeur, HPos.CENTER);
            }
        }

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        return gridPane;
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
