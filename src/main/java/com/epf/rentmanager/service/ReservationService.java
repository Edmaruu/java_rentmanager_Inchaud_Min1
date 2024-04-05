package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private ReservationDao reservationDao;


    private ReservationService(ReservationDao reservationDao){
        this.reservationDao = reservationDao;
    }




    public long create(Reservation reservation) throws ServiceException {

        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
         try {
            return ReservationDao.delete(reservation);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }

    }

    public Reservation findById(long id) throws ServiceException {

        try {
         return ReservationDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Reservation> findByClientId(long clientid) throws ServiceException {

        try {
         return reservationDao.findResaByClientId(clientid);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    public List<Reservation> findByVehicleId(long vehicleid) throws ServiceException {

        try {
           return reservationDao.findResaByVehicleId(vehicleid);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    public List<Reservation> findAll() throws ServiceException {

        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }
}
