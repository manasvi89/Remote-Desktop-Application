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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
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
    // private static final String DIR_NAME = "test3";
      
       public Server() throws IOException, SQLException, ClassNotFoundException, Exception{
           serverSocket = new ServerSocket(8080);
           serverSocket.setSoTimeout(1000000);
       }

       @Override
    public void run()
            
       {

           while(true)
          { 
               try
               {  
                  
                  server = serverSocket.accept();
                  sendScreen();

              }
             catch(SocketTimeoutException st)
             {
                   System.out.println("Socket timed out!");

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
          //    serverApp.createDirectory(DIR_NAME);
              Thread thread = new Thread(serverApp);
                thread.start();
       }

    private void createDirectory(String dirName) {
        File newDir = new File(dirName);
        if(!newDir.exists()){
            boolean isCreated = newDir.mkdir();
        }
     }
    private void sendScreen()throws AWTException, IOException {
        
             Toolkit toolkit = Toolkit.getDefaultToolkit();
             Dimension dimensions = toolkit.getScreenSize();
                 Robot robot = new Robot();  // Robot class
                 BufferedImage screenshot = robot.createScreenCapture(new Rectangle(dimensions));
                 ImageIO.write(screenshot,"png",server.getOutputStream());
            
    }

}
