package Database.DAOs;


import Models.Apartament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApartamentDAO implements BasicDAO<Apartament> {
    private static ApartamentDAO instance;
    private final Connection connection;
    private ApartamentDAO(Connection connection) {
        this.connection = connection;
    }

    public static ApartamentDAO getInstance(Connection connection) {
        if (instance == null) {
            instance = new ApartamentDAO(connection);
        }
        return instance;
    }

    @Override
    public void create(Apartament a) {
        try {
            System.out.println("id_proprietar: " + a.getId_proprietar());
            String sql1 = "INSERT INTO Proprietate (id_proprietar, denumire, locatie, capacitate, disponibilabilitate, pretPeNoapte) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, a.getId_proprietar());
            ps1.setString(2, a.getDenumire());
            ps1.setString(3, a.getLocatie());
            ps1.setInt(4, a.getCapacitate());
            ps1.setBoolean(5, a.isDisponibilabilitate());
            ps1.setDouble(6, a.getPretPeNoapte());
            System.out.println("ps1.executeUpdate()");
            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) {
                System.out.println("rs.next()");
                int id = rs.getInt(1);
                System.out.println("id: " + id);
                String sql2 = "INSERT INTO Apartament (id_proprietate, etaj, areBalcon) VALUES (?, ?, ?)";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.setInt(1, id);
                ps2.setInt(2, a.getEtaj());
                ps2.setBoolean(3, a.isAreBalcon());
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Apartament read(int id) {
        try {
            String sql = """
                        SELECT * FROM Proprietate p
                        JOIN Apartament a ON p.id_proprietate = a.id_proprietate
                        WHERE p.id_proprietate = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Apartament(
                        rs.getInt("id_proprietate"),
                        rs.getInt("id_proprietar"),
                        rs.getString("denumire"),
                        rs.getString("locatie"),
                        rs.getInt("capacitate"),
                        rs.getBoolean("disponibilabilitate"),
                        rs.getDouble("pretPeNoapte"),
                        rs.getInt("etaj"),
                        rs.getBoolean("areBalcon")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Apartament> readAll() {
        List<Apartament> list = new ArrayList<>();
        try {
            String sql = """
                        SELECT * FROM Proprietate p
                        JOIN Apartament a ON p.id_proprietate = a.id_proprietate
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Apartament(
                        rs.getInt("id_proprietate"),
                        rs.getInt("id_proprietar"),
                        rs.getString("denumire"),
                        rs.getString("locatie"),
                        rs.getInt("capacitate"),
                        rs.getBoolean("disponibilabilitate"),
                        rs.getDouble("pretPeNoapte"),
                        rs.getInt("etaj"),
                        rs.getBoolean("areBalcon")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Apartament> readAllByUser(int idUser) {
        List<Apartament> list = new ArrayList<>();
        try {
            String sql = """
                        SELECT * FROM Proprietate p
                        JOIN Apartament a ON p.id_proprietate = a.id_proprietate
                        WHERE p.id_proprietar = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Apartament(
                        rs.getInt("id_proprietate"),
                        rs.getInt("id_proprietar"),
                        rs.getString("denumire"),
                        rs.getString("locatie"),
                        rs.getInt("capacitate"),
                        rs.getBoolean("disponibilabilitate"),
                        rs.getDouble("pretPeNoapte"),
                        rs.getInt("etaj"),
                        rs.getBoolean("areBalcon")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Apartament a) {
        try {
            String sql1 = """
                        UPDATE Proprietate SET 
                        id_proprietar = ?, denumire = ?, locatie = ?, capacitate = ?, disponibilabilitate = ?, pretPeNoapte = ?
                        WHERE id_proprietate = ?
                    """;
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ps1.setInt(1, a.getId_proprietate());
            ps1.setString(2, a.getDenumire());
            ps1.setString(3, a.getLocatie());
            ps1.setInt(4, a.getCapacitate());
            ps1.setBoolean(5, a.isDisponibilabilitate());
            ps1.setDouble(6, a.getPretPeNoapte());
            ps1.setInt(7, a.getId_proprietar());
            ps1.executeUpdate();

            String sql2 = "UPDATE Apartament SET etaj = ?, areBalcon = ? WHERE id_proprietate = ?";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ps2.setInt(1, a.getEtaj());
            ps2.setBoolean(2, a.isAreBalcon());
            ps2.setInt(3, a.getId_proprietate());
            ps2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM Apartament WHERE id_proprietate = ?");
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
