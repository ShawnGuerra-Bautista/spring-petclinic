package org.springframework.samples.petclinic.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

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
			
			while(scan.hasNextLine())
			{
				statement.executeQuery(scan.nextLine());
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
