package Models;

public abstract class Proprietate {
    protected int id_proprietate;
    protected int id_proprietar;
    protected String denumire;
    protected String locatie;
    protected int capacitate;
    protected boolean disponibilabilitate;
    protected double pretPeNoapte;

    public Proprietate(int id_proprietate, int id_proprietar, String denumire, String locatie, int capacitate, boolean disponibilabilitate, double pretPeNoapte) {
        this.id_proprietate = id_proprietate;
        this.id_proprietar = id_proprietar;
        this.denumire = denumire;
        this.locatie = locatie;
        this.capacitate = capacitate;
        this.disponibilabilitate = disponibilabilitate;
        this.pretPeNoapte = pretPeNoapte;
    }

    public abstract void calculeazaPretDupaTaxe();

    @Override
    public String toString() {
        return  "Tip: " + this.getClass().getSimpleName() + "ID: " + id_proprietate + ", Denumire: " + denumire + ", Locație: " + locatie + ", Preț/noapte: " + pretPeNoapte;
    }

    public int getId_proprietate() {
        return id_proprietate;
    }

    public void setId_proprietate(int id_proprietate) {
        this.id_proprietate = id_proprietate;
    }

    public int getId_proprietar() { return id_proprietar; }

    public void setId_proprietar(int id_proprietar) { this.id_proprietar = id_proprietar; }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public int getCapacitate() {
        return capacitate;
    }

    public void setCapacitate(int capacitate) {
        this.capacitate = capacitate;
    }

    public boolean isDisponibilabilitate() {
        return disponibilabilitate;
    }

    public void setDisponibilabilitate(boolean disponibilabilitate) {
        this.disponibilabilitate = disponibilabilitate;
    }

    public double getPretPeNoapte() {
        return pretPeNoapte;
    }

    public void setPretPeNoapte(double pretPeNoapte) {
        this.pretPeNoapte = pretPeNoapte;
    }
}
