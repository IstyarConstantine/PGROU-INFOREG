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
import javax.swing.JOptionPane;

public class InterfaceO extends Interface implements Connexe {

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
            if (mode==Interface.TRAITEMENT_MODE){
                activeTraitement = Interface.DIJKSTRA_TRAITEMENT;
                d.reinit();
                d.repaint();
                d.setSrc(-1);
                d.setDest(-1);
                JOptionPane.showMessageDialog(d, "Choisissez un sommet source et un sommet d'arrivée pour le calcul du plus court chemin", "PCC - Dijkstra", JOptionPane.INFORMATION_MESSAGE);
            }
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
                activeTraitement = Interface.FORD_FULKERSON_TRAITEMENT;
                d.reinit();
                d.repaint();
                d.setSrc(-1);
                d.setDest(-1);
                JOptionPane.showMessageDialog(d, "Choisissez un sommet source et un sommet d'arrivée pour le calcul du flot.", "Ford-Fulkerson - Flot maximal", JOptionPane.INFORMATION_MESSAGE);
            }
    };

    public final AbstractAction ConnexiteO = new AbstractAction() {
        {
            putValue(Action.NAME,"Connexité");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
            putValue(Action.SHORT_DESCRIPTION,"Vérifie si le graphe est fortement connexe \n"
                                            + "(CTRL+L)");
            putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_L,KeyEvent.CTRL_DOWN_MASK));
        }
        @Override
            public void actionPerformed(ActionEvent ea) {
                if (mode==Interface.TRAITEMENT_MODE){
                    if (connexe((GOriente) d.getG())){
                        JOptionPane.showMessageDialog(d, "Le graphe est fortement connexe.", "Connexité", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(d, "Le graphe n'est pas fortement connexe.", "Connexité", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
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
                if (mode==Interface.TRAITEMENT_MODE){
                    JOptionPane.showMessageDialog(d, "La matrice d'adjacence du graphe orienté est :\n\n" + d.getG().afficher(), "Matrice d'adjacence", JOptionPane.INFORMATION_MESSAGE);
                }
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
        toolBarButtons.add(ConnexiteO);
        toolBarButtons.addSeparator();
        toolBarButtons.add(ExportGraphO);
    }
    
    @Override
    public void addMenuBar(){
        JMenu traitMenu = new JMenu("Traitement");
        traitMenu.add(Dijkstra);
        traitMenu.add(FordFulkerson);
        traitMenu.add(ConnexiteO);
        traitMenu.add(ExportGraphO);
        menuBar.add(traitMenu);
    }
    
}
