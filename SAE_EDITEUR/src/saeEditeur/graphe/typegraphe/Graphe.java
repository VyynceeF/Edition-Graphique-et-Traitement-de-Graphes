/*
 * Edition graphique et traitement de graphe
 * -----------------------------------------
 * Graphe.java                    16/01/2023
 * BUT Informatique - 2ème Année (S3)
 * Pas de droit d'auteur ni de copy right
 */
package saeEditeur.graphe.typegraphe;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import javafx.scene.layout.AnchorPane;
import saeEditeur.graphe.lien.Lien;
import saeEditeur.graphe.lien.LienException;
import saeEditeur.graphe.noeud.Noeud;
import saeEditeur.graphe.noeud.NoeudException;

/**
 * Représentation d'un graphe et ses fonctionnalités
 * @author romain.courbaize
 * @author thibauld.cosatti
 * @author vincent.faure
 * @author jules.blanchard
 * @author amine.daamouch
 */
public abstract class Graphe {
    
    /**
     * Stocke les dernières modifications sous la forme d'une pile
     * 0 => Ajout d'un nouveau noeud
     * 1 => Ajout d'un nouveau lien
     * ArrayList => Modification
     *      - ArrayList[0] == Noeud => Modification de la position d'un noeud
     *        [0 => Noeud Modifié, 1 => Ancien X, 2 => Ancien Y]
     *      - ArrayList[0] == Lien  => Modification de l'extremite d'un lien
     *        [0 => Lien Modifié, 1 => Ancien Noeud extremité, 2 => Extremite]
     *      - ArrayList[0] == Noeud && ArrayList[1] == ArrayList<Lien> Suppression de ce noeud
     *        [0 => Noeud, 1 => Lien Successeurs, 2 => Lien Predecesseurs]
     * Lien  => Suppression de ce lien
     */
    private Stack<Object> stackUndo;
    
    /**
     * Stocke les anciennes effacées modifications sous la forme d'une pile
     * 0 => Ajout d'un nouveau noeud
     * 1 => Ajout d'un nouveau lien
     * ArrayList => Modification
     *      - ArrayList[0] == Noeud => Modification de la position d'un noeud
     *        [0 => Noeud Modifié, 1 => Ancien X, 2 => Ancien Y]
     *      - ArrayList[1] == Lien  => Modification de l'extremite d'un lien
     *        [0 => Lien Modifié, 1 => Ancien Noeud extremité, 2 => Extremite]
     * Noeud => Suppression de ce noeud
     * Lien  => Suppression de ce lien
     */
    private Stack<Object> stackRedo;
    
    /** Noeud actuellement selectionne dans le graphe */
    public Noeud noeudSelectionne = null;
    
    /** Lien actuellement selectionne dans le graphe */
    public Lien lienSelectionne = null;
    
    /** Liste des noeuds du graphe */
    public ArrayList<Noeud> noeuds ;
    
    /** Décallage pour la superposition */
    final double DECALLAGE = Noeud.RAYON + 5 ;
    
    /** Liste des liens */
    public ArrayList<Lien> liens ;
    
    public Graphe(){
        Noeud.noNoeud = 0 ;
        liens =  new ArrayList<>();
        noeuds = new ArrayList<>();
        noeudSelectionne = null;
        stackUndo = new Stack<>();
        stackRedo = new Stack<>();
    }
    
    @Override
    public abstract String toString();
    
    /**
     * @return Le nom de la factory du type du graphe
     */
    public abstract String getFactory();
    
    /**
     * Ajouter un noeud dans le graphe
     * @param n Noeud à ajouter
     * @throws NoeudException Si le noeud est invalide (impossible de créer 
     *                        un noeud sur un noeud
     */
    public abstract void ajouterNoeud(Noeud n) throws NoeudException;
    
    /**
     * Ajouter un lien dans le graphe
     * @param l Lien à ajouter
     * @return Le lien ajouté
     * @throws LienException Si le lien existe déjà
     */
    public abstract Lien ajouterLien(Lien l) throws LienException;
    
    /**
     * Prédicat vérifiant s'il existe un lien similaire dans le graphe
     * @param l Lien à vérifier
     * @return True si valide, false sinon
     */
    public abstract boolean estLienValide(Lien l);

