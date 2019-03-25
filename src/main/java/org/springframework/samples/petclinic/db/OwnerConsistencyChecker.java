package org.springframework.samples.petclinic.db;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class OwnerConsistencyChecker {

    private OwnerRepository ownerRepository;
    private ArrayList<Owner> newOwners = new ArrayList<>();
    private Connection connnection =null;


    public OwnerConsistencyChecker(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Collection<Owner> getOwnerList(){
        return this.ownerRepository.findAll();
    }

    public int checkConsistency() {
        int inconsistency = 0;
        Collection<Owner> owners = getOwnerList();
        String queryOfActual = "SELECT * FROM owners";
        String url = "jdbc:sqlite:sqlite.db";
        Statement statement;
        ResultSet resultSet;

        try {
            connnection = DriverManager.getConnection(url);
            statement = connnection.createStatement();
            resultSet = statement.executeQuery(queryOfActual);
            while (resultSet.next()) {
                Owner newOwner = new Owner(resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("address"),
                    resultSet.getString("city"),
                    resultSet.getString("telephone"),
                    null, null);
                newOwners.add(newOwner);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        int index = 0;
        for(Owner expectedOwner: owners){
            Owner actualOwner = newOwners.get(index);
            if(!expectedOwner.equals(actualOwner)) {
                inconsistency+=1;
                violation(expectedOwner, actualOwner);
                fixViolation(actualOwner.getId(), actualOwner.getFirstName(), actualOwner.getLastName(), actualOwner.getAddress(), actualOwner.getCity(), actualOwner.getTelephone());
            }
            index+=1;
        }
        return inconsistency;
    }

    private void violation(Owner expectedOwner, Owner actualOwner){
        System.out.println("Violation: " +
            "\n Expected: " + expectedOwner +
            "\n Actual: " + actualOwner);
    }

    private void fixViolation(int id, String firstName, String lastName, String address, String city, String telephone){
        String url = "jdbc:sqlite:sqlite.db";
        Statement statement;
        String query = "UPDATE owners SET first_name= " + "'" + firstName + "'" +
            " AND last_name = " + "'" + lastName + "'" +
            " AND address = " + "'" + address + "'" +
            " AND city = " + "'" + city + "'" +
            " AND telephone = " + "'" + telephone + "'" +
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

