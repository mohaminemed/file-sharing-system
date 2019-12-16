package sp2p3;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.StringTokenizer;


public class FTPClient {
	DataOutputStream dout;
 public FTPClient() {

 }

 public synchronized void connect(String host, int port) throws IOException {
   if (socket != null) {
     throw new IOException("SimpleFTP is already connected. Disconnect first.");
   }
   socket = new Socket(host, port);
   DataInputStream din=new DataInputStream(socket.getInputStream());
   dout=new DataOutputStream(socket.getOutputStream());

   // Now logged in.
 }

 
 public synchronized void disconnect() throws IOException {
    
     socket = null;
   }
 
 public synchronized boolean retr( String username,String filename)
     throws IOException {


     // Socket dataSocket = new Socket(ip, port);
   dout.writeUTF(filename);
   InputStream input = socket.getInputStream();
   OutputStream output = new FileOutputStream("/home/med/Bureau/"+username+"/"+filename);
	   byte[] buffer = new byte[4096];
	   int  bytesRead = 0;
	   int counter=0;
	   UserInterface.setProgressValue(counter);
	   UserInterface.setProgressString(0+ " %");
	   while ((bytesRead = input.read(buffer)) >0) {
	     output.write(buffer, 0, bytesRead);
	     counter += bytesRead;
	     UserInterface.setProgressValue(counter);
	     int proc = (int) Math
		           .round(UserInterface.m_progress.getPercentComplete() * 100);
		       UserInterface.setProgressString(proc + " %");
	   }
	   output.flush();
	   output.close();
	   socket.close();
	   input.close();
	   if(counter>0)return true;
	   return false;
 }
 
 private Socket socket = null;

 private BufferedReader reader = null;

 private BufferedWriter writer = null;


 

}