    /**
     * Prédicat vérifiant s'il y a un noeud sur la position (x, y)
     * @param x Abscisse du point
     * @param y Ordonné du point
     * @return Le noeud en position (x, y) s'il y en a un, null sinon
     */
    public Noeud estNoeud(double x, double y) {
        
        for (Noeud aTester : noeuds) {
            // racine_carre((x_point - x_centre)² + (y_centre - y_point)) < rayon
            if (aTester.estClique(x, y)) {
                
                return aTester;
            }
        }
        return null;
    }
    
    /**
     * Ajoute dans la pile les modifications
     * @param ancienElement 
     */
    public void ajouterPileUndo(Object ancienElement) {
        
        stackUndo.push(ancienElement);
        System.out.println("Ajout Action => " + ancienElement);
    }
    
    /**
     * Ajoute dans la pile les modifications
     * @param ancienElement 
     */
    public void ajouterPileRedo(Object ancienElement) {
        
        stackRedo.push(ancienElement);
        System.out.println("Ajout Action => " + ancienElement);
    }
    
    /**
     * Supprime les modifications
     * @param zoneDessin Zone de dessin
     * @return 
     */
    public boolean undo(AnchorPane zoneDessin) {
        
        // Aucune ancienne modification
        if (stackUndo.empty()) {
            return false;
        }
        
        Object ancienModification = stackUndo.pop();
        
        // Integer => Ajout
        if (ancienModification instanceof Integer) {
            
            // La derniere modification a été un ajout de noeud
            if (ancienModification.equals(0)) {
                
                ArrayList<Object> modif = new ArrayList<>();
                modif.add(noeuds.get(noeuds.size() - 1));
                modif.add(noeuds.get(noeuds.size() - 1).successeurs.clone());
                modif.add(noeuds.get(noeuds.size() - 1).predecesseurs.clone());
                ajouterPileRedo(modif);
                supprimerNoeud(noeuds.remove(noeuds.size() - 1), zoneDessin);
            }
            // La derniere modification a été un ajout de lien
            if (ancienModification.equals(1)) {
                
                ajouterPileRedo(liens.get(liens.size() - 1));
                supprimerLien(liens.remove(liens.size() - 1), zoneDessin);
            }
        } 
        
        // ArrayList
        if (ancienModification instanceof ArrayList) {
            ArrayList<Object> listeModif = (ArrayList) ancienModification;
            
            // Modification d'une extremite d'un lien
            if (listeModif.get(0) instanceof Lien) {
                
                ArrayList<Object> modif = new ArrayList<>();
                modif.add((Lien) listeModif.get(0));
                modif.add((Noeud) listeModif.get(1));
                modif.add((int) listeModif.get(2));
                ajouterPileRedo(modif);
                
                changementExtremiteLien((Lien) listeModif.get(0), 
                                        (Noeud) listeModif.get(1), 
                                        (int) listeModif.get(2), 
                                        zoneDessin);
                return true;
            }
            // Noeud => Suppression d'un noeud
            if (listeModif.get(0) instanceof Noeud
                && listeModif.get(1) instanceof ArrayList) {
                try {
                    ajouterNoeud((Noeud) listeModif.get(0));
                    ajouterPileRedo((Noeud) listeModif.get(0));
                    ((Noeud) listeModif.get(0)).dessiner(zoneDessin);
                    // Ajout des liens successeurs supprimes
                    for (int noSuccesseur = 0 ; 
                         noSuccesseur < ((ArrayList)listeModif.get(1)).size() ;
                         noSuccesseur++) {
                        
                        try {
                            ajouterLien((Lien) ((ArrayList)listeModif.get(1)).get(noSuccesseur));
                            liens.get(liens.size() - 1).dessiner(zoneDessin); // Dessin du lien
                        } catch (LienException ex) {
                        }
                    }
                    // Ajout des liens predecesseurs supprimes
                    for (int noPredecesseur = 0 ; 
                         noPredecesseur < ((ArrayList)listeModif.get(2)).size() ;
                         noPredecesseur++) {
                        
                        try {
                            ajouterLien((Lien) ((ArrayList)listeModif.get(2)).get(noPredecesseur));
                            liens.get(liens.size() - 1).dessiner(zoneDessin); // Dessin du lien
                        } catch (LienException ex) {
                        }
                    }
                    return true;
                } catch (NoeudException ex) {}
            }
            
            // Modification de la position d'un noeud
            if (listeModif.get(0) instanceof Noeud) {
                
                System.out.println("MODIF POS");
                ArrayList<Object> modif = new ArrayList<>();
                modif.add((Noeud) listeModif.get(0));
                modif.add(((Noeud) listeModif.get(0)).position.x);
                modif.add(((Noeud) listeModif.get(0)).position.y);
                ajouterPileRedo(modif);
                
                ((Noeud) listeModif.get(0)).modifierPosition((double) listeModif.get(1), (double) listeModif.get(2), zoneDessin);
                return true;
            }
        }
        
        
        
        // Lien => Suppression d'un lien
        if (ancienModification instanceof Lien) {
            
            try {
                ajouterPileRedo(1);
                ajouterLien((Lien) ancienModification);
                ((Lien) ancienModification).dessiner(zoneDessin);
            } catch (LienException ex) {}
        }
        
        return false;
    }
    
