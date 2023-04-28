package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.epf.rentmanager.exception.ContraintException;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.dao.ReservationDao;
import org.springframework.stereotype.Service;


@Service
public class VehicleService {

	private final VehicleDao vehicleDao;
	private ReservationDao reservationDao;


	public VehicleService(VehicleDao vehicleDao, ReservationDao reservationDao){

		this.vehicleDao = vehicleDao;
		this.reservationDao = reservationDao;
	}

	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return this.vehicleDao.create(vehicle);
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public Vehicle findById(long id) throws ServiceException {
		try {
			return this.vehicleDao.findById((int)id);
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return this.vehicleDao.findAll();
		}catch (DaoException e){
			throw new ServiceException();
		}
	}
	public int Count() throws  DaoException {
		try{
			return this.vehicleDao.Count();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}
	public long delete(Vehicle vehicle) throws ServiceException {
		try {
			this.reservationDao.deleteByVehicleId(vehicle);
			return this.vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
	public long editVehicle(Vehicle vehicle) throws ServiceException, ContraintException {
		try {
				return this.vehicleDao.editVehicle(vehicle);
			} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
}
