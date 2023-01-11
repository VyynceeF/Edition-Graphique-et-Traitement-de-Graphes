/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saeEditeur;

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
import javafx.scene.shape.Circle;
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
    private Line arrow1 = null;
    
    /** Extremite de la fleche */
    private Line arrow2 = null;
    
    /** Ligne (courbee) de la fleche */
    private QuadCurve quadCurve = null;
    
    public ArcProbabiliste(Noeud source , Noeud destinataire, Graphe g){
        super(source,destinataire,g);
        coefficient = 0 ;
    }
    
    public ArcProbabiliste() {
        
    }
    
    public ArcProbabiliste(Noeud source, Noeud destinataire, double valeur, Graphe g) throws ArcProbabilisteException {
        super(source, destinataire, g);
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
        
        if (quadCurve != null) {
            
            zoneDessin.getChildren().remove(quadCurve);
            zoneDessin.getChildren().remove(arrow1);
            zoneDessin.getChildren().remove(arrow2);
            zoneDessin.getChildren().remove(valeur);
        }
        
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
            yPrimeSource = source.position.y - Noeud.RAYON; 
            xPrimeDes = source.position.x - Noeud.RAYON * Math.sqrt(2)/2;
            yPrimeDes = source.position.y - Noeud.RAYON * Math.sqrt(2)/2;
            quadCurve.setStartX(xPrimeSource);
            quadCurve.setStartY(yPrimeSource);
            quadCurve.setEndX(xPrimeDes);
            quadCurve.setEndY(yPrimeDes);
            multiplicateurPointControl = 70; 
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
        
        zoneDessin.getChildren().addAll(arrow1,arrow2, quadCurve); 
        
        
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
                // Modification (utilisé pour la vérification d'un graphe)
                this.coefficient = Double.valueOf(valeur.getText());
            }
        });
        
        
        
        valeur.relocate(xControl, yControl);
        valeur.setBackground(Background.EMPTY);
        valeur.setFont(new Font(12));     
        zoneDessin.getChildren().add(valeur);
    }
    
    /**
     * Predicat verifiant si le point (x, y) est sur le lien)
     * @param x Abscisse du point a tester
     * @param y Ordonnee du point a tester
     * @return Le lien s'il existe un lien sur la position (x, y), false sinon
     */
    @Override
    public boolean estClique(double x, double y) {
        
        return quadCurve.intersects(x, y, 1, 1);
    }
    
    @Override
    public void supprimer(AnchorPane zoneDessin) {
        
        source.successeurs.remove(this);
        destinataire.predecesseurs.remove(this);
        zoneDessin.getChildren().removeAll(quadCurve, arrow1, arrow2, valeur);
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
        return quadCurve.intersects(x,y,1,1);
    }
    
    /**
     * Augmente l'epaisseur du lien
     */
    public void lienSelectionne(AnchorPane zoneDessin) {
        
        pointDepart = new Circle(quadCurve.getStartX(), quadCurve.getStartY(), 5);
        pointArrive = new Circle(quadCurve.getEndX(), quadCurve.getEndY(), 5);
        zoneDessin.getChildren().addAll(pointDepart, pointArrive);
        quadCurve.setStrokeWidth(3);
        arrow1.setStrokeWidth(3);
        arrow2.setStrokeWidth(3);
    }
    
    /**
     * Augmente l'epaisseur du lien
     */
    @Override
    public void lienDeselectionne(AnchorPane zoneDessin) {
        
        quadCurve.setStrokeWidth(1);
        arrow1.setStrokeWidth(1);
        arrow2.setStrokeWidth(1);
        zoneDessin.getChildren().removeAll(pointDepart, pointArrive);
    }
    
    @Override
    public int estExtremite(double x, double y) {
        
        if (Math.sqrt((x - quadCurve.getStartX())*(x - quadCurve.getStartX())
                      + (y - quadCurve.getStartY())*(y - quadCurve.getStartY())) <= 5) {
            
            return 1;
        } else if (Math.sqrt((x - quadCurve.getEndX())*(x - quadCurve.getEndX())
                             + (y - quadCurve.getEndY())*(y - quadCurve.getEndY())) <= 5) {
            
            return 2;
        }
        return 0;
    }
    
    /**
     * Permet de deplacer l'extremite en position x, y
     * @param x Nouvelle abscisse
     * @param y Nouvelle ordonnee
     * @param extremite 1 -> Premiere extremite | 2 -> Derniere extremite
     * @param zoneDessin Zone de dessin
     */
    public void modifierPosition(double x, double y, int extremite,AnchorPane zoneDessin){ 
        
        
        if (extremite == 1) {
                      
            quadCurve.setStartX(x);
            quadCurve.setStartY(y);
            pointDepart.setCenterX(x);
            pointDepart.setCenterY(y);
            zoneDessin.getChildren().remove(valeur);
            
            
        } else if (extremite == 2) {
                      
            quadCurve.setEndX(x);
            quadCurve.setEndY(y);
            pointArrive.setCenterX(x);
            pointArrive.setCenterY(y);
            arrow1.setVisible(false);            
            arrow2.setVisible(false);
            valeur.setVisible(false); 
            
        }
        
    }
    
    @Override
    public void remiseDefaut() {
        
        double distance = Math.sqrt(Math.pow(destinataire.position.x - source.position.x, 2) + Math.pow(destinataire.position.y - source.position.y, 2));
        double xPrimeSource = source.position.x + (destinataire.position.x - source.position.x) / distance * Noeud.RAYON;
        double yPrimeSource = source.position.y + (destinataire.position.y - source.position.y) / distance * Noeud.RAYON;
        double xPrimeDes = destinataire.position.x - (destinataire.position.x - source.position.x) / distance * Noeud.RAYON;
        double yPrimeDes = destinataire.position.y - (destinataire.position.y - source.position.y) / distance * Noeud.RAYON;
        quadCurve.setStartX(xPrimeSource);
        quadCurve.setStartY(yPrimeSource);
        quadCurve.setEndX(xPrimeDes);
        quadCurve.setEndY(yPrimeDes);
        arrow1.setVisible(true);            
        arrow2.setVisible(true);
        valeur.setVisible(true); 
        
    }
    
    @Override
    public void changementExtremite(Noeud nouveauNoeud, int extremite, AnchorPane zoneDessin) {
        
        if (extremite == 1) {
        /* Noeud source */
                      
            // Le noeud source n'a plus ce lien comme predecesseur
            source.successeurs.remove(this);
            
            // Modification de l'extremite du lien
            source = nouveauNoeud;
            source.successeurs.add(this);
            
            // On redessine correctement le lien
            this.dessiner(zoneDessin);
            
        } else if (extremite == 2) {
        /* Noeud destinataire */
                      
            // Le noeud source n'a plus ce lien comme predecesseur
            destinataire.predecesseurs.remove(this);
            
            // Modification de l'extremite du lien
            destinataire = nouveauNoeud;
            destinataire.predecesseurs.add(this);
            
            // On redessine correctement le lien
            this.dessiner(zoneDessin);
        }
    }
    
}
