package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;

public class ClientService {

	private final ClientDao clientDao;
	public static ClientService instance;

	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}

	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}

		return instance;
	}


	public long create(Client client) throws ServiceException {
		try {
			client.setLastName(client.getLastName().toUpperCase());
			return this.clientDao.create(client);
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public Client findById(long id) throws ServiceException {
		try {
			return ClientDao.getInstance().findById((int)id);
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public List<Client> findAll() throws ServiceException {
		try {
			return ClientDao.getInstance().findAll();
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public int Count() throws  DaoException {
		try{
			return this.clientDao.Count();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

}
