/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author vincent.faure
 */
public class Proprietees {
    
    public static void updateNameNoeud(Noeud aModifier, Graphe graphe, GridPane pane){
    
        graphe.noeudSelectionne(aModifier);
                
        //Afficher les propriétées
        Text labelNomNoeud = new Text("Nom :");
        TextField nomNoeud = new TextField(aModifier.nom);
                
        nomNoeud.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                
            Button validerModif = new Button("Valider");
            pane.add(validerModif,0,4);
            pane.setColumnSpan(validerModif,2);
            pane.setHalignment(validerModif, HPos.CENTER);      
            
            validerModif.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if(graphe.estNomNoeudValide(nomNoeud.getText())){
                        aModifier.setNom(nomNoeud.getText());
                        aModifier.nomText.setText(nomNoeud.getText());
                    }else{
                        nomNoeud.setText(aModifier.nom);
                    }   
                    pane.getChildren().remove(validerModif); // Enlever le bouton attention erreur
                } 
            });
             
        });
        
        pane.add(labelNomNoeud,0,0);
        pane.add(nomNoeud,1,0);
        pane.setAlignment(Pos.CENTER);
    } 
    
    
}



















