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


public class InterfaceNO extends Interface {
    
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
                (new PrimMST()).primMST(d);
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
                System.out.println(d.getNumOfCircles());
                GNonOriente g = new GNonOriente(d);
                g.afficher();
            }
    };
    
    /**
     * JPanel pour les boutons 
     **/

    @Override
    public void addToolBar(){
        toolBarButtons.add(Prim);
        toolBarButtons.addSeparator();
        toolBarButtons.add(ExportGraphNO);
    }
    
    @Override
    public void addMenuBar(){
        JMenu traitMenu = new JMenu("Traitement");
        traitMenu.add(Prim);
        traitMenu.add(ExportGraphNO);
        menuBar.add(traitMenu);
    }
}
    
 