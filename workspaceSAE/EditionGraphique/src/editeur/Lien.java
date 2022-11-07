package editeur;





public abstract class Lien {

    /** Noeud source de l'arête */
    public Noeud source ;

    /** Noeud destianataire de l'arête */
    public Noeud destinataire ;

    /** Graphe dans lequel l'arête est créé */
    public Graphe g; // TODO

    /**
     * Créer une arete avec pour source le Noeud source 
     * et pour destinataire le Noeud destinataire.
     * @param source noeud source de l'arête
     * @param destinataire noeud destinataire de l'arête
     */
    public Lien(Noeud source, Noeud destinataire) {
        
    }
    
    /**
     * Dessine un lien entre deux noeuds distincts
     */
    public void dessiner() {
    	
    }

}