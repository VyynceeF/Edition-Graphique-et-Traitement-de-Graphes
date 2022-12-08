package conceptionV2;
/**
 *
 * @author amine.daamouch
 */
public class NoeudGrapheNonOriente extends Noeud {
    
    /** Début de l'appelation du Noeud */
    public final static String NOM = "N" ;
    
    /**
     * noNoeud commence à 0 et qui est incrémenté de 1
     * à chaque crétion de Noeud 
     */
    public static int noNoeud = 0 ;
    
    
    
    public NoeudGrapheNonOriente(double x, double y){
        super(x,y,NOM+noNoeud);
        noNoeud++;
    }
    
//    @Override 
//    public Circle dessiner(AnchorPane zoneDessin){
//        return this.dessiner(zoneDessin);
//    }
    
    
}
