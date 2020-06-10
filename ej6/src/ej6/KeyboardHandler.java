package ej6;

import java.awt.event.*;

public class KeyboardHandler extends KeyAdapter {
    
    private Fprincipal f;


    public KeyboardHandler(Fprincipal f){
        super();
        this.f = f;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            this.f.movizquierda();
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            this.f.movderecha();
        }
    }


}