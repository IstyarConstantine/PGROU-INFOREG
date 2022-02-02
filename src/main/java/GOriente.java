/*=============================================
Classe GOriente définissant un Graphe orienté
Sous classe de la classe abstraite Graphe
Auteur : Béryl CASSEL
Date de création : 27/01/2022
Date de dernière modification : 02/02/2022
=============================================*/

public class GOriente extends Graphe {

    /**
     * Constructeur d'un graphe orienté 
     * (identique au constructeur d'un graphe non orienté)
     */
    public GOriente(){
        super();
    }

    @Override
    /**
     * Méthode permettant de copier un GOriente
     * @return nouv = copie du graphe
     */
    public GOriente copie(){
        GOriente nouv = new GOriente();
        int n = this.getNbsommets();
        int[][] mat = new int[nbmax][nbmax];
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++) {
                mat[i][j] = this.getAdj()[i][j];
            }
        }
        Graphe v = this.getVersion();
        nouv.setAdj(mat);
        nouv.setNbsommets(n);
        nouv.setVersion(v);
        return nouv;
    }
    
    @Override
    /**
     * Méthode d'ajout d'un arc orienté dans le graphe
     * Avec sauvegarde du graphe initial
     * @param a = Arc à ajouter
     */
    public void addArc(Arc a){
        int s = a.getSrc();
        int d = a.getDest();
        if ((s<this.nbsommets) && (d<this.nbsommets)){
            if (this.adj[s][d] == 0){
                /*Si l'arc n'existe pas, on copie l'ancienne version
                du graphe et on modifie la matrice d'adjacence*/
                this.version = this.copie();
                this.adj[s][d] = a.getPoids();
            } else {
                System.out.println("Il existe déjà un arc entre ces deux sommets");
            }
        } else {
            System.out.println("Impossible d'ajouter cet arc");
        }
    }

    @Override
    /**
     * Méthode permettant de supprimer un arc orienté présent dans le graphe
     * Avec sauvegarde du graphe initial
     * @param a = Arc à supprimer
     */
    public void suppArc(Arc a){
        if (this.estPresent(a)){
            /*Si l'arc est présent, on le supprime*/
            this.version = this.copie();
            this.adj[a.getSrc()][a.getDest()] = 0;
        } else {
            System.out.println("L'arc n'est pas dans le graphe");
        }
    }

    @Override
    /**
     * Méthode permettant de modifier un arc orienté présent dans le graphe
     * Avec sauvegarde du graphe initial
     * @param a = Arc à modifier
     * @param p = poids à attribuer à l'Arc
     */
    public void modifArc(Arc a, int p) {
        if (this.estPresent(a)){
            /*Si l'arc est présent, on lui attribue 
            le poids passé en paramètre*/
            this.version = this.copie();
            this.adj[a.getSrc()][a.getDest()] = p;
        } else {
            System.out.println("L'arc à modifier n'est pas dans le graphe");
        }  
    }
}
