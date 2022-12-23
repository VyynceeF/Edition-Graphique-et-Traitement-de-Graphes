/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

import java.util.ArrayList;

/**
 *
 * @author amine.daamouch
 */
public class NoeudGrapheProbabiliste extends Noeud {
    
    /** Liste des successeurs  */
    ArrayList<ArcProbabiliste> successeurs ;
    
    public NoeudGrapheProbabiliste(double x, double y) {
        super(x, y);
        successeurs = new ArrayList<>();
    }
    
    public NoeudGrapheProbabiliste(String nom, double x, double y) {
        super(x, y, nom);
        successeurs = new ArrayList<>();
    }
}
