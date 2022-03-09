/*=============================================
Classe InterfaceO
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

public class InterfaceO extends Interface {

    public InterfaceO(Draw d){
        super(d);
    }

    /** Actions */

    public final AbstractAction Dijkstra = new AbstractAction(){
        {
            putValue(Action.NAME,"Dijkstra");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
            putValue(Action.SHORT_DESCRIPTION,"Applique l'algorithme de Dijkstra pour trouver \n"
                                            + "le plus court chemin entre 2 sommets \n"
                                            + "-Cliquez sur le nœud de départ \n"
                                            + "-Cliquez sur le nœud d'arrivée \n"
                                            + "(CTRL+D)");
            putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_D,KeyEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent e){
            activeTraitement = Interface.DIJKSTRA_TRAITEMENT;
            d.reinit();
            d.repaint();
        }
    };

    public final AbstractAction FordFulkerson = new AbstractAction() {
        {
            putValue(Action.NAME,"Ford Fulkerson");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_F);
            putValue(Action.SHORT_DESCRIPTION,"Applique l'algorithme de Ford Fulkerson pour calculer \n"
                                            + "le flot maximal entre 2 sommets \n"
                                            + "-Cliquez sur le nœud source \n"
                                            + "-Cliquez sur le nœud de sortie \n"
                                            + "(CTRL+F)");
            putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_F,KeyEvent.CTRL_DOWN_MASK));
        }
        @Override
            public void actionPerformed(ActionEvent ea) {
                //TO DO: implémenter Ford Fulkerson à partir de la classe MaxFlow //
            }
    };

    public final AbstractAction ExportGraphO = new AbstractAction() {
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
                GOriente g = new GOriente(d);
                g.afficher();
            }
    };

    /**
     * JPanel pour les boutons 
     **/

    @Override
    public void addToolBar(){
        toolBarButtons.add(Dijkstra);
        toolBarButtons.addSeparator();
        toolBarButtons.add(FordFulkerson);
        toolBarButtons.addSeparator();
        toolBarButtons.add(ExportGraphO);
    }
    
    @Override
    public void addMenuBar(){
        JMenu traitMenu = new JMenu("Traitement");
        traitMenu.add(Dijkstra);
        traitMenu.add(FordFulkerson);
        traitMenu.add(ExportGraphO);
        menuBar.add(traitMenu);
    }
    
}
