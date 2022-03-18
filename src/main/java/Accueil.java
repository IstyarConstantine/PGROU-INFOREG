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
import javax.swing.JOptionPane;
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
        JButton orientepond = new JButton("Graphe Orienté Pondéré");
        orientepond.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Draw d = new Draw();
                d.setOriente(Draw.ORIENTE);
                SwingUtilities.invokeLater(new InterfaceO(d)::createAndShowGui);
            }
        });
        JButton nonorientepond = new JButton("Graphe Non Orienté Pondéré");
        nonorientepond.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Draw d = new Draw();
                d.setOriente(Draw.NONORIENTE);
                SwingUtilities.invokeLater(new InterfaceNO(d)::createAndShowGui);
            }
        });
        JButton oriente = new JButton("Graphe Orienté");
        oriente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Draw d = new Draw();
                d.setOriente(Draw.ORIENTE);
                d.setPondere(false);
                SwingUtilities.invokeLater(new InterfaceO(d)::createAndShowGui);
            }
        });
        JButton nonoriente = new JButton("Graphe Non Orienté");
        nonoriente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Draw d = new Draw();
                d.setOriente(Draw.NONORIENTE);
                d.setPondere(false);
                SwingUtilities.invokeLater(new InterfaceNO(d)::createAndShowGui);
            }
        });
        JButton charge = new JButton("Charger un Graphe existant");
        charge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    String lbl = JOptionPane.showInputDialog("Entrer le nom du fichier à charger :");
                    Draw d = (new ChargeDraw(lbl)).chargerDraw();
                    switch (d.getOriente()){
                        case Draw.ORIENTE:
                            SwingUtilities.invokeLater(new InterfaceO(d)::createAndShowGui);
                            break;
                        case Draw.NONORIENTE:
                            SwingUtilities.invokeLater(new InterfaceNO(d)::createAndShowGui);
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Sauvegarde inexistante", "No Save !!", JOptionPane.INFORMATION_MESSAGE);
                            break;
                    }
                } catch (Exception NullPointerException){
                    JOptionPane.showMessageDialog(null, "Sauvegarde inexistante", "No Save !!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        contentPane.add(orientepond);
        contentPane.add(oriente);
        contentPane.add(nonorientepond);
        contentPane.add(nonoriente);
        contentPane.add(charge);
        J.setVisible(true);
        
        //SwingUtilities.invokeLater(new Interface()::createAndShowGui);
    }

}