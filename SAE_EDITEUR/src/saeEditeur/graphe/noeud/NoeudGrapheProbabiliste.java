/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur.graphe.noeud;

import saeEditeur.graphe.typegraphe.Graphe;
import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import saeEditeur.graphe.lien.Lien;
import saeEditeur.Point;
import static saeEditeur.graphe.noeud.Noeud.RAYON;

/**
 *
 * @author amine.daamouch
 */
public class NoeudGrapheProbabiliste extends Noeud {
    
    /** Nom de la classe */
    Text nomClasse = null;
    
    public NoeudGrapheProbabiliste(double x, double y, Graphe g) {
        super(x, y, g);
        nomClasse = null;
    }

    public ArrayList<Lien> getSuccesseurs() {
        return successeurs;
    }

    public ArrayList<Lien> getPredecesseurs() {
        return predecesseurs;
    }

    public void setPredecesseurs(ArrayList<Lien> predecesseurs) {
        this.predecesseurs = predecesseurs;
    }
    
    public NoeudGrapheProbabiliste() {
        
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
        
        if (nomClasse != null) {
            ajouterNomClasse(zoneDessin, nomClasse.getText());
        }
        return c;
    }
    
    public NoeudGrapheProbabiliste(String nom, double x, double y, Graphe g) {
        super(x, y, nom, g);
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

    public void setSuccesseurs(ArrayList<Lien> successeurs) {
        this.successeurs = successeurs;
    }

    public Text getNomClasse() {
        return nomClasse;
    }

    public void setNomClasse(Text nomClasse) {
        this.nomClasse = nomClasse;
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
    public void supprimer(AnchorPane zoneDessin) {
        
        // Suppression du nom de la classe s'il existe deja
        if (nomClasse != null) {
            
            zoneDessin.getChildren().remove(nomClasse);
        }
        
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
