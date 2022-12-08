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
public class Arete extends Lien {
    
    
    public Arete(Noeud source , Noeud destinataire){
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
//        
//        
//        if(source.position.x < destinataire.position.x ){
//            
//            xDist = destinataire.position.x - source.position.x;
//            yDist = destinataire.position.y - source.position.y;
//            angle = 1 / Math.tan(xDist / yDist) ; 
//            xPrimeSource = source.position.x + Noeud.RAYON * Math.cos(angle);
//            yPrimeSource = source.position.y + Noeud.RAYON * Math.sin(angle); 
//            xPrimeDes = destinataire.position.x - Noeud.RAYON * Math.cos(90 - angle);
//            yPrimeDes = destinataire.position.y - Noeud.RAYON * Math.sin(90 - angle);
//        } else {
//            xDist = destinataire.position.x + source.position.x;
//            yDist = destinataire.position.y + source.position.y;
//            angle = 1 / Math.tan(xDist / yDist) ; 
//            xPrimeSource = source.position.x - Noeud.RAYON * Math.cos(angle);
//            yPrimeSource = source.position.y - Noeud.RAYON * Math.sin(angle); 
//            xPrimeDes = destinataire.position.x + Noeud.RAYON * Math.cos(90 - angle);
//            yPrimeDes = destinataire.position.y + Noeud.RAYON * Math.sin(90 - angle);
//        }
        
        zoneDessin.getChildren().add(new Line(xPrimeSource, 
                                              yPrimeSource, 
                                              xPrimeDes,
                                              yPrimeDes
                                              ));
    }
}