    /**
     * Rétablir les anciennes modifications
     * @param zoneDessin Zone de dessin
     * @return 
     */
    public boolean redo(AnchorPane zoneDessin) {
         
        // Aucune ancienne modification
        if (stackRedo.empty()) {
            return false;
        }
        
        Object ancienModification = stackRedo.pop();
        
        // Integer => Ajout
        if (ancienModification instanceof Integer) {
            
            // La derniere modification a été un ajout de noeud
            if (ancienModification.equals(0)) {
                
                ArrayList<Object> modif = new ArrayList<>();
                modif.add(noeuds.get(noeuds.size() - 1));
                modif.add(noeuds.get(noeuds.size() - 1).successeurs.clone());
                modif.add(noeuds.get(noeuds.size() - 1).predecesseurs.clone());
                ajouterPileUndo(modif);
                supprimerNoeud(noeuds.remove(noeuds.size() - 1), zoneDessin);
            }
            // La derniere modification a été un ajout de lien
            if (ancienModification.equals(1)) {
                
                ajouterPileUndo(liens.get(liens.size() - 1));
                supprimerLien(liens.remove(liens.size() - 1), zoneDessin);
            }
        } 
        
        // ArrayList
        if (ancienModification instanceof ArrayList) {
            ArrayList<Object> listeModif = (ArrayList) ancienModification;
            
            // Modification d'une extremite d'un lien
            if (listeModif.get(0) instanceof Lien) {
                
                ArrayList<Object> modif = new ArrayList<>();
                modif.add((Lien) listeModif.get(0));
                modif.add((Noeud) listeModif.get(1));
                modif.add((int) listeModif.get(2));
                ajouterPileUndo(modif);
                
                changementExtremiteLien((Lien) listeModif.get(0), 
                                        (Noeud) listeModif.get(1), 
                                        (int) listeModif.get(2), 
                                        zoneDessin);
                return true;
            }
            // Noeud => Suppression d'un noeud
            if (listeModif.get(0) instanceof Noeud
                && listeModif.get(1) instanceof ArrayList) {
                try {
                    ajouterPileUndo((Noeud) listeModif.get(0));
                    ajouterNoeud((Noeud) listeModif.get(0));
                    ((Noeud) listeModif.get(0)).dessiner(zoneDessin);
                    // Ajout des liens successeurs supprimes
                    for (int noSuccesseur = 0 ; 
                         noSuccesseur < ((ArrayList)listeModif.get(1)).size() ;
                         noSuccesseur++) {
                        
                        try {
                            ajouterLien((Lien) ((ArrayList)listeModif.get(1)).get(noSuccesseur));
                            liens.get(liens.size() - 1).dessiner(zoneDessin); // Dessin du lien
                        } catch (LienException ex) {
                        }
                    }
                    // Ajout des liens predecesseurs supprimes
                    for (int noPredecesseur = 0 ; 
                         noPredecesseur < ((ArrayList)listeModif.get(2)).size() ;
                         noPredecesseur++) {
                        
                        try {
                            ajouterLien((Lien) ((ArrayList)listeModif.get(2)).get(noPredecesseur));
                            liens.get(liens.size() - 1).dessiner(zoneDessin); // Dessin du lien
                        } catch (LienException ex) {
                        }
                    }
                    return true;
                } catch (NoeudException ex) {}
            }
            
            // Modification de la position d'un noeud
            if (listeModif.get(0) instanceof Noeud) {
                
                ArrayList<Object> modif = new ArrayList<>();
                modif.add((Noeud) listeModif.get(0));
                modif.add(((Noeud) listeModif.get(0)).position.x);
                modif.add(((Noeud) listeModif.get(0)).position.y);
                ajouterPileUndo(modif);
                
                ((Noeud) listeModif.get(0)).modifierPosition((double) listeModif.get(1), (double) listeModif.get(2), zoneDessin);
                return true;
            }
        }
        
        // Lien => Suppression d'un lien
        if (ancienModification instanceof Lien) {
            
            try {
                ajouterPileUndo(1);
                ajouterLien((Lien) ancienModification);
                ((Lien) ancienModification).dessiner(zoneDessin);
                return true;
            } catch (LienException ex) {}
        }
        
        return false;
    }
    
