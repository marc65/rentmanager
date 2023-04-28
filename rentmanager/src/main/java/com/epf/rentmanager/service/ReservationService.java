package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.exception.ContraintException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;
@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private VehicleDao vehicleDao;
    private ClientDao clientDao;

    public ReservationService(ReservationDao reservationDao, VehicleDao vehicleDao, ClientDao clientDao) {
        this.reservationDao = reservationDao;
        this.vehicleDao = vehicleDao;
        this.clientDao = clientDao;
    }
    public long edit (Reservation reservation) throws ServiceException, ContraintException{
        try {
            List<Reservation> vehicleReservations = this.reservationDao.findResaByVehicleId(reservation.getVehicle_id().getId());
            int userConsRes = Period.between(reservation.getDebut(), reservation.getFin()).getDays() + 1;
            int vehicleConsRes = Period.between(reservation.getDebut(), reservation.getFin()).getDays() + 1;

            for (Reservation vehicleReservation : vehicleReservations) {
                if (!reservation.getDebut().isAfter(vehicleReservation.getDebut()) && !reservation.getFin().isBefore(vehicleReservation.getDebut())) {
                    throw new ContraintException("La réservation se supperpose à la reservation : " + vehicleReservation);
                } else if (!reservation.getFin().isBefore(vehicleReservation.getFin()) && !reservation.getDebut().isAfter(vehicleReservation.getFin())) {
                    throw new ContraintException("La réservation se supperpose à la reservation : " + vehicleReservation);
                } else if (!reservation.getDebut().isBefore(vehicleReservation.getDebut()) && !reservation.getFin().isAfter(vehicleReservation.getFin())) {
                    throw new ContraintException("La réservation se supperpose à la reservation : " + vehicleReservation);
                }
                if (reservation.getDebut().isEqual(vehicleReservation.getFin().plusDays(1)) || reservation.getFin().isEqual(vehicleReservation.getDebut().minusDays(1))) {
                    if (vehicleReservation.getClient_id().getId() == reservation.getClient_id().getId()) {
                        userConsRes += Period.between(vehicleReservation.getDebut(), vehicleReservation.getFin()).getDays() + 1;
                    }
                    vehicleConsRes += Period.between(vehicleReservation.getDebut(), vehicleReservation.getFin()).getDays() + 1;
                }
                if (userConsRes > 7) {
                    throw new ContraintException("Cet utilisateur réserve ce véhicule plus de 7 jours consécutifs");
                }
                if (vehicleConsRes > 30) {
                    throw new ContraintException("Ce véhicule est réservé plus de 30 jours consécutifs");
                }
            }
            return this.reservationDao.update(reservation);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public long create(Reservation reservation) throws ServiceException, ContraintException {
        try {
            List<Reservation> vehicleReservations = this.reservationDao.findResaByVehicleId(reservation.getVehicle_id().getId());
            int userConsRes = Period.between(reservation.getDebut(), reservation.getFin()).getDays() + 1;
            int vehicleConsRes = Period.between(reservation.getDebut(), reservation.getFin()).getDays() + 1;

            for (Reservation vehicleReservation : vehicleReservations) {
                // verification : non-overlapping of reservations
                if (!reservation.getDebut().isAfter(vehicleReservation.getDebut()) && !reservation.getFin().isBefore(vehicleReservation.getDebut())) {
                    throw new ContraintException("La réservation se supperpose à la reservation : " + vehicleReservation);
                } else if (!reservation.getFin().isBefore(vehicleReservation.getFin()) && !reservation.getDebut().isAfter(vehicleReservation.getFin())) {
                    throw new ContraintException("La réservation se supperpose à la reservation : " + vehicleReservation);
                } else if (!reservation.getDebut().isBefore(vehicleReservation.getDebut()) && !reservation.getFin().isAfter(vehicleReservation.getFin())) {
                    throw new ContraintException("La réservation se supperpose à la reservation : " + vehicleReservation);
                }
                // calculate consecutive days
                if (reservation.getDebut().isEqual(vehicleReservation.getFin().plusDays(1)) || reservation.getFin().isEqual(vehicleReservation.getDebut().minusDays(1))) {
                    if (vehicleReservation.getClient_id().getId() == reservation.getClient_id().getId()) {
                        userConsRes += Period.between(vehicleReservation.getDebut(), vehicleReservation.getFin()).getDays() + 1;
                    }
                    vehicleConsRes += Period.between(vehicleReservation.getDebut(), vehicleReservation.getFin()).getDays() + 1;
                }
                // verification : no more than 7 consecutive days for the same user
                if (userConsRes > 7) {
                    throw new ContraintException("Cet utilisateur réserve ce véhicule plus de 7 jours consécutifs");
                }
                // verification : no more than 30 consecutive days
                if (vehicleConsRes > 30) {
                    throw new ContraintException("Ce véhicule est réservé plus de 30 jours consécutifs");
                }
            }
            return this.reservationDao.create(reservation);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
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
    public Reservation findResaByResaId(long id) throws ServiceException {
        try {
            Reservation reservation = new Reservation();
            reservation = this.reservationDao.findResaByResaId(id);
                reservation.setVehicle_id(this.vehicleDao.findById(reservation.getVehicle_id().getId()));
                reservation.setClient_id(this.clientDao.findById(reservation.getClient_id().getId()));

            return reservation;
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }


    public List<Reservation> findResaByVehicleId(long id) throws ServiceException {
        try {
            List<Reservation> reservation = new ArrayList<Reservation>();
            reservation = this.reservationDao.findResaByVehicleId(id);
            for (Reservation r : reservation) {
                r.setClient_id(this.clientDao.findById(r.getClient_id().getId()));
                r.setVehicle_id(this.vehicleDao.findById(r.getVehicle_id().getId()));
            }
            return reservation;
        } catch (DaoException e) {
            e.printStackTrace();
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
    public long delete(Reservation reservation) throws ServiceException {
        try {
            return this.reservationDao.delete(reservation);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }
}

