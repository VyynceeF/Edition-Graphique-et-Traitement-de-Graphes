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
 * @author jules.blanchard
 * @author romain.courbaize
 */
public class Arete extends Lien {
    
    
    public Arete(Noeud source , Noeud destinataire){
        super(source,destinataire);
    }
    
    
    @Override
    public void dessiner(AnchorPane zoneDessin){
        
        double xPrimeSource;
        double yPrimeSource;
        double xPrimeDes;
        double yPrimeDes;
        
        double distance;
        
        distance = Math.sqrt(Math.pow(destinataire.position.x - source.position.x, 2) + Math.pow(destinataire.position.y - source.position.y, 2));
        xPrimeSource = source.position.x + (destinataire.position.x - source.position.x) / distance * Noeud.RAYON;
        yPrimeSource = source.position.y + (destinataire.position.y - source.position.y) / distance * Noeud.RAYON;
        xPrimeDes = destinataire.position.x - (destinataire.position.x - source.position.x) / distance * Noeud.RAYON;
        yPrimeDes = destinataire.position.y - (destinataire.position.y - source.position.y) / distance * Noeud.RAYON;
        
        zoneDessin.getChildren().add(new Line(xPrimeSource, 
                                              yPrimeSource, 
                                              xPrimeDes,
                                              yPrimeDes
                                              ));
    }
}
