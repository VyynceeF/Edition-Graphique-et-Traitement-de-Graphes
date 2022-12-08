/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;
import java.util.*;
/**
 *
 * @author amine.daamouch
 */
public class GrapheNonOriente extends Graphe {
    
    
    public GrapheNonOriente(){
        super();
    }
    
    @Override
    public void ajouterNoeud(Noeud n){
        if (n instanceof NoeudGrapheNonOriente){
            noeuds.add(n);
        }else{
            // thorw l'exception 
        }
       
    }

    @Override
    public Lien ajouterLien(Lien l)throws LienException{
        if(!(l instanceof Arete)){
           // TODO Lève l'exception
        }
        for (Lien aTester : liens) {
            if ((l.destinataire == aTester.destinataire
                   && l.source == aTester.source)
                  || (l.destinataire == aTester.source
                      && l.source == aTester.destinataire)) {      
                throw new LienException("Impossible de créer un lien sur un lien");
            }
        }
        liens.add(l);
        return l;
    }
}