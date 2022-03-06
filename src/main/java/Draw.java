/*=============================================
Classe Draw permettant de dessiner les noeuds et arcs
Sous classe de la classe Traitement
Auteur : Samy AMAL
Date de création : 03/03/2022
Date de dernière modification : 06/03/2022
=============================================*/

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
 
public class Draw extends JPanel implements MouseMotionListener {
 
    private static final int circleW = 30;
    private static final int MAX = 100;
    /** liste des cerlces. */
    private Ellipse2D.Double[] circ = new Ellipse2D.Double[MAX];
    /** liste des labels. */
    private String[] circLbl = new String[MAX];
    private int numOfCircles = 0;
    private int currentCircleIndex = -1;
    //private static int countArcClicks = 0;
    
    //pour les lignes
    private Ellipse2D.Double fromPoint ;
    private ArrayList<MyLine> lines = new ArrayList<>();
    private int numOfLines = 0;
      
    public Draw() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                int x = evt.getX();
                int y = evt.getY();
                currentCircleIndex = getRec(x, y);
                if (Interface.activeTool==Interface.NOEUD_TOOL) {
                    if (currentCircleIndex < 0){ // not inside a circle
                        add(x, y);
                    }
                }
                if (Interface.activeTool==Interface.ARC_TOOL){
                    if (currentCircleIndex >= 0){
                        fromPoint = circ[currentCircleIndex];
                        //TODO - idée : détecter deuxieme clique à la suite en étant avec arctool
                        //g.drawLine(X1,X2,Y1,Y2);
                        //countArcClicks==0;
                    }
                }
                if (Interface.activeTool==Interface.LABEL_TOOL) {
                    if (currentCircleIndex >= 0){ // inside a circle
                        //ERREUR SI ON CANCEL
                        String lbl = JOptionPane.showInputDialog("Entrer label :");
                        //System.out.println(lbl);
                        //lbl.closeLbl();
                        circLbl[currentCircleIndex] = lbl;
                        repaint();
                    }   
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                int x = evt.getX();
                int y = evt.getY();
                currentCircleIndex = getRec(x, y);
                if (Interface.activeTool==Interface.ARC_TOOL){
                    //ATTENTION : il faudra prendre le compte le cas où on pointe vers le meme cercle
                    if (currentCircleIndex >= 0) { // inside circle
                        Ellipse2D.Double p = circ[currentCircleIndex];
                        addLine(new MyLine(fromPoint, p));
                        repaint();
                    }
                }
            }
            
