/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shivani Kadam
 */
package Server;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Server extends Thread
{
       private static ServerSocket serverSocket=null;
       private static Socket server = null;
       private static ServerSocket eveSocket = null;
       private static Socket eve = null;
       private Date date = null;
      static Robot robot = null;
      static Thread ServerThread1=null;
       static Thread ServerThread2 = null;
    // private static final String DIR_NAME = "test3";
      
       public Server(Thread ServerThread1,Thread ServerThread2) throws IOException, SQLException, ClassNotFoundException, Exception{
           this.ServerThread1= ServerThread1;
           this.ServerThread2 = ServerThread2;
           serverSocket = new ServerSocket(8080);
           eveSocket = new ServerSocket(8888);
           serverSocket.setSoTimeout(1000000);
       }


       public static void main(String [] args) throws IOException, SQLException, ClassNotFoundException, Exception{

               ServerThread1 = new Thread(new Runnable() {
       @Override
    public void run()
            
       {
//                    GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
//			GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
           try {
               robot=new Robot();
           } catch (AWTException ex) {
               Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
           }
           while(true)
          { 
               try
               {  
                  
                  server = serverSocket.accept();
                  //eve = eveSocket.accept();
                  sendScreen(robot);
                  //new ReceiveEvents(eve,robot);

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
        });

Thread ServerThread2 = new Thread(new Runnable() {
       @Override
    public void run()
            
       {
           try {
               robot=new Robot();
           } catch (AWTException ex) {
               Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
           }
           while(true)
          { 
               try
               {  
                  
                 // server = serverSocket.accept();
                  eve = eveSocket.accept();
                   
                  
                  //sendScreen(robot);
                  new ReceiveEvents(eve,robot);

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
        });
          //    serverApp.createDirectory(DIR_NAME);
              Server serverApp = new Server(ServerThread1,ServerThread2);
              Thread thread = new Thread(serverApp);
                thread.start();
                ServerThread1.start();
ServerThread2.start();
       }

    private static void sendScreen(Robot robot)throws AWTException, IOException {
        
             Toolkit toolkit = Toolkit.getDefaultToolkit();
             Dimension dimensions = toolkit.getScreenSize();
                 //Robot robot = new Robot();  // Robot class
                 BufferedImage screenshot = robot.createScreenCapture(new Rectangle(dimensions));
                 ImageIO.write(screenshot,"png",server.getOutputStream());
            
    }

}
