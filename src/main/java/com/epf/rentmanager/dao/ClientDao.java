package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;

import com.epf.rentmanager.persistence.ConnectionManager;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY);
			preparedStatement.setString(1, client.getNom()); // ATTENTION /!\ : l’indice commence par 1, contrairement aux tableaux
			preparedStatement.setString(2, client.getPrenom()); // ATTENTION /!\ : l’indice commence par 1, contrairement aux tableaux
			preparedStatement.setString(3, client.getEmail()); // ATTENTION /!\ : l’indice commence par 1, contrairement aux tableaux
			Date date = Date.valueOf(client.getNaissance());
			preparedStatement.setDate(4, date);

			preparedStatement.execute();
			connection.close();

			return 1;
		} catch (SQLException error){
			error.printStackTrace();
			return 0;
		}
	}



	public long delete(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY);
			preparedStatement.setInt(1, client.getId()); // ATTENTION /!\ : l’indice commence par 1, contrairement aux tableaux
			preparedStatement.execute();

			connection.close();
			return 1;
		} catch (SQLException error){
			error.printStackTrace();
			return 0;
		}
	}

	public Client findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY) ;

			// Set the value for the placeholder
			preparedStatement.setInt(1, (int) id);

			// Execute the query
			ResultSet resultSet = preparedStatement.executeQuery();

			// Process the result set
			if (resultSet.next()) {
				// Retrieve values from the result set


				// Create a Client object with the retrieved values
				Client client;
                client = new Client((int) id, resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("email"), resultSet.getDate("naissance").toLocalDate());
                return client;
			}
		} catch (SQLException error) {
			// Handle any SQL exceptions
			error.printStackTrace();
			// You may want to handle or log the exception more appropriately in a real application
		}
		return null;
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();

			// Utilize a Statement for the select query
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENTS_QUERY) ;

			ResultSet resultSet = preparedStatement.executeQuery();

			// Process the result set and create Client objects
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				Date naissance = resultSet.getDate("naissance");

				// Create a Client object
				Client client = new Client(id, nom, prenom, email, naissance.toLocalDate());
				// Add the client to the list
				clients.add(client);
			}

			connection.close();
		} catch (SQLException error) {
			// Log the error
			error.printStackTrace(); // You should use a logger here

		}

		return clients;
	}

}
