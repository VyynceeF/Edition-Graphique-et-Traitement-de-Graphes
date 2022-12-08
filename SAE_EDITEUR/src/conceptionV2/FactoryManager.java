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
public class FactoryManager {
    
    public static HashMap<String, AbstractFactoryGraphe> factories = new HashMap<>();
    
    //public FactoryManager instance = new FactoryManager();
    
    
    
    public static AbstractFactoryGraphe factory;
    
    public FactoryManager() {
        factories.put("Graphe non orienté", new FactoryGrapheNonOriente());
        factories.put("Graphe orienté", new FactoryGrapheOriente());
    }
    public Set<String> getTypes(){
       return factories.keySet();
    }
    
    //public FactoryManager getInstance(){
    //    return instance; 
    //}
    
    public AbstractFactoryGraphe getFactory(String typeFactory) {
        return factories.get(typeFactory);
    }

}
