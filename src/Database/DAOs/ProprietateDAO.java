package Database.DAOs;
import Models.Proprietate;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;



public class ProprietateDAO {

    private ApartamentDAO apartamentDAO;
    private VilaDAO vilaDAO;
    private CamperDAO camperDAO;

    public ProprietateDAO(Connection connection) {
        this.apartamentDAO = new ApartamentDAO(connection);
        this.vilaDAO = new VilaDAO(connection);
        this.camperDAO = new CamperDAO(connection);
    }

    public List<Proprietate> readAll() {
        List<Proprietate> toate = new ArrayList<>();
        toate.addAll(apartamentDAO.readAll());
        toate.addAll(vilaDAO.readAll());
        toate.addAll(camperDAO.readAll());
        return toate;
    }

    public List<Proprietate> readAllByUser(int idUser) {
        List<Proprietate> toate = new ArrayList<>();
        toate.addAll(apartamentDAO.readAllByUser(idUser));
        toate.addAll(vilaDAO.readAllByUser(idUser));
        toate.addAll(camperDAO.readAllByUser(idUser));
        return toate;
    }
}
