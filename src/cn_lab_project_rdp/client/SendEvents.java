package cn_lab_project_rdp.client;


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
import javax.swing.JPanel;

class SendEvents implements MouseListener, MouseMotionListener, KeyListener {

    PrintWriter writer;
    int width, height;
    private JPanel frame = null;

    SendEvents(JPanel frame, Socket s) {
       
        this.frame = frame;
        this.height = height;
        this.width = width;
        frame.addKeyListener(this);
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        System.out.println("Send Events called");
        try {
            writer = new PrintWriter(s.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(SendEvents.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        writer.println("1");
           System.out.println("Mouse Pressed");
        int click = e.getButton();
        writer.println(click);
        writer.flush();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        writer.println("2");
           System.out.println("Mouse released");
        int click = e.getButton();
        writer.println(click);
        writer.flush();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        writer.println("3");
           System.out.println("Mouse moved");
     
        double x = ((double) e.getX() / frame.getWidth());
        double y = ((double) e.getY() / frame.getHeight());
        writer.println(x);
        writer.println(y);
        writer.flush();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        writer.println("4");
        int code = e.getKeyCode();
        writer.println(code);
        System.out.println("Key Pressed");
        writer.flush();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        writer.println("5");
        int code = e.getKeyCode();
        writer.println(code);
        System.out.println("Key Released");
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
