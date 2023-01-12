/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur.graphe;

import saeEditeur.graphe.propriete.Propriete;
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
     * @param zoneDessin La zone de dessin
     * @param g Graphe
     */
    public static void afficher(ElementGraphe e, GridPane pane, AnchorPane zoneDessin){

        Propriete[] proprietees = e.getPropriete();//Recuperation des proprietees de e
        
        //Creation du bouton valider invisible
        Button valider = new Button("Valider"); //Creer
        pane.add(valider,0,proprietees.length + 1); //Ajout au pane
        pane.setColumnSpan(valider,2); //Fusion colonne
        pane.setHalignment(valider, HPos.CENTER); //Alignement horizontal au centre
        valider.setVisible(false); //Invisible
        
        //Gap gridpane
        pane.setVgap(25.0);
        
        //Tableau fieldText
        TextField[] tabField = new TextField[proprietees.length];
        
        //Tableau Choice box
        ChoiceBox[] tabBox = new ChoiceBox[proprietees.length];

        //On boucle sur les proprietees de e
        for (int i = 0; i < proprietees.length; i++) {

            Propriete propriete = proprietees[i]; //On stocke la proprietee i

            Text nom = new Text(propriete.nom + " :"); //Text du nom de la propriete
            pane.add(nom,0,i); //Ajout du Text au pane

            if(!propriete.saisieDansListe){ //Si on veut un TextField
                
                //Gestion du tableau ChoiceBox
                tabBox[i] = null;

                TextField field = new TextField(); //Creation du textField pour la propriete i
                pane.add(field,1,i); //Ajout au pane
                
                propriete.setObject(field); //Appel a la fonction setObject

                tabField[i] = field; //Ajout du TextField au Tableau de TextField
                field.setId("field" + i); //Redifini l'Id du TextField
                
                //Ecoute du TextField
                field.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                
                    valider.setVisible(true);//Afficher bouton valider

                    //Action bouton valider
                    valider.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            
                            Propriete propriete; //Stockage des proprietees
                            
                            //Action sur les ChoiceBox 
                            ChoiceBox box;
                            for(int i = 0; i < tabBox.length; i++){
                                box = tabBox[i];
                                if(box != null){
                                    propriete = proprietees[i];
                                    propriete.setValeur((String) box.getValue(), box, zoneDessin);
                                }
                            }
                            
                            //Action sur les TextField
                            TextField field;
                            for(int i = 0; i < tabField.length; i++){
                                field = tabField[i];
                                if(field != null){
                                    propriete = proprietees[i];
                                    propriete.setValeur(field.getText(), field, zoneDessin);
                                }
                            }
                            //Faire disparaitre le bouton valider apres le clic
                            valider.setVisible(false);
                            proprietees[0].elementGraphe.g.deselectionnerAll(zoneDessin);
                            fermer(pane);
                        }
                    });
                });
                
            }else{ //Si on veut une ChoiceBox
                
                //Gestion de tabField
                tabField[i] = null;
                
                //Ajout de la choice box dans propriete
                ChoiceBox box = new ChoiceBox();
                pane.add(box,1,i);
                
                //Ajout des item dans la ChoiceBox
                for(int j = 0; j < propriete.getListeChoix().length; j++){
                    box.getItems().add(propriete.getListeChoix()[j]);
                }
                
                propriete.setObject(box); //Appel à setObject

                tabBox[i] = box; //Ajout de la ChoiceBox dans le tableau de ChoiceBox
                box.setId("box" + i); // on set l'id du ChoiceBox
                
                //Action chamgement sur une choice box
                box.setOnAction(event -> {
                
                    valider.setVisible(true);//Afficher bouton

                    //Action bouton valider
                    valider.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            
                            Propriete propriete;//Stockage de la propriete
                            
                            //Action sur les TextField
                            TextField field;
                            for(int i = 0; i < tabField.length; i++){
                                field = tabField[i];
                                if(field != null){
                                    propriete = proprietees[i];
                                    propriete.setValeur(field.getText(), field, zoneDessin);
                                }
                            }
                            
                            //Action sur les ChoiceBox
                            ChoiceBox box;
                            for(int i = 0; i < tabBox.length; i++){
                                box = tabBox[i];
                                if(box != null){
                                    propriete = proprietees[i];
                                    propriete.setValeur((String) box.getValue(), box, zoneDessin);
                                }
                            }
                            //Faire disparaitre le bouton
                            valider.setVisible(false);
                            proprietees[0].elementGraphe.g.deselectionnerAll(zoneDessin);
                            fermer(pane);
                        }
                    });
                });
                
            }
        }     
    }

    /**
     * Clear le pane
     * @param pane à clear
     */
    public static void fermer(GridPane pane){
        /* Clear le grid des proprietees*/
        pane.getChildren().clear();
    }
 
}
