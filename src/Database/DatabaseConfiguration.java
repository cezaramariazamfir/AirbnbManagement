package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConfiguration {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/proiect_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Cezara2003!";
    private static Connection connection;


    private DatabaseConfiguration() {
    }

    public static Connection getDatabaseConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la conectarea la baza de date: " + e.getMessage(), e);
        }

        return connection;
    }
    //TODO STERGE CODUL COMENTAT
//    public static void testConnection() {
//        try (Connection conn = getDatabaseConnection();
//             Statement stmt = conn.createStatement()) {
//            // Executăm o interogare simplă pentru a verifica conexiunea
//            ResultSet rs = stmt.executeQuery("SELECT 1");
//
//            if (rs.next()) {
//                System.out.println("Conexiunea la baza de date a fost realizată cu succes!");
//            } else {
//                System.out.println("Eroare la realizarea conexiunii!");
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Conexiunea a eșuat: " + e.getMessage());
//        }
//    }

    public static void closeDatabaseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Eroare la închiderea conexiunii: " + e.getMessage());
        }
    }

//    public static void main(String[] args) {
//        testConnection(); // Testează conexiunea la BD
//    }
}
