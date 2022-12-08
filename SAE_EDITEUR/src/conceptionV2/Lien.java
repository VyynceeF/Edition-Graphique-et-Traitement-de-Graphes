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
public abstract class Lien {
    
    /** premier noeud partant du lien */
    Noeud source ;
     /** deuxieme noeud partant du lien */
    Noeud destinataire;
    
    
    public Lien(Noeud source , Noeud destinataire){
        this.source = source;
        this.destinataire= destinataire;
    }
    
   
  
    public abstract void dessiner(AnchorPane zoneDessin);
        
        
       
}
