/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.text.Font;


/**
 *
 * @author amine.daamouch
 */
public class ArcProbabiliste extends Lien {
    
    
    final static double FLECHE_LONGUEUR = 20;
    
    double coefficient;
    
    TextField valeur;
    
    /** Extremite de la fleche */
    private Line arrow1;
    
    /** Extremite de la fleche */
    private Line arrow2;
    
    /** Ligne (courbee) de la fleche */
    private QuadCurve quadCurve;
    
    public ArcProbabiliste(Noeud source , Noeud destinataire){
        super(source,destinataire);
        coefficient = 0 ;
    }
    
    public ArcProbabiliste(Noeud source, Noeud destinataire, double valeur) throws ArcProbabilisteException {
        super(source, destinataire);
        if(valeur < 0 || valeur > 1  ){
            throw new ArcProbabilisteException("la probabilités de cette arc ne peut"
                    + "pas être supérieur à 1 ou inférieur à 0 ");
        }
        coefficient = valeur;
    }
    
    public void setCoeff(double val) throws ArcProbabilisteException{
        if(val <= 0 || val > 1  ){
             throw new ArcProbabilisteException("la probabilités de cette arc ne peut"
                    + "pas être supérieur à 1 ou inférieur ou égal à 0 ");
        }
        coefficient = val;
        valeur.setText(Double.toString(val)); 
    }
    
