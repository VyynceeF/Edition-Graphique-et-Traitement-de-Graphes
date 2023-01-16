/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * NoeudGrapheOriente.java        16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe.noeud;

import saeEditeur.graphe.typegraphe.Graphe;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import saeEditeur.Point;
import static saeEditeur.graphe.noeud.Noeud.RAYON;

/**
 * Représentation d'un noeud pour un graphe orienté
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public class NoeudGrapheOriente extends Noeud {

    public NoeudGrapheOriente(double x, double y, Graphe g) {
        super(x,y,g);
    }
    
    public NoeudGrapheOriente() {
        
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Text getNomText() {
        return nomText;
    }

    public void setNomText(Text nomText) {
        this.nomText = nomText;
    }

    public Circle getC() {
        return c;
    }

    public void setC(Circle c) {
        this.c = c;
    }

    public static int getNoNoeud() {
        return noNoeud;
    }

    public static void setNoNoeud(int noNoeud) {
        Noeud.noNoeud = noNoeud;
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
