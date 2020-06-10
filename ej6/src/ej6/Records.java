package ej6;

import java.io.IOException;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Records extends JDialog {
    
    private Fprincipal l;
    private JTextArea txa = new JTextArea();

    public Records(Fprincipal c) {
        super(c,"Records",true);
        this.l = c;
        this.txa.setSize(this.txa.getPreferredSize());
        this.txa.setEditable(false);
        this.add(txa);
        
        if(this.l.getFile().exists() && this.l.getFile().length() != 0){
            try (Scanner sc = new Scanner(this.l.getFile())) {
                while(sc.hasNextLine()){
                    this.txa.setText(this.txa.getText()+sc.nextLine()+"\n");
                }
                this.txa.setSize(this.txa.getPreferredSize());
            } catch (SecurityException | IllegalArgumentException | IOException e) {
                JOptionPane.showMessageDialog(this, "Error al leer el archivo!");
            }
        }
    }

}