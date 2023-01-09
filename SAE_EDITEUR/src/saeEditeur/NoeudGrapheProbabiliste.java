/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

/**
 *
 * @author amine.daamouch
 */
public class NoeudGrapheProbabiliste extends Noeud {
    
    /** Liste des successeurs  */
    ArrayList<ArcProbabiliste> successeursProbabilistes ;
    
    /** Nom de la classe */
    Text nomClasse;
    
    public NoeudGrapheProbabiliste(double x, double y) {
        super(x, y);
        successeursProbabilistes = new ArrayList<>();
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

    public ArrayList<ArcProbabiliste> getSuccesseursProbabilistes() {
        return successeursProbabilistes;
    }

    public void setSuccesseurs(ArrayList<ArcProbabiliste> successeurs) {
        this.successeursProbabilistes = successeursProbabilistes;
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
    
}
