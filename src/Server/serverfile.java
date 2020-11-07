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
import java.io.*;

public class serverfile {

    private Socket socket = null;
    private File dstFile = null;
    private static ObjectOutputStream outputStream = null;
    private static ObjectInputStream inputStream =null;
    private FileEvent fileEvent = null;
    private static String fname = null;
    private FileOutputStream fileOutputStream = null;
    private String destinationPath1 = "F:/study/sem5/WCLab/";

    public static void main(String args[]) throws Exception {                           // establishing the connection with the server
        ServerSocket sersock = new ServerSocket(1234);
        System.out.println("Server ready ");
        Socket sock = sersock.accept();
        System.out.println("Connection is successful ");

       serverfile o1 = new serverfile();
       
        InputStream istream = sock.getInputStream(); 
         BufferedReader fileRead = new BufferedReader(new InputStreamReader(istream));
         fname = fileRead.readLine();
         System.out.println("inputstream : " + istream);
         System.out.println("fileread : " + fileRead);
         System.out.println("fname : " + fname);
         if(fname.equals("")){
             inputStream = new ObjectInputStream(sock.getInputStream());
            System.out.println("download thvi joie");
            o1.downloadFile();
         }
         else{
         outputStream = new ObjectOutputStream(sock.getOutputStream());
         System.out.println("send nai");
         o1.sendFile(); 
          }  
         istream.close();
        fileRead.close();
             
        sock.close();
      
        sersock.close();
        
    }

    public void sendFile() {
        fileEvent = new FileEvent();
        String fileName = fname.substring(fname.lastIndexOf("/") + 1, fname.length());
        String path = fname.substring(0, fname.lastIndexOf("/") + 1);
        fileEvent.setDestinationDirectory(destinationPath1);
        fileEvent.setFilename(fileName);
        fileEvent.setSourceDirectory(fname);
        File file = new File(fname);
        if (file.isFile()) {
            try {
                DataInputStream diStream = new DataInputStream(new FileInputStream(file));
                long len = (int) file.length();
                byte[] fileBytes = new byte[(int) len];
                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read, fileBytes.length - read)) >= 0) {
                    read = read + numRead;
                }
                fileEvent.setFileSize(len);
                fileEvent.setFileData(fileBytes);
                fileEvent.setStatus("Success");
            } catch (Exception e) {
                e.printStackTrace();
                fileEvent.setStatus("Error");
            }
        } else {
            System.out.println("path specified is not pointing to a file");
            fileEvent.setStatus("Error");
        }
//Now writing the FileEvent object to socket
        try {
            outputStream.writeObject(fileEvent);
            System.out.println("Done...Going to exit");
            Thread.sleep(3000);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
