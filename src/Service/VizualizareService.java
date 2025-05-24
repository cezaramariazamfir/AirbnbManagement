package Service;

import Models.Vizualizare;

import java.util.TreeSet;

public class VizualizareService {
    private final TreeSet<Vizualizare> vizualizari;

    public VizualizareService() {
        this.vizualizari = new TreeSet<>();
    }

    public void addVizualizare(Vizualizare vizualizare) {
        vizualizari.add(vizualizare);
    }

    public void showVizualizari() {
        for (Vizualizare v : vizualizari) {
            System.out.println(v);
        }
    }

    public void ultimaVizualizare() {
        if (vizualizari.size() > 0) {
            Vizualizare v = vizualizari.last();
            System.out.println("Recomandare: " + v.getDenumireProprietate() + "(" + v.getId_proprietate() + ")");
        }
    }
}
