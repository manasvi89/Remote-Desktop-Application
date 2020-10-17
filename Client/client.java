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
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;

public class client implements Runnable {

    private static long nextTime = 0;
    private static client clientApp = null;
    private String serverName = "127.0.0.1"; //loop back ip
    private int portNo = 8080;
    //private Socket serverSocket = null;
   
    
    public static void main(String[] args) throws InterruptedException {
        clientApp = new client();
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
        while(true){
            if(nextTime < System.currentTimeMillis()){
                System.out.println(" get screen shot ");
                try {
                    clientApp.sendScreen();
                    clientApp.getNextFreq();
                } catch (AWTException e) {
                    // TO DO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TO DO Auto-generated catch block
                    e.printStackTrace();
                } catch(Exception e){
                    e.printStackTrace();
                }
               
            }
            //System.out.println(" statrted ....");
        }
       
    }

    private void sendScreen()throws AWTException, IOException {
           Socket serverSocket = new Socket(serverName, portNo);
             Toolkit toolkit = Toolkit.getDefaultToolkit();
             Dimension dimensions = toolkit.getScreenSize();
                 Robot robot = new Robot();  // Robot class
                 BufferedImage screenshot = robot.createScreenCapture(new Rectangle(dimensions));
                 ImageIO.write(screenshot,"png",serverSocket.getOutputStream());
                 serverSocket.close();
    }
}

