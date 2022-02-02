/*=============================================
Classe abstraite Graphe définissant la structure 
générale d'un graphe
Auteur : Béryl CASSEL
Date de création : 27/01/2022
Date de dernière modification : 02/02/2022
=============================================*/

public abstract class Graphe {

    /**
     * Nombre maximal de sommets que peut contenir un graphe 
     */
    protected final int nbmax = 100;

    /**
     * Nombre de sommets du graphe considéré
     */
    protected int nbsommets;

    /**
     * Matrice d'adjacence
     */
    protected int[][] adj;

    /**
     * Version précédente du graphe, stockée à chaque modifications
     */
    protected Graphe version;

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
    public void addSommet(){
        if (this.nbsommets < this.nbmax){
            /*Sauvegarde du Graphe actuel*/
            this.version = this.copie();
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
     * Méthode permettant de supprimer un sommet et les arcs 
     * passant par lui si cela est encore possible
     * Modifie aussi la version précédente du graphe 
     * pour pouvoir revenir en arrière
     */
    public void suppSommet(){
        if (this.nbsommets == 0){
            System.out.println("Il n'y a pas de sommet à supprimer");
        } else {
            /*Sauvegarde du Graphe actuel*/
            this.version = this.copie();
            /*Suppression du dernier sommet*/
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
        return (a.getSrc() < this.nbsommets) 
            && (a.getDest() < this.nbsommets) 
            && (this.adj[a.getSrc()][a.getDest()] == a.getPoids());
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
    public void addArc(int s, int d, int p){
        Arc a = new Arc(s,d,p);
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
    public void suppArc(int s, int d, int p){
        Arc a = new Arc(s,d,p);
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
    public void modifArc(int s, int d, int p1, int p2){
        Arc a = new Arc(s,d,p1);
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

}