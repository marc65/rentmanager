package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		return 0;
	}
	
	public long delete(Reservation reservation) throws DaoException {
		return 0;
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservation = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			preparedStatement.setInt(1, (int) clientId);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				long vehicleId = rs.getInt("vehicle_id");

				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();

				Vehicle vehicle = VehicleDao.getInstance().findById(vehicleId);
				Client client = ClientDao.getInstance().findById(clientId);

				reservation.add(new Reservation(id, client, vehicle,debut, fin));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservation;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservation = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			preparedStatement.setInt(1, (int) vehicleId);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				long clientId = rs.getInt("client_id");

				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();
				Vehicle vehicle = VehicleDao.getInstance().findById(vehicleId);
				Client client = ClientDao.getInstance().findById(clientId);

				reservation.add(new Reservation(id, client, vehicle,debut, fin));

			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservation;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();

		try {
			Connection connection = ConnectionManager.getConnection();

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);

			while (rs.next()) {
				int id = rs.getInt("id");

				int clientId = rs.getInt("client_id");
				int vehicleId = rs.getInt("vehicle_id");

				LocalDate debut = rs.getDate("debut").toLocalDate();
				LocalDate fin = rs.getDate("fin").toLocalDate();

				Client client = ClientDao.getInstance().findById(clientId);
				Vehicle vehicle = VehicleDao.getInstance().findById(vehicleId);

				//vehicles.add(new Vehicle(id, constructeur, modele, nb_places));
				reservations.add(new Reservation(id,client, vehicle, debut, fin));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservations;
	}
	public int Count () throws DaoException {
		//4
		// List<Client> clients = new ArrayList<Client>();

		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Reservation");

			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return 0;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}

