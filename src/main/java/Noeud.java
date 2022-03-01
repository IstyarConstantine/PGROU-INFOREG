/*=============================================
Classe Noeud définissant un noeud du graphe
Auteur : Béryl CASSEL
Date de création : 27/01/2022
Date de dernière modification : 01/03/2022
=============================================*/

import java.awt.Color;

public class Noeud {

    /**
     * Identifiant du noeud dans le graphe
     */
    private int id;

    /**
     * Nom du noeud dans l'interface
     */
    private String nom;

    /**
     * Abcisse du noeud dans l'interface
     */
    private float x;

    /**
     * Ordonnée du noeud dans l'interface
     */
    private float y;

    /**
     * Couleur du noeud dans l'interface
     */
    private Color col;

    public Noeud(float x, float y, Color c, String n){
        this.setX(x);
        this.setY(y);
        this.setCol(c);
        this.setNom(n);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Color getCol() {
        return col;
    }

    public void setCol(Color col) {
        this.col = col;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Noeud deplace(){
        Noeud n = new Noeud(this.getX(), this.getY(), this.getCol(), this.getNom());
        n.setId(this.getId()-1);
        return n;
    }

    public String toString(){
        return this.id + " : [" + this.x +";" + this.y + "]";
    }
    
}
