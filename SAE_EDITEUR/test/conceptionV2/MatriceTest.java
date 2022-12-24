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
public class MatriceTest {
    
    public static void main(String[] args) throws NoeudException, LienException{
        GrapheProbabiliste g = new GrapheProbabiliste();
        GrapheProbabiliste g2 = new GrapheProbabiliste();
        NoeudGrapheProbabiliste n1 = new NoeudGrapheProbabiliste(0,1);
        NoeudGrapheProbabiliste n2 = new NoeudGrapheProbabiliste(0,1);
        NoeudGrapheProbabiliste n3 = new NoeudGrapheProbabiliste(0,1);

        g2.ajouterNoeud(n1);
        g2.ajouterNoeud(n2);
        g2.ajouterNoeud(n3);
        
//        ArcProbabiliste l1 = new ArcProbabiliste(n1, n3, 1);
//        ArcProbabiliste l2 = new ArcProbabiliste(n2,n3 , 1);
//        ArcProbabiliste l3 = new ArcProbabiliste(n3, n2, 1);
        
//        g2.ajouterLien(l1);
//        g2.ajouterLien(l2);    
//        g2.ajouterLien(l3);
        
        NoeudGrapheProbabiliste s1 = new NoeudGrapheProbabiliste("s1", 0,1);
        NoeudGrapheProbabiliste s2 = new NoeudGrapheProbabiliste("s2", 0,2);
        NoeudGrapheProbabiliste s3 = new NoeudGrapheProbabiliste("s3", 0,3);
        NoeudGrapheProbabiliste s4 = new NoeudGrapheProbabiliste("s4", 0,4);
        NoeudGrapheProbabiliste s5 = new NoeudGrapheProbabiliste("s5", 0,1);
    
        g.ajouterNoeud(s1);
        g.ajouterNoeud(s2);
        g.ajouterNoeud(s3);
        g.ajouterNoeud(s4);
        g.ajouterNoeud(s5);
        
        
        
        ArcProbabiliste l1 = new ArcProbabiliste(s1, s2, 0.05);
        ArcProbabiliste l2 = new ArcProbabiliste(s1, s3, 0.95);
        ArcProbabiliste l3 = new ArcProbabiliste(s2, s2, 1);
        ArcProbabiliste l4 = new ArcProbabiliste(s3, s2, 0.8);
        ArcProbabiliste l5 = new ArcProbabiliste(s3, s4, 0.2);
        ArcProbabiliste l6 = new ArcProbabiliste(s4, s1, 0.65);
        ArcProbabiliste l7 = new ArcProbabiliste(s4, s5, 0.35);
        ArcProbabiliste l8 = new ArcProbabiliste(s5, s5, 1.0);
        
        g.ajouterLien(l1);
        g.ajouterLien(l2);
        g.ajouterLien(l3);
        g.ajouterLien(l4);
        g.ajouterLien(l5);
        g.ajouterLien(l6);
        g.ajouterLien(l7);
        g.ajouterLien(l8);
        
        Matrice m = new Matrice(g);
        Matrice m2 = new Matrice(g2);
        
        m.afficherMatrice();
        
        System.out.println(m.estChemin(new ArrayList<NoeudGrapheProbabiliste>(), s1, s4));
        System.out.println(m.regroupementClasse());
    }   
    
    
}