/*=============================================
Classe PrimMST définissant l'algorithme de PrimMST
Sous classe de la classe Traitement
Auteur : Samy AMAL
Date de création : 04/02/2022
Date de dernière modification : 10/02/2022
=============================================*/

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
     * Sommet source
     */
    private int src;
 
    /**
     * Méthode réalisant l'algorithme de Dijkstra du PCC
     * @param g Graphe
     * @param s sommet source
     */
    public void dijkstra(Graphe g, int s){
       
        this.dist = new int[g.getNbsommets()]; 
        // The output array. dist[i] will hold
        // the shortest distance from src to i
        
        this.predecesseur = new int[g.getNbsommets()];
        
        this.src = s ; //EST-CE UTILE ??

        // vu[i] will true if vertex i is included in shortest
        // path tree or shortest distance from src to i is finalized
        boolean vu[] = new boolean[g.getNbsommets()];

        // Initialize all distances as INFINITE and stpSet[] as false
        for (int i = 0; i < g.getNbsommets(); i++) {
            this.dist[i] = Integer.MAX_VALUE;
            vu[i] = false;
        }

        // Distance of source vertex from itself is always 0
        this.dist[s] = 0;
        // Source has no predecesseur
        this.predecesseur[s] = -1;

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
    }
        
    /**
     * Méthode permettant d'afficher le résultat
     * dans l'ordre croissant des distances parcourues
     */
    public void printResult(){
        System.out.println("Vertex \t\t Distance from Source \t\t Predecesseurs");
        
        //we print dist and predecesseur for the first vertex
        System.out.println(0 + " \t\t " + dist[0] + " \t\t\t\t " + this.predecesseur[0]);
        
        //index dist min and last dist
        int minIndex = 0 ;
        int minDist = Integer.MAX_VALUE;
        int lastDist = 0 ;
        //compteur
        int c = 0;

        while(c < this.dist.length-1){
            for (int i = 1; i < this.dist.length; i++){
                if(this.dist[i]<minDist && this.dist[i]>lastDist){
                    minDist = this.dist[i];
                    minIndex = i;
                }
            }
            System.out.println(minIndex + " \t\t " + dist[minIndex] + " \t\t\t\t " + this.predecesseur[minIndex]);
            lastDist = minDist;
            minDist = Integer.MAX_VALUE; 
            c = c+1 ;
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

    /**
     * Getter du sommet source
     * @return src
     */
    public int getSrc() {
        return src;
    }
    
    

}

