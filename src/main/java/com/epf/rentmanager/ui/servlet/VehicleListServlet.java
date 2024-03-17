package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cars")
public class VehicleListServlet extends HttpServlet{


    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer la liste des véhicules depuis le service
            List<Vehicle> vehicles = VehicleService.getInstance().findAll();

            // Définir l'attribut "vehicles" dans la requête pour passer la liste des véhicules à la JSP
            request.setAttribute("vehicles", vehicles);

            // Rediriger vers la JSP pour afficher les données
            request.getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
        } catch (Exception e) {
            // Gérer les erreurs
            e.printStackTrace(); // Vous pouvez remplacer ceci par un message d'erreur plus approprié
        }
    }
}
