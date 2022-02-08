public class TestGraphe {
    
    public static void main(String[] args){
        GNonOriente g = new GNonOriente();
        GNonOriente d = g.copie();
        for (int i=0;i<5;i++){
            g.addSommet();
        }
        g.addArc(4,0,2);
        g.addArc(2,3,5);
        g.addArc(6,1,6);
        g.addArc(1,2,1);
        g.afficher();
        System.out.println("\n");
        d.addSommet();
        g.afficher();
        System.out.println("\n");
        d.afficher();
        System.out.println("\n");
        g.retourEnArriere();
        g.afficher();
        System.out.println("\n");
        d.afficher();
        System.out.println("\n");
        d.retourEnArriere();
        d.afficher();
        System.out.println("\n");
        g.afficher();
        System.out.println("\n");
        g.sauvGraph("sauv.txt");
    }
}
