package Database.DAOs;

import Models.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RezervareDAO  {
    private static RezervareDAO instance;
    private final Connection connection;

    public RezervareDAO(Connection connection) {
        this.connection = connection;
    }

    public static RezervareDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new RezervareDAO(connection);
        }
        return instance;
    }

    public void create(Rezervare rezervare) {String sql = "INSERT INTO Rezervare (id_proprietate, dataStart, dataEnd, costTotal, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, rezervare.getProprietate().getId_proprietate());
            stmt.setDate(2, Date.valueOf(rezervare.getDataStart()));
            stmt.setDate(3, Date.valueOf(rezervare.getDataEnd()));
            stmt.setDouble(4, rezervare.getCostTotal());
            stmt.setString(5, rezervare.getStatus().name());
            stmt.executeUpdate();

            // Preia id-ul generat
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                rezervare.setId_rezervare(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Rezervare read(int id, List<Proprietate> proprietati) {
        String sql = "SELECT * FROM Rezervare WHERE id_rezervare = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int idRez = rs.getInt("id_rezervare");
                int idProp = rs.getInt("id_proprietate");
                LocalDate dataStart = rs.getDate("dataStart").toLocalDate();
                LocalDate dataEnd = rs.getDate("dataEnd").toLocalDate();
                double cost = rs.getDouble("costTotal");
                Status status = Status.valueOf(rs.getString("status"));

                Proprietate proprietate = proprietati.stream()
                        .filter(p -> p.getId_proprietate() == idProp)
                        .findFirst()
                        .orElse(null);

                if (proprietate == null) return null;

                Rezervare rezervare = new Rezervare(idRez, proprietate, dataStart, dataEnd, status);
                rezervare.setCostTotal(cost);
                return rezervare;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Rezervare> readAll(List<Proprietate> proprietati) {
        List<Rezervare> list = new ArrayList<>();
        String sql = "SELECT * FROM Rezervare";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int idRez = rs.getInt("id_rezervare");
                int idProp = rs.getInt("id_proprietate");
                LocalDate dataStart = rs.getDate("dataStart").toLocalDate();
                LocalDate dataEnd = rs.getDate("dataEnd").toLocalDate();
                double cost = rs.getDouble("costTotal");
                Status status = Status.valueOf(rs.getString("status"));

                Proprietate proprietate = proprietati.stream()
                        .filter(p -> p.getId_proprietate() == idProp)
                        .findFirst()
                        .orElse(null);

                if (proprietate == null) continue;

                Rezervare rezervare = new Rezervare(idRez, proprietate, dataStart, dataEnd, status);
                rezervare.setCostTotal(cost);
                list.add(rezervare);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Rezervare> readAllByProprietate(int idProprietate, List<Proprietate> proprietati) {
        List<Rezervare> rezervari = new ArrayList<>();
        String sql = "SELECT * FROM Rezervare WHERE id_proprietate = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProprietate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idRez = rs.getInt("id_rezervare");
                LocalDate dataStart = rs.getDate("dataStart").toLocalDate();
                LocalDate dataEnd = rs.getDate("dataEnd").toLocalDate();
                double cost = rs.getDouble("costTotal");
                Status status = Status.valueOf(rs.getString("status"));

                Proprietate proprietate = proprietati.stream()
                        .filter(p -> p.getId_proprietate() == idProprietate)
                        .findFirst()
                        .orElse(null);

                if (proprietate == null) continue;

                Rezervare rezervare = new Rezervare(idRez, proprietate, dataStart, dataEnd, status);
                rezervare.setCostTotal(cost);
                rezervari.add(rezervare);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rezervari;
    }



    public void update(Rezervare rezervare) {
        String sql = "UPDATE Rezervare SET id_proprietate = ?, dataStart = ?, dataEnd = ?, costTotal = ?, status = ? WHERE id_rezervare = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, rezervare.getProprietate().getId_proprietate());
            stmt.setDate(2, Date.valueOf(rezervare.getDataStart()));
            stmt.setDate(3, Date.valueOf(rezervare.getDataEnd()));
            stmt.setDouble(4, rezervare.getCostTotal());
            stmt.setString(5, rezervare.getStatus().name());
            stmt.setInt(6, rezervare.getId_rezervare());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(int id) {
        String sql = "DELETE FROM Rezervare WHERE id_rezervare = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existaRezervareSuprapusa(int idProprietate, LocalDate dataStart, LocalDate dataEnd) {
        String sql = "SELECT COUNT(*) FROM rezervare WHERE id_proprietate = ? AND NOT (dataEnd <= ? OR dataStart >= ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idProprietate);
            ps.setDate(2, java.sql.Date.valueOf(dataStart));
            ps.setDate(3, java.sql.Date.valueOf(dataEnd));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
