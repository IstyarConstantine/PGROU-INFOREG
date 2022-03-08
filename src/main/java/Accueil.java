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
        JFrame J = new JFrame("Choix");
        J.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        J.setSize(200,100);
        J.setLocationRelativeTo(null);
        JPanel contentPane = (JPanel) J.getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        JButton oriente = new JButton("Graphe Orienté");
        oriente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                SwingUtilities.invokeLater(new InterfaceO()::createAndShowGui);
            }
        });
        JButton nonoriente = new JButton("Graphe non orienté");
        nonoriente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                SwingUtilities.invokeLater(new InterfaceNO()::createAndShowGui);
            }
        });
        contentPane.add(oriente);
        contentPane.add(nonoriente);
        J.setVisible(true);
        
        //SwingUtilities.invokeLater(new Interface()::createAndShowGui);
    }

}