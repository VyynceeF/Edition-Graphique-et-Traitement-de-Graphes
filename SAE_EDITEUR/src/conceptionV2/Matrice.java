/*
 * Cette classe permet de récuperer la matrice de transition d'un graphe probabiliste
 * et d'autres méthodes utilitaires.
 * 
 */
package conceptionV2;

import java.util.ArrayList;

/**
 *
 * @author amine.daamouch
 */
public class Matrice {
    
    
    public GrapheProbabiliste g;
    
    
    public Matrice(GrapheProbabiliste g){
        this.g = g ;
    }
    
 
    
    public void afficherMatrice(){
        if(g.liens.isEmpty()){
            System.out.println("La matrice est null");
        }else{
            for (int i = 0; i < g.liens.size() ;i++) {
                System.out.println(g.liens.get(i));
            }
        }
    }
    
    public double[][] matriceTransition(){
        double [][] tab = new double[g.noeuds.size()][g.noeuds.size()]; 
        for(int i = 0 ; i < g.noeuds.size() ; i++){
            for(int y = 0 ; i < g.noeuds.size(); i++){
                tab[i][y] = 0.0;
                for(Lien lien : g.liens){
                    ArcProbabiliste l = (ArcProbabiliste) lien;
                    if(g.noeuds.get(i) == lien.source && g.noeuds.get(y) == lien.destinataire){
                        tab[i][y] = l.coefficient;
                    }
                }        
            } 
        }     
        return tab;
    }
    
    
    public boolean estUnSuccesseur(Noeud n ){
        for(Lien l : g.liens){
             if(l.source == n)
                 return true;
        }
       return false;
    }
    public boolean estUnPredecesseur(Noeud n){
        for(Lien l : g.liens){
             if(l.destinataire == n)
                 return true;
        }
       return false;
    }
    
    public boolean estAbsorbant(Noeud n){
        for(Lien l : g.liens){
            if(l.destinataire == n ){
                for(Lien y : g.liens){
                    if(y.source != n || (n == y.source && n == y.destinataire)){
                        return true;
                    }
                }
                
                
            }
        }
        return false;
    }
    
    public static boolean estErgodique(Noeud n){
        return false; // A FAIRE
    }
    
    
    
    
}
