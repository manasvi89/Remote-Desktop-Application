/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shivani Kadam
 */
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class clientfile {

    public final static int SOCKET_PORT = 1234;
    public final static String SERVER = "127.0.0.1";
    // public final static String FILE_TO_RECEIVED = "test.txt";  

    public final static int FILE_SIZE = 10000;

    public static void main(String[] args) throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            sock = new Socket(SERVER, SOCKET_PORT);

            while (true) {

                System.out.println("Connecting...");

                // receive file
                for (int i = 0; i < 3; i++) {

                    File file = new File("file" + i);
                    file.createNewFile();
                    byte[] mybytearray = new byte[FILE_SIZE];
                    InputStream ip = sock.getInputStream();
                    fos = new FileOutputStream(file);
                    bos = new BufferedOutputStream(fos);
                    bytesRead = ip.read(mybytearray, 0, mybytearray.length);
                    current = bytesRead;

                    bos.write(mybytearray, 0, current);
                    bos.flush();
                    System.out.println("File " + file + " downloaded " + current + " bytes read");
                    if (fos != null) {
                        fos.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                    
                }
            }
        } finally {
            if (sock != null) {
                sock.close();
            }
        }
    }
}
