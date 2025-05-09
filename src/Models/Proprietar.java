package Models;

public class Proprietar {
    private int id_proprietar;
    private String nume;
    private String email;
    private String parola;

    public Proprietar(int id_proprietar, String nume, String email, String parola) {
        this.id_proprietar = id_proprietar;
        this.nume = nume;
        this.email = email;
        this.parola = parola;
    }

    public Proprietar(String nume, String email, String parola) {
        this.nume = nume;
        this.email = email;
        this.parola = parola;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getId_proprietar() {
        return id_proprietar;
    }

    public void setId_proprietar(int id_proprietar) {
        this.id_proprietar = id_proprietar;
    }

    @Override
    public String toString() {
        return "Proprietar{" +
                "id_proprietar=" + id_proprietar +
                ", nume='" + nume + '\'' +
                ", email='" + email + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }
}
