import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;

/*=============================================
Classe abstraite Graphe définissant la structure 
générale d'un graphe
Auteur : Béryl CASSEL
Date de création : 27/01/2022
Date de dernière modification : 01/03/2022
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

    /**
     * Version précédente du graphe, stockée à chaque modifications
     */
    protected Graphe version;

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
    public Graphe getVersion(){
        return version;
    }

    /**
     * Setter du graphe de version précédente
     * @param version
     */
    public void setVersion(Graphe version){
        this.version = version;
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
    public void addSommet(int x, int y, Color c, String n){
        if (this.nbsommets < Graphe.nbmax){
            /*Sauvegarde du Graphe actuel*/
            this.version = this.copie();
            /*Ajout du sommet en ajoutant des zéros à adj*/
            for (int i=0;i<=this.nbsommets;i++){
                this.adj[i][this.nbsommets] = 0;
                this.adj[this.nbsommets][i] = 0;
            }
            Noeud s = new Noeud(x,y,c,n);
            s.setId(this.nbsommets);
            this.lstNoeuds.add(s);
            ++this.nbsommets;
        } else {
            System.out.println("Impossible d'ajouter un sommet supplémentaire");
        }
    }

    /**
     * Méthode permettant de supprimer un sommet et les arcs 
     * passant par lui si cela est encore possible
     * Modifie aussi la version précédente du graphe 
     * pour pouvoir revenir en arrière
     */
    public void suppSommet(int id){
        if (this.nbsommets == 0){
            System.out.println("Il n'y a pas de sommet à supprimer");
        } else {
            /*Sauvegarde du Graphe actuel*/
            this.version = this.copie();
            /*Suppression du sommet*/
            ArrayList<Noeud> aux1 = new ArrayList<Noeud>();
            int[][] aux2 = new int[Graphe.nbmax][Graphe.nbmax];
            for (int i=0;i<this.nbsommets;i++){
                if (i<id){
                    aux1.add(this.lstNoeuds.get(i));
                    for (int j=0; j<=i;j++){
                        aux2[i][j]=this.adj[i][j];
                        aux2[j][i]=this.adj[i][j];
                    }
                } else {
                    if (i>id){
                        Noeud s = this.lstNoeuds.get(i).deplace();
                        aux1.add(s);
                        for (int j=0;j<=i-1;j++){
                            if (j<id){
                                aux2[j][i-1]=this.adj[j][i];
                                aux2[i-1][j]=this.adj[i][j];
                            } else {
                                aux2[j][i-1]=this.adj[j+1][i];
                                aux2[i-1][j]=this.adj[i][j+1];
                            }
                        }
                    }
                }
            }
            ArrayList<Arc> aux3 = new ArrayList<Arc>();
            for (int i=0;i<this.lstArcs.size();i++){
                Arc a = this.lstArcs.get(i);
                if (a.decale(id)){
                    aux3.add(a);
                }
            }
            this.lstNoeuds = aux1;
            this.adj = aux2;
            this.lstArcs = aux3;
            --this.nbsommets;
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
    public void addArc(int s, int d, int p, Color c){
        Arc a = new Arc(s,d,p);
        a.setCol(c);
        this.addArc(a);
    }

    /**
     * Méthode permettant de supprimer un Arc passé en 
     * paramètre au Graphe si cela est possible
     * @param a l'Arc à supprimer
     */
    public abstract void suppArc(Arc a);
    
    /**
     * Méthode permettant de supprimer un arc en donnant 
     * directement ses composantes en paramètre
     * @param s = source de l'arc à supprimer
     * @param d = destination de l'arc à supprimer
     * @param p = poids de l'arc à supprimer
     */
    public void suppArc(int s, int d, int p, Color c){
        Arc a = new Arc(s,d,p);
        a.setCol(c);
        this.suppArc(a);
    }

    /**
     * Méthode permettant de modifier le poids 
     * d'un Arc passé en paramètre
     * @param a l'Arc à modifier
     * @param p le poids à lui attribuer
     */
    public abstract void modifArc(Arc a, int p);
    
    /**
     * Méthode permettant de modifier le poids 
     * d'un arc en entrant ses composantes en paramètre
     * @param s = source de l'arc à modifier
     * @param d = destination de l'arc à modifier
     * @param p1 = poids de l'arc à modifier
     * @param p2 = nouveau poids à lui attribuer
     */
    public void modifArc(int s, int d, int p1, int p2, Color c){
        Arc a = new Arc(s,d,p1);
        a.setCol(c);
        this.modifArc(a,p2);
    }

    /**
     * Méthode permettant de revenir à la version précédente
     * du Graphe à l'aide de son attribut version
     */
    public void retourEnArriere(){
        /*Si la version est null, on ne peut rien faire*/
        if (version == null){
            System.out.println("Impossible de revenir en arriere");
        /*Sinon, on change les attributs du Graphe courant avec ceux
        du graphe de version*/
        } else {
            Graphe prec = this.getVersion();
            this.adj = prec.getAdj();
            this.nbsommets = prec.getNbsommets();
            this.version = prec.getVersion();
        }
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

    protected void placeArc(Arc a){
        int i = 0;
        boolean place = false;
        int s = a.getSrc();
        int d = a.getDest();
        ArrayList<Arc> aux = new ArrayList<Arc>();
        while ((i<this.lstArcs.size()) && (!place)){
            Arc arc = this.lstArcs.get(i);
            int sbis = arc.getSrc();
            int dbis = arc.getDest();
            if (sbis < s){
                aux.add(arc);
                ++i;
            } else {
                if (sbis == s){
                    if (dbis < d){
                        aux.add(arc);
                        ++i;
                    } else {
                        aux.add(a);
                        place = true;
                    }
                } else {
                    aux.add(a);
                    place = true;
                }
            }
        }
        if (place){
            while (i<this.lstArcs.size()){
                aux.add(this.lstArcs.get(i));
                ++i;
            }
        } else {
            aux.add(a);
        }
        this.lstArcs = aux;
    }

}