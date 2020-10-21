/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shivani Kadam
 */
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Client implements Runnable {

    private static long nextTime = 0;
    private static Client clientApp = null;
    private String serverName = "127.0.0.1"; //loop back ip
    private int portNo = 8080;
    private Date date = null;
    //private Socket serverSocket = null;
   
    
    public static void main(String[] args) throws InterruptedException {
        clientApp = new Client();
        clientApp.getNextFreq();
        Thread thread = new Thread(clientApp);
        thread.start();
    }

    private void getNextFreq() {
        long currentTime = System.currentTimeMillis();
        Random random = new Random();
        long value = random.nextInt(10); 
        nextTime = currentTime + value;
        //return currentTime+value;
    }

    @Override
    public void run() {
         JFrame frame = new JFrame();
    
        frame.setSize(1920, 1050);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        
        frame.pack();
        frame.setVisible(true);
        while(true){
            try {

                date = new Date();
                Socket serverSocket = new Socket(serverName, portNo);
                DateFormat dateFormat = new SimpleDateFormat("_yyMMdd_HHmmss");
                String fileName = serverSocket.getInetAddress().getHostName().replace(".", "-");
                System.out.println(fileName);
                BufferedImage img=ImageIO.read(ImageIO.createImageInputStream(serverSocket.getInputStream()));
                
                
                JLabel lab = new JLabel(new ImageIcon(img));
                lab.setSize(500, 500);
                frame.add(lab);
                frame.repaint();
                frame.pack();
                
                
                sleep(1000);
                frame.remove(lab);
                
                System.out.println("Image here");
             
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                 Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
       
    }

    
}
