/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae_editeur;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

/**
 *
 * @author amine.daamouch
 */
public class FXMLDocumentController implements Initializable {
    int radioCliquer = 0 ;
    //boolean radioNoeudcliquer = false;
    //boolean radioAretecliquer = false;
   //boolean cliqueZoneDessin = false;
    @FXML
    private Label label;
    @FXML
    private ToggleGroup palette;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
   
    @FXML
    /**
     * évenement dès lors que l'on clique sur le radio bouton
     * de le noeud
     */
    private void noeudCliquer(ActionEvent event) {
        radioCliquer = 1;
        
    }

     @FXML
    /**
     * évenement dès lors que l'on clique sur le radio bouton
     * de l'arete
     */
    private void areteCliquer(ActionEvent event) {
        radioCliquer = 2 ;
    }
    
    
    @FXML
    private void cliqueZoneDessin(MouseEvent event) {
        // récupere Position du cliqque sur la zone de dessin
        double x = event.getSceneX();
        double y = event.getSceneY();
        
        if(radioCliquer == 2) {
            //appele de la méthode dessiner de la classe Arete
        }
        if(radioCliquer == 1){
            // appele de la méthode dessiner de la classe Noeud
            
        }
    }

   
   
    
}
