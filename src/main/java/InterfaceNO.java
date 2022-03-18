/*=============================================
Classe InterfaceNO
Auteur : Béryl CASSEL
Date de création : 08/03/2022
Date de dernière modification : 08/03/2022
=============================================*/

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.JMenu;
import javax.swing.JOptionPane;


public class InterfaceNO extends Interface {

    public InterfaceNO(Draw d){
        super(d);
    }
    
    /** Actions */

    public final AbstractAction Prim = new AbstractAction(){
        {
            putValue(Action.NAME,"Prim");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
            putValue(Action.SHORT_DESCRIPTION,"Applique l'algorithme de Prim pour trouver \n"
                                            + "l'arbre couvrant minimal du graphe (CTRL+P)");
            putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_P,KeyEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent e){
            if (mode==Interface.TRAITEMENT_MODE){
                activeTraitement = Interface.PRIM_TRAITEMENT;
                d.reinit();
                d.repaint();
                boolean v = (new PrimMST()).primMST(d);
                if (!v){
                    JOptionPane.showMessageDialog(d, "Le graphe n'est pas connexe, impossible de déterminer l'ACM.", "Graphe Non Connexe !", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    };

    public final AbstractAction Kruskal = new AbstractAction(){
        {
            putValue(Action.NAME,"Kruskal");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_K);
            putValue(Action.SHORT_DESCRIPTION,"Applique l'algorithme de Kruskal pour trouver \n"
                                            + "l'arbre couvrant minimal du graphe (CTRL+K)");
            putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_K,KeyEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent e){
            if (mode==Interface.TRAITEMENT_MODE){
                //TO DO: implémenter Kruskal à l'aide de la classe Graph// 
            }
        }
    };

    public final AbstractAction ConnexiteNO = new AbstractAction() {
        {
            putValue(Action.NAME,"Connexité");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
            putValue(Action.SHORT_DESCRIPTION,"Vérifie si le graphe est connexe \n"
                                            + "(CTRL+L)");
            putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_L,KeyEvent.CTRL_DOWN_MASK));
        }
        @Override
            public void actionPerformed(ActionEvent ea) {
                if (mode==Interface.TRAITEMENT_MODE){
                    if (Connexe.connexe(new GNonOriente(d))){
                        JOptionPane.showMessageDialog(d, "Le graphe est connexe.", "Connexité", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(d, "Le graphe n'est pas connexe.", "Connexité", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
    };

    public final AbstractAction ExportGraphNO = new AbstractAction() {
        {
            putValue(Action.NAME,"Export Graphe");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_G);
            putValue(Action.SHORT_DESCRIPTION,"Exporte au format classe graphe (CTRL+G)");
            putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_G,KeyEvent.CTRL_DOWN_MASK));
        }
        @Override
            public void actionPerformed(ActionEvent ea) {
                if (mode==Interface.TRAITEMENT_MODE){
                    System.out.println(d.getNumOfCircles());
                    GNonOriente g = new GNonOriente(d);
                    g.afficher();
                }
            }
    };
    
    /**
     * JPanel pour les boutons 
     **/

    @Override
    public void addToolBar(){
        toolBarButtons.add(Prim);
        toolBarButtons.addSeparator();
        toolBarButtons.add(Kruskal);
        toolBarButtons.addSeparator();
        toolBarButtons.add(ConnexiteNO);
        toolBarButtons.addSeparator();
        toolBarButtons.add(ExportGraphNO);
    }
    
    @Override
    public void addMenuBar(){
        JMenu traitMenu = new JMenu("Traitement");
        traitMenu.add(Prim);
        traitMenu.add(Kruskal);
        traitMenu.add(ExportGraphNO);
        traitMenu.add(ConnexiteNO);
        menuBar.add(traitMenu);
    }
}
    
 