package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;

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
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RESERVATION_QUERY);
			preparedStatement.setInt(1, reservation.getId());
			preparedStatement.setInt(2, reservation.getVehicleId());

			Date date = Date.valueOf(reservation.getDebut());
			Date date2 = Date.valueOf(reservation.getFin());
			preparedStatement.setDate(3, date);
			preparedStatement.setDate(4, date2);

			return 0;
		} catch (SQLException error){
			return 0;
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY);
			preparedStatement.setInt(1, reservation.getId()); // ATTENTION /!\ : l’indice commence par 1, contrairement aux tableaux
			preparedStatement.execute();

			connection.close();
			return 1;
		} catch (SQLException error){
			return 0;
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		return new ArrayList<Reservation>();
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		return new ArrayList<Reservation>();
	}

	public List<Reservation> findAll() throws DaoException {
		return new ArrayList<Reservation>();
	}
}
