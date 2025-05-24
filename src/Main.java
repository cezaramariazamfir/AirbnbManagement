import Database.DAOs.*;
import Database.DatabaseConfiguration;
import Database.DatabaseTables;
import Meniu.GuestMeniu;
import Models.Proprietar;
import Meniu.ProprietarMeniu;
import Service.ProprietarService;
import Service.RezervareService;

import java.sql.Connection;
import java.util.Scanner;

import Audit.Audit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //CREAREA TABELELOR IN BD
//        DatabaseTables tables = new DatabaseTables();
//        tables.createTables();

//        //ADAUG PROPRIETARI IN BD
//        Proprietar proprietar = new Proprietar(1, "Ion Popescu", "ion.popescu@gmail.com", "ion123");
//        ProprietarDAO proprietarDAO = ProprietarDAO.getInstance();
//        proprietarDAO.create(proprietar);
//
//        Proprietar proprietar2 = new Proprietar(2, "Ana Dumitrescu", "ana.dumitrescu@gmail.com", "ana123");
//        proprietarDAO.create(proprietar2);

        //CREARE CONEXIUNE BD
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        //DAOs
        RezervareDAO rezervareDAO = new RezervareDAO(connection);
        ProprietarDAO proprietarDAO = ProprietarDAO.getInstance(connection);
        ApartamentDAO apartamentDAO = ApartamentDAO.getInstance(connection);
        VilaDAO vilaDAO = VilaDAO.getInstance(connection);
        CamperDAO camperDAO = CamperDAO.getInstance(connection);

        //SERVICE
        ProprietarService proprietarService = new ProprietarService(connection);

        //MENIU
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Meniu:");
            System.out.println("1. Am deja cont");
            System.out.println("2. Proprietar nou");
            System.out.println("3. Guest");
            System.out.println("4. Iesire");
            System.out.print("Alegeți o opțiune (1-4): ");

            int optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1:
                    //Am deja cont
                    System.out.println("1. Login");
                    System.out.println("2. Schimbare parola");
                    System.out.println("3. Inapoi");
                    System.out.print("Alegeți o opțiune (1-3): ");

                    int optiune2 = scanner.nextInt();
                    scanner.nextLine();

                    switch (optiune2) {
                        case 1:
                            //Login
                            System.out.print("Email: ");
                            String email = scanner.nextLine();
                            System.out.print("Parola: ");
                            String parola = scanner.nextLine();

                            if (proprietarService.login(email, parola) == true) {
                                Audit.logAction("Login reusit user " + email);
                                String nume = proprietarDAO.findByEmail(email).getNume();
                                int id = proprietarDAO.findByEmail(email).getId_proprietar();

                                ProprietarMeniu meniu = new ProprietarMeniu(id, connection);
                                meniu.pornesteMeniu();
                            }
                            break;
                        case 2:
                            //Schimbare parola
                            System.out.print("Email: ");
                            String email2 = scanner.nextLine();
                            System.out.print("Parola veche: ");
                            String parolaVeche = scanner.nextLine();
                            System.out.print("Parola noua: ");
                            String parolaNoua = scanner.nextLine();
                            proprietarService.schimbaParola(email2, parolaVeche, parolaNoua, parolaNoua);
                            Audit.logAction("Schimbare parola user " + email2);
                            break;
                        case 3:
                            //Inapoi
                            break;
                    }
                    break;

                case 2:
                    //Proprietar nou
                    Proprietar p = proprietarService.citireProprietarDeLaTastatura();
                    String nume = p.getNume();
                    int id = p.getId_proprietar();

                    Audit.logAction("Creare prorpietar nou " + p.getEmail());
                    ProprietarMeniu meniu = new ProprietarMeniu(id, connection);
                    meniu.pornesteMeniu();
                    break;

                case 3:
                    GuestMeniu guestMeniu = new GuestMeniu(connection);
                    guestMeniu.pornesteMeniu();
                    break;

                case 4:
                    //Iesire
                    return;

                default:
                    System.out.println("Optiune invalida.");
            }
        }
        scanner.close();
    }

}
