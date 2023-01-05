/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
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
        
        /* Deselectionner de tous les elements */
        graphe.deselectionnerAll();
        
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
                
                graphe.noeudSelectionne(graphe.estNoeud(x, y));
            }
            // Cas - Clic sur Lien
            if (graphe.estLien(x, y) != null) {
                
                graphe.lienSelectionne(graphe.estLien(x, y));
            }
            
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
        
        graphe.enregistrer();
    }


}
   
 