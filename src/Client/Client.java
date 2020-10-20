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
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Client implements Runnable {

    private static long nextTime = 0;
    private static Client clientApp = null;
    private String serverName = "127.0.0.1"; //loop back ip
    private int portNo = 8080;
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
            try {
                sleep(1000);
                //System.out.println(" statrted ....");
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
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
