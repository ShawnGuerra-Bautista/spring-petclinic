package org.springframework.samples.petclinic.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.Vet;

import java.sql.ResultSet;

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
	
	public static void AddOwner(String fileName, Owner owner){
		int id = owner.getId();
        String first_name = owner.getFirstName();
        String last_name = owner.getLastName();
        String address = owner.getAddress();
        String city = owner.getCity();
        String telephone = owner.getTelephone();
		
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

            //Shadow Write

            statement.executeUpdate("INSERT INTO owners VALUES ('"+id+"','"+first_name+"','"+last_name+"','"+address+"','"+city+"','"+telephone+"')");
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
	
	public static void AddPets(String fileName, Pet pet){
		int id = pet.getId();
        String name = pet.getName();
        LocalDate birthDate = pet.getBirthDate();
        PetType type= pet.getType();
        Owner owner = pet.getOwner();

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

            //Shadow Write

            statement.executeUpdate("INSERT INTO pets VALUES ('"+id+"','"+name+"','"+birthDate+"','"+type+"','"+owner+"')");
            ResultSet result = statement.executeQuery("select * from pets");

            while(result.next())
            {
                //Shadow Read
                System.out.println("name = " + result.getString("name"));
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

public static void AddVets(String fileName, Vet vet){
		int id = vet.getId();
        String first_name = vet.getFirstName();
        String last_name = vet.getLastName();
		
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

            statement.executeUpdate("INSERT INTO vets VALUES ('"+id+"','"+first_name+"','"+last_name+"')");
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
public static void AddVisits(String fileName, Visit visit){
	int id = visit.getId();
	Integer pet_id = visit.getPetId();
    LocalDate visit_date = visit.getDate();
    String description = visit.getDescription();
    

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

        //Shadow Write

        statement.executeUpdate("INSERT INTO visits VALUES ('"+id+"','"+pet_id+"','"+visit_date+"','"+description+"')");
        ResultSet result = statement.executeQuery("select * from visits");

        while(result.next())
        {
            //Shadow Read
            System.out.println("name = " + result.getString("pet_id"));
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
