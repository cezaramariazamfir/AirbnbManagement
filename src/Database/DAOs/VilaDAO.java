package Database.DAOs;

import Models.Vila;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VilaDAO implements BasicDAO<Vila> {

    private final Connection connection;

    public VilaDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Vila v) {
        try {
            String sql1 = "INSERT INTO Proprietate (id_proprietar, denumire, locatie, capacitate, disponibilabilitate, pretPeNoapte) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, v.getId_proprietar());
            ps1.setString(2, v.getDenumire());
            ps1.setString(3, v.getLocatie());
            ps1.setInt(4, v.getCapacitate());
            ps1.setBoolean(5, v.isDisponibilabilitate());
            ps1.setDouble(6, v.getPretPeNoapte());
            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("id: " + id);
                String sql2 = "INSERT INTO Vila (id_proprietate, suprafataGradina, arePiscina) VALUES (?, ?, ?)";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.setInt(1, id);
                ps2.setDouble(2, v.getSuprafataGradina());
                ps2.setBoolean(3, v.isArePiscina());
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vila read(int id) {
        try {
            String sql = """
                SELECT * FROM Proprietate p
                JOIN Vila v ON p.id_proprietate = v.id_proprietate
                WHERE p.id_proprietate = ?
            """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Vila(
                        rs.getInt("id_proprietate"),
                        rs.getInt("id_proprietar"),
                        rs.getString("denumire"),
                        rs.getString("locatie"),
                        rs.getInt("capacitate"),
                        rs.getBoolean("disponibilabilitate"),
                        rs.getDouble("pretPeNoapte"),
                        rs.getDouble("suprafataGradina"),
                        rs.getBoolean("arePiscina")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Vila> readAll() {
        List<Vila> list = new ArrayList<>();
        try {
            String sql = """
                SELECT * FROM Proprietate p
                JOIN Vila v ON p.id_proprietate = v.id_proprietate
            """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Vila(
                        rs.getInt("id_proprietate"),
                        rs.getInt("id_proprietar"),
                        rs.getString("denumire"),
                        rs.getString("locatie"),
                        rs.getInt("capacitate"),
                        rs.getBoolean("disponibilabilitate"),
                        rs.getDouble("pretPeNoapte"),
                        rs.getDouble("suprafataGradina"),
                        rs.getBoolean("arePiscina")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Vila> readAllByUser(int userId) {
        List<Vila> list = new ArrayList<>();
        try {
            String sql = """
            SELECT * FROM Proprietate p
            JOIN Vila v ON p.id_proprietate = v.id_proprietate
            WHERE p.id_proprietar = ?
        """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Vila(
                        rs.getInt("id_proprietate"),
                        rs.getInt("id_proprietar"),
                        rs.getString("denumire"),
                        rs.getString("locatie"),
                        rs.getInt("capacitate"),
                        rs.getBoolean("disponibilabilitate"),
                        rs.getDouble("pretPeNoapte"),
                        rs.getDouble("suprafataGradina"),
                        rs.getBoolean("arePiscina")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    @Override
    public void update(Vila v) {
        try {
            String sql1 = """
                UPDATE Proprietate SET 
                id_proprietar = ?, denumire = ?, locatie = ?, capacitate = ?, disponibilabilitate = ?, pretPeNoapte = ?
                WHERE id_proprietate = ?
            """;
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setInt(1, v.getId_proprietar());
            ps1.setString(2, v.getDenumire());
            ps1.setString(3, v.getLocatie());
            ps1.setInt(4, v.getCapacitate());
            ps1.setBoolean(5, v.isDisponibilabilitate());
            ps1.setDouble(6, v.getPretPeNoapte());
            ps1.setInt(7, v.getId_proprietate());
            ps1.executeUpdate();

            String sql2 = "UPDATE Vila SET suprafataGradina = ?, arePiscina = ? WHERE id_proprietate = ?";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setDouble(1, v.getSuprafataGradina());
            ps2.setBoolean(2, v.isArePiscina());
            ps2.setInt(3, v.getId_proprietate());
            ps2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM Vila WHERE id_proprietate = ?");
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
