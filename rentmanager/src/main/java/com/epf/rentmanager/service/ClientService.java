package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.epf.rentmanager.exception.ContraintException;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private final ClientDao clientDao;

	public ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}


	public long create(Client client) throws ServiceException, ContraintException {
		try {
			client.setLastName(client.getLastName().toUpperCase());

			LocalDate currentDate = LocalDate.now();
			LocalDate birthDate = client.getBirthDate();
			Period diff = Period.between(birthDate, currentDate);
			int age = diff.getYears();

			int nom_length = client.getLastName().length();
			int prenom_length = client.getFirstName().length();

			if ( age < 18){
				throw new ContraintException("TROP JEUNE");
			} else if (this.clientDao.checkIfEmailExist(client.getEmail())) {
				throw new ContraintException("Email déjà existant");
			} else if (nom_length <3){
				throw new ContraintException("Nom trop court");
			}
			else if (prenom_length <3){
				throw new ContraintException("Prenom trop court");
			}
			else{
				return this.clientDao.create(client);}
		} catch (DaoException e){
			throw new ServiceException();
		}
	}

	public Client findById(long id) throws ServiceException {
		try {
			return this.clientDao.findById((int)id);
		}catch (DaoException e){
			throw new ServiceException();
		}
	}

	public List<Client> findAll() throws ServiceException {
		try {
			return this.clientDao.findAll();
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

	public long delete(Client client) throws ServiceException {
		try {
			return this.clientDao.delete(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}
}

