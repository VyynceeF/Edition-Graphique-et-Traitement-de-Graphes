package editeur;

import java.util.ArrayList;




public class Graphe {
    
    public ArrayList<Noeud> noeuds;
    
    
    public Noeud source = null ;
     
    public Noeud destinataire = null ; 
    
    public ArrayList<Lien> liens;

    public final double DECALLAGE = Noeud.RAYON + 5 ;
    /**
     * Prédicat vérifiant si un noeud peut être créer ou non
     * @return true ssi la zone du noeud à créer est en dehors de la zone de chaque noeud déjà créer 
     */
    public boolean estNoeudValide(double x, double y) {
    	
    	for (int noATester = 0 ; noATester < noeuds.size() ; noATester++) {
    		
    		// Coin haut droit
    		if (noeuds.get(noATester).position.x - DECALLAGE <= x + DECALLAGE
                    && x + DECALLAGE <= noeuds.get(noATester).position.x + DECALLAGE
                    && noeuds.get(noATester).position.y - DECALLAGE <= y + DECALLAGE
                    && y + DECALLAGE <= noeuds.get(noATester).position.y + DECALLAGE) {

                    return false;
    		}

    		// Coin haut gauche
    		if (noeuds.get(noATester).position.x - DECALLAGE <= x - DECALLAGE
                    && x - DECALLAGE <= noeuds.get(noATester).position.x + DECALLAGE
                    && noeuds.get(noATester).position.y - DECALLAGE <= y + DECALLAGE
                    && y + DECALLAGE <= noeuds.get(noATester).position.y + DECALLAGE) {

                    return false;
    		}

    		// Coin bas gauche
    		if (noeuds.get(noATester).position.x - DECALLAGE <= x - DECALLAGE
                    && x - DECALLAGE <= noeuds.get(noATester).position.x + DECALLAGE
                    && noeuds.get(noATester).position.y - DECALLAGE <= y - DECALLAGE
                    && y - DECALLAGE <= noeuds.get(noATester).position.y + DECALLAGE) {

                    return false;
    		}

    		// Coin bas droit
    		if (noeuds.get(noATester).position.x - DECALLAGE <= x + DECALLAGE
                    && x + DECALLAGE <= noeuds.get(noATester).position.x + DECALLAGE
                    && noeuds.get(noATester).position.y - DECALLAGE <= y - DECALLAGE
                    && y - DECALLAGE <= noeuds.get(noATester).position.y + DECALLAGE) {

                    return false;
    		}
    	}    	
        
        if (DECALLAGE > x 
            || 2000 - DECALLAGE < x
            ||  DECALLAGE > y
            || 2000 - DECALLAGE < y) {
            return false;
        }
        return true;
    }
    
    public boolean estLienValide(){
        
        for (int i = 0; i < liens.size(); i++) {
            if(source == liens.get(i).source 
                && destinataire == liens.get(i).destinataire  ){
                return false; 
            }
        }
        return !(source == destinataire);
    }
    /**
     * Ajoute un noeud dans la liste de noeuds du graphe afin de pouvoir les stocker 
     * @throws Ssi la zone du noeud à créer est en dehors de la zone de chaque noeud déjà créer 
     */
    public void ajouterNoeud(double x, double y) throws NoeudException {
    	
        if (!estNoeudValide(x, y)) {
            throw new NoeudException();
        }
        
        noeuds.add(new Noeud(x, y , this));
    }

    /**
     * Ajoute un lien dans la liste de liens du graphe afin de pouvoir les stocker 
     */
    public void ajouterLien() throws LienException {
        
        if (!estLienValide()) {
            throw new LienException();
        }
        liens.add(new Lien(source,destinataire, this ));
        
    }

    public Graphe() {
    	noeuds = new ArrayList<Noeud> ();
    	liens = new ArrayList<Lien> ();
    }
    

}
