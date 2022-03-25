/*=============================================
Classe Transitions permettant de sauvegarder 
les modifications et de les rétablir à l'aide 
des boutons
Auteur : Isaias VENEGAS
Date de création : 24/03/2022
Date de dernière modification : 25/03/2022
=============================================*/
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

class Enregistrement {
    String action; // ajouter/supprimer des noeud/arc, modifier l'étiquette
    double x; // position de creation d'un noeud
    double y; // position de creation d'un noeud
    MyLine line; // arc créé
    int n; // indice de l'élément
    String currentLbl; // label/poid originale
    String newLbl; // nouveau label/poids
    double x2; // position de déplacement d'un noeud
    double y2; // position de déplacement d'un noeud
   
    // Constructor pour les actions sur les nœuds
    public Enregistrement(String action, double x, double y, int n) {
        this.action = action;
        this.x = x;
        this.y = y;
        this.n = n;
    }
    
    // Constructor pour les actions sur les arcs
    public Enregistrement(String action, MyLine line, int n) {
        this.action = action;
        this.line = line;
        this.n = n;
    }

    // Constructor pour les actions de modification des étiquettes
    public Enregistrement(String action, String currentLbl, String newLbl, int n) {
        this.action = action;
        this.n = n;
        this.currentLbl = currentLbl;
        this.newLbl = newLbl;
    }
    
    // Constructor pour les actions de mouvement
    public Enregistrement(String action, double x, double y, double x2, double y2, int n) {
        this.action = action;
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        this.n = n;
    }
}
public class Transitions{
    // piles Ctrl+Z et Ctrl+Y
    protected ConcurrentHashMap<Integer, Enregistrement> previousStates;
    protected ConcurrentHashMap<Integer, Enregistrement> nextStates;

    public Transitions(){
       this.previousStates = new ConcurrentHashMap<>();
       this.nextStates = new ConcurrentHashMap<>();
    }
    
    public Enregistrement createLog(String action, double x, double y, int n) {
        return new Enregistrement(action,x,y,n);
    }
    
    public Enregistrement createLog(String action, MyLine line, int n) {
        return new Enregistrement(action,line,n);
    }
    
    public Enregistrement createLog(String action, String currentLbl, String newLbl, int n) {
        return new Enregistrement(action,currentLbl,newLbl,n);
    }
    
    public Enregistrement createLog(String action, double x, double y, double x2, double y2, int n) {
        return new Enregistrement(action,x,y,x2,y2,n);
    }

    public Enregistrement getPreviousState(){
        return previousStates.get(previousStates.size()-1);
    }

    public Enregistrement getNextState(){
        return nextStates.get(nextStates.size()-1);
    }

    public Collection<Enregistrement> getPreviousStates(){
        return previousStates.values();
    }

    public Collection<Enregistrement> getNextStates(){
        return nextStates.values();
    }

    public boolean addPreviousState(Enregistrement log){
        Enregistrement put = previousStates.put(previousStates.size(), log);
        return put!=null;
    }

    public void clearNextStates(){
        nextStates.clear(); // On élimine les actions futures
    }

    public boolean addNextState(Enregistrement log){
        Enregistrement put = nextStates.put(nextStates.size(), log);
        return put!=null;
    }
}
