import java.util.PriorityQueue;

/*=============================================
Classe Connexe contenant les méthodes
permettants de vérifier si un graphe est connexe
Auteur : Béryl CASSEL
Date de création : 18/03/2022
Date de dernière modification : 18/03/2022
=============================================*/

public interface Connexe {

    public static boolean existeChemin(Graphe g, int u, int v){
        boolean[] vu = new boolean[g.getNbsommets()];
        for (int i=0;i<g.getNbsommets();i++){
            vu[i] = false;
        }
        PriorityQueue<Integer> q = new PriorityQueue<Integer>();
        q.add(u);
        while (!(q.isEmpty())){
            int ind = q.poll();
            vu[ind]=true;
            for (int j=0;j<g.getNbsommets();j++){
                if ((g.getAdj()[ind][j]>0) && (!vu[j])){
                    q.add(j);
                    vu[j]=true;
                }  
                if ((g.getAdj()[ind][j]>0) && (j==v)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean connexe(GNonOriente g){
        for (int i=0;i<g.getNbsommets();i++){
            for (int j=i+1;j<g.getNbsommets();j++){
                if (!existeChemin(g, i, j)){
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean connexe(GOriente g){
        for (int i=0;i<g.getNbsommets();i++){
            for (int j=0;j<g.getNbsommets();j++){
                if (!existeChemin(g, i, j)){
                    return false;
                }
            }
        }
        return true;
    }
    
}
