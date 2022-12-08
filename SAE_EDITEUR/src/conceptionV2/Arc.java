/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

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
        
        /* distance en x des deux noeuds*/
        double xDist ; 
        /* distance en y des deux noeuds*/
        double yDist ; 
        double xPrimeSource;
        double yPrimeSource;
        double xPrimeDes;
        double yPrimeDes;
        double angle;
        
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
        zoneDessin.getChildren().addAll(new Line(xPrimeSource, 
                                              yPrimeSource, 
                                              xPrimeDes,
                                              yPrimeDes
                                              ), arrow1,arrow2); 
    }

 
}
