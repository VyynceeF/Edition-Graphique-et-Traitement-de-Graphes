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
        
        
        double distance;
        
        distance = Math.sqrt(Math.pow(destinataire.position.x - source.position.x, 2) + Math.pow(destinataire.position.y - source.position.y, 2));
        xPrimeSource = source.position.x + (destinataire.position.x - source.position.x) / distance * Noeud.RAYON;
        yPrimeSource = source.position.y + (destinataire.position.y - source.position.y) / distance * Noeud.RAYON;
        xPrimeDes = destinataire.position.x - (destinataire.position.x - source.position.x) / distance * Noeud.RAYON;
        yPrimeDes = destinataire.position.y - (destinataire.position.y - source.position.y) / distance * Noeud.RAYON;

        double pente = (yPrimeSource - yPrimeDes)/ (xPrimeSource- xPrimeDes);
        double ligneAngle = Math.atan(pente);
        double angleFleche = xPrimeSource > xPrimeDes ? Math.toRadians(45) : -Math.toRadians(225);
        double flecheLongueur =  20;
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
        
        
        QuadCurve quadCurve = new QuadCurve();
        quadCurve.setStartX(xPrimeSource); 
        quadCurve.setStartY(yPrimeSource); 
        quadCurve.setEndX(xPrimeDes); 
        quadCurve.setEndY(yPrimeDes);
        
        xControl = yPrimeSource > yPrimeDes ? (xPrimeSource + xPrimeDes) / 2 + 50 : (xPrimeSource + xPrimeDes) / 2 - 50;
        yControl = xPrimeSource > xPrimeDes ? (yPrimeSource + yPrimeDes) / 2 + 50 : (yPrimeSource + yPrimeDes) / 2 - 50;
        
        quadCurve.setControlX(xControl); 
        quadCurve.setControlY(yControl);
        quadCurve.setFill(null);
        quadCurve.setStroke(Color.BLACK);
        //Creating a Group object  
        Group groupeFleche = new Group(quadCurve);
        zoneDessin.getChildren().addAll(arrow1,arrow2, groupeFleche); 
    }

}
