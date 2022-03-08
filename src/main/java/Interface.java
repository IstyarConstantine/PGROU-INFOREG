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
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

public abstract class Interface {

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

    public void createAndShowGui() {
        
        this.d = new Draw();
        frame = new JFrame("INFOREG");
        //fermer la fenêtre quand on quitte
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        initToolBar();        
        initPaneImage();
        initMenuBar();
        
        //BorderLayout.LINE_START permet de fixer le JPanel au début de la ligne (centre gauche)
        frame.add(toolBarButtons, BorderLayout.LINE_START);
        //BorderLayout.CENTER permet de fixer le JPanel au centre
        frame.add(paneImage,BorderLayout.CENTER);
        frame.setJMenuBar(menuBar);
        
        frame.getContentPane().add(this.d);
        
        frame.pack();
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            

    }
    
    /**
     * JPanel pour les boutons 
     **/
    public abstract void initToolBar();
        
    
    public abstract void initMenuBar();
    
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
