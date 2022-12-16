/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptionV2;

/**
 *
 * @author amine.daamouch
 */
public class GrapheOriente extends Graphe{
/**
 *
 * @author amine.daamouch
     * @param n
 */

    
    @Override
    public void ajouterNoeud(Noeud n) throws NoeudException {
        if (n instanceof NoeudGrapheOriente){
            noeuds.add(n);
        }else{
            throw new NoeudException("Impossible de créer un Noeud");
        }
       
    }

    @Override
    public Lien ajouterLien(Lien l)throws LienException{
        if(!(l instanceof Arc)){
           throw new LienException("Impossible de créer un Lien");
        }
        if (l.destinataire == l.source) {
         
        }
        
        for (Lien aTester : liens) {
            if ((l.destinataire == aTester.destinataire
                   && l.source == aTester.source)) {      
                throw new LienException("Impossible de créer un lien sur un lien");
            }
                     
        }
        
        liens.add(l);
        return l;
    }
}