            @Override
                public void mouseClicked(MouseEvent evt) {
                    int x = evt.getX();
                    int y = evt.getY();
                    if (evt.getClickCount() >= 2) {
                        if (Interface.activeTool==Interface.NOEUD_TOOL) {
                            remove(currentCircleIndex);
                        }
                    }
                }
            });
            addMouseMotionListener(this);
    }
 
    //Méthode permettant de draw les éléments. */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < numOfCircles; i++) {
            //((Graphics2D) g).setPaint(Color.BLACK);
            ((Graphics2D) g).draw(circ[i]); 
            //((Graphics2D) g).setPaint(Color.WHITE);
            //((Graphics2D) g).fill(circ[i]);
            ((Graphics2D) g).setPaint(Color.BLACK);
            ((Graphics2D) g).drawString(circLbl[i],
                    (int) circ[i].getCenterX() - 5,
                    (int) circ[i].getCenterY() + 6);
        }
        for (int i = 0 ; i < numOfLines; i++){
            ((Graphics2D) g).setPaint(Color.BLUE);
            ((Graphics2D) g).drawLine(lines.get(i).getFromPoint().x, lines.get(i).getFromPoint().y,
                    lines.get(i).getToPoint().x, lines.get(i).getToPoint().y);
            
        }
    }
 
    public int getRec(int x, int y) {
        for (int i = 0; i < numOfCircles; i++) {
            if (circ[i].contains(x, y)) {
                return i;
            }
        }
        return -1;
    }
 
    public void add(int x, int y) {
        if (numOfCircles < MAX) {
            circ[numOfCircles] =  new Ellipse2D.Double(x, y, circleW, circleW);
            circLbl[numOfCircles] = " ";
            currentCircleIndex = numOfCircles;
            numOfCircles++;
            repaint();
        }
    }
    
    public void addLine(MyLine line) {
        if (numOfLines < MAX) {
            lines.add(line);
            numOfLines++;
            repaint();
        }
    }
    
    @Override
    public void remove(int n) {
        if (n < 0 || n >= numOfCircles) {
            return;
        }
        numOfCircles--;
        circ[n] = circ[numOfCircles];
        circLbl[n] = circLbl[numOfCircles];
        if (currentCircleIndex == n) {
            currentCircleIndex = -1;
        }
        // Comment supprimer une ligne ?
        if(numOfLines > 0){
            for (MyLine l : lines){
                if (l.getFrom().equals(circ[currentCircleIndex]) || l.getTo().equals(circ[currentCircleIndex])){
                    lines.remove(l);
                    numOfLines--;
                }
            }
        }
        repaint();
    }
    
    /** Modifier le curseur lorsqu'on se trouve sur un cercle. */
    @Override
    public void mouseMoved(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        if (getRec(x, y) >= 0) {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(Cursor.getDefaultCursor());
        }  
    }
 
    @Override
    public void mouseDragged(MouseEvent event) {
        if (Interface.activeTool==Interface.NOEUD_TOOL || Interface.activeTool==Interface.SELECT_TOOL) {
            int x = event.getX();
            int y = event.getY();
            if (currentCircleIndex >= 0) {
                Graphics graphics = getGraphics();
                graphics.setXORMode(getBackground());
                ((Graphics2D) graphics).draw(circ[currentCircleIndex]);
                ((Graphics2D) graphics).drawString(circLbl[currentCircleIndex],
                    (int) circ[currentCircleIndex].getCenterX() - 5,
                    (int) circ[currentCircleIndex].getCenterY() + 6);
                if(numOfLines > 0){
                    for (MyLine l : lines){
                        if (l.getFrom().equals(circ[currentCircleIndex]) || l.getTo().equals(circ[currentCircleIndex])){ 
                            ((Graphics2D) graphics).setPaint(Color.BLUE);
                            ((Graphics2D) graphics).drawLine(l.getFromPoint().x,l.getFromPoint().y,
                            l.getToPoint().x, l.getToPoint().y);
                        }
                    }
                }
                circ[currentCircleIndex].x = x;
                circ[currentCircleIndex].y = y;
                ((Graphics2D) graphics).setPaint(Color.BLACK);
                ((Graphics2D) graphics).draw(circ[currentCircleIndex]);
                //((Graphics2D) graphics).setPaint(Color.WHITE);
                //((Graphics2D) graphics).fill(circ[currentCircleIndex]);
                //((Graphics2D) graphics).setPaint(Color.BLACK);
                ((Graphics2D) graphics).drawString(circLbl[currentCircleIndex],
                    (int) circ[currentCircleIndex].getCenterX() - 5,
                    (int) circ[currentCircleIndex].getCenterY() + 6);
                if(numOfLines > 0){
                    for (MyLine l : lines){
                        if (l.getFrom().equals(circ[currentCircleIndex]) || l.getTo().equals(circ[currentCircleIndex])){
                            ((Graphics2D) graphics).setPaint(Color.BLUE);  
                            ((Graphics2D) graphics).drawLine(l.getFromPoint().x,l.getFromPoint().y,
                            l.getToPoint().x, l.getToPoint().y);
                        }
                    }
                }
                graphics.dispose();
            }
        }    
    }
    
    public class MyLine {
        private final Ellipse2D.Double from;
        private final Ellipse2D.Double  to;

        public MyLine(Ellipse2D.Double fromPoint, Ellipse2D.Double toPoint) {
            this.from = fromPoint;
            this.to = toPoint;
        }
        
        public Ellipse2D.Double getFrom() {
            return this.from;
        }
        
        public Ellipse2D.Double getTo() {
            return this.to;
        }

        public Point getFromPoint() {
            double centerX = this.from.getCenterX();
            double centerY = this.from.getCenterY();
            Point p = new Point((int)centerX, (int)centerY);
            return p;
        }

        public Point getToPoint() {
            double centerX = this.to.getCenterX();
            double centerY = this.to.getCenterY();
            Point p = new Point((int)centerX, (int)centerY);
            return p;
        }

    }
}
