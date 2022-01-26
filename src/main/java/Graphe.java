import java.util.Stack;

public abstract class Graphe {

    private final int nbmax = 100;
    protected int nbsommets;
    protected int[][] adj;
    protected Stack<String> modif;
    protected Stack<Arc> lstArc;

   public Graphe(){
       this.nbsommets = 0;
       this.adj = new int[nbmax][nbmax];
       this.modif = new Stack<String>();
       this.lstArc = new Stack<Arc>();
   }

    public Stack<String> getModif() {
        return modif;
    }

    public void setModif(Stack<String> modif) {
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

    public Stack<Arc> getLstArc() {
        return lstArc;
    }

    public void setLstArc(Stack<Arc> lstArc) {
        this.lstArc = lstArc;
    }
    
    public void addSommet(){
        if (this.nbsommets < this.nbmax){
            for (int i=0;i<=this.nbsommets;i++){
                this.adj[i][this.nbsommets] = 0;
                this.adj[this.nbsommets][i] = 0;
            }
            ++this.nbsommets;
            modif.push("AddSommet");
        } else {
            System.out.println("Impossible d'ajouter un sommet supplémentaire");
        }
    }

    public void suppSommet(){
        if (this.nbsommets == 0){
            System.out.println("Il n'y a pas de sommet à supprimer");
        } else {
            --this.nbsommets;
            modif.push("SuppSommet");
        }
    }

    public boolean estPresent(Arc a){
        if ((a.getSrc() < this.nbsommets) && (a.getDest() < this.nbsommets) && (this.adj[a.getSrc()][a.getDest()] == a.getPoids())){
            return true;
        } else {
            return false;
        }
    }

    public abstract void addArc(Arc a);

    public abstract void suppArc(Arc a);

    public abstract void modifArc(Arc a, int p);

    public void retourEnArriere(){
        if (modif.empty()){
            System.out.println("Impossible de revenir en arriere");
        } else {
            String inst = modif.pop();
            switch (inst){
                case "SuppSommet" -> this.addSommet();
                case "AddSommet" -> this.suppSommet();
                case "SuppArc" -> this.addArc(lstArc.pop());
                case "AddArc" -> this.suppArc(lstArc.pop());
                case "ModifArc" -> {
                    Arc nouv = lstArc.pop();
                    this.modifArc(nouv, lstArc.pop().getPoids());
                }
                default -> System.out.println("Action inconnue");
            
            }
        }
    }

}
