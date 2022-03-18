/*=============================================
Classe ChargeDraw permettant de charger
un graphe sauvegardé
Auteur : Béryl CASSEL
Date de création : 11/03/2022
Date de dernière modification : 11/03/2022
=============================================*/

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class ChargeDraw {

    private final String source;

    private final String del = " ";

    private Draw d;

    public ChargeDraw(String source){
        if (source.length() < 8 || !source.toLowerCase().substring(source.length()-8).equals(".inforeg")) {
            this.source = source + ".inforeg";
        } else {
            this.source = source;
        }
    }

    public Draw chargerDraw() throws FileNotFoundException, IOException{
        this.d = new Draw();
        d.setNomSauvegarde(this.source);
        try (BufferedReader fichier = new BufferedReader(new FileReader(this.source))) {
            String ligne = fichier.readLine();
            StringTokenizer tokenizer = new StringTokenizer(ligne, del);
            d.setOriente(Integer.parseInt(tokenizer.nextToken()));
            int nC = Integer.parseInt(tokenizer.nextToken());
            int nA = Integer.parseInt(tokenizer.nextToken());
            d.setCircleW((double) Integer.parseInt(tokenizer.nextToken()));
            d.setLineWidth((float) Integer.parseInt(tokenizer.nextToken()));
            int b = Integer.parseInt(tokenizer.nextToken());
            if (b==0){
                d.setPondere(false);
            }
            for (int j=0;j<nC;j++){
                addCircle(fichier.readLine(),j);
            }
            for (int i=0;i<nA;i++){
                addArc(fichier.readLine());
            }
            fichier.close();
        }
        return this.d;
    }
    
    private void addCircle(String ligne, int ind){
        StringTokenizer tokenizer = new StringTokenizer(ligne, del);
        int x = Integer.parseInt(tokenizer.nextToken());
        int y = Integer.parseInt(tokenizer.nextToken());
        d.add(x,y);
        d.getCircLbl()[ind] = tokenizer.nextToken();
    }

    private void addArc(String ligne){
        StringTokenizer tokenizer = new StringTokenizer(ligne, del);
        int x1 = Integer.parseInt(tokenizer.nextToken());
        int y1 = Integer.parseInt(tokenizer.nextToken());
        int x2 = Integer.parseInt(tokenizer.nextToken());
        int y2 = Integer.parseInt(tokenizer.nextToken());
        Ellipse2D.Double from = d.getCirc()[d.getRec(x1,y1)];
        Ellipse2D.Double to = d.getCirc()[d.getRec(x2,y2)];
        int p = Integer.parseInt(tokenizer.nextToken());
        int rgb = Integer.parseInt(tokenizer.nextToken());
        Color c = new Color(rgb);
        MyLine arc = new MyLine(from,to, p, c);
        int x3 = Integer.parseInt(tokenizer.nextToken());
        int y3 = Integer.parseInt(tokenizer.nextToken());
        Ellipse2D.Double clou = new Ellipse2D.Double(x3,y3,MyLine.RCLOU,MyLine.RCLOU);
        arc.setClou(clou);
        d.addLine(arc);
    }
}
