/*=============================================
Classe Draw permettant d'exporter les graphes en source LaTeX
Auteur : Samy AMAL
Date de création : 18/2022
Date de dernière modification : 25/03/2022
=============================================*/

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ExportLatex {
    
    /**
     * JFrame permettant d'afficher le source LaTeX
     */
    protected JFrame frame;
    
    /**
     * JPanel contenant le source Latex d'export
     */
    protected JPanel panel;
    /**
     * JPanel contenant les boutons modifiant l'épaisseur des arcs
     */
    protected JPanel panelThicknessArc;
    
    
    protected JPanel panelColorArc;
    
    /**
     * Couleur de remplissage des noeuds
     */
    private String noeudFill ;
    
    /**
     * Style des arcs
     */
    private String styleLine ;
    
    /**
     * Constructeur ide la classe
     */
    public ExportLatex(){
        noeudFill = "gray!50";
        styleLine = "very thick";
    }

    /**
     * Méthode permettant de générer le Frame 
     * @param d Draw
     */
    public void frameLatex(Draw d){      
        frame = new JFrame("Export Latex");
        //fermer la fenêtre quand on quitte
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        initPanelThicknessArc(d);
        initPanelCouleurArc(d);
        initPanelLatex(d);
        
        frame.pack();
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    
    /**
     * Méthode permettant de modifier les couleurs des arcs en noir
     * @param d Draw
     */
    public void initPanelCouleurArc(Draw d){
        
        panelColorArc= new JPanel();
        panelColorArc.setBorder (new TitledBorder(new EtchedBorder(),"Couleur des arcs"));
        
        //Thickness des arcs
        JRadioButton colorArc1 = new JRadioButton("Couleurs du graphe");
        JRadioButton colorArc2 = new JRadioButton("Tout en noir");
        
        ButtonGroup groupColorArc = new ButtonGroup();
        groupColorArc.add(colorArc1);
        groupColorArc.add(colorArc2);
        
        ActionListener groupColorArcListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource()==colorArc1) { 
                    ;
                } else if (ae.getSource()==colorArc2) {
                    ;
                }
            }
        };
        colorArc1.addActionListener(groupColorArcListener);
        colorArc2.addActionListener(groupColorArcListener);

        panelColorArc.add(colorArc1);
        panelColorArc.add(colorArc2);
        frame.add(panelColorArc,BorderLayout.LINE_START);
    }
   
    
    
    
    /**
     * Méthode permettant de créer le JPanel contenant les boutons permettant de 
     * modifier l'épaisseur des arcs
     * @param d Draw
     */
    public void initPanelThicknessArc(Draw d){
        
        panelThicknessArc = new JPanel();
        panelThicknessArc.setBounds(0,0,100,20);
        panelThicknessArc.setBorder (new TitledBorder(new EtchedBorder(),"Epaisseur des arcs"));
        
        
        //Thickness des arcs
        JRadioButton styleArc1 = new JRadioButton("ultra thick");
        JRadioButton styleArc2 = new JRadioButton("very thick");
        JRadioButton styleArc3 = new JRadioButton("thick");
        JRadioButton styleArc4 = new JRadioButton("default");
        
        ButtonGroup groupStyleArc = new ButtonGroup();
        groupStyleArc.add(styleArc1);
        groupStyleArc.add(styleArc2);
        groupStyleArc.add(styleArc3);
        groupStyleArc.add(styleArc4);
        
        ActionListener groupStyleArcListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource()==styleArc1) { 
                    setStyleArc("ultra thick");
                } else if (ae.getSource()==styleArc2) {
                    setStyleArc("very thick");
                } else if (ae.getSource()==styleArc3) {
                    setStyleArc("thick");
                } else if (ae.getSource()==styleArc4) {
                    setStyleArc("default");
                }
            }
        };
        styleArc1.addActionListener(groupStyleArcListener);
        styleArc2.addActionListener(groupStyleArcListener);
        styleArc3.addActionListener(groupStyleArcListener);
        styleArc4.addActionListener(groupStyleArcListener);
        //styleArc2 activé au démarrage
        styleArc2.setSelected(true);

        panelThicknessArc.add(styleArc1);
        panelThicknessArc.add(styleArc2);
        panelThicknessArc.add(styleArc3);
        panelThicknessArc.add(styleArc4);
        frame.add(panelThicknessArc);
    }
    
    
    
    /**
     * Méthode permettant de créer le JPanel contenant le source Latex de l'export
     * @param d Draw
     */
    public void initPanelLatex(Draw d){
        panel = new JPanel();
        panel.setBorder (new TitledBorder(new EtchedBorder(),"Source LaTeX"));
        JTextArea display = new JTextArea(16, 58);
        display.setEditable (false); // set textArea non-editable
        JScrollPane scroll = new JScrollPane(display);
        scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        JButton exportLatexButton = new JButton("Exporter");
        exportLatexButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                display.setText("");
                display.append(getSourceLatex(d));  
            } 
        } );
        panel.add(exportLatexButton);
        panel.add(scroll);
        
        frame.add(panel,BorderLayout.PAGE_END);
    }
    
    /**
     * Méthode permettant de générer le source LaTeX à partir du Draw
     * @param d Draw
     */
    public String getSourceLatex(Draw d){
        
        String latex = "\\documentclass{article}\n"+
                       "\\usepackage[utf8]{inputenc}\n"+
                       "\\usepackage{tikz}\n"+
                       "\\usetikzlibrary{positioning}\n"+  
                       "\\usepackage{pgfplots}\n"+
                       " \n"+
                       "\\begin{document}\n"+
                       " \n"+
                       "\\begin{tikzpicture}[scale=0.04]\n"+
                       writeCoordinates(d);
        if(d.getOriente()==0){//orienté
            if(d.getPondere()){//pondéré
                latex += writeLinesPondOriente(d);
            }else{
                latex += writeLinesNonPondOriente(d);
            }
        }else{
            if(d.getPondere()){//non orienté + pondéré
                latex += writeLinesPondNonOriente(d);
            }else{
                latex += writeLinesNonPondNonOriente(d);
            }
        }
        latex += "\\end{tikzpicture}\n"+
                 "\n"+
                 "\\end{document}\n";
        return latex;
    }
    
    /**
     * Méthode permettant de récupérer les coordonées des noeuds
     * @param d Draw
     */
    public String writeCoordinates(Draw d){
        
        Ellipse2D.Double[] circ = d.getCirc();
        String[] lbl = d.getCircLbl() ;
        String coord = "";
        
        for (int i =0;i<d.getNumOfCircles();i++){
            int x = (int) circ[i].getCenterX() ;
            int y = (int) circ[i].getCenterY() ;
            coord = coord + "\\node[draw,circle,fill="+noeudFill+"] ("+ String.valueOf(i)+") at ("
                    + String.valueOf(x)+",-"+ String.valueOf(y)+") {"
                    + lbl[i] + "}; \n";
            
        }
        
        return coord;
    }
    
    /**
     * Méthode permettant de générer le source Latex pour les arcs
     * Cas Pondéré Orienté
     * @param d Draw
     */
    public String writeLinesPondOriente(Draw d){
        String arcs = "\\tikzstyle{style}=[->,"+styleLine +"]\n";
        GOriente g = new GOriente(d);
        int[][] adj = g.getAdj();
        for(int i = 0; i < adj.length; i++){
            for(int j = 0; j < adj.length; j++){
                if(adj[i][j] != 0){
                    arcs = arcs + "\\draw[style] (" + String.valueOf(i) + ")--(" + String.valueOf(j) + ") node[midway,label="+ String.valueOf(adj[i][j])  +"] {};\n" ;
                }
            }
        } 
        return arcs;
    }
    
    /**
     * Méthode permettant de générer le source Latex pour les arcs
     * Cas Pondéré non Orienté
     * @param d Draw
     */
    public String writeLinesPondNonOriente(Draw d){
        String arcs = "\\tikzstyle{style}=["+styleLine+"]\n";
        GNonOriente g = new GNonOriente(d);
        int[][] adj = g.getAdj();
        for(int i = 0; i < adj.length; i++){
            for(int j = i; j < adj.length; j++){
                if(adj[i][j] != 0){
                    arcs = arcs + "\\draw[style] (" + String.valueOf(i) + ")--(" + String.valueOf(j) + ") node[midway,label="+ String.valueOf(adj[i][j])  +"] {};\n" ;
                }
            }
        }  
        return arcs;
    }
    
    /**
     * Méthode permettant de générer le source Latex pour les arcs
     * Cas non Pondéré Orienté
     * @param d Draw
     */    
    public String writeLinesNonPondOriente(Draw d){
        String arcs = "\\tikzstyle{style}=[->,"+styleLine +"]\n";
        GOriente g = new GOriente(d);
        int[][] adj = g.getAdj();
        for(int i = 0; i < adj.length; i++){
            for(int j = 0; j < adj.length; j++){
                if(adj[i][j] != 0){
                    arcs = arcs + "\\draw[style] (" + String.valueOf(i) + ")--(" + String.valueOf(j) + ") {};\n" ;
                }
            }
        } 
        return arcs;
    }
    
    /**
     * Méthode permettant de générer le source Latex pour les arcs
     * Cas non Pondéré non Orienté
     * @param d Draw
     */
    public String writeLinesNonPondNonOriente(Draw d){
        String arcs = "\\tikzstyle{style}=["+styleLine+"]\n";
        GNonOriente g = new GNonOriente(d);
        int[][] adj = g.getAdj();
        for(int i = 0; i < adj.length; i++){
            for(int j = i; j < adj.length; j++){
                if(adj[i][j] != 0){
                    arcs = arcs + "\\draw[style] (" + String.valueOf(i) + ")--(" + String.valueOf(j) + ") {};\n" ;
                }
            }
        }  
        return arcs;
    }
    
    /**
     * Méthode permettant de modifier le style des arcs 
     */
    public void setStyleArc(String s){
        this.styleLine = s;
    }
    
}

