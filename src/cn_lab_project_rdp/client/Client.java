package cn_lab_project_rdp.client;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Client extends Thread {

    class Control {

        public AtomicInteger height = new AtomicInteger(0);
        public AtomicInteger width = new AtomicInteger(0);
        public volatile JFrame frame;
        public volatile JPanel panel;
    }

    final Control c = new Control();
    clientfirstpage c1 = new clientfirstpage();
    private static long nextTime = 0;
    private static Client clientApp = null;
    private String serverName = c1.ipAddress.getText(); //loop back ip
    private static int portNo = 8087;
    static String ip ="";
    
    
    class T1 implements Runnable {

        @Override
        public void run() {

            while (true) {
                try {
Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimensions = toolkit.getScreenSize();
                    Socket serverSocket = new Socket(serverName, portNo);
                    DateFormat dateFormat = new SimpleDateFormat("_yyMMdd_HHmmss");
                    String fileName = serverSocket.getInetAddress().getHostName().replace(".", "-");
                    System.out.println(fileName);
                    BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(serverSocket.getInputStream()));
                    c.height.set(img.getHeight());
                    c.width.set(img.getWidth());
     
                    c.panel.setSize(dimensions.width,dimensions.height);
     
                  
                    JLabel lab;
                    lab = new JLabel(new ImageIcon((new ImageIcon(img).getImage().getScaledInstance(c.panel.getWidth(), c.panel.getHeight(), java.awt.Image.SCALE_SMOOTH))));
                    c.panel.add(lab);
                    c.frame.repaint();
                    c.frame.pack();

                    sleep(100);
                    c.panel.remove(lab);

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

                    Socket eve = new Socket(serverName, 8888);

                    new SendEvents(c.panel, eve);

                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    class T3 implements Runnable {

        @Override
        public void run() {
            clientmsg c = new clientmsg(ip);
            c.sendmess();
            c.repaint();
            c.pack();
        }

    }

    Client(String ip) {

        c.frame = new JFrame();
        c.panel = new JPanel();
        c.frame.setSize(1930, 1050);
         c.panel.setSize(1930, 1050);
        c.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        c.frame.add(c.panel);
        c.frame.pack();
        c.frame.setVisible(true);
        c.height.set(c.height.get());
        c.width.set(c.width.get());
        this.ip = ip;
        serverName = ip;
        T1 t1 = new T1();
        T2 t2 = new T2();
        T3 t3 = new T3();
        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();

 
    }



}