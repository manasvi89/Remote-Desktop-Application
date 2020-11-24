/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn_lab_project_rdp.client;

/**
 *
 * @author Dishita Madani
 */
import cn_lab_project_rdp.server.servermsg;
import cn_lab_project_rdp.server.servermsgpanel;
import java.awt.AWTException;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

class Client extends Thread {

    class Control {

        public AtomicInteger height = new AtomicInteger(0);
        public AtomicInteger width = new AtomicInteger(0);
        public volatile JFrame frame;
        public volatile JPanel panel;
    }

    final Control c = new Control();
    private static long nextTime = 0;
    private static Client clientApp = null;
    private static String serverName = "127.0.0.1"; //loop back ip
    private static int portNo = 8087;
    //static JFrame frame;
    // static JPanel panel;
    //static int width;
    //static int height;
    //private Date date = null;
    //static Thread t1;
    //static Thread t2;
    //private Socket serverSocket = null;

    class T1 implements Runnable {

        @Override
        public void run() {

            while (true) {
                try {
                    

                    Socket serverSocket = new Socket(serverName, portNo);
                    //Socket eve = new Socket(serverName,8888);
                    DateFormat dateFormat = new SimpleDateFormat("_yyMMdd_HHmmss");
                    String fileName = serverSocket.getInetAddress().getHostName().replace(".", "-");
                    System.out.println(fileName);
                    BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(serverSocket.getInputStream()));
                    c.height.set(img.getHeight());
                    c.width.set(img.getWidth());

                    JLabel lab;
                    lab = new JLabel(new ImageIcon((new ImageIcon(img).getImage().getScaledInstance(c.panel.getWidth(), c.panel.getHeight(), java.awt.Image.SCALE_SMOOTH))));
                    // lab.setSize(500, 500);
                    c.panel.add(lab);
                    c.frame.repaint();
                    c.frame.pack();

                    //    new SendEvents(panel,eve,height,width);
                    sleep(100);
                    c.panel.remove(lab);
                    
                    clientmsgpanel cp = new  clientmsgpanel();
                    c.panel.add(cp);
                    
                    servermsgpanel sp = new servermsgpanel();
                    c.panel.add(sp);
                    
                    System.out.println("Image here");

                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

    class T2 implements Runnable {

        @Override
        public void run() {

            while (true) {
                try {

//                Socket serverSocket = new Socket(serverName, portNo);
                    Socket eve = new Socket(serverName, 8888);

                    System.out.println("Height:" + c.height.get());
                    System.out.println("Width:" + c.width.get());
                    new SendEvents(c.panel, eve, c.height.get(), c.width.get());

                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        
    }

    class T3 implements Runnable{

        @Override
        public void run() {
            clientmsg c = new clientmsg();
            c.sendmess();
            c.repaint();
            
            c.setVisible(true);
        }
        
    }

    Client() {

        c.frame = new JFrame();
        c.panel = new JPanel();
        c.frame.setSize(1920, 1050);
        c.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        c.frame.add(c.panel);
        c.frame.pack();
        c.frame.setVisible(true);
                c.height.set(c.height.get());
                c.width.set(c.width.get());
        //T1 t1 = new T1();
        //T2 t2 = new T2();
        T3 t3 = new T3();
        //new Thread(t1).start();
        //new Thread(t2).start();
        new Thread(t3).start();
        //t1 = this.t1;
        //t2 = this.t2;
        //  t1 = new T1();
        //t2 = new T2();

    }
//   public synchronized int getHeight(){
//        return this.height;
//    }
//   synchronized void setHeight(int height){
//       this.height = height;
//   }
//   public synchronized int getWidth(){
//        return this.width;
//    }
//   synchronized void setWidth(int width){
//       this.width = width;
//   }

    public static void main(String[] args) throws InterruptedException {

        clientfirstpage c1 = new clientfirstpage();
           c1.setVisible(true);
        //clientApp = new Client();

        
        //clientApp.getNextFreq();

        //Thread thread = new Thread(clientApp);
        //thread.start();
    }

    private void getNextFreq() {
        long currentTime = System.currentTimeMillis();
        Random random = new Random();
        long value = random.nextInt(10);
        nextTime = currentTime + value;
        //return currentTime+value;
    }

}