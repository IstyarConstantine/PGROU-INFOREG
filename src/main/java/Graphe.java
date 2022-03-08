import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*=============================================
Classe abstraite Graphe définissant la structure 
générale d'un graphe
Auteur : Béryl CASSEL
Date de création : 27/01/2022
Date de dernière modification : 08/03/2022
=============================================*/

public abstract class Graphe {

    /**
     * Nombre de Noeuds du Graphe
     */
    protected int nbsommets;

    /**
     * Nombre maximal de sommets que peut contenir un graphe 
     */
    protected final static int nbmax = 100;

    /**
     * Matrice d'adjacence
     */
    protected int[][] adj;

    protected ArrayList<Noeud> lstNoeuds;

    protected ArrayList<Arc> lstArcs;

    /**
     * Constructeur d'un graphe
     */
    public Graphe(){
        this.adj = new int[Graphe.nbmax][Graphe.nbmax];
        this.nbsommets = 0;
        this.lstArcs = new ArrayList<Arc>();
        this.lstNoeuds = new ArrayList<Noeud>();
    }

    public Graphe(Draw d){
        this.nbsommets = 0;
        this.adj = new int[Graphe.nbmax][Graphe.nbmax];
        this.lstArcs = new ArrayList<Arc>();
        this.lstNoeuds = new ArrayList<Noeud>();
        for (int i=0;i<d.getNumOfCircles();i++){
            addSommet(d.getCircLbl()[i],i);
        }
        ArrayList<Draw.MyLine> lines = d.getLines();
        for (int i=0;i<d.getNumOfLines();i++){
            Draw.MyLine l = lines.get(i);
            int p = l.getPoids();
            int src = d.getRec(l.getFromPoint().x,l.getFromPoint().y);
            int dest = d.getRec(l.getToPoint().x,l.getToPoint().y);
            addArc(src, dest, p, i);
        }
    }

    public ArrayList<Noeud> getLstNoeuds() {
        return lstNoeuds;
    }

    public void setLstNoeuds(ArrayList<Noeud> lstNoeuds) {
        this.lstNoeuds = lstNoeuds;
    }

    public ArrayList<Arc> getLstArcs() {
        return lstArcs;
    }

    public void setLstArcs(ArrayList<Arc> lstArcs) {
        this.lstArcs = lstArcs;
    }
    
    /**
     * Getter du graphe de version précédente
     * @return un graphe
     */

    /**
     * Getter de la matrice d'adjacence du graphe
     * @return un tableau nbmax*nbmax
     */
    public int[][] getAdj() {
        return adj;
    }

    /**
     * Setter de la matrice d'adjacence du graphe
     * @param adj tableau nbmax*nbmax
     */
    public void setAdj(int[][] adj) {
        this.adj = adj;
    }

    /**
     * Getter du nombre de sommets du graphe
     * @return un entier
     */
    public int getNbsommets() {
        return nbsommets;
    }

    /**
     * Setter du nombre de sommets du graphe
     * @param nbsommets
     */
    public void setNbsommets(int nbsommets) {
        this.nbsommets = nbsommets;
    }

    /**
     * Méthode permettant d'afficher dans le terminal 
     * la matrice d'adjacence du graphe
     */
    public void afficher(){
        System.out.println("Noeuds :");
        for (int i=0;i<this.nbsommets;i++){
            System.out.println(this.lstNoeuds.get(i).toString());
        }
        System.out.println("Arcs :");
        for (int i=0;i<this.lstArcs.size();i++){
            System.out.println(this.lstArcs.get(i).toString());
        }
        System.out.println("Matrice :");
        String t;
        for (int i=0;i<this.nbsommets;i++){
            t = "|";
            for (int j=0;j<this.nbsommets;j++){
                t += this.adj[i][j] + "|";
            }
            System.out.println(t);
        }
    }
    
    /**
     * Méthode permettant de copier le Graphe sans créer d'alias
     * @return un Graphe
     */
    public abstract Graphe copie();

    /**
     * Méthode permettant d'ajouter un sommet si cela est encore possible
     * Modifie aussi la version précédente du graphe 
     * pour pouvoir revenir en arrière
     */
    public void addSommet(String n,int ind){
        if (this.nbsommets < Graphe.nbmax){
            /*Ajout du sommet en ajoutant des zéros à adj*/
            for (int i=0;i<=this.nbsommets;i++){
                this.adj[i][this.nbsommets] = 0;
                this.adj[this.nbsommets][i] = 0;
            }
            Noeud s = new Noeud(n,ind);
            s.setId(this.nbsommets);
            this.lstNoeuds.add(s);
            ++this.nbsommets;
        } else {
            System.out.println("Impossible d'ajouter un sommet supplémentaire");
        }
    }



    /**
     * Test si un Arc est bien présent dans le graphe
     * @param a un Arc
     * @return un booléen 
     * (true si l'Arc est dans le graphe, false sinon)
     */
    public boolean estPresent(Arc a){
        return this.lstArcs.contains(a);
    }

    /**
     * Méthode permettant d'ajouter un Arc passé en 
     * paramètre au Graphe si cela est possible
     * @param a l'Arc à ajouter
     */
    public abstract void addArc(Arc a);
    
    /**
     * Méthode permettant d'ajouter un arc en donnant 
     * directement ses composantes en paramètre
     * @param s = source de l'arc à ajouter
     * @param d = destination de l'arc à ajouter
     * @param p = poids de l'arc à ajouter
     */
    public void addArc(int s, int d, int p, int ind){
        Arc a = new Arc(s,d,p,ind);
        this.addArc(a);
    }

    public void sauvGraph(String sauv){
        try {
            BufferedWriter fichier = new BufferedWriter(new FileWriter(sauv));
            fichier.write(this.getClass().getSimpleName());
            fichier.newLine();
            String ligne = this.nbsommets + "";
            fichier.write(ligne);
            fichier.newLine();
            for (int i=0;i<this.nbsommets;i++){
                ligne = "";
                for (int j=0;j<this.nbsommets;j++){
                    ligne += this.adj[i][j] + " ";
                }
                fichier.write(ligne);
                fichier.newLine();
            }
            fichier.flush();
            fichier.close();
        } catch (IOException e) {
            System.out.println("Erreur");
        }
    }

    public abstract int findArc(int src, int dest);

}