/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shivani Kadam
 */
import java.net.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class server extends Thread
{
       private ServerSocket serverSocket=null;
       private static Socket server = null;
       private Date date = null;
     private static final String DIR_NAME = "test3";
      
       public server() throws IOException, SQLException, ClassNotFoundException, Exception{
           serverSocket = new ServerSocket(8080);
           serverSocket.setSoTimeout(1000000);
       }

    public void run()
       {
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
                  ImageIO.write(img, "png", new File
                  (dateFormat.format(date)+".png"));
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
              server serverApp = new server();
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
