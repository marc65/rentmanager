package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Autowired
	private ClientService clientService = this.clientService;
	@Autowired
	private VehicleService vehicleService = this.vehicleService;
	@Autowired
	private ReservationService reservationService = this.reservationService;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int nbClients = 0;
		int nbVehicle = 0;
		int nbReservation = 0;
		try {
			nbClients = this.clientService.Count();
			nbVehicle = this.vehicleService.Count();
			nbReservation = this.reservationService.Count();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}

		request.setAttribute("nbClients",nbClients);
		request.setAttribute("nbVehicle",nbVehicle);
		request.setAttribute("nbReservation",nbReservation);


		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);

	}

	}

