/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shivani Kadam
 */
import Server.FileEvent;
import java.net.*;
import java.io.*;

public class clientfile {

    private static String sourceFilePath;
    private static ObjectInputStream inputStream =null;
    private FileEvent fileEvent = null;
    private File dstFile = null;
    private FileOutputStream fileOutputStream = null;

    public static void main(String args[]) throws Exception {
        Socket sock = new Socket("127.0.0.1", 1234);

        // reading the file name 
        System.out.print("Enter the file name : ");
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
        sourceFilePath = keyRead.readLine();

        // sending the file name to server
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);
        pwrite.println(sourceFilePath);
        inputStream = new ObjectInputStream(sock.getInputStream());
        clientfile o1 = new clientfile();
        o1.downloadFile();
        
        sock.close();
        ostream.close();;
        pwrite.close();
    }

    public void downloadFile() {
        try {
            fileEvent = (FileEvent) inputStream.readObject();
            if (fileEvent.getStatus().equalsIgnoreCase("Error")) {
                System.out.println("Error occurred ..So exiting");
                System.exit(0);
            }
            String outputFile = fileEvent.getDestinationDirectory() + fileEvent.getFilename();
            if (!new File(fileEvent.getDestinationDirectory()).exists()) {
                new File(fileEvent.getDestinationDirectory()).mkdirs();
            }
            dstFile = new File(outputFile);
            fileOutputStream = new FileOutputStream(dstFile);
            fileOutputStream.write(fileEvent.getFileData());
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("Output file : " + outputFile + " is successfully saved ");
            Thread.sleep(3000);
            System.exit(0);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