    /**
     * Permet de savoir s'il y a un lien sur la position (x, y)
     * @param x Abscisse du point a tester
     * @param y Ordonnee du point a tester
     * @return Le lien s'il existe un lien sur la position (x, y), false sinon
     */
    public Lien estLien(double x, double y) {
        
        for (Lien aTester : liens) {
            // racine_carre((x_point - x_centre)² + (y_centre - y_point)) < rayon
            if (aTester.estClique(x, y)) {
                
                return aTester;
            }
        }
        return null;
    }
    
    /**
     * Prédicat vérifiant si un noeud peut être créer ou non
     * @return true ssi la zone du noeud à créer est en dehors de la zone de chaque noeud déjà créer 
     */
    public boolean estNoeudValide(double x, double y) throws NoeudException{
    	
    	for (int noATester = 0 ; noATester < noeuds.size() ; noATester++) {
    		
    		// Coin haut droit
    		if (noeuds.get(noATester).position.x - DECALLAGE <= x + DECALLAGE
                    && x + DECALLAGE <= noeuds.get(noATester).position.x + DECALLAGE
                    && noeuds.get(noATester).position.y - DECALLAGE <= y + DECALLAGE
                    && y + DECALLAGE <= noeuds.get(noATester).position.y + DECALLAGE) {
                    throw new NoeudException("Impossible de créer un noeud "
                            + "sur un noeud existant");
                    
    		}

    		// Coin haut gauche
    		if (noeuds.get(noATester).position.x - DECALLAGE <= x - DECALLAGE
                    && x - DECALLAGE <= noeuds.get(noATester).position.x + DECALLAGE
                    && noeuds.get(noATester).position.y - DECALLAGE <= y + DECALLAGE
                    && y + DECALLAGE <= noeuds.get(noATester).position.y + DECALLAGE) {

                    throw new NoeudException("Impossible de créer un noeud "
                            + "sur un noeud existant");
    		}

    		// Coin bas gauche
    		if (noeuds.get(noATester).position.x - DECALLAGE <= x - DECALLAGE
                    && x - DECALLAGE <= noeuds.get(noATester).position.x + DECALLAGE
                    && noeuds.get(noATester).position.y - DECALLAGE <= y - DECALLAGE
                    && y - DECALLAGE <= noeuds.get(noATester).position.y + DECALLAGE) {

                    throw new NoeudException("Impossible de créer un noeud "
                            + "sur un noeud existant");
    		}

    		// Coin bas droit
    		if (noeuds.get(noATester).position.x - DECALLAGE <= x + DECALLAGE
                    && x + DECALLAGE <= noeuds.get(noATester).position.x + DECALLAGE
                    && noeuds.get(noATester).position.y - DECALLAGE <= y - DECALLAGE
                    && y - DECALLAGE <= noeuds.get(noATester).position.y + DECALLAGE) {

                    throw new NoeudException("Impossible de créer un noeud "
                            + "sur un noeud existant");
    		}
    	}    	
        
        if (DECALLAGE > x 
            || 2000 - DECALLAGE < x
            ||  DECALLAGE > y
            || 2000 - DECALLAGE < y) {
            throw new NoeudException("Impossible de créer un noeud "
                            + "sur un noeud existant");
        }
        return true;
    }
    
