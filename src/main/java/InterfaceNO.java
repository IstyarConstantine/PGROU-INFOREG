/*=============================================
Classe InterfaceNO
Auteur : Béryl CASSEL
Date de création : 08/03/2022
Date de dernière modification : 08/03/2022
=============================================*/

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

public class InterfaceNO extends Interface{
    
    /**
     * JPanel pour les boutons 
     **/
    @Override
    public void initToolBar() {
        //paneButtons = new JPanel();
        toolBarButtons = new JToolBar(null, JToolBar.VERTICAL);
        //Panel le long de l'axe Y
        toolBarButtons.setLayout(new BoxLayout(toolBarButtons, BoxLayout.Y_AXIS));
        
        //intialise les boutons 
        select = new JRadioButton("Select");
        noeud = new JRadioButton("Noeud");
        arc = new JRadioButton("Arc"); 
        label = new JRadioButton("Label");
        edition = new JRadioButton("Édition");
        traitement = new JRadioButton("Traitement");
        save = new JButton("SAUVEGARDER");
        load = new JButton("CHARGER");
        
        //ajoute un séparateur de taille par défaut
        toolBarButtons.addSeparator();
        
        JButton colorButton = new JButton("Color");
        colorButton.setMnemonic('o');
        colorButton.setToolTipText("Choose a Color");
        ActionListener colorListener;
        colorListener = (ActionEvent arg0) -> {
            Color c = JColorChooser.showDialog(frame, "Choose a color", color);
            if (c!=null) {
                for (int i=1;i<colorSample.getHeight();i++){
                    for (int j=1;j<colorSample.getHeight();j++){
                        colorSample.setRGB(i,j,c.getRGB());
                    }
                }
                setColor(c);
                d.setCurrentColor(c);
            }
        };
        colorButton.addActionListener(colorListener);
        colorButton.setIcon(new ImageIcon(colorSample));
        toolBarButtons.add(colorButton);
        setColor(this.color);
        
        //ajoute un séparateur de taille par défaut
        toolBarButtons.addSeparator();
        
        JLabel l1 = new JLabel("  Action");
        JLabel l2 = new JLabel("  Mode");
        //On crée un ButtonGroup pour que seul l'un puisse être activé à la fois 
        ButtonGroup groupAction = new ButtonGroup();
        groupAction.add(select);
        groupAction.add(noeud);
        groupAction.add(arc);
        groupAction.add(label);
        ButtonGroup groupMode = new ButtonGroup();
        groupMode.add(edition);
        groupMode.add(traitement);
        //On ajoute les éléments au JPanel
        toolBarButtons.add(l1);
        toolBarButtons.add(select);
        toolBarButtons.add(noeud);
        toolBarButtons.add(arc);
        toolBarButtons.add(label);
        toolBarButtons.add(l2);
        toolBarButtons.add(edition);
        toolBarButtons.add(traitement);
        //pane.add(Box.createVerticalGlue());

        //ajoute un séparateur de taille par défaut
        toolBarButtons.addSeparator();
          
        //Action Listener
        ActionListener toolGroupListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource()==select) { 
                    activeTool = SELECT_TOOL;
                } else if (ae.getSource()==noeud) {
                    activeTool = NOEUD_TOOL;   
                } else if (ae.getSource()==arc) {
                    activeTool = ARC_TOOL;
                } else if (ae.getSource()==label){
                    activeTool = LABEL_TOOL;
                } 

            }
        };
        ActionListener modeGroupListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource()==edition) {
                    mode = EDITION_MODE;
                } else if (ae.getSource()==traitement) {
                    mode = TRAITEMENT_MODE;
                }
            }
        };

        select.addActionListener(toolGroupListener);
        noeud.addActionListener(toolGroupListener);
        arc.addActionListener(toolGroupListener);
        label.addActionListener(toolGroupListener);
        edition.addActionListener(modeGroupListener);
        traitement.addActionListener(modeGroupListener);
        
        toolBarButtons.setFloatable(false);
        toolBarButtons.setBorderPainted(true);
    }
    
    @Override
    public void initMenuBar(){
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Fichier");
        JMenu traitMenu = new JMenu("Traitement");
        JMenu helpMenu = new JMenu("Aide");
        JMenu aboutMenu = new JMenu("A propos");
        
        JMenuItem ouvrir = new JMenuItem("Ouvrir");
        JMenuItem enregistrer = new JMenuItem("Enregistrer");
        JMenuItem enregistrerSous = new JMenuItem("EnregistrerSous");
        JMenu exporter = new JMenu("Exporter");
        
        JMenuItem exportLatex = new JMenuItem("Exporter au format LaTeX");
        exportLatex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //TODO 
                //idée : fenêtre dans laquelle on remplit les critères (couleurs, style ...)
            }
        });
        exporter.add(exportLatex);
        
        fileMenu.add(ouvrir);
        fileMenu.addSeparator();
        fileMenu.add(enregistrer);
        fileMenu.add(enregistrerSous);
        fileMenu.addSeparator();
        fileMenu.add(exporter);
        
        JMenuItem prim = new JMenuItem("Prim");
        prim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ea) {
                d.reinit();
                d.repaint();
                (new PrimMST()).primMST(d);
            }
        });
        JMenuItem finmodif = new JMenuItem("Export Graphe");
        finmodif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ea) {
                System.out.println(d.getNumOfCircles());
                GNonOriente g = new GNonOriente(d);
                g.afficher();
            }
        });

        traitMenu.add(prim);
        traitMenu.add(finmodif);
        
        //Sub Menus de Aide
        JMenu helpSubMenu = new JMenu("Utilisation des boutons");
        JMenuItem helpSubMenuItem1 = new JMenuItem("Création de noeud");
        helpSubMenuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String str = "Pour créer des noeuds : \n"
                                + "\n"
                                + "    - veillez à ce que le bouton 'Noeud' soit activé \n"
                                + "\n"
                                + "    - puis, déplacer votre souris sur une zone et cliquer pour créer un noeud \n"
                                + "\n"
                                + "    - pour déplacer un noeud : maintenez le clique gauche sur un noeud, déplacez vers une zone de l'écran et relâchez\n"
                                + "\n"
                                + "    - pour supprimer un noeud : double-cliquez sur un noeud\n";
                JOptionPane.showMessageDialog(frame ,str,"Bouton Noeud", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        JMenuItem helpSubMenuItem2 = new JMenuItem("Help Sub menu item 2");
        helpMenu.add(helpSubMenu);
        helpSubMenu.add(helpSubMenuItem1);
        helpSubMenu.add(helpSubMenuItem2);
        
        JMenuItem credits = new JMenuItem("Crédits");
        aboutMenu.add(credits);
        
        credits.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String creditStr = "Application créée par Béryl CASSEL, Cristobal CARRASCO DE RODT, Jorge QUISPE , Isaías VENEGAS et Samy AMAL \n"
                                    + "\n"
                                    + "dans le cadre du projet de groupe INFOREG \n"
                                    + "\n"
                                    + "encadré par Olivier ROUX";
                JOptionPane.showMessageDialog(frame ,creditStr,"Credits", JOptionPane.INFORMATION_MESSAGE);
            }
        });
                
        menuBar.add(fileMenu);
        menuBar.add(traitMenu);
        menuBar.add(helpMenu); 
        menuBar.add(aboutMenu);
        
        //CTRL Z / CTRL Y
        ImageIcon iconBack = new ImageIcon("back.png");
        ImageIcon iconForward = new ImageIcon("forward.png");
        //resize
        Image imageBack = iconBack.getImage().getScaledInstance(15,15, java.awt.Image.SCALE_AREA_AVERAGING);
        Image imageForward = iconForward.getImage().getScaledInstance(15,15, java.awt.Image.SCALE_AREA_AVERAGING);
        iconBack = new ImageIcon(imageBack); 
        iconForward = new ImageIcon(imageForward);
        back = new JButton(iconBack);
        forward = new JButton(iconForward);
        //placer les back/forward à droite 
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(back);
        menuBar.add(forward);

    }
}
    
 