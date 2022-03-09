/*=============================================
Classe LabelInput qui permet de définir le label d'un noeud avec pop up
Auteur : Samy AMAL
Date de création : 04/03/2022
Date de dernière modification : 06/03/2022
=============================================*/

/*import javax.swing.*;
import java.awt.event.*;

public class LabelInput extends JFrame {

    private JButton buttonOK;
    private JTextField label; 
    public boolean on ;

    public JTextField getLabel() {
        return label;
    }

    public LabelInput(){
        this.on = true; 
        this.setSize(325,100);
        this.setTitle("Enter Label :");
        this.setDefaultCloseOperation(
        JFrame.EXIT_ON_CLOSE);

        ButtonListener bl = new ButtonListener();

        JPanel panel1 = new JPanel();

        panel1.add(new JLabel("Enter text : ")); 
        label = new JTextField(15); 
        panel1.add(label);

        buttonOK = new JButton("OK");
        buttonOK.addActionListener(bl);
        panel1.add(buttonOK);

        this.add(panel1);
        this.setVisible(true);
    }
    
    public void closeLbl(){
        this.on = false;
        this.dispose();
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == buttonOK) {
                String name = label.getText(); 
                if (name.length() == 0) {
                    JOptionPane.showMessageDialog(LabelInput.this,"Y A R","Moron",JOptionPane.INFORMATION_MESSAGE);
                } else {
                    LabelInput.this.dispose();//JOptionPane.showMessageDialog(LabelInput.this,"Good morning " + name,"Salutations",JOptionPane.INFORMATION_MESSAGE);
                }
                label.requestFocus(); 
            }
        }
    }
}*/