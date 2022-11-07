package editeur;





public abstract  class Noeud {

    /** Nom du Noeud */
    public final String NOM;

    /**
     * Nom du Noeud de la forme "Nx", 
     * avec x qui commence à 0 et qui est incrémenté de 1
     * à chaque crétion de Noeud 
     */
    public static int noNoeud = 0;

    /** Graphe dans lequel le Noeud est créé */
    public Graphe g ; // TODO 

    /** Position du noeud dans la zone de dessin */
    public Point position;

    /** Rayon du cercle representant le noeud */ 
    public static final int RAYON = 10;

    /**
     * Créer un Noeud avec comme information relative son nom et sa position (x, y)
	 * nomIncrementable est incrémenté de 1
	 * La position est créée à l'aide de la classe Point
     * @param x  l'abscice du noeud
     * @param y  l'ordonnée du noeud 
     */
    public Noeud(int x, int y) {
          
        position = new Point(x,y);
        NOM = "N" + noNoeud;
        noNoeud++;
    }

    /** 
     * Dessine le Noeud sur la zone de dessin, 
     * appelant la méthode Circle de l'Editeur graphique. 
     */
    public void dessiner() {
        //TODO
    }

}
