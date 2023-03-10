package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/details")

public class UserDetailServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(req,resp);

    }
}
