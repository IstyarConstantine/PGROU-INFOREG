/*=============================================
Classe Interface
Auteur : Samy AMAL
Date de création : 03/03/2022
Date de dernière modification : 08/03/2022
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
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class Interface{

    protected JFrame frame;
    
    /** Les JPanel. */
    protected JToolBar toolBarButtons;
    protected JPanel paneImage;
    protected Draw d;
    
    
    /** Le Menu. */ 
    protected JMenuBar menuBar;
    
    /** Les boutons. */
    protected JRadioButton select;
    protected JRadioButton noeud;
    protected JRadioButton arc;
    protected JRadioButton label;
    protected JRadioButton edition;
    protected JRadioButton traitement;
    protected JButton save;
    protected JButton load;
    protected JButton clearSelection;
    protected JButton back ;
    protected JButton forward;
    
    /** Reference to the original image. */
    protected BufferedImage originalImage;
    /** Image used to make changes. */
    protected BufferedImage canvasImage;
    
    /** Couleur utilisée. */
    protected Color color = Color.WHITE;
    protected BufferedImage colorSample = new BufferedImage(16,16,BufferedImage.TYPE_INT_RGB);
    protected Rectangle selection;
    protected static Color colorBg ;
    
    protected RenderingHints renderingHints;
    protected JLabel imageLabel;
    
    /** Tools pour savoir l'état/bouton selectionné. */
    protected static int activeTool;
    public static final int SELECT_TOOL = 10;
    public static final int NOEUD_TOOL = 11;
    public static final int ARC_TOOL = 12;
    public static final int LABEL_TOOL = 13;
    protected static int mode;
    public static final int EDITION_MODE = 1;
    public static final int TRAITEMENT_MODE = 2;
    protected static int activeTraitement;
    public static final int PRIM_TRAITEMENT = 21;
    public static final int DIJKSTRA_TRAITEMENT = 22;
    
    /** Attribut pour la taille des Noeuds. */
    protected static int taille ;

        /** Actions */

    /** Action de sauvegarde du graphe dans une sauvegarde existante */
    public final AbstractAction Save = new AbstractAction(){
        {
            putValue(Action.NAME,"Enregistrer");
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
            putValue(Action.SHORT_DESCRIPTION,"Sauvegarde le graphe actuel (CTRL+S)");
            putValue(Action.ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK));
        }

        @Override
        public void actionPerformed(ActionEvent e){

            // Si un fichier de sauvegarde existe déjà, on l'écrase et on effectue une nouvelle sauvegarde
            if (d.getPathSauvegarde()!=" "){
                File f = new File(d.getPathSauvegarde());
                (new SauvDraw(f)).sauvegarderDraw(d);

            // Sinon, on créé un nouveau fichier de sauvegarde
            } else {
                try {
                    JFileChooser dialogue = new JFileChooser(".");
                    if (dialogue.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                        File fichier = dialogue.getSelectedFile();
                        String source = fichier.getName();
                        if (source.length() < 8 || !source.toLowerCase().substring(source.length()-8).equals(".inforeg")) {
                            d.setPathSauvegarde(fichier.getPath() + ".inforeg");
                        } else {
                            d.setPathSauvegarde(fichier.getPath());
                        }
                        (new SauvDraw(fichier)).sauvegarderDraw(d);
                    }
                } catch (Exception NullPointerException){
                    System.out.println("Opération annulée");
                }
            }
        };
    };

    /** Création d'unnouveau fichier de sauvegarde */
    public final AbstractAction SaveAs = new AbstractAction(){
        {
            putValue(Action.NAME,"Enregistrer Sous");
            putValue(Action.SHORT_DESCRIPTION,"Sauvegarde le graphe actuel");
        }

        @Override
        public void actionPerformed(ActionEvent e){
            try {
                JFileChooser dialogue = new JFileChooser(".");
                if (dialogue.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
                    File fichier = dialogue.getSelectedFile();
                    String source = fichier.getName();
                    if (source.length() < 8 || !source.toLowerCase().substring(source.length()-8).equals(".inforeg")) {
                        d.setPathSauvegarde(fichier.getPath() + ".inforeg");
                    } else {
                        d.setPathSauvegarde(fichier.getPath());
                    }
                    (new SauvDraw(fichier)).sauvegarderDraw(d);
                }
            } catch (Exception NullPointerException){
                System.out.println("Opération annulée");
            }
        };
    };

    /**
     * Constructeur d'une interface
     * @param d = instance de Draw permettant de dessiner le graphe
     */
    public Interface(Draw d){
        this.d = d;
    }

    /**
     * Affichage de l'interface
     */
    public void createAndShowGui() {

        frame = new JFrame("INFOREG "+d.getPathSauvegarde());
        //fermer la fenêtre quand on quitte
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        initToolBar();  
        addToolBar();      
        initPaneImage();
        initLeftMenuBar();
        addMenuBar();
        initRightMenuBar();
        frame.add(toolBarButtons, BorderLayout.LINE_START);
        frame.add(paneImage,BorderLayout.CENTER);
        Interface.colorBg = paneImage.getBackground();
        frame.setJMenuBar(menuBar);
        
        frame.getContentPane().add(this.d);
        this.d.repaint();
        
        frame.pack();
        
        frame.setVisible(true);
            
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
        edition = new JRadioButton("Édition");
        traitement = new JRadioButton("Traitement");
        
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
        
        //Taille
        final SpinnerNumberModel strokeModel = new SpinnerNumberModel(20,1,100,1);
        JSpinner strokeSize = new JSpinner(strokeModel);
        ChangeListener strokeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                Object o = strokeModel.getValue();
                Integer i = (Integer)o; 
                taille = i;
                d.tailleCirc();
                } 
        };
        strokeSize.addChangeListener(strokeListener);
        strokeSize.setMaximumSize(strokeSize.getPreferredSize());
        JLabel strokeLabel = new JLabel(" Taille");
        strokeLabel.setLabelFor(strokeSize);

        toolBarButtons.add(strokeLabel);
        toolBarButtons.add(strokeSize);
        
        //ajoute un séparateur de taille par défaut
        toolBarButtons.addSeparator();
        
        
        JLabel l1 = new JLabel("  Édition");
        JLabel l2 = new JLabel("  Mode");
        //On crée un ButtonGroup pour que seul l'un puisse être activé à la fois 
        ButtonGroup groupMode = new ButtonGroup();
        groupMode.add(edition);
        groupMode.add(traitement);
        ButtonGroup groupAction = new ButtonGroup();
        groupAction.add(select);
        groupAction.add(noeud);
        groupAction.add(arc);
        groupAction.add(label);
        //On ajoute les éléments au JPanel
        toolBarButtons.add(l2);
        toolBarButtons.addSeparator();
        toolBarButtons.add(edition);
        toolBarButtons.add(traitement);
        toolBarButtons.addSeparator();
        toolBarButtons.add(l1);
        toolBarButtons.addSeparator();
        toolBarButtons.add(select);
        toolBarButtons.add(noeud);
        toolBarButtons.add(arc);
        toolBarButtons.add(label);
        //pane.add(Box.createVerticalGlue());

        //ajoute un séparateur de taille par défaut
        toolBarButtons.addSeparator();
        JLabel l = new JLabel("  Traitement"); 
        toolBarButtons.add(l);
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
                    d.reinit();
                    d.repaint();
                    mode = TRAITEMENT_MODE;
                    d.exportGraphe();
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

    public abstract void addToolBar();

    public void initLeftMenuBar(){

        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Fichier");
        //Sub Menus de Fichier
        JMenuItem ouvrir = new JMenuItem("Ouvrir");
        //JMenuItem enregistrer = new JMenuItem("Enregistrer");
        //JMenuItem enregistrerSous = new JMenuItem("EnregistrerSous");
        JMenu exporter = new JMenu("Exporter");
        
        JMenuItem exportLatex = new JMenuItem("Exporter au format LaTeX");
        exportLatex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ExportLatex frameLatex = new ExportLatex();
                frameLatex.frameLatex(d);
            }
        });

        exporter.add(exportLatex);
        
        fileMenu.add(ouvrir);
        fileMenu.addSeparator();
        fileMenu.add(Save);
        fileMenu.add(SaveAs);
        //fileMenu.add(enregistrer);
        //fileMenu.add(enregistrerSous);
        fileMenu.addSeparator();
        fileMenu.add(exporter);
        
        menuBar.add(fileMenu);

    }
    
    public abstract void addMenuBar();

    public void initRightMenuBar(){
        JMenu helpMenu = new JMenu("Aide");
        JMenu aboutMenu = new JMenu("A propos");

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
