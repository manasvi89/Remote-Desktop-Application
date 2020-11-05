/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shivani Kadam
 */
// Chat Server runs at port no. 9999
import java.net.*;  
import java.io.*;  
public class servermsg{  
public static void main(String args[])throws Exception{  
ServerSocket ss=new ServerSocket(1234);  
System.out.println("Server ready ");
Socket sock=ss.accept(); 
System.out.println("Connection is successful ");

DataInputStream istream=new DataInputStream(sock.getInputStream());  
DataOutputStream ostream=new DataOutputStream(sock.getOutputStream());  
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
  
String str="",str2="";  
do{  
str=istream.readUTF();  
System.out.println("client : "+str); 
System.out.print("me : ");
str2=br.readLine();  
ostream.writeUTF(str2);  
ostream.flush();  
} while(!str.equals("bye")); 
istream.close();  
sock.close();  
ss.close();  
}}  