
package cn_lab_project_rdp.server;

import cn_lab_project_rdp.server.FileEvent;
import java.net.*;
import java.io.*;

public class serverfile {

	/* Define socket and parameters */
    private Socket socket = null;
    private File dstFile = null;
    private static ObjectOutputStream outputStream = null;
    private static ObjectInputStream inputStream = null;
    private FileEvent fileEvent = null;
    private static String fname = null;
    private FileOutputStream fileOutputStream = null;
    private String destinationPath1 = "D:/Download/";

    public void fileServer() throws IOException {
        ServerSocket sersock = new ServerSocket(1234);
        System.out.println("Server ready ");
        Socket sock = sersock.accept();
        System.out.println("Connection is successful ");

        serverfile o1 = new serverfile();
	
	/* Define input stream and read line from client */
        InputStream istream = sock.getInputStream();
        BufferedReader fileRead = new BufferedReader(new InputStreamReader(istream));
        fname = fileRead.readLine();

	/* fname is filename given by client */
        if (fname.contains("download")) {

            System.out.println(removeWord(fname, "download"));

		/* remove word " download " from string */
            fname = removeWord(fname, "download");
            outputStream = new ObjectOutputStream(sock.getOutputStream());
            
		/* call function send file */
            o1.sendFile();
            istream.close();
            fileRead.close();
        } else {
            
		/* not download then its upload file option */
            inputStream = new ObjectInputStream(sock.getInputStream());
            
            /* client upload file so download in server*/
            o1.downloadFile();
        }

        sock.close();
        sersock.close();

    }

    public static void main(String args[]) throws Exception {                           // establishing the connection with the server

    }

	/* Function for remove word from string */
    public static String removeWord(String string, String word) {
        if (string.contains(word)) {
            String tempWord = word + " ";
            string = string.replaceAll(tempWord, "");
            tempWord = " " + word;
            string = string.replaceAll(tempWord, "");
        }
        return string;
    }

    public void sendFile() throws IOException {

		/* file event - management */
        fileEvent = new FileEvent();
        String fileName = fname.substring(fname.lastIndexOf("/") + 1, fname.length());
        String path = fname.substring(0, fname.lastIndexOf("/") + 1);
        fileEvent.setDestinationDirectory(destinationPath1);
        fileEvent.setFilename(fileName);
        fileEvent.setSourceDirectory(fname);
        File file = new File(fname);
        if (file.isFile()) {
            try {
			/* Read input name and data of file from client */
                DataInputStream diStream = new DataInputStream(new FileInputStream(file));
                long len = (int) file.length();
                byte[] fileBytes = new byte[(int) len];
                int read = 0;
                int numRead = 0;
                while (read < fileBytes.length && (numRead = diStream.read(fileBytes, read, fileBytes.length - read)) >= 0) {
                    read = read + numRead;
                }

		/* send file with all its attributes */
                fileEvent.setFileSize(len);
                fileEvent.setFileData(fileBytes);
                fileEvent.setStatus("Success");
            } catch (Exception e) {
                e.printStackTrace();
                fileEvent.setStatus("Error");
            }
        } else {

		/* not proper path error  */
            System.out.println("path specified is not pointing to a file");
            fileEvent.setStatus("Error");
        }

        try {
            outputStream.writeObject(fileEvent);
            System.out.println("Done...Going to exit");
            Thread.sleep(3000);
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

	/* download file function */
    public void downloadFile() {
        try {

		/* error if path is not found or file event is not handled */
            fileEvent = (FileEvent) inputStream.readObject();
            if (fileEvent.getStatus().equalsIgnoreCase("Error")) {
                System.out.println("Error occurred ..So exiting");
                System.exit(0);
            }

		/* successfully downloaded */
            String outputFile = fileEvent.getDestinationDirectory() + fileEvent.getFilename();
            if (!new File(fileEvent.getDestinationDirectory()).exists()) {
                new File(fileEvent.getDestinationDirectory()).mkdirs();
            }

		/* Destination of output file */
            dstFile = new File(outputFile);
            fileOutputStream = new FileOutputStream(dstFile);
            fileOutputStream.write(fileEvent.getFileData());
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("Output file : " + outputFile + " is successfully saved ");
            Thread.sleep(3000);
            System.exit(0);

		/* catch error */
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
