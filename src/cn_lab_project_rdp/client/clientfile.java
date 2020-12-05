/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn_lab_project_rdp.client;

/**
 *
 * @author Dishita Madani
 */

import cn_lab_project_rdp.server.FileEvent;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class clientfile {

    private static String sourceFilePath1;
    private static String sourceFilePath2;
    private static ObjectInputStream inputStream = null;
    private static ObjectOutputStream outputStream = null;
    private FileEvent fileEvent = null;
    private File dstFile = null;
    private static String fname = null;
    private FileOutputStream fileOutputStream = null;
    private String destinationPath = "D:/Download/";

    public void fileClient() throws IOException{
        Socket sock = new Socket("127.0.0.1", 1234);
        clientfile o1 = new clientfile();
        Scanner input = new Scanner(System.in);

        
            System.out.println("Want to upload or download ? ");
            String opt = input.next();
            if (opt.equals("upload")) {
                System.out.print("Enter the file name : ");
                BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
              //  sourceFilePath1 = keyRead.readLine();
                
                //InputStream istream = sock.getInputStream();
                //BufferedReader fileRead = new BufferedReader(new InputStreamReader(istream));
                fname = keyRead.readLine();
                OutputStream ostream = null;
                ostream = sock.getOutputStream();
                PrintWriter pwrite = new PrintWriter(ostream, true);
                pwrite.println("");
                outputStream = new ObjectOutputStream(sock.getOutputStream());
                System.out.println("send thvi joie");
                o1.sendFile();
                sock.close();
                keyRead.close();
                

            } else if (opt.equals("download")) {
                System.out.print("Enter the file name : ");
                BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
                sourceFilePath2 = keyRead.readLine();

                // sending the file name to server
                OutputStream ostream = null;
                ostream = sock.getOutputStream();
                PrintWriter pwrite = new PrintWriter(ostream, true);
                pwrite.println(sourceFilePath2 + " " +opt);
                inputStream = new ObjectInputStream(sock.getInputStream());
                System.out.println("download nai");
                o1.downloadFile();
                sock.close();
                ostream.close();
                pwrite.close();
            } else {
                System.out.println("please type upload or download.");
            }
        
    }
    public static void main(String args[]) throws Exception {
        
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

    public void sendFile() {
        fileEvent = new FileEvent();
        String fileName = fname.substring(fname.lastIndexOf("/") + 1, fname.length());
        String path = fname.substring(0, fname.lastIndexOf("/") + 1);
        fileEvent.setDestinationDirectory(destinationPath);
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(clientfile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
