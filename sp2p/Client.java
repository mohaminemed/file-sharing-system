package sp2p;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Vector;

public class Client extends Thread{
	private Socket s=null ;
	private Vector<String> listefile;
	private Vector<String> remotefile;
	private String username;
	private String passwd;
	public static  Object[][] data=new Object[100][100];
	static DataInputStream din;
	static DataOutputStream dout;
	static UserInterface uu;
	static int lisPort;
	 
	public Client(String user, String pass) {
		super();
		listefile= new Vector<>();
		remotefile= new Vector<>();
		username=user;
		passwd=pass;
		try {
			//this.s  = new Socket("192.168.0.172",7878);
			this.s  = new Socket("127.0.0.1",7878);
			din=new DataInputStream(s.getInputStream());
			dout=new DataOutputStream(s.getOutputStream());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
public void run () {
	
	try {
	
	if(s.isConnected())
	{
		
	}
	while(true)
	{   do {
		Random rr= new Random();
		lisPort=rr.nextInt(6000);}
	    while(lisPort<5000);
	    dout.writeUTF("1");
		dout.writeUTF(username);
		dout.writeUTF(passwd);
		dout.writeUTF(""+lisPort);
		String rev=din.readUTF();

		 if (rev.equalsIgnoreCase("BYE"))//si le msg= bye la conx s'arret
        {
			 dout.writeUTF("BYE");
             System.out.println("Connection ended by server");
             break;
        }
		 else if(rev.equalsIgnoreCase("Logged On!")){
	         uu= new UserInterface(username);
			 System.out.println(rev);
			 File repertoire = new File("/home/med/Bureau/"+username);
		     listeRepertoire(repertoire);
		     dout.writeUTF(""+listefile.size());
		     for(int i=0;i<listefile.size();i++)
		 	 dout.writeUTF(listefile.get(i));	   
		 }
		 break;
		
	}
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("Connected to Server....");
	int count=1;
	ServerSocket ss;
	try {
		ss = new ServerSocket(lisPort);
	
	System.out.println("Client is running...................");
	while(true)
	{
		new uploadThread(username,ss.accept(),count).start();
		count++;
	}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void listeRepertoire ( File repertoire ) throws IOException {
    System.out.println ( repertoire.getName());
   
    if ( !repertoire.isDirectory ( ) )
	//dout.writeUTF(repertoire.getName());
    listefile.add(repertoire.getName());
    else
     {
            File[] list = repertoire.listFiles();
            if (list != null){
                for ( int i = 0; i < list.length; i++) {
                        // Appel r�cursif sur les sous-r�pertoires
                        listeRepertoire( list[i]);
                } 
            } else 
            	System.err.println(repertoire + " : Erreur de lecture.");
            
    } 
}
 public static void  searchFile (String filename) throws IOException{
	 dout.writeUTF(uu.getSearchFile());
	 int i=0;
	 String str,rev;
	 while(!(str=din.readUTF()).equals("FIN")){
	 data[i][0]=str;
	 System.out.print(data[i][0]+"|");
	 rev=din.readUTF();
	 data[i][1]=rev;
	 System.out.print(data[i][1]+"|");
	 rev=din.readUTF();
	 data[i][2]=rev;
	 System.out.println(data[i][2]);
	 uu.setData(data);
	 i++;
	 }
	 
 }
	
}

class uploadThread extends Thread
{   
	String user;
	Socket s=null;
	int n;
	
	
	public uploadThread(String user,Socket socket,int count)
	{
		s=socket;
		n=count;
		this.user=user;
		System.out.println(n+" client connected: "+s);
	}
	public void run()
	{
		try
		{
			DataInputStream din=new DataInputStream(s.getInputStream());
			String filename=din.readUTF();
			File file = new File("/home/med/Bureau/"+user+"/"+filename);
			System.out.println(file.getPath());
			stor(new FileInputStream(file), filename);
		}
		catch(Exception e){}
	
	
	}
public synchronized void stor(InputStream inputStream, String filename)
		     throws IOException {

		    
		     OutputStream output = s.getOutputStream();  
			   byte[] buffer = new byte[4096];
			   int  bytesRead = 0;
			   while ((bytesRead = inputStream.read(buffer)) >0) {
			     output.write(buffer, 0, bytesRead);	
	
	          }
			   output.close();
			   inputStream.close();
			   s.close();
}

}
    	
   
	

	
