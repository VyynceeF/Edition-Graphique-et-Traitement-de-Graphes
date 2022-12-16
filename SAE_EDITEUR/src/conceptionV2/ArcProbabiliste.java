/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

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
    
    double coefficient;
    
    TextField valeur;
    
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
    public void dessiner(AnchorPane zoneDessin) {
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
        
        distance = Math.sqrt(Math.pow(destinataire.position.x - source.position.x, 2) + Math.pow(destinataire.position.y - source.position.y, 2));
        xPrimeSource = source.position.x + (destinataire.position.x - source.position.x) / (distance + EPSILON) * Noeud.RAYON;
        yPrimeSource = source.position.y + (destinataire.position.y - source.position.y) / (distance+ EPSILON) * Noeud.RAYON;
        xPrimeDes = destinataire.position.x - (destinataire.position.x - source.position.x) / (distance+ EPSILON) * Noeud.RAYON;
        yPrimeDes = destinataire.position.y - (destinataire.position.y - source.position.y) / (distance + EPSILON) * Noeud.RAYON;

        
        
        QuadCurve quadCurve = new QuadCurve();
        quadCurve.setStartX(xPrimeSource); 
        quadCurve.setStartY(yPrimeSource); 
        quadCurve.setEndX(xPrimeDes); 
        quadCurve.setEndY(yPrimeDes);
        
        xVectDirect = xPrimeDes - xPrimeSource; 
        yVectDirect = yPrimeDes - yPrimeSource; 
        
        xVectOrtho = - yVectDirect * (1 / Math.sqrt(yVectDirect * yVectDirect + xVectDirect * xVectDirect));
        yVectOrtho =   xVectDirect* (1 / Math.sqrt(yVectDirect * yVectDirect + xVectDirect * xVectDirect));
        
        xControl = xVectOrtho * 80 + (xPrimeSource + xPrimeDes) / 2; 
        yControl = yVectOrtho * 80 + (yPrimeSource + yPrimeDes) / 2;
        
        double pente = (yControl  - yPrimeDes)/ (xControl - xPrimeDes);
        double ligneAngle = Math.atan(pente);
        double angleFleche = 0;
        
        if ( xPrimeSource > xPrimeDes) {
            angleFleche = Math.PI/8; 
        } else if ( xPrimeSource == xPrimeDes) {
            angleFleche = yPrimeSource > yPrimeDes ? Math.PI / 8 : 9 * Math.PI / 8; 
        } else {
            angleFleche = 9 * Math.PI / 8; 
        }
        double flecheLongueur = 20;
        
        // dans le cas de la boucle 
        if (distance == 0.0){
            yPrimeSource = source.position.y + Noeud.RAYON; 
            xPrimeSource = source.position.x; 
            
        }
        
        
        /** Fleche coté Gauche */
        Line arrow1 = new Line();
        arrow1.setStartX(xPrimeDes);
        arrow1.setStartY(yPrimeDes);
        arrow1.setEndX(xPrimeDes + flecheLongueur * Math.cos(ligneAngle - angleFleche));
        arrow1.setEndY(yPrimeDes + flecheLongueur * Math.sin(ligneAngle - angleFleche));
        /** Fleche coté droit */
        Line arrow2 = new Line();
        arrow2.setStartX(xPrimeDes);
        arrow2.setStartY(yPrimeDes);
        arrow2.setEndX(xPrimeDes + flecheLongueur * Math.cos(ligneAngle + angleFleche));
        arrow2.setEndY(yPrimeDes + flecheLongueur * Math.sin(ligneAngle + angleFleche));
        
        quadCurve.setControlX(xControl); 
        quadCurve.setControlY(yControl);
        
        
        valeur = new TextField(coeff);
        valeur.relocate(xControl, yControl );
        valeur.setBackground(Background.EMPTY);
        valeur.setFont(new Font(12));     
        
        quadCurve.setFill(null);
        quadCurve.setStroke(Color.BLACK);
        //Creating a Group object  
        Group groupeFleche = new Group(quadCurve);
        zoneDessin.getChildren().addAll(arrow1,arrow2, groupeFleche,valeur); 
    }
    
    
    
}
