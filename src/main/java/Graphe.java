import java.util.Stack;

public abstract class Graphe {

    protected final int nbmax = 100;
    protected int nbsommets;
    protected int[][] adj;
    protected Stack<Graphe> modif;

    @SuppressWarnings("Convert2Diamond")
   public Graphe(){
       this.nbsommets = 0;
       this.adj = new int[nbmax][nbmax];
       this.modif = new Stack<Graphe>();
   }

    public Stack<Graphe> getModif() {
        return modif;
    }

    public void setModif(Stack<Graphe> modif) {
        this.modif = modif;
    }

    public int[][] getAdj() {
        return adj;
    }

    public void setAdj(int[][] adj) {
        this.adj = adj;
    }

    public int getNbsommets() {
        return nbsommets;
    }

    public void setNbsommets(int nbsommets) {
        this.nbsommets = nbsommets;
    }

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
    
    public abstract Graphe copie();

    public void addSommet(){
        if (this.nbsommets < this.nbmax){
            modif.push(this.copie());
            for (int i=0;i<=this.nbsommets;i++){
                this.adj[i][this.nbsommets] = 0;
                this.adj[this.nbsommets][i] = 0;
            }
            ++this.nbsommets;
        } else {
            System.out.println("Impossible d'ajouter un sommet supplémentaire");
        }
    }

    public void suppSommet(){
        if (this.nbsommets == 0){
            System.out.println("Il n'y a pas de sommet à supprimer");
        } else {
            modif.push(this.copie());
            --this.nbsommets;
        }
    }

    public boolean estPresent(Arc a){
        return (a.getSrc() < this.nbsommets) && (a.getDest() < this.nbsommets) && (this.adj[a.getSrc()][a.getDest()] == a.getPoids());
    }

    public abstract void addArc(Arc a);
    
    public void addArc(int s, int d, int p){
        Arc a = new Arc(s,d,p);
        this.addArc(a);
    }

    public abstract void suppArc(Arc a);
    
    public void suppArc(int s, int d, int p){
        Arc a = new Arc(s,d,p);
        this.suppArc(a);
    }

    public abstract void modifArc(Arc a, int p);
    
    public void modifArc(int s, int d, int p1, int p2){
        Arc a = new Arc(s,d,p1);
        this.modifArc(a,p2);
    }

    public void retourEnArriere(){
        if (modif.empty()){
            System.out.println("Impossible de revenir en arriere");
        } else {
            Graphe prec = modif.pop();
            this.adj = prec.getAdj();
            this.nbsommets = prec.getNbsommets();
            this.modif = prec.getModif();
        }
    }

}
