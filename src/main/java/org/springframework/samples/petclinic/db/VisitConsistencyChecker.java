package org.springframework.samples.petclinic.db;

import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class VisitConsistencyChecker {

    private VisitRepository visitRepository;
    private ArrayList<Visit> newVisits;
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
            Visit actualVisit = newVisits.get(index);
            if(!expectedVisit.equals(actualVisit)){
                inconsistency+=1;
                violation(expectedVisit, actualVisit);
                fixViolation(actualVisit.getPetId(), Date.valueOf(actualVisit.getDate()), actualVisit.getDescription(), actualVisit.getPetId());
            }
            index+=1;
        }
        return inconsistency;
    }

    public void violation(Visit expectedVisit, Visit actualVisit){
        System.out.println("Violation: " +
            "\n Expected: " + expectedVisit +
            "\n Actual: " + actualVisit);
    }

    public void fixViolation(int id, Date visitDate, String description, int petId){
        String url = "jdbc:sqlite:sqlite.db";
        Statement statement;
        String query = "UPDATE vets SET visit_date = " + "'" + visitDate + "'" +
            " AND description = " + "'" + description + "'" +
            " AND pet_id = " + "'" + petId + "'" +
            " WHERE id = " + id;
        try {
            connnection = DriverManager.getConnection(url);
            statement = connnection.createStatement();
            statement.executeUpdate(query);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
