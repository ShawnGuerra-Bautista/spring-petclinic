package org.springframework.samples.petclinic.db;

import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collection;

public class VisitConsistencyChecker {

    private VisitRepository visitRepository;
    private Collection<Visit> newVisits;
    private Connection connnection =null;


    public VisitConsistencyChecker(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    public Collection<Visit> getVetList(){
        return this.visitRepository.findAll();
    }

    public int checkConsistency() {
        int inconsistency = 0;
        Collection<Visit> visits = getVetList();
        String queryOfActual = "SELECT * FROM vets";
        String url = "jdbc:sqlite:sqlite.db";
        Statement statement;
        ResultSet resultSet;

        try {
            connnection = DriverManager.getConnection(url);
            statement = connnection.createStatement();
            resultSet = statement.executeQuery(queryOfActual);
            while (resultSet.next()) {
                Visit newVisit = new Visit(resultSet.getInt("id"), resultSet.getDate("visit_date").toLocalDate(),
                    resultSet.getString("description"), resultSet.getInt("pet_id"));
                newVisits.add(newVisit);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        int index = 0;
        for(Visit expectedVisit: visits){

        }


        return inconsistency;
    }

    //takes the arraylist pass from the connectDatabase
    //method which takes the values from the other db and comparesit with the new one
    public void compare(Collection<Vet> vets) {


        //ArrayList <String> lastName=new ArrayList<>();
        for (Vet v : vets) { // data from the first DB (old db)



        }
    }
}
