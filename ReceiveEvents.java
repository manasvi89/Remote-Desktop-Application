/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn_lab_project_rdp.server;

/**
 *
 * @author Dishita Madani
 */
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author manasvi
 */
public class ReceiveEvents {
  
    ReceiveEvents(Socket s,Robot r) throws IOException{
        
       Scanner scanner = new Scanner(s.getInputStream());
       
       while(true){
           try{
           int event = scanner.nextInt();
           System.out.println(event);
           switch(event){
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
                   r.mouseMove(scanner.nextInt(),scanner.nextInt());
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
           }catch(Exception ex){
               
               System.out.println("Exception in receive events:"+ex);
           }
       }
        
    }
}
