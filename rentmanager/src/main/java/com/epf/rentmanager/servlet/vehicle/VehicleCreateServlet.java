package com.epf.rentmanager.servlet.vehicle;

import com.epf.rentmanager.exception.ContraintException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
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

@WebServlet("/vehicle/createVehicles")
public class VehicleCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        Vehicle vehicle = new Vehicle();

        String constructor = request.getParameter("constructor");
        String model = request.getParameter("model");
        String nb_places = request.getParameter("nb_places");

        vehicle.setConstructor(constructor);
        vehicle.setModel(model);
        vehicle.setNb_places(Integer.parseInt(nb_places));

        try {
            this.vehicleService.create(vehicle);
        } catch (ServiceException e1) {
            e1.printStackTrace();
        }
        response.sendRedirect("/rentmanager/users");


    }

}
