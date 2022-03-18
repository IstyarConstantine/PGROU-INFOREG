/*=============================================
Classe Traitement
Auteur : Samy AMAL
Date de création : 02/02/2022
Date de dernière modification : 02/02/2022
=============================================*/

public interface Traitement {
        
    // A utility function to find the vertex with minimum distance value,
    // from the set of vertices not yet included in shortest path tree
    
    public default int findMin(int dist[], boolean vu[], int nbsommets){
        
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;
        
        for (int v = 0; v < nbsommets; v++) 
            if (vu[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        
        return min_index;
    }    
}
