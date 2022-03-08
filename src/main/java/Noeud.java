/*=============================================
Classe Noeud définissant un noeud du graphe
Auteur : Béryl CASSEL
Date de création : 27/01/2022
Date de dernière modification : 08/03/2022
=============================================*/

public class Noeud {

    /**
     * Indice du cercle correspondant la liste circ de la classe Draw
     */
    private int id;

    /**
     * Nom du noeud dans l'interface
     */
    private String nom;

    public Noeud(String n,int ind){
        this.setNom(n);
        this.setId(ind);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return this.id + " : " + this.nom;
    }
    
}
