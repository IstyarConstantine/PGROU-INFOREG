import java.util.Stack;

public class GOriente extends Graphe {

    public GOriente(){
        super();
    }

    @Override
    public GOriente copie(){
        GOriente nouv = new GOriente();
        int n = this.getNbsommets();
        int[][] mat = new int[nbmax][nbmax];
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++) {
                mat[i][j] = this.getAdj()[i][j];
            }
        }
        int count = 0;
        Stack<Graphe> aux = new Stack<Graphe>();
        while ((!(this.modif.isEmpty())) && (count<10)){
            ++count;
            aux.push(this.modif.pop());
        }
        nouv.setAdj(mat);
        nouv.setNbsommets(n);
        while (!(aux.isEmpty())){
            nouv.modif.push(aux.pop());
        }
        return nouv;
    }
    
    @Override
    public void addArc(Arc a){
        int s = a.getSrc();
        int d = a.getSrc();
        if ((s<this.nbsommets) && (d<this.nbsommets)){
            if (this.adj[s][d] != 0){
                this.modif.push(this.copie());
                this.adj[s][d] = a.getPoids();
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
            this.modif.push(this.copie());
            this.adj[a.getSrc()][a.getDest()] = 0;
        } else {
            System.out.println("L'arc n'est pas dans le graphe");
        }
    }

    @Override
    public void modifArc(Arc a, int p) {
        if (this.estPresent(a)){
            this.modif.push(this.copie());
            this.adj[a.getSrc()][a.getDest()] = p;
        } else {
            System.out.println("L'arc à modifier n'est pas dans le graphe");
        }  
    }
}
