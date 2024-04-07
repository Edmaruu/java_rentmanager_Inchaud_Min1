package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;

	public VehicleService(VehicleDao vehicleDao){
		this.vehicleDao = vehicleDao;
	}
	

	
	
	public long create(Vehicle vehicle) throws ServiceException {

		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création ou de la mise à jour du client.", e);
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {

			try {
				return vehicleDao.delete(vehicle);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
	}


	public Vehicle findById(int id) throws ServiceException {

		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération du client.", e);
		}
	}

	public List<Vehicle> findAll() throws ServiceException {

		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création ou de la mise à jour du client.", e);
		}
	}

	public int count() throws ServiceException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors du recensement du nombre de client.", e);
		}
	}
	
}
