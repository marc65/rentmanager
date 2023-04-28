package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
    public VehicleDao() {
    }

    private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places, model) VALUES(?, ?, ?);";
    private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
    private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places, model FROM Vehicle WHERE id=?;";
    private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places, model FROM Vehicle;";
    private static final String EDIT_VEHICLES_QUERY = "UPDATE Vehicle SET constructeur = ?, nb_places = ?, model = ? WHERE id = ?;";


    public long create(Vehicle vehicle) throws DaoException {
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY);

            preparedStatement.setString(1, vehicle.getConstructor());
            preparedStatement.setString(2, String.valueOf(vehicle.getNb_places()));
            preparedStatement.setString(3, vehicle.getModel());

            long newID = preparedStatement.executeUpdate();

            connection.close();

            return	newID;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
    }

    public long delete(Vehicle vehicle) throws DaoException {
        try(Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY);//, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, (int) vehicle.getId());
            preparedStatement.executeUpdate();
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Vehicle findById(long id) throws DaoException {
        Vehicle vehicule = new Vehicle();

        try {
            Connection connection = ConnectionManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLE_QUERY);
            preparedStatement.setInt(1, (int) id);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                vehicule.setId((int) id);
                vehicule.setConstructor(rs.getString("constructeur"));
                vehicule.setNb_places(rs.getInt("nb_places"));
                vehicule.setModel(rs.getString("model"));

            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
        return vehicule;
    }


    public List<Vehicle> findAll() throws DaoException {
        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        try {
            Connection connection = ConnectionManager.getConnection();

            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);

            while (rs.next()) {
                int id = rs.getInt("id");
                String constructeur = rs.getString("constructeur");
                String modele = rs.getString("model");
                int nb_places = rs.getInt("nb_places");


                //vehicles.add(new Vehicle(id, constructeur, modele, nb_places));
                vehicles.add(new Vehicle(id, constructeur, nb_places, modele));
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException();
        }
        return vehicles;
    }

    public int Count () throws DaoException {
        //4
        // List<Client> clients = new ArrayList<Client>();

        try {
            Connection connection = ConnectionManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Vehicle");

            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public long editVehicle(Vehicle vehicle) throws DaoException {
        long n = 0;
        try(Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement pstmt = connection.prepareStatement(EDIT_VEHICLES_QUERY);

            pstmt.setString(1,vehicle.getConstructor());
            pstmt.setString(2, String.valueOf(vehicle.getNb_places()));
            pstmt.setString(3,vehicle.getModel());
            pstmt.setInt(4, (int) vehicle.getId());

            n = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException();
        }
        return n;
    }

}
	


