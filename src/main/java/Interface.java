/*=============================================
Classe Interface 
Auteur : Samy AMAL
Date de création : 03/03/2022
Date de dernière modification : 06/03/2022
=============================================*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Interface {
    
    private JFrame frame;
    
    /** Les JPanel. */
    private JToolBar toolBarButtons;
    private JPanel paneImage;
    private Draw d;
    
    /** Le Menu. */ 
    private JMenuBar menuBar;
    
    /** Les boutons. */
    private JRadioButton select;
    private JRadioButton noeud;
    private JRadioButton arc;
    private JRadioButton label;
    private JButton save;
    private JButton load;
    private JButton clearSelection;
    private JButton back ;
    private JButton forward;
    
    /** Reference to the original image. */
    private BufferedImage originalImage;
    /** Image used to make changes. */
    private BufferedImage canvasImage;
    
    /** Couleur utilisée. */
    private Color color = Color.WHITE;
    private BufferedImage colorSample = new BufferedImage(16,16,BufferedImage.TYPE_INT_RGB);
    private Rectangle selection;
    
    private RenderingHints renderingHints;
    private JLabel imageLabel;
    
    /** Tools pour savoir l'état/bouton selectionné. */
    protected static int activeTool;
    public static final int SELECT_TOOL = 10;
    public static final int NOEUD_TOOL = 11;
    public static final int ARC_TOOL = 12;
    public static final int LABEL_TOOL = 13;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Interface()::createAndShowGui);
    }

    public void createAndShowGui() {
        
        frame = new JFrame(getClass().getSimpleName());
        //fermer la fenêtre quand on quitte
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        initToolBar();        
        initPaneImage();
        initMenuBar();
        
        //BorderLayout.LINE_START permet de fixer le JPanel au début de la ligne (centre gauche)
        frame.add(toolBarButtons, BorderLayout.LINE_START);
        //BorderLayout.CENTER permet de fixer le JPanel au centre
        frame.add(paneImage,BorderLayout.CENTER);
        frame.setJMenuBar(menuBar);
        this.d = new Draw();
        frame.getContentPane().add(this.d);
        
        frame.pack();
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            

    }
    
    /**
     * JPanel pour les boutons 
     **/
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
        //On crée un ButtonGroup pour que seul l'un puisse être activé à la fois 
        ButtonGroup groupAction = new ButtonGroup();
        groupAction.add(select);
        groupAction.add(noeud);
        groupAction.add(arc);
        groupAction.add(label);
        //On ajoute les éléments au JPanel
        toolBarButtons.add(l1);
        toolBarButtons.add(select);
        toolBarButtons.add(noeud);
        toolBarButtons.add(arc);
        toolBarButtons.add(label);
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
        select.addActionListener(toolGroupListener);
        noeud.addActionListener(toolGroupListener);
        arc.addActionListener(toolGroupListener);
        label.addActionListener(toolGroupListener);
        
        toolBarButtons.setFloatable(false);
        toolBarButtons.setBorderPainted(true);
    }
    
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
        JMenuItem dijkstra = new JMenuItem("Dijkstra");
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
        traitMenu.add(dijkstra);
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
    
    /** 
     * Méthode permettant de modifier la couleur
     * @param color 
     */
    public void setColor(Color color) {
        this.color = color;
        //clear(colorSample);
    }

    /*
     *   Méthodes suivantes incertaines/inutiles pour l'instant
     */
    
    /*
     * JPanel pour le BufferedImage (méthode getgui de BasicPaint)
     */
    public void initPaneImage(){

        Map<RenderingHints.Key, Object> hintsMap = new HashMap<>();
        hintsMap.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hintsMap.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        hintsMap.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        renderingHints = new RenderingHints(hintsMap); 

        setImage(new BufferedImage(320,240,BufferedImage.TYPE_INT_RGB));
        paneImage = new JPanel(new BorderLayout(4,4));
        paneImage.setBorder(new EmptyBorder(5,3,5,3));
        
        JPanel imageView = new JPanel(new GridBagLayout());
        imageView.setPreferredSize(new Dimension(480,320));
        imageLabel = new JLabel(new ImageIcon(canvasImage));
        JScrollPane imageScroll = new JScrollPane(imageView);
        imageView.add(imageLabel);
        //imageLabel.addMouseMotionListener(new ImageMouseMotionListener());
        //imageLabel.addMouseListener(new ImageMouseListener());
        paneImage.add(imageScroll,BorderLayout.CENTER);
        
        clear(colorSample);
        clear(canvasImage);
        
    }
    
    /**
     * Méthode venant de BasicPaint
     * @param image 
     */
    public void setImage(BufferedImage image) {
        this.originalImage = image;
        int w = image.getWidth();
        int h = image.getHeight();
        canvasImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = this.canvasImage.createGraphics();
        g.setRenderingHints(renderingHints);
        g.drawImage(image, 0, 0, paneImage);
        g.setColor(this.color);
        g.dispose();

        selection = new Rectangle(0,0,w,h); 
        if (this.imageLabel!=null) {
            imageLabel.setIcon(new ImageIcon(canvasImage));
            this.imageLabel.repaint();
        }
        //if (gui!=null) {
        //    gui.invalidate();
        //}
    }
    
    /** Clears the entire image area by painting it with the current color. */
    public void clear(BufferedImage bi) {
        Graphics2D g = bi.createGraphics();
        g.setRenderingHints(renderingHints);
        g.setColor(color);
        g.fillRect(0, 0, bi.getWidth(), bi.getHeight());

        g.dispose();
        imageLabel.repaint();
    }
    
}
