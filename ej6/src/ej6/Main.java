package ej6;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        Fprincipal f = new Fprincipal();

        f.setSize(200,600);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}