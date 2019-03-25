package org.springframework.samples.petclinic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collection;

import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;


public class PetConsistencyChecker {

	private final PetRepository petRepo;
	Connection connector =null;
	
	public PetConsistencyChecker() {
		petRepo=null;
	}
	
	public PetConsistencyChecker(PetRepository petRepo) {
		this.petRepo=petRepo;
	}
	
	public Collection<Pet> getVetList(){
		return this.petRepo.findAll();
	}
	
	public void compare(Collection<Pet> pets) {
		String fileName= "sqlite.db";  
		String url = "jdbc:sqlite:" + fileName;
		Statement statement;
		ResultSet resultSet;
		String birthDateOldDb ="";
		for (Pet p : pets) { 
			String query = "SELECT birth_date FROM pets WHERE name= " + "'" + p.getName() + "'" + "AND" + "id= " + p.getId();
			try {
				connector = DriverManager.getConnection(url); 
				statement = connector.createStatement();
				resultSet = statement.executeQuery(query);
				while(resultSet.next()) {
					birthDateOldDb = resultSet.getString("birth_date");
				}
				if(birthDateOldDb.compareTo(p.getBirthDate().toString()) != 0) {
				  
				  inconsistentData(p.getName(), p.getBirthDate(), p.getId());
			    }
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void inconsistentData(String Name, LocalDate birthDate , int Id) {
		
		 String fileName= "sqlite.db"; 
		 String url = "jdbc:sqlite:" + fileName;
		 Statement statement;
		String query = "UPDATE pets SET birth_date = " + "'" + birthDate.toString() + "'" + " WHERE name = " + "" + Name + "'" + " AND id = " + Id;
		try {
			connector = DriverManager.getConnection(url); 
			statement = connector.createStatement();
			statement.executeUpdate(query);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
