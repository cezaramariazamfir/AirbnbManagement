package Database.DAOs;

import Models.Proprietar;
import Database.DatabaseConfiguration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProprietarDAO implements BasicDAO<Proprietar> {
    private static ProprietarDAO instance;
    private final Connection connection;

    private ProprietarDAO() {
        this.connection = DatabaseConfiguration.getDatabaseConnection();
    }

    public static ProprietarDAO getInstance() {
        if (instance == null) {
            instance = new ProprietarDAO();
        }
        return instance;
    }

    public Proprietar findByEmail(String email) {
        String sql = "SELECT * FROM Proprietar WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Proprietar(
                        rs.getInt("id_proprietar"),
                        rs.getString("nume"),
                        rs.getString("email"),
                        rs.getString("parola")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Dacă nu găsim niciun utilizator cu acest email
    }

    @Override
    public void create(Proprietar proprietar) {
        String sql = "INSERT INTO Proprietar (nume, email, parola) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, proprietar.getNume());
            stmt.setString(2, proprietar.getEmail());
            stmt.setString(3, proprietar.getParola());
            stmt.executeUpdate();

            Proprietar proprietarSalvat = findByEmail(proprietar.getEmail());
            if (proprietarSalvat != null) {
                // Setează ID-ul salvat pe obiectul original
                proprietar.setId_proprietar(proprietarSalvat.getId_proprietar());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Proprietar read(int id) {
        String sql = "SELECT * FROM Proprietar WHERE id_proprietar = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Proprietar(
                        rs.getInt("id_proprietar"),
                        rs.getString("nume"),
                        rs.getString("email"),
                        rs.getString("parola")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Proprietar> readAll() {
        List<Proprietar> list = new ArrayList<>();
        String sql = "SELECT * FROM Proprietar";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Proprietar p = new Proprietar(
                        rs.getInt("id_proprietar"),
                        rs.getString("nume"),
                        rs.getString("email"),
                        rs.getString("parola")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Proprietar proprietar) {
        String sql = "UPDATE Proprietar SET nume = ?, email = ?, parola = ? WHERE id_proprietar = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, proprietar.getNume());
            stmt.setString(2, proprietar.getEmail());
            stmt.setString(3, proprietar.getParola());
            stmt.setInt(4, proprietar.getId_proprietar());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Proprietar WHERE id_proprietar = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
