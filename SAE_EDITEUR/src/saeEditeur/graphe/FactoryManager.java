/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * FactoryManager.java            16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe;

import java.util.*;

/**
 * Contient tous les types de graphe possible
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public class FactoryManager {
    
    /** Ensemble contenant les Nom de graphe et leur factory */
    public static HashMap<String, AbstractFactoryGraphe> factories = new HashMap<>();
    
    /** Instancie tous les types de graphe et leur factory */
    public FactoryManager() {
        factories.put("Graphe non orienté", new FactoryGrapheNonOriente());
        factories.put("Graphe orienté", new FactoryGrapheOriente());
        factories.put("Graphe orienté pondéré", new FactoryGraphePondere());
        factories.put("Graphe probabiliste", new FactoryGrapheProbabiliste());
    }
    
    /**
     * @return Ensemble de tous les types de graphe disponible
     */
    public Set<String> getTypes(){
       return factories.keySet();
    }
    
    /**
     * @param typeFactory Type de graphe souhaité
     * @return La factory associé au type de graphe souhaité
     */
    public AbstractFactoryGraphe getFactory(String typeFactory) {
        return factories.get(typeFactory);
    }

}
