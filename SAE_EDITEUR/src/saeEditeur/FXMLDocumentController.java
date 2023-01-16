/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * FXMLDocumentController.java    16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur;

import saeEditeur.graphe.AbstractFactoryGraphe;
import saeEditeur.graphe.FactoryManager;
import saeEditeur.graphe.EditeurDeProprietes;
import saeEditeur.graphe.lien.LienException;
import saeEditeur.graphe.lien.ArcProbabilisteException;
import saeEditeur.graphe.lien.Lien;
import saeEditeur.graphe.noeud.Noeud;
import saeEditeur.graphe.noeud.NoeudException;
import saeEditeur.graphe.typegraphe.GrapheProbabiliste;
import saeEditeur.graphe.typegraphe.Graphe;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Gestion de toutes les actions et affichage possible sur "Editeur de graphe"
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public class FXMLDocumentController implements Initializable {
    
    /*
     * 1 => Création de Noeud
     * 2 => Création de Lien
     * 3 => Sélection
     */
    int selection = 0 ;
    
    /** Factory contenant tous les types de graphe */
    public static FactoryManager factoryManager = new FactoryManager();
    
    /** 
     * Permet la création d'une nouvelle instance d'un graphe en fonction d'un
     * certain type de graphe
     */
    public AbstractFactoryGraphe factory;
    
    /** Graphe actuel (null si aucun graphe ouvert/sélectionné)
     */
    Graphe graphe;    
    
    /** Zone de dessin contenant les noeuds et les liens */
    @FXML
    private AnchorPane zoneDessin;
    
    /** Texte sur lequel l'utilisateur clique pour avoir la "Création d'un noeud" */
    @FXML
    private Text textNoeud;
    
    /** Texte sur lequel l'utilisateur clique pour avoir la "Création d'un lien" */
    @FXML
    private Text textLien;
    
    /** Texte sur lequel l'utilisateur clique pour avoir la "Sélection" */
    @FXML
    private Text textSelection;
    
    /** 
     * Menu déroulant permettant la création d'un nouveau graphe 
     * ou bien l'enregistrement/ouverture d'un graphe
     */
    @FXML
    public Menu menuNouveauGraphe;
    
    /** Ligne représentant un lien lors de la création */
    public Line lineMouseDrag = null;
    
    /** Scroll pane de la zone de dessin */
    @FXML
    private javafx.scene.control.ScrollPane scrollpane;
    
    /** Premier noeud lors de la création d'un lien */
    private Noeud premierNoeud;
    
    /** Pane contenant les différentes actions de sélection (Noeud, Lien, ...) */
    @FXML
    private Pane paneSelection;
    
    /** Bouton pour la vérification d'un graphe probabiliste */
    private Button btnVerifier = null;
    
    /** NavBar du logiciel */
    @FXML
    private MenuBar navbar;
    
    /** Menu contenant les traitements d'un graphe probabiliste */
    private Menu menuEdition = null;
    
    /** Anciennes coordonnées d'un éléments du graphe (lors du déplacement) */
    private double xAncien, yAncien;
    
    /** Grid pane contenant les propriétés d'un élément sélectionné */
    @FXML
    private GridPane gridProprietees;
    
    /** Menu contenant toutes la partie de gauche du logiciel (Sélection, Propriétés ...) */
    @FXML
    private Pane menu;
    
    /** Zone contenant la légende du graphe probabiliste */
    private Pane legende;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            for (String aAjouter : factoryManager.getTypes()) {
                MenuItem menuItem = new MenuItem(aAjouter);
                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        
                        factory = factoryManager.getFactory(aAjouter);
                        graphe = factory.creerGraphe();
                        // Vider tous les Noeuds et Liens présents sur le graphe
                        zoneDessin.getChildren().clear();
                        // Supprimer bouton Verifier graphe
                        if (btnVerifier != null) {
                            paneSelection.getChildren().remove(btnVerifier);
                            navbar.getMenus().remove(menuEdition);
                            menu.getChildren().remove(legende);
                            legende = null;
                            btnVerifier = null;
                        }
                        // Ajoute le bouton de verification si graphe probabiliste
                        if (aAjouter.equals("Graphe probabiliste")) {
                            btnVerifier = GrapheProbabiliste.ajouterBoutonVerification(paneSelection, 
                                                                                       zoneDessin, 
                                                                                       (GrapheProbabiliste) graphe);
                            
                            legende = GrapheProbabiliste.ajouterLegende(menu);
                            
                            menuEdition = GrapheProbabiliste.ajouterMenuNavBar(navbar, (GrapheProbabiliste) graphe, zoneDessin);
                        } 
                    }
                });
                menuNouveauGraphe.getItems().add(menuItem);
            }
        
        }catch(Exception e ){ }
    }
    
    /**
     * Action sur la zone de dessin
     * Si aucun graphe n'a été sélection alors l'utilisateur est prévenu
     * Si Sélection == 1 => Création d'un nouveau noeud en (x, y) sur la zone de dessin
     * Si Sélection == 3 => Si le curseur est sur un lien ou un noeud alors sélection
     *                      sinon désélection de tous les éléments du graphe
     * @param event Evenement sur la zone de dessin
     * @throws NoeudException Impossible de créer un noeud invalide
     * @throws ArcProbabilisteException  Impossible de créer un lien invalide
     */
    @FXML
    private void cliqueZoneDessin(MouseEvent event) throws NoeudException , ArcProbabilisteException{
        // récupere Position du clique sur la zone de dessin
        double x = event.getX();
        double y = event.getY();
        
        // Verifie qu'un graphe est ouvert ou cree
        if (graphe != null) {
            
            /* Deselectionner de tous les elements */
            graphe.deselectionnerAll(zoneDessin);
            
            /* Clear le grid des proprietees */
            EditeurDeProprietes.fermer(gridProprietees);


            /* Execution de l'action en fonction de la selection */
            // l'action du noeudCliquer
            if (selection == 1) {

                try{
                    graphe.estNoeudValide(x, y);
                    Noeud n = factory.creerNoeud(x, y, graphe);

                    graphe.ajouterNoeud(n);         
                    n.dessiner(zoneDessin);
                    graphe.ajouterPileUndo(0); // Ajout dans la pile (undo)
                } catch (NoeudException e) {
                }
            } else if (selection == 3) {

                // Cas - Clic sur noeud
                if (graphe.estNoeud(x, y) != null) {

                    scrollpane.setPannable(false);
                    graphe.noeudSelectionne(graphe.estNoeud(x, y));
                    xAncien = graphe.noeudSelectionne.position.x;
                    yAncien = graphe.noeudSelectionne.position.y;
                    
                    // Propriete du Noeud
                    EditeurDeProprietes.afficher(graphe.noeudSelectionne,gridProprietees,zoneDessin);;
                }
                // Cas - Clic sur Lien
                if (graphe.estLien(x, y) != null && graphe.noeudSelectionne == null) {

                    scrollpane.setPannable(false);
                    graphe.lienSelectionne(graphe.estLien(x, y), zoneDessin);
                    
                    //Propriete du Lien
                    EditeurDeProprietes.afficher(graphe.lienSelectionne,gridProprietees, zoneDessin);
                }
            }
        } else {
            // Affichage alerte aucun graphe selectionne
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText("Aucun graphe sélectionné");
            alert.setContentText("Veuillez créer ou ouvrir un graphe avant de créer un noeud.");

            alert.showAndWait();
        }
    }  

    /**
     * La sélection passe à 1 (Création d'un noeud)
     * @param event Evenement sur le texte ou le cercle
     */
    @FXML
    private void cliqueNoeud(MouseEvent event) {
        
        selectionNoeud();
    }
    
    /**
     * L'utilisateur a choisi la sélection 1 (Création d'un noeud)
     * Le texte est épaissi et la sélection passe à 1
     */
    private void selectionNoeud() {
        
        selection = 1 ;
        zoneDessin.setCursor(Cursor.HAND);
        textSelection.setStyle("-fx-font-weight: none");
        textLien.setStyle("-fx-font-weight: none");
        textNoeud.setStyle("-fx-font-weight: bold");
        scrollpane.setPannable(false);
    }

    /**
     * La sélection passe à 2 (Création d'un lien)
     * @param event Evenement sur le texte ou la ligne
     */
    @FXML
    private void cliqueLien(MouseEvent event) {
        
        selectionLien();
    }
    
    /**
     * L'utilisateur a choisi la sélection 2 (Création d'un Lien)
     * Le texte est épaissi et la sélection passe à 2
     */
    private void selectionLien() {
        
        selection = 2 ;
        zoneDessin.setCursor(Cursor.CROSSHAIR);  // Change le curseur en main 
        textNoeud.setStyle("-fx-font-weight: none");
        textSelection.setStyle("-fx-font-weight: none");
        textLien.setStyle("-fx-font-weight: bold");
        scrollpane.setPannable(false);
    }

    /**
     * La sélection passe à 3 (Sélection)
     * @param event Evenement sur le texte ou le curseur
     */
    @FXML
    private void cliqueSelection(MouseEvent event) {
        
        selectionSelection();
    }
    
    /**
     * L'utilisateur a choisi la sélection 3 (Sélection)
     * Le texte est épaissi et la sélection passe à 3
     */
    private void selectionSelection() {
        
        selection = 3 ; 
        zoneDessin.setCursor(Cursor.DEFAULT);
        textNoeud.setStyle("-fx-font-weight: none");
        textLien.setStyle("-fx-font-weight: none");
        textSelection.setStyle("-fx-font-weight: bold");
        scrollpane.setPannable(true);
    }
    
    /**
     * L'utilisateur réalise un glisser/déposer     
     * Si aucun graphe n'a été sélection alors l'utilisateur est prévenu
     * Si Sélection == 2 => Pré visualisation d'un lien
     * Si Sélection == 3 => Noeud sélectionné -- Le noeud est déplacé
     *                      Lien  sélectionné -- Le lien est déplacé
     * @param event Evenement sur la zone de dessin
     */
    @FXML
    private void zoneDessinMouseDragged(MouseEvent event) {
        
        double xPrimeSource;
        double yPrimeSource;
        double distance;
        
        // Verifie qu'un graphe est ouvert ou cree
        if (graphe != null) {
            if (selection == 2) {
                double x, y; // Position souris

                // Récupérer position souris en évitant de déborder de la zone de dessin
                x = event.getX();
                y = event.getY();

                // Créer une nouvelle enveloppe ou modifier l'enveloppe en cours
                if (lineMouseDrag == null && graphe.estNoeud(x, y) != null) {

                    // 1er point cliqué : il faut créer une nouvelle enveloppe
                    premierNoeud = graphe.estNoeud(x, y);
                    lineMouseDrag = new Line(x, y, x, y);
                    zoneDessin.getChildren().add(lineMouseDrag);
                } else if (lineMouseDrag != null && premierNoeud != null) {    

                    // Récupérer 2ème point et dessiner l'enveloppe
                    distance = Math.sqrt(Math.pow(premierNoeud.position.x - x, 2) + Math.pow(premierNoeud.position.y - y, 2));
                    xPrimeSource = premierNoeud.position.x + (x - premierNoeud.position.x) / distance * Noeud.RAYON;
                    yPrimeSource = premierNoeud.position.y + (y - premierNoeud.position.y) / distance * Noeud.RAYON;
                    lineMouseDrag.setStartX(xPrimeSource);
                    lineMouseDrag.setStartY(yPrimeSource);
                    lineMouseDrag.setEndX(x);
                    lineMouseDrag.setEndY(y);
                }

            } else if (selection == 3) {
                
                double x, y; // Position souris

                // Récupérer position souris en évitant de déborder de la zone de dessin
                x = event.getX();
                y = event.getY();

                // Permet de deplacer un noeud
                if (graphe.noeudSelectionne != null) {

                    // 1er point cliqué : il faut créer une nouvelle enveloppe
                    graphe.noeudSelectionne.modifierPosition(x, y, zoneDessin);
                }
                // Permet de deplacer un lien
                if (graphe.lienSelectionne != null) {

                    // 1er point cliqué : il faut créer une nouvelle enveloppe
                    graphe.lienSelectionne.modifierPosition(x, y, graphe.lienSelectionne.estExtremite(x, y), zoneDessin);
                }
                
            }
        } else {
            // Affichage alerte aucun graphe selectionne
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText("Aucun graphe sélectionné");
            alert.setContentText("Veuillez créer ou ouvrir un graphe avant toutes actions.");

            alert.showAndWait();
        }
    }

    /**
     * L'utilisateur réalise un glisser/déposer     
     * Si aucun graphe n'a été sélection alors l'utilisateur est prévenu
     * Si Sélection == 2 => Si le lien peut être créé alors il est ajouté
     * Si Sélection == 3 => Noeud sélectionné -- Si le noeud est valide après 
     *                                           déplacement alors la modification
     *                                           est réalisée
     *                      Lien  sélectionné -- Si le lien est valide après 
     *                                           déplacement alors la modification
     *                                           est réalisée
     * @param event Evenement sur la zone de dessin
     */
    @FXML
    private void zoneDessinMouseReleased(MouseEvent event) throws LienException {
        
        if (selection == 2 && lineMouseDrag != null) {
            Noeud destinataire = graphe.estNoeud(lineMouseDrag.getEndX(), lineMouseDrag.getEndY());

            if (destinataire != null) {
                try{
                    Lien l = graphe.ajouterLien(factory.creerLien(premierNoeud, destinataire, graphe));               
                    l.dessiner(zoneDessin);
                    graphe.ajouterPileUndo(1); // Ajout dans la pile (undo)
                    
                }catch(LienException e){
                    System.out.println(e.getMessage());
                }
                
            }
            zoneDessin.getChildren().remove(lineMouseDrag);
            lineMouseDrag = null;
        } else if (selection == 3) {
                
            double x, y; // Position souris

            // Récupérer position souris en évitant de déborder de la zone de dessin
            x = event.getX();
            y = event.getY();

            if (graphe.noeudSelectionne != null                   
                && (xAncien != graphe.noeudSelectionne.position.x        // Vérifie que la position du
                    || yAncien != graphe.noeudSelectionne.position.y)) { // noeud a été modifié
                
                // Si la position du noeud est invalide 
                // Alors on le remet sur son ancienne position
                if (!graphe.estNoeudValideApresDeplacement(x, y, graphe.noeudSelectionne)) {

                    graphe.noeudSelectionne.modifierPosition(xAncien, yAncien, zoneDessin);
                    graphe.noeudSelectionne.noeudSelectionne();
                } else {
                    ArrayList<Object> ancienPosition = new ArrayList<>();
                    ancienPosition.add(graphe.noeudSelectionne);
                    ancienPosition.add(xAncien);
                    ancienPosition.add(yAncien);
                    graphe.ajouterPileUndo(ancienPosition);
                }
            } else if (graphe.lienSelectionne != null) {
                
                Noeud nouveauNoeud = graphe.estNoeud(x, y);
                Lien nouveauLien;
                int extremite = graphe.lienSelectionne.estExtremite(x, y);
              
                if (nouveauNoeud != null) {
                    
                    ArrayList<Object> ancienPosition = new ArrayList<>(); // Liste pour la pile d'action
                    ancienPosition.add(graphe.lienSelectionne);
                    // Créer un nouveau lien pour le comparer a ceux existant
                    if (extremite == 1) {
                        nouveauLien = factory.creerLien(nouveauNoeud, graphe.lienSelectionne.destinataire,graphe);
                        ancienPosition.add(graphe.lienSelectionne.source); // Ajout dans la liste l'ancien noeud
                    } else {
                        nouveauLien = factory.creerLien(graphe.lienSelectionne.source, nouveauNoeud,graphe);
                        ancienPosition.add(graphe.lienSelectionne.destinataire); // Ajout dans la liste l'ancien noeud
                    }
                    ancienPosition.add(extremite); // Ajout dans la liste l'extremite
                    // Test la presence ou non d'un lien entre le nouveau noeud et l'autre
                    if (graphe.estLienValide(nouveauLien)) {
                        // Changement l'extremité
                        graphe.changementExtremiteLien(graphe.lienSelectionne, nouveauNoeud, extremite, zoneDessin);
                        graphe.ajouterPileUndo(ancienPosition); // Ajout dans la pile d'action
                    } else {
                        // Remise par defaut de l'extremite du lien
                        graphe.lienSelectionne.remiseDefaut();
                        graphe.deselectionnerAll(zoneDessin);
                        EditeurDeProprietes.fermer(gridProprietees);
                    }
                    
                } else {
                    // Remise par defaut de l'extremite du lien
                    graphe.lienSelectionne.remiseDefaut();
                    graphe.deselectionnerAll(zoneDessin);
                    EditeurDeProprietes.fermer(gridProprietees);
                }
            }
        }
    }

    /**
     * Si aucun graphe n'a été sélection alors l'utilisateur est prévenu
     * Ouvre une popup pour l'enregistrement d'un graphe
     * L'utilisateur choisit un dossier et nom pour l'enregistrement
     * Si ses données sont éronées alors il est prévenu
     * @param event Action sur l'enregistrement d'un graphe
     */
    @FXML
    private void enregistrerGraphe(ActionEvent event) {
        
        // Verifie qu'un graphe est ouvert ou cree
        if (graphe != null) {
            
            
            Stage popUp = new Stage();
            popUp.getIcons().add(new Image("/saeEditeur/logo.png"));
            popUp.initModality(Modality.APPLICATION_MODAL);
            StackPane pane = new StackPane();
            GridPane gridPane = new GridPane();
            
            // Label des inputs
            Text labelChemin = new Text("Saisir le chemin absolu pour l'enregistrement");
            Text labelNom = new Text("Saisir le nom");
            
            // Phrase d'erreur
            Text erreur = new Text();
            
            Button inputChemin = new Button("Choisir dossier");
            inputChemin.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {

                    
                    final DirectoryChooser dialog = new DirectoryChooser(); 
                    final File directory = dialog.showDialog(inputChemin.getScene().getWindow());
                    if (directory != null) { 
                        // Effectuer le traitement. 
                        
                        inputChemin.setText(directory.getAbsolutePath());
                    } 
                }
            });
            TextField inputNom = new TextField(graphe.toString());
            
            // Button calcul
            Button btCalcul = new Button("Enregistrer");
            btCalcul.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    
                    try {
                        graphe.enregistrer(inputChemin.getText() + "/" + inputNom.getText());
                        popUp.close();
                        
                        // Affichage pour confirmer l'enregistrement
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Enregistrement");
                        alert.setHeaderText("L'enregistrement a été réalisé avec succès");

                        alert.showAndWait();
                        
                    } catch (FileNotFoundException err) {
                        
                        // Erreur dans la saisie du chemin et du noeud
                        erreur.setText("Les informations sont érronées.");
                    }
                }
            });
            
            gridPane.add(labelChemin, 0, 0);
            gridPane.add(labelNom, 0, 1);
            gridPane.add(inputChemin, 1, 0);
            gridPane.add(inputNom, 1, 1);
            gridPane.add(btCalcul, 1, 2);
            gridPane.add(erreur, 0, 3);
            gridPane.setColumnSpan(erreur,2);
            // CSS
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            
            pane.getChildren().add(gridPane);
            popUp.setScene(new Scene(pane, 500, 200));
            popUp.showAndWait();
        } else {
            // Affichage alerte aucun graphe selectionne
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText("Aucun graphe sélectionné");
            alert.setContentText("Veuillez créer ou ouvrir un graphe avant toutes actions.");

            alert.showAndWait();
        }

    }

    /**
     * Ouvre une popup pour l'ouverture d'un graphe
     * L'utilisateur choisit un fichier
     * Si ses données sont éronées alors il est prévenu
     * Sinon il est averti de la bonne ouverture de son graphe
     * @param event Action sur l'ouverture d'un graphe
     */
    @FXML
    private void ouvrirGraphe(ActionEvent event) {
        
        Stage popUp = new Stage();
        popUp.getIcons().add(new Image("/saeEditeur/logo.png"));
        popUp.initModality(Modality.APPLICATION_MODAL);
        StackPane pane = new StackPane();
        GridPane gridPane = new GridPane();

        // Label des inputs
        Text labelChemin = new Text("Saisir le fichier du graphe ");

        // Phrase d'erreur
        Text erreur = new Text();

        Button inputChemin = new Button("Choisir fichier");
        inputChemin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                final FileChooser dialog = new FileChooser(); 
                final File file = dialog.showSaveDialog(inputChemin.getScene().getWindow());
                if (file != null) { 
                    // Effectuer le traitement. 

                    inputChemin.setText(file.getAbsolutePath());
                } 
            }
        });

        // Button calcul
        Button btOurvrir = new Button("Ouvrir");
        btOurvrir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                try {
                    graphe = Graphe.ouvrir(inputChemin.getText());
                    // Vider tous les Noeuds et Liens présents sur le graphe
                    zoneDessin.getChildren().clear();
                    graphe.dessiner(zoneDessin);
                    // Factory
                    factory = factoryManager.getFactory(graphe.getFactory());
                    
                    // Supprimer bouton Verifier graphe
                    if (btnVerifier != null) {
                        paneSelection.getChildren().remove(btnVerifier);
                        navbar.getMenus().remove(menuEdition);
                        menu.getChildren().remove(legende);
                        legende = null;
                        btnVerifier = null;
                    }
                    
                    // Ajoute le bouton de verification si graphe probabiliste
                    if (graphe.getFactory().equals("Graphe probabiliste")) {
                        btnVerifier = GrapheProbabiliste.ajouterBoutonVerification(paneSelection, 
                                                                                   zoneDessin, 
                                                                                   (GrapheProbabiliste) graphe);
                        
                        legende = GrapheProbabiliste.ajouterLegende(menu);

                        menuEdition = GrapheProbabiliste.ajouterMenuNavBar(navbar, (GrapheProbabiliste) graphe, zoneDessin);
                    } 
                    
                    popUp.close();

                    // Affichage pour confirmer l'enregistrement
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Ouverture");
                    alert.setHeaderText("Le graphe a été ouvert avec succès");

                    alert.showAndWait();

                } catch (FileNotFoundException err) {

                    // Erreur dans la saisie du chemin et du noeud
                    erreur.setText("Les informations sont érronées.");
                }
            }
        });

        gridPane.add(labelChemin, 0, 0);
        gridPane.add(inputChemin, 1, 0);
        gridPane.add(btOurvrir, 1, 2);
        gridPane.add(erreur, 0, 3);
        gridPane.setColumnSpan(erreur,2);
        // CSS
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        pane.getChildren().add(gridPane);
        popUp.setScene(new Scene(pane, 500, 200));
        popUp.showAndWait();
    }

    /**
     * Si aucun graphe n'a été sélection alors l'utilisateur est prévenu
     * Ctrl + Suppr => Supprime l'élément sélectionné
     * Ctrl + N     => Passe la sélection à 1 (Création d'un noeud)
     * Ctrl + L     => Passe la sélection à 2 (Création d'un lien)
     * Ctrl + A     => Passe la sélection à 3 (Sélection)
     * Ctrl + Z     => Annuler l'action précédente
     * Ctrl + Y     => Rétablir l'acion annuler
     * Ctrl + F     => Vérifier le graphe si le graphe est probabiliste
     * @param event Action sur le clavier
     */
    @FXML
    private void raccourci(KeyEvent event) {
        
        KeyCode keyCode = event.getCode();
        
        if (graphe != null) {
            // Suppression du noeud selectionne
            if (graphe.noeudSelectionne != null && keyCode.equals(KeyCode.DELETE)) {

                ArrayList<Object> noeudSupprimerUndo = new ArrayList<>();
                noeudSupprimerUndo.add(graphe.noeudSelectionne);
                noeudSupprimerUndo.add((ArrayList<Lien>) graphe.noeudSelectionne.successeurs.clone());
                noeudSupprimerUndo.add((ArrayList<Lien>) graphe.noeudSelectionne.predecesseurs.clone());

                graphe.ajouterPileUndo(noeudSupprimerUndo);
                graphe.supprimerNoeud(graphe.noeudSelectionne, zoneDessin);
                graphe.deselectionnerAll(zoneDessin);
                EditeurDeProprietes.fermer(gridProprietees);
            }
            // Suppression du lien selectionne
            if (graphe != null && graphe.lienSelectionne != null && keyCode.equals(KeyCode.DELETE)) {

                graphe.ajouterPileUndo(graphe.lienSelectionne);
                graphe.supprimerLien(graphe.lienSelectionne, zoneDessin);
                graphe.deselectionnerAll(zoneDessin);
                EditeurDeProprietes.fermer(gridProprietees);
            }
            // Raccourci clavier - Création d'un nouveau noeud
            if (event.isControlDown() && event.getCode() == KeyCode.N){

                selectionNoeud();
            }
            // Raccourci clavier - Création d'un nouveau lien
            if (event.isControlDown() && event.getCode() == KeyCode.L){

                selectionLien();
            }
            // Raccourci clavier - Sélectionner l'option de "Sélection"
            if (event.isControlDown() && event.getCode() == KeyCode.A){

                selectionSelection();
            }
            // Raccourci clavier - Annuler dernière action
            if (event.isControlDown() && event.getCode() == KeyCode.Z){

                graphe.undo(zoneDessin);
            }
            // Raccourci clavier - Retablir dernière action
            if (event.isControlDown() && event.getCode() == KeyCode.Y){

                graphe.redo(zoneDessin);
            }

            // Raccourci clavier - Sélectionner l'option de vérification du graph
            if (graphe.getFactory().equals("Graphe probabiliste") && event.isControlDown() && event.getCode() == KeyCode.F){

                ((GrapheProbabiliste) graphe).verifierGraphe();
            }
        } else {
            // Affichage alerte aucun graphe selectionne
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText("Aucun graphe sélectionné");
            alert.setContentText("Veuillez créer ou ouvrir un graphe avant toutes actions.");

            alert.showAndWait();
        }
    }

    @FXML
    private void deplacement(KeyEvent event) {
    }
    
}
   
 