/*=============================================
Classe PrimMST définissant l'algorithme de PrimMST
Sous classe de la classe Traitement
Auteur : Samy AMAL
Date de création : 04/02/2022
Date de dernière modification : 10/02/2022
=============================================*/

import java.awt.Color;

public class Dijkstra extends Traitement{
    
    /**
     * Liste des prédecesseurs
     */
    private int predecesseur[];
    
    /**
     * Liste des distances(poids)
     */
    private int dist[];
 
    /**
     * Méthode réalisant l'algorithme de Dijkstra du PCC
     * @param g Graphe
     * @param s sommet source
     */
    public void dijkstra(Draw d, int src, int dest){

       GOriente g = new GOriente(d);
        this.dist = new int[g.getNbsommets()]; 
        // The output array. dist[i] will hold
        // the shortest distance from src to i
        
        this.predecesseur = new int[g.getNbsommets()];

        // vu[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        boolean vu[] = new boolean[g.getNbsommets()];

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < g.getNbsommets(); i++) {
            this.dist[i] = Integer.MAX_VALUE;
            vu[i] = false;
        }

        // Distance of source vertex from itself is always 0
        this.dist[src] = 0;
        // Source has no predecesseur
        this.predecesseur[src] = -1;

        // Find shortest path for all vertices
        for (int count = 0; count < g.getNbsommets() - 1; count++) {
            // Pick the minimum distance vertex from the set of vertices
            // not yet processed. u is always equal to src in first
            // iteration.
            int u = findMin(this.dist, vu, g.getNbsommets());

            // Mark the picked vertex as processed
            vu[u] = true;

            // Update dist value of the adjacent vertices of the
            // picked vertex.
            for (int v = 0; v < g.getNbsommets(); v++)

                // Update dist[v] only if is not in sptSet, there is an
                // edge from u to v, and total weight of path from src to
                // v through u is smaller than current value of dist[v]
                if (!vu[v] && g.getAdj()[u][v] != 0 && this.dist[u] != Integer.MAX_VALUE && this.dist[u] + g.getAdj()[u][v] < this.dist[v]){
                    this.dist[v] = this.dist[u] + g.getAdj()[u][v];
                    this.predecesseur[v]= u;
                }
        }
        printResult(d,src,dest);
    }
        
    /**
     * Méthode permettant d'afficher le résultat
     * dans l'ordre croissant des distances parcourues
     */
    public void printResult(Draw d,int src,int dest){
        System.out.println("Vertex \t\t Distance from Source \t\t Predecesseurs");
        int s = dest;
        int p = predecesseur[s];
        int count = 0;
        //index dist min and last dist
        while ((s!=src) && (count<d.getNumOfCircles()) && (p!=-1)){
            System.out.println(s + " \t\t " + dist[s] + " \t\t\t\t " + p);
            int ind = d.findLine(p, s);
            d.getLines().get(ind).setC(Color.RED);
            s = p;
            p = predecesseur[p];
            count++;
        }
        d.repaint();
        if ((count==Graphe.nbmax) || (p==-1)){
            System.out.println("Il n'existe pas de chemin entre ces deux sommets");
        }
    }

    /**
     * Getter de la liste des prédecesseurs
     * @return predecesseur
     */
    public int[] getPredecesseur() {
        return predecesseur;
    }

    /**
     * Getter de la liste des distances(poids) parcourues
     * @return dist
     */
    public int[] getDist() {
        return dist;
    }
    
    

}

