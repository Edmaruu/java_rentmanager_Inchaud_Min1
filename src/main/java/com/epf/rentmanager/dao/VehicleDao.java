package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY);
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setInt(2, vehicle.getNbPlaces());
			preparedStatement.execute();
			connection.close();
			return 1;
		} catch (SQLException error){
			return 0;
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY);
			preparedStatement.setInt(1, vehicle.getId()); // ATTENTION /!\ : l’indice commence par 1, contrairement aux tableaux
			preparedStatement.execute();

			connection.close();
			return 1;
		} catch (SQLException error){
			return 0;
		}
	}

	public Vehicle findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLE_QUERY) ;

			// Set the value for the placeholder
			preparedStatement.setInt(1, (int) id);

			// Execute the query
			ResultSet resultSet = preparedStatement.executeQuery();

			// Process the result set
			if (resultSet.next()) {
				// Retrieve values from the result set


				// Create a Client object with the retrieved values
				Vehicle vehicle;
				vehicle = new Vehicle((int) id, resultSet.getString("constructeur"), resultSet.getString("modele"), resultSet.getByte("nb_place"));
				return vehicle;
			}
		} catch (SQLException error) {
			// Handle any SQL exceptions
			error.printStackTrace();
			// You may want to handle or log the exception more appropriately in a real application
		}
		return null;
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<>();

		try {
			Connection connection = ConnectionManager.getConnection();

			// Utilize a Statement for the select query
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLES_QUERY) ;

			ResultSet resultSet = preparedStatement.executeQuery();

			// Process the result set and create Client objects
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				byte nb_places = resultSet.getByte("nb_places");

				// Create a Client object
				Vehicle vehicle = new Vehicle(id, constructeur, modele, nb_places);
				// Add the client to the list
				vehicles.add(vehicle);
			}

			connection.close();
		} catch (SQLException error) {
			// Log the error
			error.printStackTrace(); // You should use a logger here

		}

		return vehicles;
		
	}
	

}
