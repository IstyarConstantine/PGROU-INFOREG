/*=============================================
Classe abstraite Graphe définissant la structure 
générale d'un graphe
Auteur : Béryl CASSEL
Date de création : 27/01/2022
Date de dernière modification : 18/03/2022
=============================================*/

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

    protected ArrayList<Arc> lstArcs;

    /**
     * Constructeur d'un graphe
     */
    public Graphe(){
        this.adj = new int[Graphe.nbmax][Graphe.nbmax];
        this.nbsommets = 0;
        this.lstArcs = new ArrayList<Arc>();
    }

    public Graphe(Draw d){
        this.nbsommets = 0;
        this.adj = new int[Graphe.nbmax][Graphe.nbmax];
        this.lstArcs = new ArrayList<Arc>();
        for (int i=0;i<d.getNumOfCircles();i++){
            addSommet(d.getCircLbl()[i],i);
        }
        ArrayList<MyLine> lines = d.getLines();
        for (int i=0;i<d.getNumOfLines();i++){
            MyLine l = lines.get(i);
            int p = l.getPoids();
            int src = d.findEllipse(l.getFromPoint().x,l.getFromPoint().y);
            int dest = d.findEllipse(l.getToPoint().x,l.getToPoint().y);
            addArc(src, dest, p, i);
        }
    }

    public ArrayList<Arc> getLstArcs() {
        return lstArcs;
    }

    public void setLstArcs(ArrayList<Arc> lstArcs) {
        this.lstArcs = lstArcs;
    }

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
     * Méthode permettant de générer un String représentant
     * la matrice d'adjacence du graphe
     * @return un String représentant la matrice
     */
    public String afficher(){
        int d = findMaxDecimal();
        String mat = "";
        for (int i = 0;i<nbsommets;i++){
            mat += "|";
            for (int j = 0; j<nbsommets;j++){
                mat += formatInt(adj[i][j], d) + "|";
            }
            mat += "\n";
        }
        return mat;
    }

    public int findMaxDecimal(){
        int max = 0;
        for (int i=0;i<nbsommets;i++){
            for (int j=0;j<nbsommets;j++){
                if (String.valueOf(adj[i][j]).length()>max){
                    max = String.valueOf(adj[i][j]).length();
                }
            }
        }
        return max;
    }

    public String formatInt(int n, int d){
        String aux = String.valueOf(n);
        int count = d - aux.length();
        while (count>0){
            aux = "_" + aux;
            --count;
        }
        return aux;
    }

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