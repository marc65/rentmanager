package com.epf.rentmanager.servlet.User;

import com.epf.rentmanager.exception.ContraintException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/createUsers")
public class UserCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        Client client = new Client();

        String nom = request.getParameter("last_name");
        String prenom = request.getParameter("first_name");
        String email = request.getParameter("email");
        String naissance = request.getParameter("birthDate");

        LocalDate date_naissance = LocalDate.parse(naissance);

        client.setFirstName(nom.toUpperCase());
        client.setLastName(prenom);
        client.setEmail(email);
        client.setBirthDate(date_naissance);


        try {
            this.clientService.create(client);
        } catch (ServiceException e1) {
            e1.printStackTrace();
        } catch (ContraintException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/rentmanager/users");


    }

}
