package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ReservationService;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClientService clientService = ClientService.getInstance();
	private VehicleService vehicleService = VehicleService.getInstance();
	private ReservationService reservationService = ReservationService.getInstance();


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

