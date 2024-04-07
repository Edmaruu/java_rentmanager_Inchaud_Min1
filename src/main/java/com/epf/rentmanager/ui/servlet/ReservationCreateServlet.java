package com.epf.rentmanager.ui.servlet;


import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet{

    @Autowired
    ClientService clientService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    ReservationService reservationService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        // affichage du formulaire
        try {

            // Récupérer la liste des clients
            List<Client> clients = clientService.findAll();
            // Récupérer la liste des véhicules
            List<Vehicle> vehicles = vehicleService.findAll();

            // Placer les listes dans les attributs de la requête
            request.setAttribute("clients", clients);
            request.setAttribute("vehicles", vehicles);
            // Rediriger vers la JSP pour afficher les données
            request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        } catch (Exception e) {
            // Gérer les erreurs
            e.printStackTrace(); // Vous pouvez remplacer ceci par un message d'erreur plus approprié
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        // traitement du formulaire (appel à la méthode de sauvegarde)
        try {




            List<Reservation> reservations = reservationService.findAll();


            Reservation existingReservation = null;
            Reservation existingReservation2 = null;

            int client_id = Integer.parseInt(request.getParameter("client"));
            int vehicle_id = Integer.parseInt(request.getParameter("car"));
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate debut = LocalDate.parse(request.getParameter("begin"), dateFormatter);
            LocalDate fin = LocalDate.parse(request.getParameter("end"), dateFormatter);

            // Création de l'objet Vehicle
            int countReservation = reservationService.count();

            Reservation reservation = new Reservation(countReservation, client_id, vehicle_id, debut, fin);

            for(Reservation reservation1 : reservations){


                if (reservation1.getDebut().equals(reservation.getDebut()) && reservation1.getVehicle_id() == reservation.getVehicle_id() ||
                        reservation1.getFin().equals(reservation.getDebut()) && reservation1.getVehicle_id() == reservation.getVehicle_id()){

                    existingReservation = reservation1;
                    break;
                } else if (reservation1.getVehicle_id() == reservation.getVehicle_id() && (reservation.getDebut().isBefore(reservation1.getFin()) || reservation.getDebut().equals(reservation1.getFin()))
                        && (reservation.getFin().isAfter(reservation1.getDebut()) || reservation.getFin().equals(reservation1.getDebut()))){

                    existingReservation2 = reservation1;
                    break;
                }
            }

            long joursDeReservation = ChronoUnit.DAYS.between(reservation.getDebut(), reservation.getFin()) + 1; // Ajouter 1 pour inclure également le dernier jour

            long totalDaysReserved = ChronoUnit.DAYS.between(reservation.getDebut(), reservation.getFin()) + 1;

            List<Reservation> reservationV = reservationService.findByVehicleId(reservation.getVehicle_id());

            for (Reservation reservation2 : reservationV) {

                LocalDate debutReservation = reservation2.getDebut();
                LocalDate finReservation = reservation2.getFin();

                // Calculer le nombre de jours de réservation pour cette réservation spécifique
                long daysReserved = ChronoUnit.DAYS.between(debutReservation, finReservation) + 1; // Ajouter 1 pour inclure également le dernier jour

                // Ajouter le nombre de jours de cette réservation à la durée totale de réservation
                totalDaysReserved += daysReserved;
            }

            if(existingReservation != null ){
                request.setAttribute("message", "La voiture a déja été réservée pour ce jour, veuillez en choisir une autre ou choisissez un autre jour");
                // Récupérer la liste des clients
                List<Client> clients = clientService.findAll();
                // Récupérer la liste des véhicules
                List<Vehicle> vehicles = vehicleService.findAll();

                // Placer les listes dans les attributs de la requête
                request.setAttribute("clients", clients);
                request.setAttribute("vehicles", vehicles);
                request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
            } else if (joursDeReservation > 7) {
                // Récupérer la liste des clients
                List<Client> clients = clientService.findAll();
                // Récupérer la liste des véhicules
                List<Vehicle> vehicles = vehicleService.findAll();

                // Placer les listes dans les attributs de la requête
                request.setAttribute("clients", clients);
                request.setAttribute("vehicles", vehicles);
                request.setAttribute("message", "Vous ne pouvez pas réserver une voiture pendant 7 jours de suite");
                request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);

            } else if(existingReservation2 != null ){
                request.setAttribute("message", "La voiture a déja une réservation pendant cette période");
                // Récupérer la liste des clients
                List<Client> clients = clientService.findAll();
                // Récupérer la liste des véhicules
                List<Vehicle> vehicles = vehicleService.findAll();

                // Placer les listes dans les attributs de la requête
                request.setAttribute("clients", clients);
                request.setAttribute("vehicles", vehicles);
                request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
            } else if (totalDaysReserved > 30) {
                request.setAttribute("message", "Cette voiture a été réservé 30 jours d'affilé, veuillez en choisir une autre");
                // Récupérer la liste des clients
                List<Client> clients = clientService.findAll();
                // Récupérer la liste des véhicules
                List<Vehicle> vehicles = vehicleService.findAll();

                // Placer les listes dans les attributs de la requête
                request.setAttribute("clients", clients);
                request.setAttribute("vehicles", vehicles);
                request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
            }else {
                reservationService.create(reservation);
                response.sendRedirect(request.getContextPath()+"/rents");
            }


        } catch (Exception e) {
            // Gestion des exceptions
            e.printStackTrace(); // À adapter selon votre gestion d'erreurs
            response.sendRedirect(request.getContextPath()+"/rents");
        }
    }


}