/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

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
    
    public void ajouterNomClasse(Pane zoneDessin, String textNomClasse) {
        
        // Suppression du nom de la classe s'il existe deja
        if (nomClasse != null) {
            
            zoneDessin.getChildren().remove(nomClasse);
        }
        
        nomClasse = new Text(position.x, position.y + 15, textNomClasse);
        nomClasse.setFont(new Font(12));
        
        nomClasse.setBoundsType(TextBoundsType.VISUAL);
        nomClasse.setX(position.x - (nomText.getLayoutBounds().getWidth() / 2));
        
        zoneDessin.getChildren().add(nomClasse);
        
    }
    
    /**
     * Changement de la couleur de la bordure en vert 
     */
    public void changementCouleurTransitoire() {
        
        c.setStroke(Color.GREEN);
    }
    
    /**
     * Changement de la couleur de la bordure en bleu
     */
    public void changementCouleurErgodique() {
        
        c.setStroke(Color.BLUE);
    }
}
