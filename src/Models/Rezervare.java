package Models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rezervare {
    private int id_rezervare;
    private Proprietate proprietate;
    private LocalDate dataStart;
    private LocalDate dataEnd;
    private double costTotal;
    private Status status;


    public Rezervare(int id_rezervare, Proprietate proprietate, LocalDate dataStart, LocalDate dataEnd, Status status) {
        this.id_rezervare = id_rezervare;
        this.proprietate = proprietate;
        this.dataStart = dataStart;
        this.dataEnd = dataEnd;
        this.status = status;
        this.calculeazaCostTotal(); // calculezi automat la creare
    }

    public Rezervare(Proprietate proprietate, LocalDate dataStart, LocalDate dataEnd, Status status) {
        this.proprietate = proprietate;
        this.dataStart = dataStart;
        this.dataEnd = dataEnd;
        this.status = status;
        this.calculeazaCostTotal(); // calculezi automat la creare
    }

    public void calculeazaCostTotal() {
        int nrZile = (int) ChronoUnit.DAYS.between(dataStart, dataEnd);
        this.costTotal = nrZile * proprietate.getPretPeNoapte();
    }

    @Override
    public String toString() {
        return "Id rezervare: " + id_rezervare +
                ", Proprietate: " + proprietate.getDenumire() +
                ", Data check in: " + dataStart +
                ", Data check out: " + dataEnd +
                ", Pret: " + costTotal +
                ", Status: " + status ;
    }

    public int getId_rezervare() {
        return id_rezervare;
    }

    public void setId_rezervare(int id_rezervare) {
        this.id_rezervare = id_rezervare;
    }

    public Proprietate getProprietate() {
        return proprietate;
    }

    public void setProprietate(Proprietate proprietate) {
        this.proprietate = proprietate;
    }

    public LocalDate getDataStart() {
        return dataStart;
    }

    public void setDataStart(LocalDate dataStart) {
        this.dataStart = dataStart;
    }

    public LocalDate getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(LocalDate dataEnd) {
        this.dataEnd = dataEnd;
    }

    public double getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(double costTotal) {
        this.costTotal = costTotal;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
