package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService() {
        this.reservationDao = ReservationDao.getInstance();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }

        return instance;
    }


    public long create(Reservation reservation) throws ServiceException {
        // TODO: créer un client
        return 0;
    }
    public List<Reservation> findResaByClientId(long id) throws ServiceException {
        List<Reservation> reservation = new ArrayList<>();

        try {
            //Vehicle vehicle = VehicleDao.getInstance().findById(vehicleId);
            return ReservationDao.getInstance().findResaByClientId((id));
        }catch (DaoException e){
            throw new ServiceException();
        }
    }

    public List<Reservation> findResaByVehicleId(long id) throws ServiceException {
        try {
            return ReservationDao.getInstance().findResaByVehicleId(id);
        }catch (DaoException e){
            throw new ServiceException();
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return ReservationDao.getInstance().findAll();
        }catch (DaoException e){
            throw new ServiceException();
        }
    }
    public int Count() throws  DaoException {
        try{
            return this.reservationDao.Count();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }
}

