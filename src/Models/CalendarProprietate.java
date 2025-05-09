package Models;

import java.time.LocalDate;
import java.util.Map;

public class CalendarProprietate {
    private int id_proprietate;
    private Map<LocalDate, Boolean> disponibilitati;

    public CalendarProprietate(int id_proprietate, Map<LocalDate, Boolean> disponibilitati) {
        this.id_proprietate = id_proprietate;
        this.disponibilitati = disponibilitati;
    }

    //TODO AFISARE MAI FRUMOASA
    @Override
    public String toString() {
        return "CalendarProprietate{" + "id_proprietate=" + id_proprietate + ", disponibilitati=" + disponibilitati + '}';
    }

    public int getId_proprietate() {
        return id_proprietate;
    }

    public void setId_proprietate(int id_proprietate) {
        this.id_proprietate = id_proprietate;
    }

    public Map<LocalDate, Boolean> getDisponibilitati() {
        return disponibilitati;
    }

    public void setDisponibilitati(Map<LocalDate, Boolean> disponibilitati) {
        this.disponibilitati = disponibilitati;
    }
}