    /**
     * Prédicat vérifiant si un noeud peut être créer ou non
     * @return true ssi la zone du noeud à créer est en dehors de la zone de chaque noeud déjà créer 
     */
    public boolean estNoeudValideApresDeplacement(double x, double y, Noeud n) {
    	
        ArrayList<Noeud> noeudsTests = (ArrayList<Noeud>) noeuds.clone();
        noeudsTests.remove(n);
        
    	for (int noATester = 0 ; noATester < noeudsTests.size() ; noATester++) {
    		
    		// Coin haut droit
    		if (noeudsTests.get(noATester).position.x - DECALLAGE <= x + DECALLAGE
                    && x + DECALLAGE <= noeudsTests.get(noATester).position.x + DECALLAGE
                    && noeudsTests.get(noATester).position.y - DECALLAGE <= y + DECALLAGE
                    && y + DECALLAGE <= noeudsTests.get(noATester).position.y + DECALLAGE) {
                    
                    return false;                    
    		}

    		// Coin haut gauche
    		if (noeudsTests.get(noATester).position.x - DECALLAGE <= x - DECALLAGE
                    && x - DECALLAGE <= noeudsTests.get(noATester).position.x + DECALLAGE
                    && noeudsTests.get(noATester).position.y - DECALLAGE <= y + DECALLAGE
                    && y + DECALLAGE <= noeudsTests.get(noATester).position.y + DECALLAGE) {

                    return false;                    
    		}

    		// Coin bas gauche
    		if (noeudsTests.get(noATester).position.x - DECALLAGE <= x - DECALLAGE
                    && x - DECALLAGE <= noeudsTests.get(noATester).position.x + DECALLAGE
                    && noeudsTests.get(noATester).position.y - DECALLAGE <= y - DECALLAGE
                    && y - DECALLAGE <= noeudsTests.get(noATester).position.y + DECALLAGE) {

                    return false;                    
    		}

    		// Coin bas droit
    		if (noeudsTests.get(noATester).position.x - DECALLAGE <= x + DECALLAGE
                    && x + DECALLAGE <= noeudsTests.get(noATester).position.x + DECALLAGE
                    && noeudsTests.get(noATester).position.y - DECALLAGE <= y - DECALLAGE
                    && y - DECALLAGE <= noeudsTests.get(noATester).position.y + DECALLAGE) {

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
    
   
    /**
     * Permet d'enregistrer le graphe dans un fichier
     * @param chemin Chemin et nom du fichier
     */
    public void enregistrer(String chemin) throws FileNotFoundException {
        
        // Sérialisation XML d'un noeud dans fichier essai.xml*
        System.out.println(chemin + ".xml");
        XMLEncoder encoder = new XMLEncoder(new FileOutputStream(chemin + ".xml"));
        encoder.writeObject(this);
        encoder.close();
           
    }
    
    /**
     * Permet d'ouvrir un graphe contenu dans le fichier 
     * @param chemin Chemin et nom du fichier
     */
    public static Graphe ouvrir(String chemin) throws FileNotFoundException {
        
        // Déserialisation et affichage du noeud pour véfification
        try {
            // Le fichier est bien un enregistrement d'un graphe
            XMLDecoder decoder = new XMLDecoder(new FileInputStream(chemin));

            Graphe graphe = (Graphe) decoder.readObject();
            
            return graphe;
            
        } catch (Exception e) {
            
            throw new FileNotFoundException();
        }    
    }
    
    /**
     * Dessine sur la zone de dessin tous les elements du graphe (après ouverture)
     * @param zoneDessin Zone de dessin du graphe
     */
    public void dessiner(AnchorPane zoneDessin) {
        
        /* Dessin des noeuds */
        for (int noNoeud = 0 ; noNoeud < noeuds.size() ; noNoeud++) {
            
            noeuds.get(noNoeud).dessiner(zoneDessin);
        }
        
        /* Dessin des liens */
        for (int noLien = 0 ; noLien < liens.size() ; noLien++) {
            
            liens.get(noLien).dessiner(zoneDessin);
        }
        
        /* Precedente selection */
        if (noeudSelectionne != null) {
            
            noeudSelectionne.noeudSelectionne();
        }
        if (lienSelectionne != null) {
            
            lienSelectionne.lienSelectionne(zoneDessin);
        }
    }

    public ArrayList<Noeud> getNoeuds() {
        return noeuds;
    }

    public void setNoeuds(ArrayList<Noeud> noeuds) {
        this.noeuds = noeuds;
    }

    public ArrayList<Lien> getLiens() {
        return liens;
    }

    public void setLiens(ArrayList<Lien> liens) {
        this.liens = liens;
    }
    
    /**
     * Stocke le noeud selectionne (pour le deselectionner plus tard)
     * Et Augmente l'epaisseur de la bordure representant le noeud
     * @param noeudSelectionne Noeud selectionne
     */
    public void noeudSelectionne(Noeud noeudSelectionne) {
        
        noeudSelectionne.noeudSelectionne();
        this.noeudSelectionne = noeudSelectionne;
    }
    
    /**
     * Stocke le lien selectionne (pour le deselectionner plus tard)
     * Et Augmente l'epaisseur du lien
     * @param lienSelectionne Noeud selectionne
     */
    public void lienSelectionne(Lien lienSelectionne, AnchorPane zoneDessin) {
        
        lienSelectionne.lienSelectionne(zoneDessin);
        this.lienSelectionne = lienSelectionne;
    }
    
    /**
     * Deselectionne tous les elements du graphe
     */
    public void deselectionnerAll(AnchorPane zoneDessin) {
        
        if (noeudSelectionne != null) {
            
            noeudSelectionne.noeudDeselectionne();
            noeudSelectionne = null;
        }
        if (lienSelectionne != null) {
            
            lienSelectionne.lienDeselectionne(zoneDessin);
            lienSelectionne = null;
        }
    }

    /**
     * Supprime le lien du graphe et supprime sa représentation
     * @param lienSelectionne Lien à supprimer
     * @param zoneDessin Zone de dessin
     */
    public void supprimerLien(Lien lienSelectionne, AnchorPane zoneDessin) {
        liens.remove(lienSelectionne);
        lienSelectionne.supprimer(zoneDessin);
        
        this.lienSelectionne = null;
        
        deselectionnerAll(zoneDessin);
    }
    
    /**
     * Supprime le noeud du graphe et supprime sa représentation
     * @param noeudSelectionne Lien à supprimer
     * @param zoneDessin Zone de dessin
     */
    public void supprimerNoeud(Noeud noeudSelectionne, AnchorPane zoneDessin) {
        
        noeuds.remove(noeudSelectionne);
        /* Suppression dans la zone de dessin */
        noeudSelectionne.supprimer(zoneDessin);
        
        deselectionnerAll(zoneDessin);
    }
    
    /**
     * Modifie l'extremite du lien selectionne par le nouveau noeud
     * @param lienSelectionne Lien à modifier
     * @param nouveauNoeud Nouveau noeud extremite
     * @param extremite 1 -> Premiere extremite | 2 -> Derniere extremite
     * @param zoneDessin Zone de dessin
     */
    public void changementExtremiteLien(Lien lienSelectionne, Noeud nouveauNoeud, int extremite, AnchorPane zoneDessin) {
        
        lienSelectionne.changementExtremite(nouveauNoeud, extremite, zoneDessin);
    }
    
    /**
     * Renvoie le nom du noeud a creer
     * Verifie que le nom n'est pas utiliser
     * Si utiliser alors il le modifie
     * @param aVerifier nom du noeud
     * @param complement permet de creer le nom du noeud si il existe deja 
     * @return le nom du noeud a creer 
     */
    public String nomNoeud(String aVerifier, int complement){
        
        for (int i = 0; i < noeuds.size(); i++) {
            if(aVerifier.equals(noeuds.get(i).nom)){
                return nomNoeud("N" + complement, complement + 1);
            }
        }
        return aVerifier;
    }
}
