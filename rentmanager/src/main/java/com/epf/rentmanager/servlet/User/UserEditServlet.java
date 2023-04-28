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

@WebServlet("/users/editUsers")
public class UserEditServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    int client_edit_id;
    Client client = new Client();
    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp").forward(request, response);
        client_edit_id = Integer.parseInt(request.getParameter("id"));

        try{
           client = clientService.findById(client_edit_id);
           request.setAttribute("info_client",client);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {


        String nom = request.getParameter("last_name");
        String prenom = request.getParameter("first_name");
        String email = request.getParameter("email");
        String naissance = request.getParameter("birthDate");

        if(!nom.isEmpty()){
            client.setFirstName(nom.toUpperCase());
        }
        if(!prenom.isEmpty()){
            client.setLastName(prenom);
        }
        if(!email.isEmpty()){
            client.setEmail(email);
        }

        LocalDate date_naissance = LocalDate.parse(naissance);
        if(!naissance.isEmpty()){
            client.setBirthDate(date_naissance);
        }


        try {
            this.clientService.editClient(client);
        } catch (ServiceException e1) {
            e1.printStackTrace();
        } catch (ContraintException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("/rentmanager/users");


    }

}
