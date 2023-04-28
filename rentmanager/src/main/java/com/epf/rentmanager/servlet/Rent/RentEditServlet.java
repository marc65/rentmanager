package com.epf.rentmanager.servlet.Rent;

import com.epf.rentmanager.exception.ContraintException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import jdk.vm.ci.meta.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/rents/editRent")
public class RentEditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    Reservation reservation = new Reservation();
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;

    int res_id;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        res_id = Integer.parseInt(request.getParameter("id"));
        try {
            request.setAttribute("clients",this.clientService.findAll());
            request.setAttribute("vehicles",this.vehicleService.findAll());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("/WEB-INF/views/rents/edit.jsp").forward(request, response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        Vehicle vehicle = new Vehicle();
        Client client = new Client();
        reservation.setId(res_id);
        client.setId(Long.parseLong(request.getParameter("client")));
        vehicle.setId(Long.parseLong(request.getParameter("vehicle")));
        String debut = request.getParameter("debut");
        String fin = request.getParameter("fin");

        LocalDate debut_date = LocalDate.parse(debut);
        LocalDate fin_date = LocalDate.parse(fin);

        reservation.setClient_id(client);
        reservation.setVehicle_id(vehicle);
        reservation.setDebut(debut_date);
        reservation.setFin(fin_date);



        try {
            this.reservationService.edit(reservation);
        } catch (ServiceException e1) {
            e1.printStackTrace();
        } catch (ContraintException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/rentmanager/rents");


    }

}
