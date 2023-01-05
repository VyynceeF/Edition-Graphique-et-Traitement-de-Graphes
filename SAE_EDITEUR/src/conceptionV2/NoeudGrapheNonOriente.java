package conceptionV2;

import java.io.Serializable;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;



/**
 *
 * @author amine.daamouch
 */
public class NoeudGrapheNonOriente extends Noeud {
    
    public NoeudGrapheNonOriente(double x, double y){
        super(x,y);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Text getNomText() {
        return nomText;
    }

    public void setNomText(Text nomText) {
        this.nomText = nomText;
    }

    public Circle getC() {
        return c;
    }

    public void setC(Circle c) {
        this.c = c;
    }

    public static int getNoNoeud() {
        return noNoeud;
    }

    public static void setNoNoeud(int noNoeud) {
        Noeud.noNoeud = noNoeud;
    }
    
    
    
}
