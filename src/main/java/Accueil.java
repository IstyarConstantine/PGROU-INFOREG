/*=============================================
Classe Accueil 
Auteur : Béryl CASSEL
Date de création : 08/03/2022
Date de dernière modification : 08/03/2022
=============================================*/

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Accueil {  

    public static void main(String[] args) {
        JFrame J = new JFrame("INFOREG");
        J.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        J.setSize(200,200);
        J.setLocationRelativeTo(null);
        JPanel contentPane = (JPanel) J.getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        JButton oriente = new JButton("Graphe Orienté");
        oriente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Draw d = new Draw();
                d.setOriente(true);
                SwingUtilities.invokeLater(new InterfaceO(d)::createAndShowGui);
            }
        });
        JButton nonoriente = new JButton("Graphe non orienté");
        nonoriente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Draw d = new Draw();
                d.setOriente(false);
                SwingUtilities.invokeLater(new InterfaceNO(d)::createAndShowGui);
            }
        });
        JButton charge = new JButton("Charger un Graphe existant");
        charge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //TO DO: Méthode de charge de graphe//
            }
        });
        contentPane.add(oriente);
        contentPane.add(nonoriente);
        contentPane.add(charge);
        J.setVisible(true);
        
        //SwingUtilities.invokeLater(new Interface()::createAndShowGui);
    }

}