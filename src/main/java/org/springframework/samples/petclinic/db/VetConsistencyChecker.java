package org.springframework.samples.petclinic.db;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.stereotype.Service;

@Service
public class VetConsistencyChecker {
	
	private final VetRepository vetrepo;
	
	Connection conn =null;
	
	public VetConsistencyChecker() {
		vetrepo=null;
	}
	/*
	public void addData() {
		 String fileName= "sqlite.db"; //url to the sqlite db
		 String url = "jdbc:sqlite:" + fileName;
		 Statement statement;
		 String query="INSERT INTO vets (id, first_name, last_name) VALUES ( 1, 'Bob','Builder')";
		 try {
			 conn = DriverManager.getConnection(url); //connecting to the second db
				statement = conn.createStatement();
				statement.executeUpdate(query);
				System.out.println(query);
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
	}
	*/
	
	public VetConsistencyChecker(VetRepository vetrepo) {
		this.vetrepo=vetrepo;
	}
	
	public Collection<Vet> getVetList(){
		return this.vetrepo.findAll();
	}
	
	/*
	//gets all the values from the old database and stores it onto the arraylist
	public ArrayList<Vet> getDataFromOldDb() {
		String fileName="";
		String url = "jdbc:sqlite:" + fileName;
		Statement statement;
		ResultSet resultSet;
		String query = "SELECT * FROM vets";  //query through the vets database
		Vet vets;
		ArrayList<Vet> vet = new ArrayList<>();
		try {
			conn = DriverManager.getConnection(url);
			statement = conn.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				vets = new Vet(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"));
				vet.add(vets);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vet;
	}
	*/
	
	//method which replace the value for each each inconsistent value
	 public void inconsistentData(String LastName, String firstName, int Id) {
		//update the value of the new db data to old db data
		 String fileName= "sqlite.db"; //url to the sqlite db
		 String url = "jdbc:sqlite:" + fileName;
		 Statement statement;
		String query = "UPDATE vets SET last_name = " + "'" + LastName + "'" + " WHERE first_name = " + "" + firstName + "'" + " AND id = " + Id;
		try {
			conn = DriverManager.getConnection(url); //connecting to the second db
			statement = conn.createStatement();
			statement.executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//takes the arraylist pass from the connectDatabase
	//method which takes the values from the other db and comparesit with the new one
	public void compare(Collection<Vet> vets) {
		String fileName= "sqlite.db";  //url to the sqlite db
		String url = "jdbc:sqlite:" + fileName;
		Statement statement;
		ResultSet resultSet;
		String lastNameDB2 ="";
		//ArrayList <String> lastName=new ArrayList<>();
		for (Vet v : vets) { // data from the first DB (old db)
			String query = "SELECT last_name FROM vets WHERE first_name= " + "'" + v.getFirstName() + "'" + "AND" + "id= " + v.getId() ;
			try {
				conn = DriverManager.getConnection(url); //connecting to the second db
				statement = conn.createStatement();
				resultSet = statement.executeQuery(query);
				while(resultSet.next()) {
					//goes through the list and checks if the condition is met
				   lastNameDB2 = resultSet.getString("last_name");
					//lastName.add(lastNameDB2);
				}
				if(lastNameDB2.compareTo(v.getLastName()) != 0) {
				  //inconsistent method
				  inconsistentData(v.getLastName(), v.getFirstName(), v.getId());
			    }
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	

}

