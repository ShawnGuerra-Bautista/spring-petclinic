package org.springframework.samples.petclinic.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class SQLiteDatabase {

	public static void createNewDatabase(String fileName)
	{
		String url = "jdbc:sqlite:" + fileName;
		
		try (Connection conn = DriverManager.getConnection(url))
		{
			if (conn != null)
			{
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("Driver Name: " + meta.getDriverName());
				
				System.out.println("SQLite database created");
			}
			
			Statement statement = conn.createStatement();
			
			Scanner scan = new Scanner(new File("src/main/resources/db/SQLite/schema.sqlite"));
			
			String sql = "";
			
			while(scan.hasNextLine())
			{
				String str = scan.nextLine();
				
				if (scan.hasNextLine())
					str += "\n";
				
				sql += str;
				
				if(str.endsWith(";") || str.endsWith(";\n"))
				{
					statement.execute(sql);
					sql = "";
				}
			}
			
			scan.close();
			
		}
		catch(SQLException e1)
		{
			System.err.println(e1.getMessage());
		}
		catch(IOException e2)
		{
			System.err.println(e2.getMessage());
		}
	}
	
	public static void AddToDatabase(String fileName, String toAdd){

		String url = "jdbc:sqlite:" + fileName;
		
		try (Connection conn = DriverManager.getConnection(url))
		{
			if (conn != null)
			{
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("Driver Name: " + meta.getDriverName());
				
				System.out.println("SQLite database created");
			}
			
			Statement statement = conn.createStatement();
			
			Scanner scan = new Scanner(new File("src/main/resources/db/SQLite/schema.sqlite"));
			
			String sql = "";
			
			//Shadow Write
			
			statement.executeUpdate("insert into owners values(11, 'George', 'Test', '110 W. Liberty St.', 'Madison', '6085551023')");
			
			ResultSet result = statement.executeQuery("select * from owners");
			
			while(result.next())
			{
				//Shadow Read
				System.out.println("last_name = " + result.getString("last_name"));
			}
			
			scan.close();
			
		}
		catch(SQLException e1)
		{
			System.err.println(e1.getMessage());
		}
		catch(IOException e2)
		{
			System.err.println(e2.getMessage());
		}
	}
	
	
	public static void main(String[] args)
	{
		createNewDatabase("sqlite.db");
	}
}
