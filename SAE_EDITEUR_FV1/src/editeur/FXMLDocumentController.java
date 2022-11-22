/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editeur;

import java.awt.ScrollPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

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
    
   
    
    
    Graphe graphe = new Graphe();    
    @FXML
    private AnchorPane zoneDessin;
    @FXML
    private Text textNoeud;
    @FXML
    private Text textLien;
    @FXML
    private Text textSelection;
   
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

   
    
    @FXML
    private void cliqueZoneDessin(MouseEvent event) {
        
        // l'action du noeudCliquer
        if (selection == 1) {
            System.out.println("Clique sur le noeud");
             // récupere Position du clique sur la zone de dessin
            double x = event.getX();
            double y = event.getY();
            try {
                graphe.ajouterNoeud(x,y);
                graphe.noeuds.get(graphe.noeuds.size()-1).dessiner(zoneDessin);
                
                System.out.println("action faite");
            } catch (NoeudException e) {
                
            }
        } else if (selection == 2) {
            System.out.println("Clique sur lien");
            
            if(graphe.source != null && graphe.destinataire != null){
                try{
                    graphe.ajouterLien();
                    System.out.println("Ajout du lien dans le graphe");
                    graphe.liens.get(graphe.liens.size()-1).dessiner(zoneDessin);
                    System.out.println("dessin du Lien entre les deux noeuds");
                    
                }catch(LienException  e ){
                    System.out.println("Echec ");
                }
                
                graphe.source = null ;
                graphe.destinataire= null;
            } else {
                
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
        
    }

    @FXML
    private void cliqueSelection(MouseEvent event) {
        selection = 3 ; 
        zoneDessin.setCursor(Cursor.DEFAULT);
        textNoeud.setStyle("-fx-font-weight: none");
        textLien.setStyle("-fx-font-weight: none");
        textSelection.setStyle("-fx-font-weight: bold");
        
    }
    
    
    
}
   
 