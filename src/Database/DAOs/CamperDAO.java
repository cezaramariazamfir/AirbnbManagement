package Database.DAOs;

import Models.Camper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CamperDAO implements BasicDAO<Camper> {
    private static CamperDAO instance;
    private final Connection connection;

    private CamperDAO(Connection connection) {
        this.connection = connection;
    }

    public static CamperDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new CamperDAO(connection);
        }
        return instance;
    }

    @Override
    public void create(Camper c) {
        try {
            String sql1 = "INSERT INTO Proprietate (id_proprietar, denumire, locatie, capacitate, disponibilabilitate, pretPeNoapte) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, c.getId_proprietar());
            ps1.setString(2, c.getDenumire());
            ps1.setString(3, c.getLocatie());
            ps1.setInt(4, c.getCapacitate());
            ps1.setBoolean(5, c.isDisponibilabilitate());
            ps1.setDouble(6, c.getPretPeNoapte());
            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                String sql2 = "INSERT INTO Camper (id_proprietate, lungime, autonomieApa) VALUES (?, ?, ?)";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.setInt(1, id);
                ps2.setDouble(2, c.getLungime());
                ps2.setInt(3, c.getAutonomieApa());
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Camper read(int id) {
        try {
            String sql = """
                SELECT * FROM Proprietate p
                JOIN Camper c ON p.id_proprietate = c.id_proprietate
                WHERE p.id_proprietate = ?
            """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Camper(
                        rs.getInt("id_proprietate"),
                        rs.getInt("id_proprietar"),
                        rs.getString("denumire"),
                        rs.getString("locatie"),
                        rs.getInt("capacitate"),
                        rs.getBoolean("disponibilabilitate"),
                        rs.getDouble("pretPeNoapte"),
                        rs.getDouble("lungime"),
                        rs.getInt("autonomieApa")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Camper> readAll() {
        List<Camper> list = new ArrayList<>();
        try {
            String sql = """
                SELECT * FROM Proprietate p
                JOIN Camper c ON p.id_proprietate = c.id_proprietate
            """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Camper(
                        rs.getInt("id_proprietate"),
                        rs.getInt("id_proprietar"),
                        rs.getString("denumire"),
                        rs.getString("locatie"),
                        rs.getInt("capacitate"),
                        rs.getBoolean("disponibilabilitate"),
                        rs.getDouble("pretPeNoapte"),
                        rs.getDouble("lungime"),
                        rs.getInt("autonomieApa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Camper> readAllByUser(int userId) {
        List<Camper> list = new ArrayList<>();
        try {
            String sql = """
            SELECT * FROM Proprietate p
            JOIN Camper c ON p.id_proprietate = c.id_proprietate
            WHERE p.id_proprietar = ?
        """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Camper(
                        rs.getInt("id_proprietate"),
                        rs.getInt("id_proprietar"),
                        rs.getString("denumire"),
                        rs.getString("locatie"),
                        rs.getInt("capacitate"),
                        rs.getBoolean("disponibilabilitate"),
                        rs.getDouble("pretPeNoapte"),
                        rs.getDouble("lungime"),
                        rs.getInt("autonomieApa")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public void update(Camper c) {
        try {
            String sql1 = """
                UPDATE Proprietate SET 
                id_proprietar = ?, denumire = ?, locatie = ?, capacitate = ?, disponibilabilitate = ?, pretPeNoapte = ?
                WHERE id_proprietate = ?
            """;
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setInt(1, c.getId_proprietar());
            ps1.setString(2, c.getDenumire());
            ps1.setString(3, c.getLocatie());
            ps1.setInt(4, c.getCapacitate());
            ps1.setBoolean(5, c.isDisponibilabilitate());
            ps1.setDouble(6, c.getPretPeNoapte());
            ps1.setInt(7, c.getId_proprietate());
            ps1.executeUpdate();

            String sql2 = "UPDATE Camper SET lungime = ?, autonomieApa = ? WHERE id_proprietate = ?";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setDouble(1, c.getLungime());
            ps2.setInt(2, c.getAutonomieApa());
            ps2.setInt(3, c.getId_proprietate());
            ps2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM Camper WHERE id_proprietate = ?");
            ps2.setInt(1, id);
            ps2.executeUpdate();

            PreparedStatement ps1 = connection.prepareStatement("DELETE FROM Proprietate WHERE id_proprietate = ?");
            ps1.setInt(1, id);
            ps1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
