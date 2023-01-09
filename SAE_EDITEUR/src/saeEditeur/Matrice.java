/*
 * Cette classe permet de récuperer la matrice de transition d'un graphe probabiliste
 * et d'autres méthodes utilitaires.
 * 
 */
package saeEditeur;

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
            if(!listeVisitees.contains(source.successeurs.get(i).destinataire) ){
                /* cas où le chemin est minimun de poids 1 */
                if(source.successeurs.get(i).source == destinataire){
                    return true;   
                }
                listeVisitees.add((NoeudGrapheProbabiliste) source.successeurs.get(i).destinataire);
                /* cas d'une boucle */
                if(source.successeurs.get(i).destinataire == source && source.successeurs.size() == 1 ){
                    return false;
                }
                /* Cas où le chemin est minimun de poids 2 */
                if(estChemin(listeVisitees, (NoeudGrapheProbabiliste) source.successeurs.get(i).destinataire, destinataire)){
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
    
    public ArrayList<ArrayList<Noeud>> regroupementClasse() {
        
        // Liste des noeuds pas encore dans une classe
        ArrayList<Noeud> ensembleNoeud = g.noeuds;
        
        ArrayList<ArrayList<Noeud>> classes = new ArrayList<>();
        ArrayList<Noeud> classe;
        
        Noeud noeudActuel; 
        
        while (ensembleNoeud.size() != 0) {
            
            noeudActuel = ensembleNoeud.get(0);
            classe = new ArrayList<>();
            // Ajout dans la classe
            classe.add(noeudActuel);
            // Comme dans une classe alors suppression dans l'ensemble des noeuds
            ensembleNoeud.remove(noeudActuel);
            for (int noATester = 0 ; noATester < ensembleNoeud.size() ; noATester++) {
                
                if (estTransitifSysmetrique((NoeudGrapheProbabiliste) noeudActuel, 
                                            (NoeudGrapheProbabiliste) ensembleNoeud.get(noATester))) {
                    
                    classe.add(ensembleNoeud.get(noATester));
                    ensembleNoeud.remove(ensembleNoeud.get(noATester));
                    noATester--;
                }
            }
            // Ajout de la classe dans la liste de classes
            classes.add(classe);
        }
        
        return classes;
    }
    
    /**
     * Verifie qu'il y est un chemin entre n1 et n2 
     * et verifie qu'il y est un chemin entre n2 et n1
     * @param n1 Noeud a tester
     * @param n2 Noeud a tester
     * @return true s'il y a un chemin entre n1 et n2, et qu'il y a un chemin entre
     *         n2 et n1, false sinon
     */
    public boolean estTransitifSysmetrique(NoeudGrapheProbabiliste n1, NoeudGrapheProbabiliste n2) {
        
        return estChemin(new ArrayList<NoeudGrapheProbabiliste>(), n1, n2)
               && estChemin(new ArrayList<NoeudGrapheProbabiliste>(), n2, n1);
    }
    
}
