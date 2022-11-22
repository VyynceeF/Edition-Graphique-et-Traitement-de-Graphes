package editeur;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;





public  class Lien {

    /** Noeud source de l'arête */
    public Noeud source ;
    
    /** Ligne reliant les deux noeuds entre eux */
    Line l ;
    
    /** Noeud destianataire de l'arête */
    public Noeud destinataire ;

    /** Graphe dans lequel l'arête est créé */
    public Graphe g; // TODO

    /**
     * Créer une arete avec pour source le Noeud source 
     * et pour destinataire le Noeud destinataire.
     * @param source noeud source de l'arête
     * @param destinataire noeud destinataire de l'arête
     */
    public Lien(Noeud source, Noeud destinataire, Graphe g ) {
        this.g = g ;
        this.source = source;
        this.destinataire = destinataire;
    }
    
    /**
     * Dessine un lien entre deux noeuds distincts
     */
    public Line dessiner(AnchorPane zoneDessin) {
    	l = new Line();
        l.setStartX(source.position.x);
        l.setStartY(source.position.y);
        l.setEndX(destinataire.position.x);
        l.setEndY(destinataire.position.y);
        l.setStroke(Color.BLACK);
        zoneDessin.getChildren().add(l);
       
        source.dessiner(zoneDessin);
        destinataire.dessiner(zoneDessin);
         return l;
    }

}
