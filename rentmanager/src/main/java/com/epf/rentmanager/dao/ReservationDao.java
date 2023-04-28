package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	public ReservationDao() {}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_BY_RESA_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";

	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id = ?, vehicle_id = ?, debut = ?, fin = ? WHERE id = ?";
	private static final String DELETE_RESERVATION_BY_CLIENT_ID_QUERY = "DELETE FROM Reservation WHERE client_id=?;";
	private static final String DELETE_RESERVATION_BY_VEHICLE_ID_QUERY = "DELETE FROM Reservation WHERE vehicle_id=?;";
	public long create(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, (int) reservation.getClient_id().getId());
			preparedStatement.setInt(2, (int) reservation.getVehicle_id().getId());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));
			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();
			int newId = 0;
			if (rs.next()) {
				newId = rs.getInt(1);
			}
			return newId;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public long update(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, (int) reservation.getClient_id().getId());
			preparedStatement.setInt(2, (int) reservation.getVehicle_id().getId());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));
			preparedStatement.setInt(5, (int) reservation.getId());
			preparedStatement.executeUpdate();

			ResultSet rs = preparedStatement.getGeneratedKeys();
			int NbIdChange = 0;
			if (rs.next()) {
				NbIdChange = rs.getInt(1);
			}
			return NbIdChange;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public long delete(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY);
			preparedStatement.setInt(1, (int) reservation.getId());
			preparedStatement.executeUpdate();

			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public long deleteByClientId(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_BY_CLIENT_ID_QUERY);
			preparedStatement.setInt(1, (int) client.getId());
			preparedStatement.executeUpdate();

			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public long deleteByVehicleId(Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_BY_VEHICLE_ID_QUERY);
			preparedStatement.setInt(1, (int) vehicle.getId());
			preparedStatement.executeUpdate();

			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}


	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservation = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			preparedStatement.setInt(1, (int) clientId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				long id = (rs.getInt("id"));
				long vehicleId = (rs.getInt("vehicle_id"));

				Client client = new Client((int) clientId);
				Vehicle vehicle = new Vehicle((int) vehicleId);

				LocalDate debut = (rs.getDate("debut").toLocalDate());
				LocalDate fin = (rs.getDate("fin").toLocalDate());

				reservation.add(new Reservation((int) id, client, vehicle, debut, fin));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservation;
	}

	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException, ServiceException {
		List<Reservation> reservation = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			preparedStatement.setInt(1, (int) vehicleId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				long id = (rs.getInt("id"));
				long clientId = (rs.getInt("client_id"));

				Client client = new Client((int) clientId);
				Vehicle vehicle = new Vehicle((int) vehicleId);

				LocalDate debut = (rs.getDate("debut").toLocalDate());
				LocalDate fin = (rs.getDate("fin").toLocalDate());

				reservation.add(new Reservation((int) id, client, vehicle, debut, fin));
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

				Client client = new Client(clientId);
				Vehicle vehicle = new Vehicle(vehicleId);

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

	public Reservation findResaByResaId(long id) throws DaoException {
		Reservation reservation = new Reservation();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_RESA_QUERY);
			preparedStatement.setInt(1, (int) id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				long clientId = (rs.getInt("client_id"));
				long vehicleId = (rs.getInt("vehicle_id"));

				Client client = new Client((int) clientId);
				Vehicle vehicle = new Vehicle((int) vehicleId);

				LocalDate debut = (rs.getDate("debut").toLocalDate());
				LocalDate fin = (rs.getDate("fin").toLocalDate());
				reservation.setClient_id(client);
				reservation.setVehicle_id(vehicle);
				reservation.setId((int) id);
				reservation.setDebut(debut);
				reservation.setFin(fin);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return reservation;
	}
	}


