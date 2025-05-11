package Meniu;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Database.DAOs.ApartamentDAO;
import Database.DAOs.CamperDAO;
import Database.DAOs.VilaDAO;
import Database.DatabaseConfiguration;
import Models.Apartament;
import Models.Camper;
import Models.Proprietate;
import Models.Vila;


public class ProprietarMeniu {
    private final int idProprietar;
    private final VilaDAO vilaDAO;
    private final ApartamentDAO apartamentDAO;
    private final CamperDAO camperDAO;
    private final Scanner scanner;

    public ProprietarMeniu(int idProprietar) {
        this.idProprietar = idProprietar;
        this.scanner = new Scanner(System.in);

        // Create database connection
        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        // Pass the connection to the DAOs
        this.vilaDAO = new VilaDAO(connection);
        this.apartamentDAO = new ApartamentDAO(connection);
        this.camperDAO = new CamperDAO(connection);
    }

    public void pornesteMeniu() {
        while (true) {
            System.out.println("\n--- Meniu Proprietar ---");
            System.out.println("1. Adauga Vila");
            System.out.println("2. Adauga Apartament");
            System.out.println("3. Adauga Camper");
            System.out.println("4. Vezi toate proprietatile mele");
            System.out.println("5. Sterge o proprietate");
            System.out.println("6. Modifica o proprietate");
            System.out.println("0. Iesire");

            int optiune = Integer.parseInt(scanner.nextLine());

            switch (optiune) {
                case 1 -> adaugaVila();
                case 2 -> adaugaApartament();
                case 3 -> adaugaCamper();
                case 4 -> afiseazaToateProprietatile();
                case 5 -> stergeProprietate();
                case 6 -> modificaProprietate();
                case 0 -> {
                    System.out.println("La revedere!");
                    return;
                }
                default -> System.out.println("Optiune invalida.");
            }
        }
    }

    private void adaugaVila() {
        System.out.println("Denumire:");
        String denumire = scanner.nextLine();
        System.out.println("Locatie:");
        String locatie = scanner.nextLine();
        System.out.println("Capacitate:");
        int capacitate = Integer.parseInt(scanner.nextLine());
        System.out.println("Disponibila (true/false):");
        boolean disponibil = Boolean.parseBoolean(scanner.nextLine());
        System.out.println("Pret pe noapte:");
        double pret = Double.parseDouble(scanner.nextLine());
        System.out.println("Suprafata gradina:");
        double suprafata = Double.parseDouble(scanner.nextLine());
        System.out.println("Are piscina (true/false):");
        boolean piscina = Boolean.parseBoolean(scanner.nextLine());

        vilaDAO.create(new Vila(0, idProprietar, denumire, locatie, capacitate, disponibil, pret, suprafata, piscina));
    }

    private void adaugaApartament() {
        System.out.println("Denumire:");
        String denumire = scanner.nextLine();
        System.out.println("Locatie:");
        String locatie = scanner.nextLine();
        System.out.println("Capacitate:");
        int capacitate = Integer.parseInt(scanner.nextLine());
        System.out.println("Disponibil (true/false):");
        boolean disponibil = Boolean.parseBoolean(scanner.nextLine());
        System.out.println("Pret pe noapte:");
        double pret = Double.parseDouble(scanner.nextLine());
        System.out.println("Etaj:");
        int etaj = Integer.parseInt(scanner.nextLine());
        System.out.println("Are balcon (true/false):");
        boolean balcon = Boolean.parseBoolean(scanner.nextLine());

        apartamentDAO.create(new Apartament(0, idProprietar, denumire, locatie, capacitate, disponibil, pret, etaj, balcon));
    }

    private void adaugaCamper() {
        System.out.println("Denumire:");
        String denumire = scanner.nextLine();
        System.out.println("Locatie:");
        String locatie = scanner.nextLine();
        System.out.println("Capacitate:");
        int capacitate = Integer.parseInt(scanner.nextLine());
        System.out.println("Disponibil (true/false):");
        boolean disponibil = Boolean.parseBoolean(scanner.nextLine());
        System.out.println("Pret pe noapte:");
        double pret = Double.parseDouble(scanner.nextLine());
        System.out.println("Lungime:");
        double lungime = Double.parseDouble(scanner.nextLine());
        System.out.println("Autonomie apa (in litri):");
        int autonomie = Integer.parseInt(scanner.nextLine());

        camperDAO.create(new Camper(0, idProprietar, denumire, locatie, capacitate, disponibil, pret, lungime, autonomie));
    }

    private void afiseazaToateProprietatile() {
        List<Proprietate> proprietati = new ArrayList<>();

        proprietati.addAll(vilaDAO.readAllByUser(idProprietar));
        proprietati.addAll(apartamentDAO.readAllByUser(idProprietar));
        proprietati.addAll(camperDAO.readAllByUser(idProprietar));

        System.out.println("----- Proprietățile tale -----");
        proprietati.forEach(System.out::println);
    }

    private void stergeProprietate() {
        System.out.println("Introdu ID-ul proprietatii de sters:");
        int id = Integer.parseInt(scanner.nextLine());

        // Stergem doar dacă e a proprietarului
        if (esteProprietateaMea(id)) {
            vilaDAO.delete(id);
            apartamentDAO.delete(id);
            camperDAO.delete(id);
            System.out.println("Stergere efectuata.");
        } else {
            System.out.println("Aceasta proprietate nu iti apartine.");
        }
    }

    private void modificaProprietate() {
        System.out.println("Introdu ID-ul proprietatii de modificat:");
        int id = Integer.parseInt(scanner.nextLine());

        if (!esteProprietateaMea(id)) {
            System.out.println("Nu ai drepturi asupra acestei proprietati.");
            return;
        }

        System.out.println("Ce tip este proprietatea? (vila/apartament/camper)");
        String tip = scanner.nextLine().toLowerCase();

        switch (tip) {
            case "vila" -> {
                Vila vila = vilaDAO.read(id);
                if (vila == null) return;
                System.out.println("Nou pret:");
                vila.setPretPeNoapte(Double.parseDouble(scanner.nextLine()));
                vilaDAO.update(vila); // doar prețul va fi actualizat
            }
            case "apartament" -> {
                Apartament ap = apartamentDAO.read(id);
                if (ap == null) return;
                System.out.println("Nou pret:");
                ap.setPretPeNoapte(Double.parseDouble(scanner.nextLine()));
                apartamentDAO.update(ap); // doar prețul va fi actualizat
            }
            case "camper" -> {
                Camper c = camperDAO.read(id);
                if (c == null) return;
                System.out.println("Nou pret:");
                c.setPretPeNoapte(Double.parseDouble(scanner.nextLine()));
                camperDAO.update(c); // doar prețul va fi actualizat
            }
            default -> System.out.println("Tip necunoscut.");
        }
    }


    private boolean esteProprietateaMea(int id) {
        Proprietate p = vilaDAO.read(id);
        if (p == null) p = apartamentDAO.read(id);
        if (p == null) p = camperDAO.read(id);
        return p != null && p.getId_proprietar() == idProprietar;
    }
}
