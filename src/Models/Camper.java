package Models;

public class Camper extends Proprietate{
    private double lungime;
    private int autonomieApa; //nr de zile

    public Camper(int id_proprietate, int id_proprietar, String denumire, String locatie, int capacitate, boolean disponibilabilitate, double pretPeNoapte, double lungime, int autonomieApa) {
        super(id_proprietate, id_proprietar,denumire, locatie, capacitate, disponibilabilitate, pretPeNoapte);
        this.lungime = lungime;
        this.autonomieApa = autonomieApa;
        this.calculeazaPretDupaTaxe();
    }

    @Override
    public void calculeazaPretDupaTaxe() {
        this.pretPeNoapte *= 1.2;
    }


    @Override
    public String toString() {
        return super.toString() + "\nLungime camper(m): " + lungime + "\nAutonomie apa(zile): " + autonomieApa + '\n';
    }

    public double getLungime() {
        return lungime;
    }

    public void setLungime(double lungime) {
        this.lungime = lungime;
    }

    public int getAutonomieApa() {
        return autonomieApa;
    }

    public void setAutonomieApa(int autonomieApa) {
        this.autonomieApa = autonomieApa;
    }
}
