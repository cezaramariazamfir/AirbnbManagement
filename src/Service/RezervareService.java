package Service;

import Models.*;
import Database.DAOs.RezervareDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class RezervareService {
    private final RezervareDAO rezervareDAO;
    private final Scanner scanner = new Scanner(System.in);

    public RezervareService(RezervareDAO rezervareDAO) {
        this.rezervareDAO = rezervareDAO;
    }

    public void adaugaRezervare(List<Proprietate> proprietati) {
        System.out.println("ID Proprietate: ");
        int idProp = scanner.nextInt();
        scanner.nextLine();

        Proprietate proprietateSelectata = null;
        for (Proprietate p : proprietati) {
            if (p.getId_proprietate() == idProp) {
                proprietateSelectata = p;
                break;
            }
        }

        if (proprietateSelectata == null) {
            System.out.println("Nu exista nicio proprietate cu acest ID.");
            return;
        }

        System.out.println("Introdu data de check-in (yyyy-mm-dd):");
        LocalDate dataStart = LocalDate.parse(scanner.nextLine());

        System.out.println("Introdu data de check-out (yyyy-mm-dd):");
        LocalDate dataEnd = LocalDate.parse(scanner.nextLine());

        if (rezervareDAO.existaRezervareSuprapusa(idProp, dataStart, dataEnd)) {
            System.out.println("Exista deja o rezervare suprapusa pentru aceasta perioada.");
            return;
        }

        System.out.println("Achitati acum? (da/nu):");
        String raspuns = scanner.nextLine().trim().toLowerCase();

        Status status = raspuns.equals("da") ? Status.ACHITATA : Status.CONFIRMATA;

        Rezervare rezervare = new Rezervare(proprietateSelectata, dataStart, dataEnd, status);
        rezervareDAO.create(rezervare);

        System.out.println("Rezervare adaugata cu succes:\n" + rezervare);
    }


    private boolean datesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        // Verifică dacă două intervale se suprapun
        // excludem cazul în care un interval se termină exact când începe celălalt
        return (start1.isBefore(end2)) && (end1.isAfter(start2));
    }
}
