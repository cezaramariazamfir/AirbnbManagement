package Models;

public class Vila extends Proprietate {
    private double suprafataGradina;
    boolean arePiscina;

    public Vila(int id_proprietate, int id_proprietar, String denumire, String locatie, int capacitate, boolean disponibilabilitate, double pretPeNoapte, double suprafataGradina, boolean arePiscina) {
        super(id_proprietate, id_proprietar, denumire, locatie, capacitate, disponibilabilitate, pretPeNoapte);
        this.suprafataGradina = suprafataGradina;
        this.arePiscina = arePiscina;
        this.calculeazaPretDupaTaxe();
    }

    @Override
    public void calculeazaPretDupaTaxe() {
        this.pretPeNoapte *= 1.4;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSuprafata gradina(m^2): " + suprafataGradina + "\nAre piscina: " + (arePiscina ? "Da" : "Nu") + '\n';
    }

    public double getSuprafataGradina() {
        return suprafataGradina;
    }

    public void setSuprafataGradina(double suprafataGradina) {
        this.suprafataGradina = suprafataGradina;
    }

    public boolean isArePiscina() {
        return arePiscina;
    }

    public void setArePiscina(boolean arePiscina) {
        this.arePiscina = arePiscina;
    }
}
