package Models;

public class Apartament extends Proprietate {
    private int etaj;
    private boolean areBalcon;

    public Apartament(int id_proprietate, int id_proprietar, String denumire, String locatie, int capacitate, boolean disponibilabilitate, double pretPeNoapte, int etaj, boolean areBalcon) {
        super(id_proprietate, id_proprietar,denumire, locatie, capacitate, disponibilabilitate, pretPeNoapte);
        this.etaj = etaj;
        this.areBalcon = areBalcon;
        this.calculeazaPretDupaTaxe();
    }

    @Override
    public void calculeazaPretDupaTaxe() {
        this.pretPeNoapte *= 1.3;
    }

    @Override
    public String toString() {
        return super.toString() + "\nEtaj: " + etaj + "\nAre balcon: " + (areBalcon ? "Da" : "Nu") + '\n';
    }

    public int getEtaj() {
        return etaj;
    }

    public void setEtaj(int etaj) {
        this.etaj = etaj;
    }

    public boolean isAreBalcon() {
        return areBalcon;
    }

    public void setAreBalcon(boolean areBalcon) {
        this.areBalcon = areBalcon;
    }
}
