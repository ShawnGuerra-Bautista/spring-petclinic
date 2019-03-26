package org.springframework.samples.petclinic.db;

import java.util.Collection;

import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.vet.VetSqliteRepository;
import org.springframework.stereotype.Service;

@Service
public class VetConsistencyChecker implements ConsistencyChecker<Vet> {

	private final VetRepository oldVetRepository;
	private final VetSqliteRepository newVetRepository;

	public VetConsistencyChecker(VetRepository oldVetRepository, VetSqliteRepository newVetRepository) {
	    this.newVetRepository = newVetRepository;
		this.oldVetRepository = oldVetRepository;
	}

/*
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

	//method which takes the values from the other db and compares it with the new one
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
*/

    @Override
    public int checkConsistency() {
        return 0;
    }

    @Override
    public void violation() {

    }

    @Override
    public void fixViolation() {

    }

    @Override
    public Collection<Vet> getOldEntities() {
        return this.oldVetRepository.findAll();
    }

    @Override
    public Collection<Vet> getNewEntities(){
	    return null;
    }
}

