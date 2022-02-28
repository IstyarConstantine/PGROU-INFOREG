
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TestGraphe {
    
    public static void main(String[] args){
        JFrame f = new JFrame("ma fenetre");
        f.setSize(300,100);
        JPanel pannel = new JPanel(); 

        JButton bouton = new JButton("saisir");
        pannel.add(bouton);

        JTextField jEdit = new JTextField("votre nom");

        JLabel jLabel1 =new JLabel("Nom : "); 
        jLabel1.setBackground(Color.red);
        jLabel1.setDisplayedMnemonic('n');
        jLabel1.setLabelFor(jEdit);
        pannel.add(jLabel1);
        pannel.add(jEdit);

        f.getContentPane().add(pannel);
        f.setVisible(true);

        GNonOriente g = new GNonOriente();
        GNonOriente d = g.copie();
        for (int i=0;i<5;i++){
            g.addSommet();
        }
        g.addArc(4,0,2,Color.BLACK);
        g.addArc(2,3,5,Color.BLACK);
        g.addArc(6,1,6,Color.BLACK);
        g.addArc(1,2,1,Color.BLACK);
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
