package Meniu;

import Audit.Audit;

import Database.DAOs.*;
import Models.Proprietate;
import Models.Rezervare;
import Models.Vizualizare;
import Service.ProprietarService;
import Service.ProprietateService;
import Service.RezervareService;
import Service.VizualizareService;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class GuestMeniu {
    private final ProprietateService proprietateService;
    private final VizualizareService vizualizareService;
    private final RezervareService rezervareService;

    private final VilaDAO vilaDAO;
    private final ApartamentDAO apartamentDAO;
    private final CamperDAO camperDAO;
    private final RezervareDAO rezervareDAO;
    private final Scanner scanner;

    private final List<Proprietate> proprietati;

    public GuestMeniu(Connection connection) {
        this.scanner = new Scanner(System.in);

        // DAOs
        this.vilaDAO = VilaDAO.getInstance(connection);
        this.apartamentDAO = ApartamentDAO.getInstance(connection);
        this.camperDAO = CamperDAO.getInstance(connection);
        this.rezervareDAO = RezervareDAO.getInstance(connection);

        // Citim toate proprietatile
        proprietati = new ArrayList<>();
        proprietati.addAll(vilaDAO.readAll());
        proprietati.addAll(apartamentDAO.readAll());
        proprietati.addAll(camperDAO.readAll());


        this.proprietateService = new ProprietateService(proprietati);
        this.vizualizareService = new VizualizareService();

        // Cream serviciul de rezervari cu dao si listele citite
        this.rezervareService = new RezervareService(rezervareDAO);
    }

    public void pornesteMeniu() {

        while (true) {
            System.out.println("\n--- Meniu Guest ---");
            System.out.println("1. Vezi proprietati");
            System.out.println("2. Vezi proprietati sortate dupa pret");
            System.out.println("3. Vezi proprietati filtrate dupa locatie");
            System.out.println("4. Vezi detalii despre proprietate");
            System.out.println("5. Rezerva proprietate");
            System.out.println("6. Iesire");

            int optiune = Integer.parseInt(scanner.nextLine());

            switch (optiune) {
                case 1 -> {
                    proprietateService.afiseazaToateProprietatile();
                    Audit.logAction("Afisare toate proprietati");
                }
                case 2 -> {
                    proprietateService.afiseazaProprietatiSortateDupaPret();
                    Audit.logAction("Afisare toate proprietatile sortate dupa pret");
                }
                case 3 -> {
                    System.out.println("Locatie: ");
                    String locatie = scanner.nextLine();
                    proprietateService.afiseazaProprietatiFiltrateDupaLocatie(locatie);
                    Audit.logAction("Afisare toate proprietati filtrate dupa locatie " + locatie);
                }
                case 4 -> {
                    System.out.println("Id proprietate: ");
                    int idProprietate = scanner.nextInt();
                    scanner.nextLine();
                    proprietateService.afiseazaProprietate(idProprietate);

                    Proprietate p = proprietateService.getProprietateById(idProprietate);
                    if (p != null) {

                        Vizualizare vizualizare = new Vizualizare(
                                p.getDenumire(),
                                LocalDateTime.now(),
                                idProprietate
                        );

                        vizualizareService.addVizualizare(vizualizare);
                        Audit.logAction("Vizualizare proprietate " + p.getDenumire());
                    }
                }
                case 5 -> {
                    vizualizareService.ultimaVizualizare();
                    rezervareService.adaugaRezervare(proprietati);
                    Audit.logAction("Adaugare rezervare");
                    return;}
                case 6 -> {return;}
                default -> System.out.println("Optiune invalida.");
            }
        }
    }



}
