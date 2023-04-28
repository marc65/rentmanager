package com.epf.rentmanager.servlet.User;

import com.epf.rentmanager.exception.ServiceException;
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
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/users/details")

public class UserDetailServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ReservationService reservationService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int clientId = Integer.parseInt(req.getParameter("id"));
        try {
            List<Reservation> reservation = this.reservationService.findResaByClientId(clientId);
            List<Vehicle> vehicles = new ArrayList<>();;
            for (Reservation r : reservation) {
                r.setVehicle_id(this.vehicleService.findById(r.getVehicle_id().getId()));
                if (!vehicles.contains(r.getVehicle_id())) {
                    vehicles.add(r.getVehicle_id());
                }
            }
            req.setAttribute("vehicles", vehicles);
            req.setAttribute("client", this.clientService.findById(clientId));
            req.setAttribute("reservations", reservation);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(req,resp);
    }
}
