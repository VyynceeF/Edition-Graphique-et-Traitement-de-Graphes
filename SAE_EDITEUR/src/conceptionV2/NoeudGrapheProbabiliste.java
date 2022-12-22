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
    ArrayList<NoeudGrapheProbabiliste> successeurs ;
    
    public NoeudGrapheProbabiliste(double x, double y) {
        super(x, y);
        successeurs = new ArrayList<>();
    }
    
    
}
