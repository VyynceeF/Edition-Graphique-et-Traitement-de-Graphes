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
    
 
    
    
    
    public double[][] matriceTransition(){
        double [][] tab = new double[g.noeuds.size()][g.noeuds.size()]; 
        for(int i = 0 ; i < g.noeuds.size() ; i++){
            for(int y = 0 ; y < g.noeuds.size(); y++){
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
     public boolean estChemin(ArrayList<NoeudGrapheProbabiliste> listeVisitees,NoeudGrapheProbabiliste source , NoeudGrapheProbabiliste destinataire){   
     
        for (int i = 0; i < source.successeurs.size() ; i++) {
            if(!listeVisitees.contains(source.successeurs.get(i)) ){
                /* cas où le chemin est minimun de poids 1 */
                if(source.successeurs.get(i).source == destinataire){
                    return true;   
                }
                listeVisitees.add((NoeudGrapheProbabiliste) source.successeurs.get(i).source);
                /* cas d'une boucle */
                if(source.successeurs.get(i).source == source && source.successeurs.size() == 1 ){
                    return false;
                }
                /* Cas où le chemin est minimun de poids 2 */
                if(estChemin(listeVisitees, (NoeudGrapheProbabiliste) source.successeurs.get(i).source, destinataire)){
                    return true;
                }
  
            }
        }
        /* Arrivé , aucun successeur trouvé pour aller du noeud source au destinataire*/ 
        return false;
    }
    
    public void afficherMatrice(){
        double[][] tab = this.matriceTransition();
        for (int i = 0; i < tab.length ; i++) {
            for (int j = 0; j < tab.length ; j++) {
                 System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
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
