package ej6;

import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

public class Fprincipal extends JFrame implements ActionListener{

    private int intervalo = 500,cont = 0;
    private ArrayList<JLabel> minas = new ArrayList<JLabel>();
    private JLabel personaje = new JLabel();
    private JButton izquierda = new JButton("<");
    private JButton derecha = new JButton(">");
    private Timer timer = new Timer(intervalo,this);
    private File file = new File(System.getProperty("user.home")+System.getProperty("file.separator")+"records.txt");
    private JMenuBar menu = new JMenuBar();
    private JLabel puntuacion = new JLabel("Puntuación: 0");
    private JButton jugar = new JButton("Jugar");
    private Guardar g;
    private Records r;

    public Fprincipal() {
        super("Esquiva las minas!");
        this.setLayout(null);
        this.setFocusable(true);

        JMenu m = new JMenu("Opciones");
        JMenuItem guardar = new JMenuItem("Guardar");        
        guardar.addActionListener(this);
        m.add(guardar);
        JMenuItem records = new JMenuItem("Ver records");        
        records.addActionListener(this);
        m.add(records);
        this.menu.add(m);
        this.setJMenuBar(menu);

        this.jugar.setSize(this.jugar.getPreferredSize());
        this.jugar.setLocation(60,500);
        this.jugar.addActionListener(this);
        btnjugar();
        this.add(this.jugar);

        personaje.setIcon(new ImageIcon("src/ej6/imagenes/personaje.png"));
        personaje.setSize(personaje.getPreferredSize());
        personaje.setLocation(80,400);
        this.add(personaje);

        izquierda.setSize(izquierda.getPreferredSize());
        izquierda.setLocation(45,440);
        izquierda.addActionListener(this);
        this.addKeyListener(new KeyboardHandler(this));
        this.add(izquierda);

        derecha.setSize(derecha.getPreferredSize());
        derecha.setLocation(100,440);
        derecha.addActionListener(this);
        this.add(derecha);

        int espacio = 10;
        for(int i = 0;i < 4;i++){
            minas.add(crearLabel());
            this.add(minas.get(i));
            minas.get(i).setLocation(espacio,0);
            espacio+=50;
        }

        this.puntuacion.setSize(this.puntuacion.getPreferredSize());
        this.puntuacion.setLocation(45,475);
        this.add(this.puntuacion);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == this.izquierda){
            movizquierda();
        }else if(arg0.getSource() == this.derecha){
            movderecha();
        }else if(arg0.getSource() == this.jugar){
            timer.start();
            btnjugar();
        }else if(arg0.getSource() == this.menu.getMenu(0).getItem(1)){
            timer.stop();//Si se quieren ver los records en medio de una partida se pausa la partida.
            btnjugar();
            if(file.exists() && file.length() > 0){
                r = new Records(this);
                r.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                r.pack();
                r.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(this, "El archivo de puntuaciones está vacio!");
            }
        }else if(arg0.getSource() == this.menu.getMenu(0).getItem(0)){
            timer.stop();
            btnjugar();
            g = new Guardar(this);
            g.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            g.setVisible(true);
        }else{
            if(cont % 50 == 0 && cont != 0){//Intervalo de tiempo que controla la bajada de las bombas.
                if(intervalo -50 > 0){
                    intervalo-=50;
                }
            }
            cont++;
            int select = (int)(Math.random()*4);
            minas.get(select).setLocation(minas.get(select).getLocation().x,minas.get(select).getLocation().y+10);
            for (int i = 0; i < minas.size(); i++) {
                btnjugar();
                if(personaje.getBounds().intersects(minas.get(i).getBounds())){//Se comprueba que la mina toque al personaje.
                    timer.stop();
                    if(JOptionPane.showConfirmDialog(this, "¿Quieres guardar tu puntuación?") == 0){
                        g = new Guardar(this);
                        g.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        g.setVisible(true);
                    }
                    if(JOptionPane.showConfirmDialog(this, "¿Quieres volver a jugar?") == 0){//Si la mina toca se pregunta si se quiere volver a emprezar.
                        for (int j = 0; j < minas.size(); j++) {
                            minas.get(j).setLocation(minas.get(j).getLocation().x,0);
                        }

                        resetPuntuaion();

                        timer.start();
                    }
                }else{
                    if(minas.get(i).getLocation().y >= 400){//Se comprueba que la mina no toque al personaje.
                        sumarPuntuacion();
                    }
                }
                if(minas.get(i).getLocation().y >= 400 && timer.isRunning()){//Si la mina no toca vuelve arriba.
                    minas.get(i).setLocation(minas.get(i).getLocation().x,0);
                }
            }
        }
		
    }

    /**
     * Determina el estado del botón que permite jugar según el estado de la partida.
     */
    private void btnjugar() {
        this.jugar.setEnabled(!this.timer.isRunning());
    }

    /**
     * Resetea la puntuación a 0.
     */
    private void resetPuntuaion() {
        String res = this.puntuacion.getText().substring(this.puntuacion.getText().indexOf(":")+2,this.puntuacion.getText().length());
        this.puntuacion.setText(this.puntuacion.getText().replace(res,String.valueOf(0)));
        this.puntuacion.setSize(this.puntuacion.getPreferredSize());
    }

    /**
     * Suma 1 a la puntuación.
     */
    private void sumarPuntuacion() {
        String res = this.puntuacion.getText().substring(this.puntuacion.getText().indexOf(":")+2,this.puntuacion.getText().length());
        this.puntuacion.setText(this.puntuacion.getText().replace(res,String.valueOf(Integer.parseInt(res)+1)));
        this.puntuacion.setSize(this.puntuacion.getPreferredSize());
    }

    /**
     * Mueve al personaje a la derecha.
     */
    public void movderecha() {
        if(personaje.getLocation().x <= 165){
            personaje.setLocation(personaje.getLocation().x+10,personaje.getLocation().y);
        }
    }

    /**
     * Mueve al personaje e la izquierda.
     */
    public void movizquierda() {
        if(personaje.getLocation().x >=  20){
            personaje.setLocation(personaje.getLocation().x-10,personaje.getLocation().y);
        }
    }
    
    /**
     * Crea las minas.
     * @return Devuelve una mina.
     */
    public JLabel crearLabel(){
        JLabel res = new JLabel();
        res.setIcon(new ImageIcon("src/ej6/imagenes/mina.png"));
        res.setLocation(5,(int)(Math.random()*430+10));
        res.setSize(res.getPreferredSize());
        return res;
    }

    /**
     * Permite consultar el valor de la variable file.
     * @return Devuelve el valor de la variable file.
     */
    public File getFile() {
        return file;
    }

    /**
     * Permite consultar el valor de la variable izquierda.
     * @return Devuelve el valor de la variable izquierda.
     */
    public JButton getIzquierda() {
        return izquierda;
    }

    /**
     * Permite consultar el valor de la variable derecha.
     * @return Devuelve el valor de la variable derecha.
     */
    public JButton getDerecha() {
        return derecha;
    }

    /**
     * Permite consultar el valor de la variable puntuacion.
     * @return Devuelve el valor de la variable puntuacion.
     */
    public JLabel getPuntuacion() {
        return puntuacion;
    }
}