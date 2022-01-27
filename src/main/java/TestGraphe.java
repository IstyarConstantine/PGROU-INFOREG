public class TestGraphe {
    
    public static void main(String[] args){
        GNonOriente g = new GNonOriente();
        GNonOriente d = g.copie();
        g.addSommet();
        g.addSommet();
        d.addSommet();
        g.afficher();
        d.afficher();
    }
}
