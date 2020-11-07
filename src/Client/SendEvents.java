/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author manasvi
 */
 class SendEvents implements MouseListener,MouseMotionListener,KeyListener{
    PrintWriter writer;
    int width,height;
    private JPanel frame = null;
    SendEvents(JPanel frame,Socket s,int height,int width){
       this.frame = frame;
        this.height = height;
        this.width= width;
         frame.addKeyListener(this);
         frame.addMouseListener(this);
         frame.addMouseMotionListener(this);
         
        try {
            writer = new PrintWriter(s.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(SendEvents.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
       writer.println("1");
        int click = e.getButton();
        writer.println(click);
        writer.flush();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        writer.println("2");
        int click = e.getButton();
        writer.println(click);
        writer.flush();
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        writer.println("3");
       double x_check = (double)width/frame.getWidth();
        double y_check =(double) height/frame.getHeight();
        int x =(int) (e.getX()*x_check);
        int y =(int)( e.getY()*y_check);
        writer.println(x);
        writer.println(y);
        writer.flush();
    }


    @Override
    public void keyPressed(KeyEvent e) {
         writer.println("4");
         int code = e.getKeyCode();
         writer.println(code);
         writer.flush();
    }

    @Override
    public void keyReleased(KeyEvent e) {
         writer.println("5");
         int code = e.getKeyCode();
         writer.println(code);
         writer.flush();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }

    @Override
    public void mouseDragged(MouseEvent e) {
       
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }
    
}
