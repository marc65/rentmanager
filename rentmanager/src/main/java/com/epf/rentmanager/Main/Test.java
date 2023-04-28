package com.epf.rentmanager.Main;

import com.epf.rentmanager.exception.ContraintException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;

@Component
public class Test {
    private VehicleService vehicleService;
    private ClientService clientService;
    private ReservationService reservationService;

    public Test(VehicleService vehicleService, ClientService clientService, ReservationService reservationService){
        this.vehicleService = vehicleService;
        this.clientService = clientService;
        this.reservationService = reservationService;
    }

    public void afficherClients(){
        try {
            System.out.println(this.clientService.findAll());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
    public void afficherVehicules(){
        try {
            System.out.println(this.vehicleService.findAll());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
    public void afficherReservations(){
        try {
            System.out.println(this.reservationService.findAll());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public void rechercherClient(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez une id client : ");
            String identre = scanner.nextLine();
            System.out.println(this.clientService.findById(Long.parseLong(identre)));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public void rechercherVehicule(){

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez une id vehicule : ");
            String identre = scanner.nextLine();
            System.out.println(this.vehicleService.findById(Long.parseLong(identre)));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public void rechercherReservationParIDClient(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez une id client pour trouver ses reservations : ");
            String identre = scanner.nextLine();
            System.out.println(this.reservationService.findResaByClientId(Long.parseLong(identre)));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
    public void rechercherReservationParIDVehicule(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez une id vehicule pour trouver ses reservations : ");
            String identre = scanner.nextLine();
            System.out.println(this.reservationService.findResaByVehicleId(Long.parseLong(identre)));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

    }

    public void ajouterClient() {
        try{
            Scanner scanner = new Scanner(System.in);

            System.out.print("Nom: ");
            String lastName = scanner.nextLine();

            System.out.print("Prénom: ");
            String firstName = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Date de naissance (AAAA-MM-JJ) : ");
            String birthDateString = scanner.nextLine();

            LocalDate birthDate = LocalDate.parse(birthDateString);

            Client client = new Client();
            client.setLastName(lastName);
            client.setFirstName(firstName);
            client.setEmail(email);
            client.setBirthDate(birthDate);

            long id = clientService.create(client);
            System.out.println("Le client a été créé avec succès !");

        }catch (ServiceException e) {
            throw new RuntimeException(e);
        } catch (ContraintException e) {
            System.out.println(e.getMessage());
        }
    }

    public void compterClient() {
    try{
    System.out.println(this.clientService.Count());
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    }

    public void compterVehicle() {
        try{
            System.out.println(this.vehicleService.Count());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void ajouterVehicle() {
        try{
            Scanner scanner = new Scanner(System.in);

            System.out.print("Constructeur :");
            String constructor = scanner.nextLine();

            System.out.print("Modele : ");
            String model = scanner.nextLine();

            System.out.print("Nombre de places: ");
            String nb_place = scanner.nextLine();


            Vehicle vehicle = new Vehicle();
            vehicle.setConstructor(constructor);
            vehicle.setModel(model);
            vehicle.setNb_places(Integer.parseInt(nb_place));

            long id = vehicleService.create(vehicle);
            System.out.println("Le vehicule a été créé avec succès !");

        }catch (ServiceException e) {
            throw new RuntimeException(e);
        }

    }

    public void compterReservation() {
        try{
            System.out.println(this.reservationService.Count());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteClient() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("ID du client à supprimer :");
            long id_to_delete = Long.parseLong(scanner.nextLine());
            Client client = new Client();
            client.setId(id_to_delete);
            this.clientService.delete(client);
            System.out.println("Le client a été supprimé avec succès");
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public void editClient() {
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.print("ID du client à modifier :");
            long id_to_edit = Long.parseLong(scanner.nextLine());
            Client client = new Client();
            client.setId(id_to_edit);
            this.clientService.editClient(client);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
