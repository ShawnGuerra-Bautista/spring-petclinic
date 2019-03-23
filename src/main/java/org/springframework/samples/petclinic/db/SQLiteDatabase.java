package org.springframework.samples.petclinic.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

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
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	public static void main(String[] args)
	{
		createNewDatabase("sqlite.db");
	}
}
