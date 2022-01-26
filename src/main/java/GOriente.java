public class GOriente extends Graphe {

    public GOriente(){
        super();
    }
    
    @Override
    public void addArc(Arc a){
        int s = a.getSrc();
        int d = a.getSrc();
        if ((s<this.nbsommets) && (d<this.nbsommets)){
            if (this.adj[s][d] != 0){
                this.adj[s][d] = a.getPoids();
                this.lstArc.push(a);
                this.modif.push("AddArc");
            } else {
                System.out.println("Il existe déjà un arc entre ces deux sommets");
            }
        } else {
            System.out.println("Impossible d'ajouter cet arc");
        }
    }

    @Override
    public void suppArc(Arc a){
        if (this.estPresent(a)){
            this.adj[a.getSrc()][a.getDest()] = 0;
            this.modif.push("SuppArc");
            this.lstArc.push(a);
        } else {
            System.out.println("L'arc n'est pas dans le graphe");
        }
    }

    @Override
    public void modifArc(Arc a, int p) {
        if (this.estPresent(a)){
            this.adj[a.getSrc()][a.getDest()] = p;
            this.modif.push("ModifArc");
            Arc nouv = new Arc(a.getSrc(), a.getDest(), p);
            this.lstArc.push(a);
            this.lstArc.push(nouv);
        } else {
            System.out.println("L'arc à modifier n'est pas dans le graphe");
        }  
    }
}
