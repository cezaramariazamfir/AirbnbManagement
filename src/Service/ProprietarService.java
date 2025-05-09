package Service;
import Database.DAOs.ProprietarDAO;
import Models.Proprietar;

import java.sql.*;
import java.util.Scanner;

public class ProprietarService {

    private static ProprietarDAO proprietarDAO = ProprietarDAO.getInstance();

    public static boolean login(String email, String parola) {
        Proprietar proprietar = proprietarDAO.findByEmail(email);

        if (proprietar != null && proprietar.getParola().equals(parola)) {
            System.out.println("Login reușit!");
            return true;
        } else {
            System.out.println("Email sau parolă incorecte!");
            return false;
        }
    }

    public static Proprietar citireProprietarDeLaTastatura() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nume: ");
        String nume = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        Proprietar proprietarExistent = proprietarDAO.findByEmail(email);
        if (proprietarExistent != null) {
            System.out.println("Exista deja un utilizator cu acest email.");
            return null;
        }

        System.out.print("Parola: ");
        String parola = scanner.nextLine();

        //creez un obiect nou proprietar
        Proprietar proprietar = new Proprietar(nume, email, parola);

        //il adaug in BD
        proprietarDAO.create(proprietar);

        return proprietar;
    }

    public static boolean schimbaParola(String email, String parolaVeche, String parolaNoua, String confirmareParolaNoua) {
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
}
