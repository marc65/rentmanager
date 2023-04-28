package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	
	public ClientDao() {}

	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String VERIFY_EMAIL = "SELECT * FROM Client WHERE email = ?";
	private static final String EDIT_CLIENTS_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id = ?;";


	public long create(Client client) throws DaoException {
		List<Client> clients = new ArrayList<Client>();
		try(Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY);

			preparedStatement.setString(1, client.getLastName());
			preparedStatement.setString(2, client.getFirstName());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setDate(4, Date.valueOf(client.getBirthDate()));

			long newID = preparedStatement.executeUpdate();


			return	newID;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}


	
	public long delete(Client client) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY);//, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, (int) client.getId());
			preparedStatement.executeUpdate();

			/*ResultSet rs = preparedStatement.getGeneratedKeys();
			long oldID = 0;
			if(rs.next()){
				oldID = rs.getInt(1);
			}return oldID;*/

			return 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Client findById(long id) throws DaoException {
		Client client = new Client();

		try (Connection connection = ConnectionManager.getConnection()){

			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY);
			preparedStatement.setInt(1, (int) id);

			ResultSet rs = preparedStatement.executeQuery();

			while(rs.next()){
				client.setId(id);
				client.setLastName(rs.getString("nom"));
				client.setFirstName(rs.getString("prenom"));
				client.setEmail(rs.getString("email"));
				client.setBirthDate(rs.getDate("naissance").toLocalDate());

			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return client;
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<Client>();

		try(Connection connection = ConnectionManager.getConnection()) {

			Statement statement = connection.createStatement();

			ResultSet rs = statement.executeQuery(FIND_CLIENTS_QUERY);

			while(rs.next()){
				int id = rs.getInt("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String email = rs.getString("email");
				LocalDate date = rs.getDate("naissance").toLocalDate();

				clients.add(new Client(id, nom, prenom, email, date));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		return clients;
	}


	public int Count () throws DaoException {
		try(Connection connection = ConnectionManager.getConnection()) {

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Client");

			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				return 0;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean checkIfEmailExist (String Email) throws DaoException{
		boolean exists = false;
		try(Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement(VERIFY_EMAIL);
			stmt.setString(1, Email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				exists = true;
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return exists;
	}

	public long editClient(Client client) throws DaoException {
		long n = 0;
		try(Connection connection = ConnectionManager.getConnection()) {
			PreparedStatement pstmt = connection.prepareStatement(EDIT_CLIENTS_QUERY);

			pstmt.setString(1,client.getFirstName());
			pstmt.setString(2,client.getLastName());
			pstmt.setString(3,client.getEmail());
			pstmt.setString(4,client.getBirthDate().toString());
			pstmt.setInt(5, (int) client.getId());

			n = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;
	}

}