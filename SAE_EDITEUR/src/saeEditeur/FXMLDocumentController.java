/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;

import java.awt.event.KeyListener;
import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.net.URL;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author amine.daamouch
 */
public class FXMLDocumentController implements Initializable {
    
    /*
     * 1 => Création de Noeud
     * 2 => Création de Lien
     * 3 => Curseur
     */
    int selection = 0 ;
    
   
    public static FactoryManager factoryManager = new FactoryManager();
    
    public AbstractFactoryGraphe factory;
    
    Graphe graphe;    
    @FXML
    private AnchorPane zoneDessin;
    @FXML
    private Text textNoeud;
    @FXML
    private Text textLien;
    @FXML
    private Text textSelection;
    @FXML
    public Menu menuNouveauGraphe;
    
    public Line lineMouseDrag = null;
    @FXML
    private javafx.scene.control.ScrollPane scrollpane;
    
    private Noeud premierNoeud;
    @FXML
    private Pane paneSelection;
    
    private Button btnVerifier = null;
    
    @FXML
    private MenuBar navbar;
    
    private Menu menuEdition = null;
    
    /**
     * Anciennes coordonnées d'un éléments du graphe (lors du déplacement)
     */
    private double xAncien, yAncien;
   
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
                            btnVerifier = null;
                        }
                        // Ajoute le bouton de verification si graphe probabiliste
                        if (aAjouter.equals("Graphe probabiliste")) {
                            btnVerifier = GrapheProbabiliste.ajouterBoutonVerification(paneSelection, 
                                                                                       zoneDessin, 
                                                                                       (GrapheProbabiliste) graphe);
                            
                            menuEdition = GrapheProbabiliste.ajouterMenuNavBar(navbar, (GrapheProbabiliste) graphe, zoneDessin);
                        } 
                    }
                });
                menuNouveauGraphe.getItems().add(menuItem);
            }
        
        }catch(Exception e ){ }
    }
    
    
    
    @FXML
    private void cliqueZoneDessin(MouseEvent event) throws NoeudException , ArcProbabilisteException{
        // récupere Position du clique sur la zone de dessin
        double x = event.getX();
        double y = event.getY();
        
        // Verifie qu'un graphe est ouvert ou cree
        if (graphe != null) {
            
            /* Deselectionner de tous les elements */
            graphe.deselectionnerAll(zoneDessin);

            /* Execution de l'action en fonction de la selection */
            // l'action du noeudCliquer
            if (selection == 1) {

                try{
                    graphe.estNoeudValide(x, y);
                    Noeud n = factory.creerNoeud(x, y);

                    graphe.ajouterNoeud(n);         
                    n.dessiner(zoneDessin);
                } catch (NoeudException e) {
                }
            } else if (selection == 3) {

                // Cas - Clic sur noeud
                if (graphe.estNoeud(x, y) != null) {

                    scrollpane.setPannable(false);
                    graphe.noeudSelectionne(graphe.estNoeud(x, y));
                    xAncien = graphe.noeudSelectionne.position.x;
                    yAncien = graphe.noeudSelectionne.position.y;
                }
                // Cas - Clic sur Lien
                if (graphe.estLien(x, y) != null && graphe.noeudSelectionne == null) {

                    scrollpane.setPannable(false);
                    graphe.lienSelectionne(graphe.estLien(x, y), zoneDessin);
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
     * évenement dès lors que l'on clique sur la sélection de la création
     * d'un noeud
     */
    @FXML
    private void cliqueNoeud(MouseEvent event) {
        selection = 1 ;
        zoneDessin.setCursor(Cursor.HAND);
        textSelection.setStyle("-fx-font-weight: none");
        textLien.setStyle("-fx-font-weight: none");
        textNoeud.setStyle("-fx-font-weight: bold");
        scrollpane.setPannable(false);
    }

    /**
     * évenement dès lors que l'on clique sur la sélection de la création
     * d'un lien
     */
    @FXML
    private void cliqueLien(MouseEvent event) {
        selection = 2 ;
        zoneDessin.setCursor(Cursor.CROSSHAIR);  // Change le curseur en main 
        textNoeud.setStyle("-fx-font-weight: none");
        textSelection.setStyle("-fx-font-weight: none");
        textLien.setStyle("-fx-font-weight: bold");
        scrollpane.setPannable(false);
        
    }

    @FXML
    private void cliqueSelection(MouseEvent event) {
        selection = 3 ; 
        zoneDessin.setCursor(Cursor.DEFAULT);
        textNoeud.setStyle("-fx-font-weight: none");
        textLien.setStyle("-fx-font-weight: none");
        textSelection.setStyle("-fx-font-weight: bold");
        scrollpane.setPannable(true);
    }
    
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

    @FXML
    private void zoneDessinMouseReleased(MouseEvent event) throws LienException {
        
        if (selection == 2 && lineMouseDrag != null) {
            Noeud destinataire = graphe.estNoeud(lineMouseDrag.getEndX(), lineMouseDrag.getEndY());

            if (destinataire != null) {
                try{
                    Lien l = graphe.ajouterLien(factory.creerLien(premierNoeud, destinataire));               
                    l.dessiner(zoneDessin);
                    
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

            if (graphe.noeudSelectionne != null) {
                // Si la position du noeud est invalide 
                // Alors on le remet sur son ancienne position
                if (!graphe.estNoeudValideApresDeplacement(x, y, graphe.noeudSelectionne)) {

                    graphe.noeudSelectionne.modifierPosition(xAncien, yAncien, zoneDessin);
                    graphe.noeudSelectionne.noeudSelectionne();
                }
            } else if (graphe.lienSelectionne != null) {
                
                Noeud nouveauNoeud = graphe.estNoeud(x, y);
                
                if (nouveauNoeud != null) {
                    // Changement l'extremité
                    graphe.changementExtremiteLien(nouveauNoeud, graphe.lienSelectionne.estExtremite(x, y), zoneDessin);
                    
                } else {
                    // Remise par defaut de l'extremite du lien
                    graphe.lienSelectionne.remiseDefaut();
                    graphe.deselectionnerAll(zoneDessin);
                }
            }
        }
    }

    @FXML
    private void deplacement(KeyEvent event) {
        if(event.getCode() == KeyCode.CONTROL){  
            zoneDessin.setCursor(Cursor.E_RESIZE);
            scrollpane.setPannable(true);
        }
    }

    @FXML
    private void enregistrerGraphe(ActionEvent event) {
        
        // Verifie qu'un graphe est ouvert ou cree
        if (graphe != null) {
            
            
            Stage popUp = new Stage();
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
     * Ouvre un graphe
     * @param event 
     */
    @FXML
    private void ouvrirGraphe(ActionEvent event) {
        
        Stage popUp = new Stage();
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
                    
                    // Ajoute le bouton de verification si graphe probabiliste
                    if (graphe.getFactory().equals("Graphe probabiliste")) {
                        btnVerifier = GrapheProbabiliste.ajouterBoutonVerification(paneSelection, 
                                                                                   zoneDessin, 
                                                                                   (GrapheProbabiliste) graphe);

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

    @FXML
    private void raccourci(KeyEvent event) {
        
        KeyCode keyCode = event.getCode();
        System.out.println(keyCode);
        
        System.out.println("root click(handler)");
        event.consume();
        // Suppression du noeud selectionne
        if (graphe.noeudSelectionne != null && keyCode.equals(KeyCode.DELETE)) {
            
            graphe.supprmierNoeud(graphe.noeudSelectionne, zoneDessin);
        }
        // Suppression du lien selectionne
        if (graphe.lienSelectionne != null && keyCode.equals(KeyCode.DELETE)) {
            
            graphe.supprimerLien(graphe.lienSelectionne, zoneDessin);
        }
    }
    
}
   
 