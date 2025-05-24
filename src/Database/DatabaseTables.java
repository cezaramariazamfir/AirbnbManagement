package Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTables {
    public void createTables() {
        String[] SQLCommand = {
                """
            CREATE TABLE IF NOT EXISTS Proprietar (
                id_proprietar INT PRIMARY KEY AUTO_INCREMENT,
                nume VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL,
                parola VARCHAR(255) NOT NULL
            )
            """,
                """
           CREATE TABLE IF NOT EXISTS Proprietate (
               id_proprietate INT PRIMARY KEY AUTO_INCREMENT,
               id_proprietar INT NOT NULL,
               denumire VARCHAR(255) NOT NULL,
               locatie VARCHAR(255) NOT NULL,
               capacitate INT NOT NULL,
               disponibilabilitate BOOLEAN NOT NULL,
               pretPeNoapte DOUBLE NOT NULL,
               FOREIGN KEY (id_proprietar) REFERENCES Proprietar(id_proprietar)
           )
            """,
                """
            CREATE TABLE IF NOT EXISTS Apartament (
                id_proprietate INT PRIMARY KEY,
                etaj INT NOT NULL,
                areBalcon BOOLEAN NOT NULL,
                FOREIGN KEY (id_proprietate) REFERENCES Proprietate(id_proprietate)
            )
            """,
                """
            CREATE TABLE IF NOT EXISTS Vila (
                id_proprietate INT PRIMARY KEY,
                suprafataGradina DOUBLE NOT NULL,
                arePiscina BOOLEAN NOT NULL,
                FOREIGN KEY (id_proprietate) REFERENCES Proprietate(id_proprietate)
            )
            """,
                """
            CREATE TABLE IF NOT EXISTS Camper (
                id_proprietate INT PRIMARY KEY,
                lungime DOUBLE NOT NULL,
                autonomieApa INT NOT NULL,
                FOREIGN KEY (id_proprietate) REFERENCES Proprietate(id_proprietate)
            )
            """,
                """
            CREATE TABLE IF NOT EXISTS Rezervare (
                id_rezervare INT PRIMARY KEY AUTO_INCREMENT,
                id_proprietate INT,
                dataStart DATE,
                dataEnd DATE,
                costTotal DOUBLE,
                status ENUM('IN_ASTEPTARE', 'CONFIRMATA', 'ACHITATA'),
                FOREIGN KEY (id_proprietate) REFERENCES Proprietate(id_proprietate)
            )
            """
        };

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        if (connection != null) {
            try (Statement statement = connection.createStatement()) {
                for (String createTableSql : SQLCommand) {
                    statement.execute(createTableSql);
                }
                System.out.println("Tabelele au fost create sau exista deja.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Conexiunea la baza de date a esuat.");
        }
    }
}
