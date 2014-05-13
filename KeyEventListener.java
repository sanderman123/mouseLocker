package mouseLocker;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.*;

import javax.swing.*;

class KeyEventListener extends JPanel implements KeyListener {
    private char c = 'e';
    private String message = "";
    boolean newChar = false;    

    public KeyEventListener() {
        this.setPreferredSize(new Dimension(200, 150));
        addKeyListener(this);
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawString(message, 20, 75);
    }

    public void keyPressed(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    public void keyTyped(KeyEvent e) {
        c = e.getKeyChar();
        newChar = true;
        message = "the key that pressed is " + c;
        repaint();        
    }
    
    public char getC(){
    	newChar = false;
    	return c;    	
    }
    
    public boolean newChar(){
    	return newChar;
    }
    
    public void setMessage(String s){
    	message = s;
    	repaint();
    }
    
    public void kill(){
    	Window w = SwingUtilities.getWindowAncestor(this);
    	w.setVisible(false);
    	w.dispose();    	
    }

    public static void main(String[] s) {
        JFrame f = new JFrame();
        f.getContentPane().add(new KeyEventListener());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}
