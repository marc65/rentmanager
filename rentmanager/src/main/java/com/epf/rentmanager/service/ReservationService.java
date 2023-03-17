package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private VehicleDao vehicleDao;
    private ClientDao clientDao;

    public ReservationService(ReservationDao reservationDao, VehicleDao vehicleDao, ClientDao clientDao){
        this.reservationDao = reservationDao;
        this.vehicleDao = vehicleDao;
        this.clientDao = clientDao;
    }


    public long create(Reservation reservation) throws ServiceException {
        // TODO: créer un client
        return 0;
    }
    public List<Reservation> findResaByClientId(long id) throws ServiceException {
        try {
            List<Reservation> reservation = new ArrayList<Reservation>();
            reservation = this.reservationDao.findResaByClientId(id);
            for (Reservation r : reservation) {
                r.setVehicle_id(this.vehicleDao.findById(r.getVehicle_id().getId()));
                r.setClient_id(this.clientDao.findById(r.getClient_id().getId()));
            }
            return reservation;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public List<Reservation> findResaByVehicleId(long id) throws ServiceException {
        try {
            return this.reservationDao.findResaByVehicleId(id);
        }catch (DaoException e){
            throw new ServiceException();
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            List<Reservation> reservations = new ArrayList<Reservation>();
            reservations = this.reservationDao.findAll();
            for (Reservation r : reservations) {
                r.setVehicle_id(this.vehicleDao.findById(r.getVehicle_id().getId()));
                r.setClient_id(this.clientDao.findById(r.getClient_id().getId()));
            }
            return reservations;
        } catch (DaoException e) {
            e.printStackTrace();
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

