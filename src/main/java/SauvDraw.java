/*=============================================
Classe SauvDraw permettant de sauvegarder 
le graphe dessiné
Auteur : Béryl CASSEL
Date de création : 11/03/2022
Date de dernière modification : 11/03/2022
=============================================*/

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SauvDraw{

    private final String source;

    private BufferedWriter fichier;

    private final String del=" ";

    public SauvDraw(String source){
        this.source = source;
    }

    public void sauvegarderDraw(Draw d){
        try {
            fichier = new BufferedWriter(new FileWriter(this.source));
            fichier.write(String.valueOf(d.getOriente())
                            + del + String.valueOf(d.getNumOfCircles())
                            + del + String.valueOf(d.getNumOfLines())
                            + del + String.valueOf(Math.round(d.getCircleW()))
                            + del + String.valueOf(Math.round(d.getLineWidth())));
            for (int i=0;i<d.getNumOfCircles();i++){
                fichier.newLine();
                fichier.write((int) d.getCirc()[i].x 
                            + del + (int) d.getCirc()[i].y
                            + del + d.getCircLbl()[i]);
            }
            for (int i=0;i<d.getNumOfLines();i++){
                fichier.newLine();
                fichier.write(d.getLines().get(i).getFromPoint().x 
                                + del + d.getLines().get(i).getFromPoint().y
                                + del + d.getLines().get(i).getToPoint().x
                                + del + d.getLines().get(i).getToPoint().y
                                + del + d.getLines().get(i).getPoids()
                                + del + d.getLines().get(i).getC().getRGB()
                                + del + d.getLines().get(i).getClouPoint().x
                                + del + d.getLines().get(i).getClouPoint().y);
            }
            fichier.flush();
            fichier.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fichier non trouvé !");
        } catch (IOException e) {
            System.out.println("Erreur d'écriture du fichier");
        }
    }

}