    /**
     *
     * @param zoneDessin
     */
    @Override
    public void dessiner(AnchorPane zoneDessin)  {
        String coeff = ""+coefficient;
        
        final double EPSILON = 10E-15;
        double xPrimeSource;
        double yPrimeSource;
        double xPrimeDes;
        double yPrimeDes;
        double xControl; 
        double yControl; 
        double xVectDirect;
        double yVectDirect; 
        double xVectOrtho; 
        double yVectOrtho;
        double distance;
        double longueurFleche; 
        int multiplicateurPointControl;
        
        quadCurve = new QuadCurve();

        
        distance = Math.sqrt(Math.pow(destinataire.position.x - source.position.x, 2) + Math.pow(destinataire.position.y - source.position.y, 2));
        
        if (distance != 0.0) {
            xPrimeSource = source.position.x + (destinataire.position.x - source.position.x) / (distance + EPSILON) * Noeud.RAYON;
            yPrimeSource = source.position.y + (destinataire.position.y - source.position.y) / (distance+ EPSILON) * Noeud.RAYON;
            xPrimeDes = destinataire.position.x - (destinataire.position.x - source.position.x) / (distance+ EPSILON) * Noeud.RAYON;
            yPrimeDes = destinataire.position.y - (destinataire.position.y - source.position.y) / (distance + EPSILON) * Noeud.RAYON;
            quadCurve.setStartX(xPrimeSource); 
            quadCurve.setStartY(yPrimeSource); 
            quadCurve.setEndX(xPrimeDes); 
            quadCurve.setEndY(yPrimeDes);
            multiplicateurPointControl = 80;
        } else {
            xPrimeSource = source.position.x;
            yPrimeSource = source.position.y + Noeud.RAYON; 
            xPrimeDes = source.position.x + Noeud.RAYON;
            yPrimeDes = source.position.y;
            quadCurve.setStartX(xPrimeSource);
            quadCurve.setStartY(yPrimeSource);
            quadCurve.setEndX(xPrimeDes);
            quadCurve.setEndY(yPrimeDes);
            multiplicateurPointControl = 180; 
        }

        xVectDirect = xPrimeDes - xPrimeSource; 
        yVectDirect = yPrimeDes - yPrimeSource; 

        xVectOrtho = - yVectDirect * (1 / Math.sqrt(yVectDirect * yVectDirect + xVectDirect * xVectDirect));
        yVectOrtho =   xVectDirect* (1 / Math.sqrt(yVectDirect * yVectDirect + xVectDirect * xVectDirect));

        xControl = xVectOrtho * multiplicateurPointControl + (xPrimeSource + xPrimeDes) / 2; 
        yControl = yVectOrtho * multiplicateurPointControl + (yPrimeSource + yPrimeDes) / 2;
        
        /* Longueur flèche*/
        longueurFleche = FLECHE_LONGUEUR / Math.sqrt(Math.pow(xPrimeDes - xControl, 2) + Math.pow(yPrimeDes - yControl, 2));
        
        /* Fleche coté Gauche */
        arrow1 = new Line();
        arrow1.setStartX(xPrimeDes);
        arrow1.setStartY(yPrimeDes);
        arrow1.setEndX(xPrimeDes + longueurFleche * ((xControl - xPrimeDes) * Math.cos(Math.PI/8)-(yControl - yPrimeDes) * Math.sin(Math.PI/8)));
        arrow1.setEndY(yPrimeDes + longueurFleche * ((xControl - xPrimeDes) * Math.sin(Math.PI/8)+(yControl - yPrimeDes) * Math.cos(Math.PI/8)));
        
        /** Fleche coté droit */
        arrow2 = new Line();
        arrow2.setStartX(xPrimeDes);
        arrow2.setStartY(yPrimeDes);
        arrow2.setEndX(xPrimeDes + longueurFleche * ((xControl - xPrimeDes) * Math.cos(-Math.PI/8)-(yControl - yPrimeDes) * Math.sin(-Math.PI/8)));
        arrow2.setEndY(yPrimeDes + longueurFleche * ((xControl - xPrimeDes) * Math.sin(-Math.PI/8)+(yControl - yPrimeDes) * Math.cos(-Math.PI/8)));
        
        quadCurve.setControlX(xControl); 
        quadCurve.setControlY(yControl);       
        quadCurve.setFill(null);
        quadCurve.setStroke(Color.BLACK);
        
        //Creating a Group object  
        Group groupeFleche = new Group(quadCurve);
        zoneDessin.getChildren().addAll(arrow1,arrow2, groupeFleche); 
        
        
        valeur = new TextField(coeff);
        // force the field to be numeric only
        valeur.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                Double.parseDouble(valeur.getText());
                
                // Ici traitement si c'est un float
                this.setCoeff(Double.valueOf(valeur.getText()));
                // Changement de couleur en noir 
                arrow1.setStroke(Color.BLACK);
                arrow2.setStroke(Color.BLACK);
                quadCurve.setStroke(Color.BLACK);
                
            } catch (NumberFormatException ex) {
                // Ici traitement si ce n'est pas un float
                System.err.println("Erreur sur la saisie de la probabilités");
                valeur.setText("0.0");
                             
            } catch (ArcProbabilisteException ex) {
                // Changement de couleur en rouge
                arrow1.setStroke(Color.RED);
                arrow2.setStroke(Color.RED);
                quadCurve.setStroke(Color.RED);
            }
        });
        
        
        
        valeur.relocate(xControl, yControl);
        System.out.println("X = " + xControl + "  Y = " + yControl);
        valeur.setBackground(Background.EMPTY);
        valeur.setFont(new Font(12));     
        zoneDessin.getChildren().add(valeur);
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public TextField getValeur() {
        return valeur;
    }

    public void setValeur(TextField valeur) {
        this.valeur = valeur;
    }

    public Line getArrow1() {
        return arrow1;
    }

    public void setArrow1(Line arrow1) {
        this.arrow1 = arrow1;
    }

    public Line getArrow2() {
        return arrow2;
    }

    public void setArrow2(Line arrow2) {
        this.arrow2 = arrow2;
    }

    public QuadCurve getQuadCurve() {
        return quadCurve;
    }

    public void setQuadCurve(QuadCurve quadCurve) {
        this.quadCurve = quadCurve;
    }

    public Noeud getSource() {
        return source;
    }

    public void setSource(Noeud source) {
        this.source = source;
    }

    public Noeud getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Noeud destinataire) {
        this.destinataire = destinataire;
    }
    
    /**
     * Predicat verifiant si le point (x, y) est sur le lien)
     * @param x Abscisse du point a tester
     * @param y Ordonnee du point a tester
     * @return Le lien s'il existe un lien sur la position (x, y), false sinon
     */
    @Override
    public boolean estClique(double x, double y) {
        return false;
    }
    
    /**
     * Augmente l'epaisseur du lien
     */
    public void lienSelectionne() {
        
        
    }
    
    /**
     * Augmente l'epaisseur du lien
     */
    @Override
    public void lienDeselectionne() {
        
    }
}
