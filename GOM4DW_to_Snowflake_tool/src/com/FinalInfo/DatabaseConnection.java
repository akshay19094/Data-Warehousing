package com.FinalInfo;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import javax.swing.JOptionPane;
public class DatabaseConnection {
	// JDBC driver name and database URL
	//static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
     //   static final String JDBC_DRIVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	static final String DB_URL = "jdbc:sqlserver://localhost;database=master;integratedSecurity=true;";
        

	//  Database credentials
//	static final String USER = "root";
//	static final String PASS = "root";
	private int p_id=-1;

	Connection conn = null;
	Statement stmt=null;
        
        Driver d;
	public Connection  getConnection(String database_name)
	{
		try
		{
                    d = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
                    String db_url="jdbc:sqlserver://localhost;database="+database_name+";integratedSecurity=true;";
			System.out.println(db_url);
			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
                        conn = d.connect(db_url, new Properties()); 
			//conn = DriverManager.getConnection(db_url);
			System.out.println("connected");
			
                  
                    return conn;

		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
			return null;
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
			return null;
		}finally{


		}
		
	}

	public String newConnection()
	{
		ArrayList<Integer> databaseList = new ArrayList<Integer>();
		String sql;
		ResultSet  resultset=null;
                
		try{                       
			d= (Driver)Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();                        
			conn = DriverManager.getConnection(DB_URL);
                         System.out.println(conn);
                         
			stmt = conn.createStatement();
			resultset = stmt.executeQuery("select name from sys.databases where name not in ('master', 'tempdb', 'model', 'msdb')");

			if (stmt.execute("select name from sys.databases" )) {
				resultset = stmt.getResultSet();
                                
			}
			if(resultset!=null)
			{
				while (resultset.next()) {
					String result=resultset.getString(1);
                                        System.out.println(result);
					try
					{
						if(result.startsWith("P"))
						{
							System.out.println(result);
							Integer.parseInt(result.substring(1));
							databaseList.add(Integer.parseInt(result.substring(1)));

						}

					}
					catch(Exception e)
					{
						continue;
					}

				}



			}


			System.out.println("Creating database...");
			stmt = conn.createStatement();

			if(databaseList.size()==0)
			{
				sql = "CREATE DATABASE P"+ 1;
				p_id=1;
			}
			else
			{
				Collections.sort(databaseList);
				sql = "CREATE DATABASE P"+ (databaseList.get(databaseList.size()-1)+1);
				p_id=databaseList.get(databaseList.size()-1)+1;
			}

			stmt.executeUpdate(sql);



			System.out.println("Database created successfully...");

			///////////////////////////////////////
			if(!createTables("p"+p_id)) {
				System.out.println("Error in creating Tables");
				return "error";
			}
			




		}catch(SQLException se){
			//Handle errors for JDBC
			JOptionPane.showMessageDialog(null, "Error in Creating Project");
			p_id=-1;
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try

		System.out.println("Goodbye!");
		return "p"+p_id;

	}

	public boolean createTables(String db_name)
	{
		boolean success=true;
		System.out.println("CREATING TABLES");
		Connection con=getConnection(db_name);
		String sql = null;
		try {
			stmt = con.createStatement();

						sql="USE "+db_name;
						stmt.executeUpdate(sql);

			sql ="CREATE TABLE p_info"+
					"(p_info VARCHAR(100) NOT NULL UNIQUE,"+
					" history_freq VARCHAR(45) NULL,"+
					" history_durationNum INT NULL,"+
					" history_duration VARCHAR(45) NULL,"+
					" PRIMARY KEY (p_info))";

			System.out.println(sql);
			stmt.executeUpdate(sql);


			sql="CREATE TABLE category_subcategory"+
					"(p_info VARCHAR(100) NOT NULL ,"+
					" category_name VARCHAR(100) NOT NULL ,"+
					" subcategory_of VARCHAR(100),"+
					" category_id VARCHAR(100) NOT NULL UNIQUE,"+
					" subcategory_of_id VARCHAR(100),"+
					" PRIMARY KEY (category_id))";
					//"FOREIGN KEY (subcategory_of) REFERENCES category_subcategory (category_name))";

			System.out.println(sql);
			stmt.executeUpdate(sql);
			
			/*sql="ALTER TABLE category_subcategory"+
				" ADD INDEX subcategory_of_idx(subcategory_of_id)";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
                        
			sql="ALTER TABLE category_subcategory"+
				" ADD CONSTRAINT subcategory_of_fk"+
			  " FOREIGN KEY (subcategory_of_id) REFERENCES category_subcategory (category_id)"+
			  " ON DELETE CASCADE"+
			  " ON UPDATE CASCADE";
			System.out.println(sql);
			stmt.executeUpdate(sql);*/

			sql="CREATE TABLE infoAttribute"+
					"(p_info VARCHAR(100) NOT NULL ,"+
					" attribute VARCHAR(100) NOT NULL ,"+
					" dataType VARCHAR(100) ,"+
					" PRIMARY KEY(p_info,attribute))";

			System.out.println(sql);
			stmt.executeUpdate(sql);

			sql="CREATE TABLE category_attributes"+
				"(p_info VARCHAR(100) NOT NULL ,"+
				" category_name VARCHAR(100) NOT NULL,"+
				" attribute_name VARCHAR(100),"+
				" dataType VARCHAR(100),"+
				"category_id VARCHAR(100) NOT NULL,"+
				" PRIMARY KEY (category_id ,attribute_name ))";
			//	" FOREIGN KEY (categegory_id) REFERENCES category_subcategory (categegory_id))";

			System.out.println(sql);
			stmt.executeUpdate(sql);
			
			sql="ALTER TABLE category_attributes"+
					" ADD CONSTRAINT cat_attr_fk"+
				  " FOREIGN KEY (category_id) REFERENCES category_subcategory (category_id)"+
				  " ON DELETE CASCADE"+
				  " ON UPDATE CASCADE";
				System.out.println(sql);
				stmt.executeUpdate(sql);

			sql="CREATE TABLE aggregate"+
					" (p_info VARCHAR(100) NOT NULL, "+
					" aggregate_id INT NOT NULL,"+
					" aggregate_name VARCHAR(100),"+
					" history_freq VARCHAR(45) NULL,"+
					" history_durationNum INT NULL,"+
					" history_duration VARCHAR(45) NULL,"+
					" PRIMARY KEY(p_info ,aggregate_id))";

			System.out.println(sql);
			stmt.executeUpdate(sql);

			sql= "CREATE TABLE aggregate_computedFrom"+
					" (id  INT IDENTITY(1,1) NOT NULL UNIQUE,"+
					" p_info VARCHAR(100) NOT NULL, "+
					" aggregate_id INT NOT NULL,"+
					" attribute_name VARCHAR(100),"+
					" PRIMARY KEY (id))";
					//" FOREIGN KEY (p_info,aggregate_id) REFERENCES aggregate (p_info,aggregate_id),"+
					//" FOREIGN KEY (p_info,attribute_name) REFERENCES infoAttribute (p_info,attribute))";

			System.out.println(sql);
			stmt.executeUpdate(sql);

			sql= "CREATE TABLE dbo.dataObject_subDataObject"+
					" (p_info VARCHAR(100) NOT NULL,"+
					" sub_p_info VARCHAR(100) NOT NULL, "+
					" PRIMARY KEY (p_info))";

			System.out.println(sql);
			stmt.executeUpdate(sql);

			sql= "CREATE TABLE dbo.category_changeType"+
					"(p_info VARCHAR(100) NOT NULL ,"+
					" category_name VARCHAR(100) NOT NULL,"+
					" change_type VARCHAR(100) NOT NULL DEFAULT 'no_change', "+
					" category_id VARCHAR(100) NOT NULL UNIQUE,"+
					" time_stamp DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"+
					" PRIMARY KEY (category_id))";


			System.out.println(sql);
			stmt.executeUpdate(sql);
			
			
			sql="ALTER TABLE aggregate_computedFrom"+
					" ADD CONSTRAINT aggr_computedfrom_fk"+
				  " FOREIGN KEY (p_info,aggregate_id) REFERENCES aggregate (p_info,aggregate_id)"+
				  " ON DELETE CASCADE"+
				  " ON UPDATE CASCADE";
				System.out.println(sql);
				stmt.executeUpdate(sql);
				
			sql="ALTER TABLE aggregate_computedFrom"+
						" ADD CONSTRAINT aggr_infoAttr_fk"+
					  " FOREIGN KEY (p_info,attribute_name) REFERENCES infoAttribute (p_info,attribute)"+
					  " ON DELETE CASCADE"+
					  " ON UPDATE CASCADE";
					System.out.println(sql);
					stmt.executeUpdate(sql);	

			sql="CREATE TABLE aggregate_over_category"+
					" (id  INT IDENTITY(1,1) NOT NULL UNIQUE,"+
					" p_info VARCHAR(100) NOT NULL, "+
					" aggregate_id INT NOT NULL,"+
					" category VARCHAR(100) ,"+
					" category_id VARCHAR(100),"+
					" PRIMARY KEY (id))";
					//" FOREIGN KEY (p_info,aggregate_id) REFERENCES aggregate (p_info,aggregate_id),"+
			        //" FOREIGN KEY (p_info,category) REFERENCES category_subcategory (p_info,category_name))";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			
			sql="ALTER TABLE aggregate_over_category"+
					" ADD CONSTRAINT aggr_fk"+
				  " FOREIGN KEY (p_info,aggregate_id) REFERENCES aggregate (p_info,aggregate_id)"+
				  " ON DELETE CASCADE"+
				  " ON UPDATE CASCADE";
				System.out.println(sql);
				stmt.executeUpdate(sql);	
				
		    sql="ALTER TABLE aggregate_over_category"+
						" ADD CONSTRAINT aggr_cat_fk"+
					  " FOREIGN KEY (category_id) REFERENCES category_subcategory (category_id)"+
					  " ON DELETE CASCADE"+
					  " ON UPDATE CASCADE";
					System.out.println(sql);
					stmt.executeUpdate(sql);	

		}
		catch(Exception e)
		{
			e.printStackTrace();
			success=false;
		}
		
		return success;
	}
	
	
	
}