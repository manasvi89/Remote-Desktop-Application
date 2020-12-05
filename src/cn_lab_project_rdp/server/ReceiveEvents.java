
package cn_lab_project_rdp.server;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ReceiveEvents {

    //Constructor with socket and robot as parameters
    ReceiveEvents(Socket s, Robot r) throws IOException, AWTException {
    
        //Input Stream for receiving events from clients
        Scanner scanner = new Scanner(s.getInputStream());
        
        //Size of the Server screen
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimensions = toolkit.getScreenSize();
        
        //Creating new capture for height and width values
        Robot re = new Robot();
        BufferedImage st = re.createScreenCapture(new Rectangle(dimensions));
        
        //Set height and width values
        int height = st.getHeight();
        int width = st.getWidth();
        
        //Received Events decoder
        while (true) {
            try {
                
                //Getting Event id from input stream
                int event = scanner.nextInt();
                System.out.println(event);
                
                //Check Event id and take actions accordingly
                switch (event) {
                    case 1:
                        int mask = InputEvent.getMaskForButton(event);
                        r.mousePress(mask);
                        System.out.println("Mouse pressed");
                        break;
                    case 2:
                        int mask1 = InputEvent.getMaskForButton(event);
                        r.mouseRelease(mask1);
                        System.out.println("Mouse Released");
                        break;
                    case 3:
                        r.mouseMove((int) (scanner.nextDouble() * width), (int) (scanner.nextDouble() * height));
                        System.out.println("Mouse moved");
                        break;
                    case 4:
                        r.keyPress(scanner.nextInt());
                        System.out.println("Key Pressed");
                        break;
                    case 5:
                        r.keyRelease(scanner.nextInt());
                        System.out.println("Key Released");
                        break;
                    default:
                        break;
                }
            } catch (Exception ex) {

                System.out.println("Exception in receive events:" + ex);
            }
        }

    }
}
