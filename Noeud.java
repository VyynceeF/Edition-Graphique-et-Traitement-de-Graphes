package editeur;

import javafx.scene.text.*;
import java.awt.ScrollPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;



public class Noeud {

    /** Nom du Noeud */
    public final String NOM;
    
    /** Representation du noeud par un cercle */
    public Circle c ;
    
    
    /** Nom du Noeud à créer */
    Text nomText;
    /**
     * Nom du Noeud de la forme "Nx", 
     * avec x qui commence à 0 et qui est incrémenté de 1
     * à chaque crétion de Noeud 
     */
    public static int noNoeud = 0;

    /** Graphe dans lequel le Noeud est créé */
    public Graphe g  ;

    /** Position du noeud dans la zone de dessin */
    public Point position;

    /** Rayon du cercle representant le noeud */ 
    public static final double RAYON = 50;

    /**
     * Créer un Noeud avec comme information relative son nom et sa position (x, y)
     * nomIncrementable est incrémenté de 1
     * La position est créée à l'aide de la classe Point
     * @param x  l'abscice du noeud
     * @param y  l'ordonnée du noeud 
     */
    public Noeud(double x, double y, Graphe g ) {
        this.g = g;
        position = new Point(x,y);
        NOM = "N" + noNoeud;
        noNoeud++;
    }
    
    	
    
    /** 
     * Dessine le Noeud sur la zone de dessin, 
     * appelant la méthode Circle de l'Editeur graphique.
     * @param zoneDessin 
     */
    public Circle dessiner(AnchorPane zoneDessin) {
        /* Creation du cercle avec sa possition en (x,y) et son rayon sur le graphe*/
        c = new Circle();
        c.setCenterX(position.x);
        c.setCenterY(position.y);
        c.setRadius(RAYON);
        c.setFill(Color.WHITE);
        c.setStroke(Color.BLACK);
        zoneDessin.getChildren().remove(c);
        zoneDessin.getChildren().add(c);
        
        c.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Cercle cliqué : " +NOM );
                if(g.source == null) {
                    g.source = Noeud.this;
                } else {
                    g.destinataire = Noeud.this;
                }
            }
        });
        
        nomText = new Text(position.x,position.y, this.NOM);
        nomText.setFont(new Font(12));
        
        nomText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Nom du noeud cliqué : " +NOM );
                if(g.source == null) {
                    g.source = Noeud.this;
                } else {
                    g.destinataire = Noeud.this;
                }
            }
        });
        zoneDessin.getChildren().add(nomText);
        return c; 
        
    }
    
    

}
