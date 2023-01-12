package saeEditeur.graphe.noeud;

import saeEditeur.graphe.typegraphe.Graphe;
import java.io.Serializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import static saeEditeur.graphe.noeud.Noeud.RAYON;



/**
 *
 * @author amine.daamouch
 */
public class NoeudGrapheNonOriente extends Noeud {
    
    public NoeudGrapheNonOriente(double x, double y, Graphe g){
        super(x,y,g);
    }

    public NoeudGrapheNonOriente() {
    }
    
    @Override
    public Circle dessiner(AnchorPane zoneDessin) {    
        if (c != null) {
            zoneDessin.getChildren().remove(c);
            zoneDessin.getChildren().remove(nomText);
        }
        c = new Circle();
        c.setCenterX(position.x);
        c.setCenterY(position.y);
        c.setRadius(RAYON);
        c.setFill(Color.WHITE);
        c.setStroke(Color.BLACK);
        c.setStrokeWidth(1);
        zoneDessin.getChildren().add(c);
        
        // Nom du noeud
        nomText = new Text(position.x,position.y,this.nom);
        nomText.setFont(new Font(12));
        
        nomText.setBoundsType(TextBoundsType.VISUAL);
        nomText.setX(position.x - (nomText.getLayoutBounds().getWidth() / 2));
        
        
        zoneDessin.getChildren().add(nomText);
        return c;
    }
    
    @Override
    public void supprimer(AnchorPane zoneDessin) {
        
        /* Suppression de tous les liens */
        
        // Suppression des liens successeurs
        while (successeurs.size() != 0) {
            
            successeurs.get(0).supprimer(zoneDessin);
        }
        // Suppression des liens predecesseurs
        while (predecesseurs.size() != 0) {
            
            predecesseurs.get(0).supprimer(zoneDessin);
        }
        zoneDessin.getChildren().removeAll(c, nomText);
    }
}
