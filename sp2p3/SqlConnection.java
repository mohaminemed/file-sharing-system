package sp2p3;

import java.sql.*;


public class SqlConnection {

	//CTRL + SHIFT + O pour g�n�rer les imports
	private  Connection conn;
	        
	   public SqlConnection() throws ClassNotFoundException {
		super();
		  //Class.forName("com.mysql.jdbc.Driver");
		  System.out.println("Driver O.K.");
	      String url = "jdbc:mysql://localhost:3306/myDB3";
	      String user = "debian-sys-maint";
	      String passwd = "5Ob2gLvCidVKgpVe";

	      try {
			this.conn = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      System.out.println("Connexion effective !"); 
	          	
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}
}

