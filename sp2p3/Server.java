package sp2p3;

import java.net.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.io.*;


public class Server 
{  static ServerInterface sI;
	public static void main(String[] args)throws Exception
	{
		int count=1;
		sI= new ServerInterface("Server-3");
		SqlConnection connect = new SqlConnection();
		ServerSocket ss=new ServerSocket(7575);
		System.out.println("Server 2 is running...................");
		Thread.sleep(10000);
		Socket socket[]= new Socket[2];
		socket[0]=new Socket("127.0.0.1", 7878);
		socket[1]=new Socket("127.0.0.1", 7070);
		Socket accSer[]=new Socket[2];
		for (int i = 0; i < accSer.length; i++) {
			accSer[i]=ss.accept();
			sI.getText().append("Server-"+(i+1)+":"+accSer[i].getInetAddress()+"|"+accSer[i].getLocalPort()+"|"+accSer[i].getPort()+"\n");
			System.out.println("Server "+(i+1)+" Connected "+accSer[i]);
			new ServerThread(connect,accSer[i]).start();
		}
		DataInputStream din;
		while(true)
		{   
			Socket socket2=ss.accept();
			din=new DataInputStream(socket2.getInputStream());
			if(din.readUTF().equals("1")) {
			new ServiceThread(connect,socket2,count,socket).start();
			count++;
			}
			
		}
	}
}

class ServiceThread extends Thread
{
	Socket s=null,ser[]=new Socket[2];
	int n;
	SqlConnection connect;
	public ServiceThread(SqlConnection connect,Socket socket,int count,Socket serSock[])
	{
		s=socket;
		n=count;
		ser=serSock;
		this.connect=connect;
		System.out.println(n+" client connected: "+s);
	}
	public void run()
	{
		try
		{
			while(true)
			{
				DataInputStream din=new DataInputStream(s.getInputStream());
				DataOutputStream dout=new DataOutputStream(s.getOutputStream());
				String uN=din.readUTF();
				System.out.println("Email:"+n+"-->"+uN);
				String pW=din.readUTF();
				System.out.println("Passwd:"+n+"-->"+pW);
				String port=din.readUTF();
				System.out.println("Port:"+n+"-->"+port);
				try {
			      PreparedStatement pst = connect.getConn().prepareStatement("SELECT * FROM client where username = ? and passwd= ? ");
			      pst.setString(1, uN);
			      pst.setString(2, pW);
			      ResultSet rs = pst.executeQuery();
			      while (rs.next())
			      {  
			    	 String adress=s.getInetAddress().toString();
			    	 int index=adress.indexOf("/");
			    	 adress=adress.substring(index+1);
			    	 System.out.println(adress);
			         System.out.print("Client : ' ");
			         System.out.println(rs.getString(1)+"' Logged On !");
					  PreparedStatement pstu = connect.getConn().prepareStatement("update client set adress=? ,port=? where username= ?");
				      pstu.setString(1,adress );
				      pstu.setString(2, port);
				      pstu.setString(3, uN);
				     int rowsAdded = pstu.executeUpdate();
				     System.out.println(rowsAdded+ " Resource Updated!"); pstu.close();
				     Server.sI.getTextArea_1().append("Client-"+n+":"+uN+"|"+adress+"|"+port+"\n");
			         dout.writeUTF("Logged On!");
			      } 
			      rs.close();
			      pst.close();
			      PreparedStatement stm= connect.getConn().prepareStatement("DELETE FROM resource where clientid=?");
			      stm.setString(1,uN);
			      int rstm = stm.executeUpdate();
			      String fileName=null;
			      int coun=Integer.parseInt(din.readUTF());
			      while(coun>0){
			    	  fileName=din.readUTF();
				      PreparedStatement psti = connect.getConn().prepareStatement("insert into resource values (?,?)");
				      Server.sI.getTextArea_2().append("Client-"+n+":"+uN+"|"+fileName+"\n");
				      psti.setString(1, uN);
				      psti.setString(2, fileName);
				     int rowsAdded = psti.executeUpdate();
				     System.out.println(fileName +": "+rowsAdded+ " Resource Added!"); psti.close();
				     coun--;
			      }
			      String searchName=din.readUTF();
			      System.out.println("Search->"+searchName); 
			      PreparedStatement pstr = connect.getConn().prepareStatement("SELECT client.* FROM resource,client where client.username = resource.clientid and resname = ? ");
			      pstr.setString(1, searchName);
			      ResultSet rsr = pstr.executeQuery();
			      int size=rsr.getFetchSize();
			      System.out.println("SIZE="+size);
			      int ex=0;
			      while (rsr.next())
			      {
			         System.out.println("Resource '"+searchName+"' Exist On ["+rsr.getString(1)+","+rsr.getString(3)+","+rsr.getString(4)+"]");
			         dout.writeUTF(rsr.getString(1));
			         dout.writeUTF(rsr.getString(3));
			         dout.writeUTF(rsr.getString(4));
			         ex=1;
			      }
			      if(ex==0) {
			    	  for (int i = 0; i < ser.length; i++) {
			    	  DataInputStream dins=new DataInputStream(ser[i].getInputStream());
					  DataOutputStream douts=new DataOutputStream(ser[i].getOutputStream()); 
					  douts.writeUTF(searchName);
						 String str,rev;
						 while(!(str=dins.readUTF()).equals("FIN")){
							 System.out.print(str+"|");
							 dout.writeUTF(str);
							 rev=dins.readUTF();
							 dout.writeUTF(rev);
							 System.out.print(rev+"|");
							 rev=dins.readUTF();
							 dout.writeUTF(rev);
							 System.out.println(rev);
						 }
			    	  
			      }}
			          dout.writeUTF("FIN");
			          rsr.close();
			          pstr.close();
			      
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DataInputStream in=new DataInputStream(System.in);
				String strr=in.readLine();
				 if (strr.equalsIgnoreCase("bye"))//si le msg= bye la conx s'arret
                 {
					 dout.writeUTF("BYE");
                     System.out.println("Connection ended by server");
                     break;
                 }
				
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
}
class ServerThread extends Thread {
	Socket s=null;
	SqlConnection connect;
	public ServerThread(SqlConnection connect,Socket s) {
		this.s=s;
		this.connect=connect;
	}
	public void run() {
		try
		{
			while(true)
			{
				  DataInputStream din=new DataInputStream(s.getInputStream());
				  DataOutputStream dout=new DataOutputStream(s.getOutputStream());
				  String searchName=din.readUTF();
			      System.out.println("Search->"+searchName); 
			      PreparedStatement pstr = connect.getConn().prepareStatement("SELECT client.* FROM resource,client where client.username = resource.clientid and resname = ? ");
			      pstr.setString(1, searchName);
			      ResultSet rsr = pstr.executeQuery();
			      int size=rsr.getFetchSize();
			      System.out.println("SIZE="+size);
			      int ex=0;
			      while (rsr.next())
			      {
			         System.out.println("Resource '"+searchName+"' Exist On ["+rsr.getString(1)+","+rsr.getString(3)+","+rsr.getString(4)+"]");
			         dout.writeUTF(rsr.getString(1));
			         dout.writeUTF(rsr.getString(3));
			         dout.writeUTF(rsr.getString(4));
			         ex=1;
			      }
			      dout.writeUTF("FIN");
			}
		}catch(Exception e) {}
			
	}
}

