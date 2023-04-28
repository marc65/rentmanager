package com.epf.rentmanager.servlet.Rent;

import com.epf.rentmanager.exception.ContraintException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/rents/create")
public class RentCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        try {
            request.setAttribute("clients",this.clientService.findAll());
            request.setAttribute("vehicles",this.vehicleService.findAll());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        Reservation reservation = new Reservation();
        Vehicle vehicle = new Vehicle();
        Client client = new Client();

        client.setId(Long.parseLong(request.getParameter("client")));
        vehicle.setId(Long.parseLong(request.getParameter("vehicle")));
        String debut = request.getParameter("debut");
        String fin = request.getParameter("fin");

        reservation.setClient_id(client);
        reservation.setVehicle_id(vehicle);
        reservation.setDebut(LocalDate.parse(debut));
        reservation.setFin(LocalDate.parse(fin));

        try {
            this.reservationService.create(reservation);
        } catch (ServiceException e1) {
            e1.printStackTrace();
        } catch (ContraintException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/rentmanager/rents");


    }

}
