package Service;
import Database.DAOs.*;
import Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProprietarService {

    private RezervareDAO rezervareDAO;
    private ProprietarDAO proprietarDAO;
    private VilaDAO vilaDAO;
    private ApartamentDAO apartamentDAO;
    private CamperDAO camperDAO;

    public ProprietarService(Connection connection) {
        this.rezervareDAO = RezervareDAO.getInstance(connection);
        this.proprietarDAO = ProprietarDAO.getInstance(connection);
        this.vilaDAO = VilaDAO.getInstance(connection);
        this.apartamentDAO = ApartamentDAO.getInstance(connection);
        this.camperDAO = CamperDAO.getInstance(connection);
    }

    public  boolean login(String email, String parola) {
        Proprietar proprietar = proprietarDAO.findByEmail(email);

        if (proprietar != null && proprietar.getParola().equals(parola)) {
            System.out.println("Login reusit!");
            System.out.println("Bine ai venit, " + proprietar.getNume() + "!");
            return true;
        } else {
            System.out.println("Email sau parola incorecte!");
            return false;
        }
    }

    public  Proprietar citireProprietarDeLaTastatura() {
        Scanner scanner = new Scanner(System.in);
        String nume = "";
        String email = "";
        String parola = "";

        boolean primaData = true;

        while (nume.isEmpty() || email.isEmpty() || parola.isEmpty()) {
            if (!primaData) {
                System.out.println("Ati lasat campuri goale.\n");
            }
            primaData = false;

            System.out.print("Nume: ");
            nume = scanner.nextLine();

            System.out.print("Email: ");
            email = scanner.nextLine();

            Proprietar proprietarExistent = proprietarDAO.findByEmail(email);
            if (proprietarExistent != null) {
                System.out.println("Exista deja un utilizator cu acest email.");
                email = "";
                continue;
            }

            System.out.print("Parola: ");
            parola = scanner.nextLine();
        }

        Proprietar proprietar = new Proprietar(nume, email, parola);

        //il adaug in BD
        proprietarDAO.create(proprietar);

        return proprietar;
    }

    public boolean schimbaParola(String email, String parolaVeche, String parolaNoua, String confirmareParolaNoua) {
        Proprietar proprietar = proprietarDAO.findByEmail(email);

        if (proprietar != null) {
            if (proprietar.getParola().equals(parolaVeche)) {
                if (parolaNoua.equals(confirmareParolaNoua)) {
                    proprietar.setParola(parolaNoua);
                    proprietarDAO.update(proprietar);
                    System.out.println("Parola a fost schimbata cu succes!");
                    return true;
                } else {
                    System.out.println("Parolele noi nu se potrivesc.");
                    return false;
                }
            } else {
                System.out.println("Parola veche este incorecta.");
                return false;
            }
        } else {
            System.out.println("Nu exista niciun utilizator cu acest email.");
            return false;
        }
    }



    public void adaugaVila(int idProprietar) {
        Scanner scanner = new Scanner(System.in);
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

    public void adaugaApartament(int idProprietar) {
        Scanner scanner = new Scanner(System.in);
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

    public void adaugaCamper(int idProprietar) {
        Scanner scanner = new Scanner(System.in);
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

    public void afiseazaToateProprietatile(int idProprietar) {
        List<Proprietate> proprietati = new ArrayList<>();

        proprietati.addAll(vilaDAO.readAllByUser(idProprietar));
        proprietati.addAll(apartamentDAO.readAllByUser(idProprietar));
        proprietati.addAll(camperDAO.readAllByUser(idProprietar));

        System.out.println("----- Proprietatile tale -----");
        proprietati.forEach(System.out::println);
    }

    public void stergeProprietate(int idProprietar) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introdu ID-ul proprietatii de sters:");
        int id = Integer.parseInt(scanner.nextLine());

        // Stergem doar dacă e a proprietarului
        if (esteProprietateaMea(id, idProprietar)) {
            vilaDAO.delete(id);
            apartamentDAO.delete(id);
            camperDAO.delete(id);
            System.out.println("Stergere efectuata.");
        } else {
            System.out.println("Aceasta proprietate nu iti apartine.");
        }
    }

    public void modificaProprietate(int idProprietar) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introdu ID-ul proprietatii:");
        int id = Integer.parseInt(scanner.nextLine());

        if (!esteProprietateaMea(id, idProprietar)) {
            System.out.println("Nu ai drepturi asupra acestei proprietati.");
            return;
        }

        System.out.println("Ce tip este proprietatea? (vila/apartament/camper)");
        String tip = scanner.nextLine().toLowerCase();

        switch (tip) {
            case "vila" -> {
                Vila vila = vilaDAO.read(id);
                if (vila == null) return;
                System.out.println("Pret nou: ");
                vila.setPretPeNoapte(Double.parseDouble(scanner.nextLine()));
                vilaDAO.update(vila);
            }
            case "apartament" -> {
                Apartament ap = apartamentDAO.read(id);
                if (ap == null) return;
                System.out.println("Pret nou: ");
                ap.setPretPeNoapte(Double.parseDouble(scanner.nextLine()));
                apartamentDAO.update(ap);
            }
            case "camper" -> {
                Camper c = camperDAO.read(id);
                if (c == null) return;
                System.out.println("Pret nou: ");
                c.setPretPeNoapte(Double.parseDouble(scanner.nextLine()));
                camperDAO.update(c);
            }
            default -> System.out.println("Tip necunoscut.");
        }
    }


    public boolean esteProprietateaMea(int id, int idProprietar) {
        Proprietate p = vilaDAO.read(id);
        if (p == null) p = apartamentDAO.read(id);
        if (p == null) p = camperDAO.read(id);
        return p != null && p.getId_proprietar() == idProprietar;
    }

    public void genereazaRaport(int idProprietar) {
        List<Proprietate> proprietati = new ArrayList<>();
        float sumaIncasata = 0;
        float sumaPending = 0;

        proprietati.addAll(vilaDAO.readAllByUser(idProprietar));
        proprietati.addAll(apartamentDAO.readAllByUser(idProprietar));
        proprietati.addAll(camperDAO.readAllByUser(idProprietar));

        if (proprietati.isEmpty()) {
            System.out.println("Nu aveti proprietati listate.");
            return;
        }

        System.out.println("Raport rezervari");

        for (Proprietate prop : proprietati) {

            List<Rezervare> rezervari = rezervareDAO.readAllByProprietate(prop.getId_proprietate(), proprietati);

            if (rezervari.isEmpty()) {
                continue;
            } else {
                for (Rezervare r : rezervari) {
                    if (r.getStatus() == Status.ACHITATA) sumaIncasata += r.getCostTotal();
                    else if (r.getStatus() == Status.CONFIRMATA) sumaPending += r.getCostTotal();

                    System.out.println("Proprietate: " + prop.getDenumire() + " (ID: " + prop.getId_proprietate() + ")");
                    System.out.println("Pret:" + r.getCostTotal() + "  Status: " + r.getStatus() + "  Check-in: " + r.getDataStart() + "  Check-out: " + r.getDataEnd());
                }
            }
        }

        System.out.println("\nSuma incasata: " + sumaIncasata + " RON\n" + "Suma pending: " + sumaPending + " RON");
    }


}
