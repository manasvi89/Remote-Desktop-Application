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
public class clientmsg{  
public static void main(String args[])throws Exception{  
Socket sock=new Socket("localhost",1234);  
DataInputStream istream=new DataInputStream(sock.getInputStream());  
DataOutputStream ostream=new DataOutputStream(sock.getOutputStream());  
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
  
String str="",str2="";  
 do{
System.out.print("you : ");   
str=br.readLine();  
ostream.writeUTF(str);  
ostream.flush();  
str2=istream.readUTF();  
System.out.println("Server : "+str2);  
} while(!str.equals("bye"));
  
ostream.close();  
sock.close();  
}}  