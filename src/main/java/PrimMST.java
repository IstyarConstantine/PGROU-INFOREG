/*=============================================
Classe PrimMST définissant l'algorithme de PrimMST
Sous classe de la classe Traitement
Auteur : Samy AMAL
Date de création : 04/02/2022
Date de dernière modification : 18/03/2022
=============================================*/

import java.awt.Color;

public class PrimMST implements Connexe, Traitement {

    /**
     * Arbre couvrant minimal
     */
    private Arc arbre[];
    
    // Function to construct and print MST for a graph represented
    // using adjacency matrix representation
    public boolean primMST(Draw d) {
        
        GNonOriente G = new GNonOriente(d);
        this.arbre = new Arc[G.nbsommets];
        boolean verif = connexe(G);
        if (verif){
            // To represent set of vertices included in MST
            boolean vu[] = new boolean[G.nbsommets];

            // Initialize all keys as INFINITE
            for (int i = 0; i < G.nbsommets; i++) {
                this.arbre[i] = new Arc(-1,-1,Integer.MAX_VALUE,0);
                vu[i] = false;
            }

            // Always include first 1st vertex in MST.
            this.arbre[0].setPoids(0) ; // Make key 0 so that this vertex is
            // picked as first vertex
            this.arbre[0].setSrc(-1) ; // First node is always root of MST

            // The MST will have V vertices
            for (int count = 0; count < G.nbsommets - 1; count++) {
                // Pick thd minimum key vertex from the set of vertices
                // not yet included in MST
                int u = findMin(listePoids(), vu, G.nbsommets);

                // Add the picked vertex to the MST Set
                vu[u] = true;

                // Update key value and parent index of the adjacent
                // vertices of the picked vertex. Consider only those
                // vertices which are not yet included in MST
                for (int v = 0; v < G.nbsommets; v++)

                    // graph[u][v] is non zero only for adjacent vertices of m
                    // mstSet[v] is false for vertices not yet included in MST
                    // Update the key only if graph[u][v] is smaller than key[v]
                    if (G.adj[u][v] != 0 && vu[v] == false && G.adj[u][v] < this.arbre[v].getPoids()) {
                        this.arbre[v] = new Arc(v, u, G.adj[u][v],G.findArc(u,v));
                    }
            }
            printResult(d);
        }
        return verif;
    }
    
    // Méthode d'affichage des résultats de l'algorithme de Prim
    public void printResult(Draw d){
        System.out.println("Edge \tWeight");
        for (int i = 1; i<this.arbre.length; i++){
            int src = this.arbre[i].getSrc();
            int dest = this.arbre[i].getDest();
            System.out.println(src + " - " + dest + "\t" + this.arbre[i].getPoids());
            d.getLines().get(this.arbre[i].getLine()).setC(Color.RED);
        }
        d.repaint();
        }

    /**
     * Méthode permettant d'obtenir la liste des poids de l'arbre
     * @return liste des poids
     */
    public int[] listePoids (){
        int poids[] = new int[this.arbre.length];
        for(int i=0; i<this.arbre.length; i++){ 
            poids[i]=this.arbre[i].getPoids();
        }
        return poids;
    }
    
    /**
     * Méthode permettant d'obtenir le graphe représentant l'arbre couvrant minimal
     * @return graphe de l'arbre
     */
    public Graphe toGraphe(){
        Graphe g = new GNonOriente();
        g.setNbsommets(this.arbre.length);
        int adj[][] = new int[this.arbre.length][this.arbre.length];
        for(int i = 1; i<this.arbre.length; i++){
            adj[this.arbre[i].getSrc()][this.arbre[i].getDest()] = this.arbre[i].getPoids();
            adj[this.arbre[i].getDest()][this.arbre[i].getSrc()] = this.arbre[i].getPoids();
        }
        g.setAdj(adj);
        return g;
    }
    
    /**
     * Getter de l'arbre couvrant minimal
     * @return l'arbre
     */
    public Arc[] getArbre() {
        return arbre;
    }
    
}

