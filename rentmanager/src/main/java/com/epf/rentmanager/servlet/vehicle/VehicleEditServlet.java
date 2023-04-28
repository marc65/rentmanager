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
@WebServlet("/vehicles/editVehicle")
public class VehicleEditServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    int vehicle_edit_id;
    Vehicle vehicle = new Vehicle();
    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/vehicles/edit.jsp").forward(request, response);
        vehicle_edit_id = Integer.parseInt(request.getParameter("id"));

        try{
            vehicle = vehicleService.findById(vehicle_edit_id);
            request.setAttribute("info_vehicle",vehicle);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {


        String constructor = request.getParameter("constructor");
        String model = request.getParameter("model");
        String nb_places = request.getParameter("nb_places");

        if(!constructor.isEmpty()){
            vehicle.setConstructor(constructor);
        }
        if(!model.isEmpty()){
            vehicle.setModel(model);
        }
        if(!nb_places.isEmpty()){
            vehicle.setNb_places(Integer.parseInt(nb_places));
        }
        try {
            this.vehicleService.editVehicle(vehicle);
        } catch (ServiceException e1) {
            e1.printStackTrace();
        } catch (ContraintException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/rentmanager/cars");


    }
}
