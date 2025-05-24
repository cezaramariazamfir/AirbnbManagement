package Meniu;

import java.sql.Connection;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Audit.Audit;
import Database.DAOs.*;
import Database.DatabaseConfiguration;
import Models.Apartament;
import Models.Camper;
import Models.Proprietate;
import Models.Vila;
import Service.ProprietarService;


public class ProprietarMeniu {
    private final int idProprietar;
    private final RezervareDAO rezervareDAO;
    private final ProprietarDAO proprietarDAO;
    private final VilaDAO vilaDAO;
    private final ApartamentDAO apartamentDAO;
    private final CamperDAO camperDAO;
    private final Scanner scanner;
    private final Connection connection;

    public ProprietarMeniu(int idProprietar, Connection connection) {
        this.idProprietar = idProprietar;
        this.scanner = new Scanner(System.in);
        this.connection = connection;


        //DAOs
        this.rezervareDAO = RezervareDAO.getInstance(connection);
        this.proprietarDAO = ProprietarDAO.getInstance(connection);
        this.vilaDAO = VilaDAO.getInstance(connection);
        this.apartamentDAO = ApartamentDAO.getInstance(connection);
        this.camperDAO = CamperDAO.getInstance(connection);
    }

    public void pornesteMeniu() {
        ProprietarService proprietarService = new ProprietarService(connection);
        while (true) {
            System.out.println("\n--- Meniu Proprietar ---");
            System.out.println("1. Adauga Vila");
            System.out.println("2. Adauga Apartament");
            System.out.println("3. Adauga Camper");
            System.out.println("4. Vezi toate proprietatile mele");
            System.out.println("5. Sterge o proprietate");
            System.out.println("6. Schimba pretul unei proprietati");
            System.out.println("7. Afiseaza raport rezervari");
            System.out.println("8. Logout");

            int optiune = Integer.parseInt(scanner.nextLine());

            switch (optiune) {
                case 1 -> {
                    proprietarService.adaugaVila(idProprietar);
                    Audit.logAction("Adaugare vila user " + idProprietar);
                }
                case 2 -> {
                    proprietarService.adaugaApartament(idProprietar);
                    Audit.logAction("Adaugare apartament user " + idProprietar);
                }
                case 3 -> {
                    proprietarService.adaugaCamper(idProprietar);
                    Audit.logAction("Adaugare camper user " + idProprietar);
                }
                case 4 -> proprietarService.afiseazaToateProprietatile(idProprietar);
                case 5 -> {
                    proprietarService.stergeProprietate(idProprietar);
                    Audit.logAction("Stergere proprietate user " + idProprietar);
                }
                case 6 -> {
                    proprietarService.modificaProprietate(idProprietar);
                    Audit.logAction("Modificare proprietate user " + idProprietar);
                }
                case 7 -> {
                    proprietarService.genereazaRaport(idProprietar);
                    Audit.logAction("Generare raport user " + idProprietar);
                }
                case 8 -> {return;}
                default -> System.out.println("Optiune invalida.");
            }
        }
    }


}
