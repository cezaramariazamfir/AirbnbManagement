package Service;
import Database.DAOs.ApartamentDAO;
import Database.DAOs.CamperDAO;
import Database.DAOs.ProprietarDAO;
import Database.DAOs.VilaDAO;
import Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ProprietateService {
    private final List<Proprietate> proprietati;

    public ProprietateService(List<Proprietate> proprietati) {
        this.proprietati = proprietati;
    }

    public void afiseazaToateProprietatile() {
        for (Proprietate p : proprietati) {
            System.out.println(p.getId_proprietate() + " - " + p.getDenumire() + " - " + p.getPretPeNoapte() + " RON");
        }
    }

    public void afiseazaProprietatiSortateDupaPret() {
        proprietati.stream()
                .sorted(Comparator.comparingDouble(Proprietate::getPretPeNoapte))
                .forEach(p -> System.out.println(p.getId_proprietate() + " - " + p.getDenumire() + " - " + p.getPretPeNoapte() + " RON"));
    }

    public void afiseazaProprietatiFiltrateDupaLocatie(String locatie) {
        proprietati.stream()
                .filter(p -> p.getLocatie().equalsIgnoreCase(locatie))
                .forEach(p-> System.out.println(p.getId_proprietate() + " - " + p.getDenumire() + " - " + p.getPretPeNoapte() + " RON"));
    }


    public List<Proprietate> getProprietati() {
        return proprietati;
    }

    public void afiseazaProprietate(int idProprietate) {
        for (Proprietate p : proprietati) {
            if (p.getId_proprietate() == idProprietate) {
                System.out.println(p);
                return;
            }
        }
        System.out.println("Proprietatea cu ID-ul " + idProprietate + " nu a fost gasita.");
    }

    public Proprietate getProprietateById(int idProprietate) {
        for (Proprietate p : proprietati) {
            if (p.getId_proprietate() == idProprietate) {
                return p;
            }
        }
        return null;
    }

}
