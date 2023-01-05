/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

import java.io.Serializable;

/**
 *
 * @author amine.daamouch
 */
public class Point {

     /** l'axe des abscisses */ 
    public double x;

    /** l'axe des ordonées */ 
    public double y;

    
    /** Initialise les coordonnées du Point  */
    public Point(double x, double y) {
        this.x = x ;
        this.y = y ;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    
}
