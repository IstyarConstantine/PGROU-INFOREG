
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.StringTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author beryl
 */
public class ChargeGraphe {
    
    private final String source;
    
    public ChargeGraphe(String sauv){
        this.source = sauv;
    }
    
    /**
     * 
     * @return 
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public Graphe chargeGraphe() throws NoSuchMethodException, InstantiationException, 
            IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Graphe graph = null;
        try {
            BufferedReader fichier = new BufferedReader(new FileReader(this.source));
            StringTokenizer tokenizer = new StringTokenizer(fichier.readLine(), " ");
            String ligne = tokenizer.nextToken();
            try {
                Class type = Class.forName(ligne);
                Constructor construct = type.getConstructor(StringTokenizer.class);
                graph = (Graphe) construct.newInstance();
                tokenizer = new StringTokenizer(fichier.readLine(), " ");
                String valeur = tokenizer.nextToken();
                graph.setNbsommets(Integer.parseInt(valeur));
                int[][] mat = new int[graph.nbmax][graph.nbmax];
                for (int i=0;i<graph.getNbsommets();i++){
                    tokenizer = new StringTokenizer(fichier.readLine()," ");
                    for (int j=0;j<graph.getNbsommets();j++){
                        mat[i][j] = Integer.parseInt(tokenizer.nextToken());
                    }
                }
                graph.setAdj(mat);
            } catch (ClassNotFoundException e) {
                System.out.println("Classe inconnue");
            }
        } catch (IOException e){
            System.out.println("Erreur");
        }
        return(graph);
    }
}
