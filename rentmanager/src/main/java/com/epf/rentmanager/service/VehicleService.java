package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;


@Service
public class VehicleService {

	private final VehicleDao vehicleDao;

	public VehicleService(VehicleDao vehicleDao){
	this.vehicleDao = vehicleDao;
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
	
}
