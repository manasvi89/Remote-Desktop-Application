/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shivani Kadam
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.net.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Server extends Thread
{
       private ServerSocket serverSocket=null;
       private static Socket server = null;
       private Date date = null;
     private static final String DIR_NAME = "test3";
      
       public Server() throws IOException, SQLException, ClassNotFoundException, Exception{
           serverSocket = new ServerSocket(8080);
           serverSocket.setSoTimeout(100000);
       }

    public void run()
       {
     
      
        JFrame frame = new JFrame();
       // JPanel pane = new JPanel();
        //JLabel lab = new JLabel();
        //pane.add(lab);
        //frame.getContentPane().add(pane);
        frame.setSize(1920, 1050);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        
        frame.getContentPane().add(new JLabel("Hello"));
        frame.pack();
        frame.setVisible(true);
           while(true)
          { 
               try
               {  
                  
                  server = serverSocket.accept();
                  date = new Date();
                      DateFormat dateFormat = new SimpleDateFormat("_yyMMdd_HHmmss");
                  String fileName = server.getInetAddress().getHostName().replace(".", "-");
                  System.out.println(fileName);
               BufferedImage img=ImageIO.read(ImageIO.createImageInputStream(server.getInputStream()));
                 // ImageIO.write(img, "png", new File
                  //(dateFormat.format(date)+".png"));
                      
                  JLabel lab = new JLabel(new ImageIcon(img));
                  lab.setSize(500, 500);
                  frame.add(lab);
                  frame.repaint();
                  frame.pack();
                  // lab.setIcon(new ImageIcon(img));
                  
//                  Graphics graphics = pane.getGraphics();
////	           graphics.drawImage(img, 0, 0,300, 80, pane);
//               JPanel pane = new JPanel();
//                  JLabel lab = new JLabel(new ImageIcon(img));
//                  pane.add(lab);
//                  pane.repaint();
                  //pane.setFocusable(true);
               
                 // frame.setLayout(new BorderLayout());
                 // frame.getContentPane().add(new JLabel(new ImageIcon(img)) );
                  
             
             
                  //frame.setSize(300,80);
                //  frame.getContentPane().add(pane);
                  
                  
                  
//                  
                    sleep(1000);
                    frame.remove(lab);
                    
                 // frame.getContentPane().removeAll();
                  //frame.repaint();
                  System.out.println("Image here");
               
                  //lblimg.setIcon(img);
              }
             catch(SocketTimeoutException st)
             {
                   System.out.println("Socket timed out!");
 //createLogFile("[stocktimeoutexception]"+stExp.getMessage());
                  break;
             }
             catch(IOException e)
             {
                  e.printStackTrace();
                  break;
             }
             catch(Exception ex)
            {
                  System.out.println(ex);
            }
          }
       }
     
       public static void main(String [] args) throws IOException, SQLException, ClassNotFoundException, Exception{
              Server serverApp = new Server();
              serverApp.createDirectory(DIR_NAME);
              Thread thread = new Thread(serverApp);
                thread.start();
       }

    private void createDirectory(String dirName) {
        File newDir = new File(dirName);
        if(!newDir.exists()){
            boolean isCreated = newDir.mkdir();
        }
     }
    

// private void createLogFile(String message) {
//           String errMsg = new Date() +" --> " + message;
//        try {
//            File logFile = new File(DIR_NAME+"\\log"+System.currentTimeMillis()//+".txt");
//            logFile.createNewFile();
//            FileWriter fileWriter = new FileWriter(logFile.getAbsoluteFile());
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            bufferedWriter.write(errMsg);
//            bufferedWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//       
//    }
}
