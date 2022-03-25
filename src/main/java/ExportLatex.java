/*=============================================
Classe Draw permettant d'exporter les graphes en source LaTeX
Auteur : Samy AMAL
Date de création : 18/2022
Date de dernière modification : 25/03/2022
=============================================*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
     * JPanel contenant les éléments du frame
     */
    protected JPanel panel;
    
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
        
        initPanelLatex(d);
        
        frame.pack();
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
    
    /**
     * Méthode permettant de créer le JPanel et les éléments du frame
     * @param d Draw
     */
    public void initPanelLatex(Draw d){
        panel = new JPanel();
        panel.setBorder (new TitledBorder(new EtchedBorder(),"Paramètres d'export"));
        JTextArea display = new JTextArea(16, 58);
        display.setEditable (false); // set textArea non-editable
        JScrollPane scroll = new JScrollPane(display);
        scroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scroll);
        
        JButton exportLatexButton = new JButton("Exporter");
        exportLatexButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                display.setText("");
                display.append(getSourceLatex(d));  
            } 
        } );
        panel.add(exportLatexButton);
        frame.add(panel);
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
     * Méthode permettant de choisir le style des arcs 
     */
    public String choixStyleArc(){
        return "\\tikzstyle{style}=[very thick]";
    }
    
}

