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
    
    
    public Graphe g;
    public ArrayList<ArcProbabiliste> listeLiens ;
    public ArrayList<NoeudGrapheProbabiliste> listeNoeuds ;
    
    public Matrice(){
        listeLiens = new ArrayList<>();
    }
    
    public Matrice(ArrayList<ArcProbabiliste> liste){       
        listeLiens = liste;
    }
    
    public void afficherMatrice(){
        if(listeLiens.isEmpty()){
            System.out.println("La matrice est null");
        }else{
            for (int i = 0; i < listeLiens.size() ;i++) {
                System.out.println(listeLiens.get(i));
            }
        }
    }
    public boolean estSuccesseur(){
        
    }
    public boolean estPredecesseur(){
        
    }
    
    public Noeud[][] estFinale(){
        if(listeLiens.get(0).source){
            
            
        }
    }
    
    
    
    
    
}
