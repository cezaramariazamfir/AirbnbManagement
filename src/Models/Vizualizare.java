package Models;

import java.time.LocalDateTime;

public class Vizualizare implements Comparable<Vizualizare> {
    String denumireProprietate;
    int id_proprietate;
    LocalDateTime dateVizualizare;

    public Vizualizare(String denumireProprietate, LocalDateTime dateVizualizare, int id_proprietate) {
        this.denumireProprietate = denumireProprietate;
        this.dateVizualizare = dateVizualizare;
        this.id_proprietate = id_proprietate;
    }

    public String getDenumireProprietate() {
        return denumireProprietate;
    }

    public void setDenumireProprietate(String denumireProprietate) {
        this.denumireProprietate = denumireProprietate;
    }

    public int getId_proprietate() {
        return id_proprietate;
    }

    public void setId_proprietate(int id_proprietate) {
        this.id_proprietate = id_proprietate;
    }

    public LocalDateTime getDateVizualizare() {
        return dateVizualizare;
    }

    public void setDateVizualizare(LocalDateTime dateVizualizare) {
        this.dateVizualizare = dateVizualizare;
    }

    @Override
    public int compareTo(Vizualizare other) {
        // Mai întâi comparăm după dată
        int cmp = this.dateVizualizare.compareTo(other.dateVizualizare);
        if (cmp != 0) return cmp;

        // Dacă data e aceeași, comparăm după id ca să evităm duplicate ignorate
        return Integer.compare(this.id_proprietate, other.id_proprietate);
    }

    @Override
    public String toString() {
        return dateVizualizare + " - " + denumireProprietate + " (ID: " + id_proprietate + ")";
    }
}
