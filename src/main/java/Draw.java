/*=============================================
Classe Draw permettant de dessiner les noeuds et arcs
Sous classe de la classe Traitement
Auteur : Samy AMAL
Date de création : 03/03/2022
Date de dernière modification : 08/03/2022
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
 
    //Pour les Nœuds :
    /** Rayon des cercles représentant les Nœuds */
    private static final int circleW = 20;
    /** Nombre maximum de nœuds d'un graphe, défini dans la classe Graphe */
    private static final int MAX = Graphe.nbmax;
    /** Liste des cerlces représentant les Nœuds*/
    private Ellipse2D.Double[] circ = new Ellipse2D.Double[MAX];
    /** Liste des labels */
    private String[] circLbl = new String[MAX];
    /** Nombre courant de cercle (i.e de Nœud du Graphe) */
    private int numOfCircles = 0;
    /** Indice du dernier cercle sélectionné, initialisé à -1 */
    private int currentCircleIndex = -1;
    //private static int countArcClicks = 0;
    /** Couleur courante de la classe, initilisée à bleue */
    private Color currentColor = Color.BLUE;

    private int src = -1;
    private int dest = -1;
    private boolean oriente;
    
    //Pour les Arcs :
    /** Dernier Nœud sur lequel on a passé la souris */
    private Ellipse2D.Double fromPoint = null ;
    /** Liste des Arcs */
    private ArrayList<MyLine> lines = new ArrayList<>();
    /** Nombre d'Arcs dessinés */
    private int numOfLines = 0;
    /** Arc courant */
    private int currentArcIndex = -1;

    public int getNumOfCircles() {
		return numOfCircles;
	}

    public boolean isOriente() {
        return oriente;
    }

    public void setOriente(boolean oriente) {
        this.oriente = oriente;
    }

    public int getNumOfLines(){
        return numOfLines;
    }

    public void setCurrentColor(Color c){
        this.currentColor = c;
    }

    public ArrayList<MyLine> getLines(){
        return lines;
    }

    public String[] getCircLbl(){
        return circLbl;
    }
      
    public Draw() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (Interface.mode==Interface.EDITION_MODE){
                    int x = evt.getX();
                    int y = evt.getY();
                    // Vérifie si on clique où non sur un cercle existant
                    currentCircleIndex = getRec(x, y);
                    currentArcIndex = getArc(x,y);
                    // Si on souhaite ajouter un Nœud :
                    if (Interface.activeTool==Interface.NOEUD_TOOL) {
                        if (currentCircleIndex < 0 && currentArcIndex < 0){ // not inside a circle
                            add(x, y);
                        }
                    }
                    // Si on souhaite ajouter un label à un Nœud :
                    if (Interface.activeTool==Interface.LABEL_TOOL) {
                        if (currentCircleIndex >= 0){ // inside a circle
                            try {
                                String lbl = JOptionPane.showInputDialog("Entrer label :");
                                circLbl[currentCircleIndex] = lbl;
                                repaint();
                            } catch (Exception NullPointerException){
                                System.out.println("Opération annulée");
                            }
                        } else if (currentArcIndex >= 0){
                            String text = JOptionPane.showInputDialog("Entrer le nouveau poids de l'Arc (seuls les entiers seront acceptés):");
                            try {
                                int pds = Integer.parseInt(text);
                                lines.get(currentArcIndex).setPoids(pds);
                                repaint();
                            } catch (Exception e) {
                                System.out.println("Pas un entier !");
                            }
                        } 
                    }
                    if (Interface.activeTool==Interface.ARC_TOOL) {
                        if ((currentCircleIndex >= 0) && (fromPoint==null)){
                            fromPoint = circ[currentCircleIndex];
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (Interface.mode==Interface.EDITION_MODE){
                    int x = evt.getX();
                    int y = evt.getY();
                    // Vérifie si on clique où non sur un cercle existant
                    currentCircleIndex = getRec(x, y);
                    if (Interface.activeTool==Interface.ARC_TOOL){
                        //ATTENTION : il faudra prendre le compte le cas où on pointe vers le meme cercle
                        if ((currentCircleIndex >= 0) && (fromPoint!=null) && (!fromPoint.equals(circ[currentCircleIndex]))) { // inside circle
                            Ellipse2D.Double p = circ[currentCircleIndex];
                            String text = JOptionPane.showInputDialog("Entrer le poids de l'Arc (seuls les entiers seront acceptés):");
                            try {
                                int pds = Integer.parseInt(text);
                                addLine(new MyLine(fromPoint, p,pds));
                                repaint();
                                //fromPoint = null;
                            } catch (Exception e) {
                                System.out.println("Pas un entier !");
                                //fromPoint = null;
                            } finally {
                                fromPoint = null;
                            }    
                        }
                    }
                }
            }
            
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (Interface.mode==Interface.EDITION_MODE){
                    // Si on clique deux fois sur un Nœud, on le supprime
                    if (Interface.activeTool==Interface.NOEUD_TOOL) {
                        if (evt.getClickCount() >= 2) {
                            remove(currentCircleIndex);
                        }
                    }
                    if (Interface.activeTool==Interface.ARC_TOOL) {
                        if (evt.getClickCount() >= 2){
                            removeArc(currentArcIndex);
                        }
                    }
                }
                if (Interface.mode==Interface.TRAITEMENT_MODE) {
                    if (Interface.activeTraitement==Interface.DIJKSTRA_TRAITEMENT){
                        int x = evt.getX();
                        int y = evt.getY();
                        if (src == -1){
                            src = getRec(x,y);
                        } else if (dest == -1){
                            dest = getRec(x,y);
                            if (dest != -1) {
                                dijkstra();
                            } else {
                                src = -1;
                            }
                        }
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
                    (int) circ[i].getCenterX(),
                    (int) circ[i].getCenterY() + 20);
        }
        for (int i = 0 ; i < numOfLines; i++){
            ((Graphics2D) g).setPaint(lines.get(i).getC());
            int x1 = lines.get(i).getFromPoint().x;
            int y1 = lines.get(i).getFromPoint().y;
            int x2 = lines.get(i).getToPoint().x;
            int y2 = lines.get(i).getToPoint().y;
            int x3 = lines.get(i).getClouPoint().x;
            int y3 = lines.get(i).getClouPoint().y;
            ((Graphics2D) g).drawLine(x1,y1,x3,y3);
            ((Graphics2D) g).drawLine(x3,y3,x2,y2);
            ((Graphics2D) g).draw(lines.get(i).getClou());
            ((Graphics2D) g).drawString(""+lines.get(i).getPoids(),x3,y3-10);
            if (oriente){
                int[] t = new int[4];
                int x4 = (x3+x2)/2;
                int y4 = (y3+y2)/2;
                fleche(x3,y3,x4,y4,t);
                ((Graphics2D) g).drawLine(x4,y4,t[0],t[1]);
                ((Graphics2D) g).drawLine(x4,y4,t[2],t[3]);
            }
        }
    }
 
    /**
     * Vérifie que l'on clique sur un cercle et donne son indice dans la liste circ
     * @param x = coordonnée x du pointeur de la souris
     * @param y = coordonnée y du pointeur de la souris
     * @return -1 si on ne clique pas sur un cercle, l'indice du cercle dans la liste sinon
     */
    public int getRec(int x, int y) {
        for (int i = 0; i < numOfCircles; i++) {
            if (circ[i].contains(x, y)) { // inside a circle
                return i;
            }
        }
        return -1;
    }

    public int getArc(int x, int y) {
        for (int i = 0; i < numOfLines; i++) {
            if (lines.get(i).getClou().contains(x,y)){
                return i;
            }
        }
        return -1;
    }
 
    /**
     * Ajoute un cercle dans la liste circ et actualise l'affichage
     * @param x = abcsisse du cercle à dessiner
     * @param y = ordonnée du cercle à dessiner
     */
    public void add(int x, int y) {
        if (numOfCircles < MAX) {
            //On ajoute un cercle à la liste circ et on actualise les attributs concernés
            circ[numOfCircles] =  new Ellipse2D.Double(x, y, circleW, circleW);
            circLbl[numOfCircles] = numOfCircles + "";
            currentCircleIndex = numOfCircles;
            numOfCircles++;
            //On actualise l'affichage avec le nouveau cercle
            repaint();
        }
    }
    
    /**
     * Ajoute une ligne dans la ArrayList lines et actualise l'affichage
     * @param line = ligne à ajouter
     */
    public void addLine(MyLine line) {
        if (numOfLines < MAX) {
            //On ajoute la ligne à la liste lines
            lines.add(line);
            numOfLines++;
            //On actualise l'affichage avec la nouvelle ligne
            repaint();
        }
    }

    public int findLine(int src, int dest){
        boolean trouve = false;
        int n = 0;
        while ((n<this.numOfLines) && (!trouve)){
            if ((this.lines.get(n).getFrom().equals(circ[src]))
                && (this.lines.get(n).getTo().equals(circ[dest]))){
                    trouve = true;
                    return n;
                }
            else {
                n++;
            }
        }
        return -1;
    }
    
    @Override
    /**
     * Supprime un Nœud sélectionné
     * @param n = indice du Nœud dans la liste circ
     */
    public void remove(int n) {
        if (n < 0 || n >= numOfCircles) {
            return;
        }
        // On supprime toutes les lignes qui sont relié au cercle supprimé
        if(numOfLines > 0){
            for (MyLine l : lines){
                if (l.getFrom().equals(circ[n]) || l.getTo().equals(circ[n])){
                    lines.remove(l);
                    numOfLines--;
                }
            }
        }
        //On remplace le cercle d'indic n par le dernier cercel ajouté
        //et on supprime le denrier cercle
        numOfCircles--;
        circ[n] = circ[numOfCircles];
        circLbl[n] = circLbl[numOfCircles];
        circ[numOfCircles] = null;
        circLbl[numOfCircles] = null; 
        if (currentCircleIndex == n) {
            currentCircleIndex = -1;
        }
        repaint();
    }

    public void removeArc(int n){
        if (n<0 || n>= numOfLines) {
            return;
        } else {
            lines.remove(lines.get(n));
            numOfLines--;
        }
        repaint();
    }
    
    /** Modifie le curseur lorsqu'on se trouve sur un cercle */
    @Override
    public void mouseMoved(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        if (getRec(x, y) >= 0 || getArc(x, y) >= 0) {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        } else {
            setCursor(Cursor.getDefaultCursor());
        }  
    }
 
    /** Déplace un cercle et les lignes qui lui sont rattachées */
    @Override
    public void mouseDragged(MouseEvent event) {
        if (Interface.activeTool==Interface.NOEUD_TOOL || Interface.activeTool==Interface.SELECT_TOOL) {
            int x = event.getX();
            int y = event.getY();
            if (currentCircleIndex >= 0) {
                circ[currentCircleIndex].x = x;
                circ[currentCircleIndex].y = y;
                repaint();
            }
        } else if (Interface.activeTool==Interface.ARC_TOOL && Interface.mode==Interface.EDITION_MODE) {
            int x = event.getX();
            int y = event.getY();
            if (currentArcIndex >= 0) {
                lines.get(currentArcIndex).setClou(new Ellipse2D.Double(x,y,10,10));
                repaint();
            }
        }    
    }

    public void reinit(){
        for (int i=0;i<this.numOfLines;i++){
            this.lines.get(i).setC(Color.BLUE);
        }
    }
    public void dijkstra(){
        boolean chemin = (new Dijkstra()).dijkstra(this, src, dest);
        if (!chemin){
            JOptionPane.showMessageDialog(this, "Il n'existe pas de chemin entre ces deux nœuds", "No Path !!", JOptionPane.INFORMATION_MESSAGE);
        }
        this.src = -1;
        this.dest = -1;
    }

    private void fleche(int x1, int x2, int x3, int x4,int[] tab){
        int nb = 10;
        double norme = Math.sqrt(Math.pow((x1-x3),2)+Math.pow((x2-x4),2));
        double alpha = Math.acos((x3-x1)/norme);
        if (x4>=x2){
            double a1 = 3*Math.PI/4 - alpha;
            tab[0] = (int) Math.round(x3+nb*Math.cos(a1));
            tab[1] = (int) Math.round(x4-nb*Math.sin(a1));
            tab[2] = (int) Math.round(x3-nb*Math.sin(a1));
            tab[3] = (int) Math.round(x4-nb*Math.cos(a1));
        } else {
            double a1 = 3*Math.PI/4 + alpha;
            tab[0] = (int) Math.round(x3+nb*Math.cos(a1));
            tab[1] = (int) Math.round(x4-nb*Math.sin(a1));
            tab[2] = (int) Math.round(x3-nb*Math.sin(a1));
            tab[3] = (int) Math.round(x4-nb*Math.cos(a1));
        }
    }

    
    /** Classe définissant une ligne */
    public class MyLine {
        /** Cercle/Nœud de départ */
        private final Ellipse2D.Double from;
        /** Cercle/Nœeud d'arrivée */
        private final Ellipse2D.Double  to;
        /** Poids de l'Arc */
        private int poids;
        /** Couleur */
        private Color c;
        /** Clou */
        private Ellipse2D.Double clou;

        /** 
         * Constructeur
         * @param fromPoint = cercle de départ
         * @param toPoint = cercle d'arrivée
         */
        public MyLine(Ellipse2D.Double fromPoint, Ellipse2D.Double toPoint, int pds) {
            this.from = fromPoint;
            this.to = toPoint;
            this.poids = pds;
            this.c = currentColor;
            int x = (this.getToPoint().x + this.getFromPoint().x)/2;
            int y = (this.getToPoint().y + this.getFromPoint().y)/2;
            this.clou = new Ellipse2D.Double(x,y,10,10);
        }

        public Ellipse2D.Double getClou(){
            return this.clou;
        }

        public int getPoids(){
            return poids;
        }

        public void setPoids(int p){
            this.poids = p;
        }
        
        public Color getC() {
			return c;
		}

        public void setC(Color col){
            this.c = col;
        }

		/** 
         * Getter du cercle de départ 
         * @return from (attribut)
         */
        public Ellipse2D.Double getFrom() {
            return this.from;
        }
        
        /** 
         * Getter du cercle d'arrivée 
         * @return to (attribut)
         */
        public Ellipse2D.Double getTo() {
            return this.to;
        }

        /** 
         * Getter des coordonnées du centre du cercle de départ
         * @return p = un Point
         */
        public Point getFromPoint() {
            double centerX = this.from.getCenterX();
            double centerY = this.from.getCenterY();
            Point p = new Point((int)centerX, (int)centerY);
            return p;
        }

        /** 
         * Getter des coordonnées du centre du cercle d'arrivée
         * @return p = un Point
         */
        public Point getToPoint() {
            double centerX = this.to.getCenterX();
            double centerY = this.to.getCenterY();
            Point p = new Point((int)centerX, (int)centerY);
            return p;
        }

        public Point getClouPoint() {
            double centerX = this.clou.getCenterX();
            double centerY = this.clou.getCenterY();
            Point p = new Point((int)centerX, (int)centerY);
            return p;
        }

        public void setClou(Ellipse2D.Double nouv){
            this.clou = nouv;
        }

    }

}
