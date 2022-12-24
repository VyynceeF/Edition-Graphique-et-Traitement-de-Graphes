/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 *
 * @author amine.daamouch
 */
public class NoeudGrapheProbabiliste extends Noeud {
    
    /** Liste des successeurs  */
    ArrayList<ArcProbabiliste> successeurs ;
    
    /** Nom de la classe */
    Text nomClasse;
    
    public NoeudGrapheProbabiliste(double x, double y) {
        super(x, y);
        successeurs = new ArrayList<>();
        nomClasse = null;
    }
    
    public NoeudGrapheProbabiliste(String nom, double x, double y) {
        super(x, y, nom);
        successeurs = new ArrayList<>();
    }
    
    public void ajouterNomClasse(Pane pane) {
        
        // Suppression du nom de la classe s'il existe deja
        if (nomClasse != null) {
            
            pane.getChildren().remove(nomClasse);
        }
        
    }
}
