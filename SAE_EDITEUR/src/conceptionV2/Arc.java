/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;

/**
 *
 * @author amine.daamouch
 */
public class Arc extends Lien{
    
  
    public Arc(Noeud source,Noeud destinataire){
        super(source,destinataire);
    }
    
    
    
   
    @Override
    public void dessiner(AnchorPane zoneDessin){
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
        xPrimeSource = source.position.x + (destinataire.position.x - source.position.x) / distance * Noeud.RAYON;
        yPrimeSource = source.position.y + (destinataire.position.y - source.position.y) / distance * Noeud.RAYON;
        xPrimeDes = destinataire.position.x - (destinataire.position.x - source.position.x) / distance * Noeud.RAYON;
        yPrimeDes = destinataire.position.y - (destinataire.position.y - source.position.y) / distance * Noeud.RAYON;

        
        
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
        
        if ( xPrimeSource > xPrimeDes ) {
            angleFleche = Math.PI/6; 
        } else if ( xPrimeSource == xPrimeDes) {
            angleFleche = yPrimeSource > yPrimeDes ? Math.PI / 6 : 7 * Math.PI / 6; 
        } else {
            angleFleche = 7 * Math.PI / 6; 
        }
        double flecheLongueur = 20;
        
        /** Fleche coté Gauche */
        Line arrow1 = new Line();
        arrow1.setStartX(xPrimeDes);
        arrow1.setStartY(yPrimeDes);
        arrow1.setEndX(xPrimeDes + flecheLongueur * Math.cos(ligneAngle - angleFleche)  );
        arrow1.setEndY(yPrimeDes + flecheLongueur * Math.sin(ligneAngle - angleFleche)  );
        /** Fleche coté droit */
        Line arrow2 = new Line();
        arrow2.setStartX(xPrimeDes);
        arrow2.setStartY(yPrimeDes);
        arrow2.setEndX(xPrimeDes + flecheLongueur * Math.cos(ligneAngle + angleFleche));
        arrow2.setEndY(yPrimeDes + flecheLongueur * Math.sin(ligneAngle + angleFleche));
        
        quadCurve.setControlX(xControl); 
        quadCurve.setControlY(yControl);
        
        System.out.println(xPrimeSource + "   " + xPrimeDes+ "    " +  xControl + "   " + yControl );
        System.out.println(xVectOrtho + "   " + yVectOrtho);
        quadCurve.setFill(null);
        quadCurve.setStroke(Color.BLACK);
        //Creating a Group object  
        Group groupeFleche = new Group(quadCurve);
        zoneDessin.getChildren().addAll(arrow1,arrow2, groupeFleche); 
    }

}
