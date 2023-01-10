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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author vincent.faure
 */
public class EditeurDeProprietes {
        
    /**
     * Affiche dans la zone de propriete les proprietes editable de l'element
     * avec des TextField ou des ChoiceBox pour saisir ou modifier
     * @param e Element du graphe
     * @param pane GridPane
     * @param g Graphe
     */
    public static void afficher(ElementGraphe e, GridPane pane, AnchorPane zoneDessin){

        //Initialisation Noeud et Lien
        System.out.println(e.getClass());
        
        Propriete[] proprietees = e.getPropriete();//Recuperation des proprietees de e
        
        //Creation du bouton valider invisible
        Button valider = new Button("Valider");
        pane.add(valider,0,proprietees.length + 1);
        pane.setColumnSpan(valider,2);
        pane.setHalignment(valider, HPos.CENTER);
        valider.setVisible(false);
        
        //Gap gridpane
        pane.setVgap(25.0);
        
        //Tableau fieldText
        TextField[] tabField = new TextField[proprietees.length];
        
        //Tableau Choice box
        ChoiceBox[] tabBox = new ChoiceBox[proprietees.length];

        for (int i = 0; i < proprietees.length; i++) {

            Propriete propriete = proprietees[i];

            Text nom = new Text(propriete.nom + " :");
            pane.add(nom,0,i);

            if(!propriete.saisieDansListe){ //Si on veut un TextField
                
                //Gestion du tableau ChoiceBox
                tabBox[i] = null;

                TextField field = new TextField(); 
                pane.add(field,1,i);
                
                propriete.setObject(field);

                tabField[i] = field;
                field.setId("field" + i);
                
                field.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                
                    valider.setVisible(true);//Afficher bouton

                    //Action bouton valider
                    valider.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            TextField field;
                            Propriete propriete;
                            for(int i = 0; i < tabField.length; i++){
                                field = tabField[i];
                                if(field != null){
                                    propriete = proprietees[i];
                                    propriete.setValeur(field.getText(), field, zoneDessin);
                                }
                            }
                            //Faire disparaitre le bouton
                            valider.setVisible(false);
                        }
                    });
                });
            }else{
                
                //Gestion de tabField
                tabField[i] = null;
                
                //Ajout de la choice box dans propriete
                ChoiceBox box = new ChoiceBox();
                pane.add(box,1,i);
                
                //Ajout des item dans la box
                for(int j = 0; j < propriete.getListeChoix().length; j++){
                    box.getItems().add(propriete.getListeChoix()[j]);
                }
                propriete.setObject(box);

                tabBox[i] = box;
                box.setId("box" + i);
                
                //Action chamgement sur une choice box
                box.setOnAction(event -> {
                
                    valider.setVisible(true);//Afficher bouton

                    //Action bouton valider
                    valider.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            ChoiceBox box;
                            Propriete propriete;
                            for(int i = 0; i < tabBox.length; i++){
                                box = tabBox[i];
                                if(box != null){
                                    propriete = proprietees[i];
                                    propriete.setValeur((String) box.getValue(), box, zoneDessin);
                                }
                            }
                            //Faire disparaitre le bouton
                            valider.setVisible(false);
                        }
                    });
                });
                
            }
        }     
    }

    
    public static void fermer(GridPane pane){
        /* Clear le grid des proprietees*/
        pane.getChildren().clear();
    }
 
}
