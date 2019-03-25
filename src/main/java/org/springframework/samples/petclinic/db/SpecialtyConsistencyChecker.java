package org.springframework.samples.petclinic.db;

import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.SpecialtyRepository;
import org.springframework.samples.petclinic.visit.Visit;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class SpecialtyConsistencyChecker {
    private SpecialtyRepository specialtyRepository;
    private ArrayList<Specialty> newSpecialties = new ArrayList<>();
    private Connection connnection =null;


    public SpecialtyConsistencyChecker(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public Collection<Specialty> getSpecialtyList(){
        return this.specialtyRepository.findAll();
    }

    public int checkConsistency() {
        int inconsistency = 0;
        Collection<Specialty> specialties = getSpecialtyList();
        String queryOfActual = "SELECT * FROM specialties";
        String url = "jdbc:sqlite:sqlite.db";
        Statement statement;
        ResultSet resultSet;

        try {
            connnection = DriverManager.getConnection(url);
            statement = connnection.createStatement();
            resultSet = statement.executeQuery(queryOfActual);
            while (resultSet.next()) {
                Specialty newSpecialty = new Specialty(resultSet.getInt("id"), resultSet.getString("name"));
                newSpecialties.add(newSpecialty);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        int index = 0;
        for(Specialty expectedSpecialty: specialties){
            Specialty actualSpecialty = newSpecialties.get(index);
            if(!expectedSpecialty.equals(actualSpecialty)){
                inconsistency+=1;
                violation(expectedSpecialty, actualSpecialty);
                fixViolation(actualSpecialty.getId(), actualSpecialty.getName());
            }
            index+=1;
        }
        return inconsistency;
    }

    public void violation(Specialty expectedSpecialty, Specialty actualSpecialty){
        System.out.println("Violation: " +
            "\n Expected: " + expectedSpecialty +
            "\n Actual: " + actualSpecialty);
    }

    public void fixViolation(int id, String name){
        String url = "jdbc:sqlite:sqlite.db";
        Statement statement;
        String query = "UPDATE specialties SET name = " + "'" + name + "'" +
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
