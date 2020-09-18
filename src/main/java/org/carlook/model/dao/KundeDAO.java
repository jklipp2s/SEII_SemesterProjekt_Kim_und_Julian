package org.carlook.model.dao;

import org.carlook.process.control.exception.DatabaseException;
import org.carlook.model.objects.dto.Kunde;
import org.carlook.services.db.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class KundeDAO extends RegistrierterBenutzerDAO {

    private static KundeDAO kundeDAO;
    private String table = "kunde";

    public static synchronized KundeDAO getInstance() {

        if (kundeDAO == null)
            kundeDAO = new KundeDAO();
        return kundeDAO;
    }


    public void registerKunde(Kunde kunde) throws DatabaseException {
        String sqlBefehl = "INSERT INTO " + table + " VALUES(?)";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);

        try {
            registerUser(kunde);
            statement.setString(1, kunde.getEmail());
            statement.executeUpdate();


        } catch (
                SQLException ex) {
            Logger.getLogger(KundeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        finally {
            DatabaseConnection.getInstance().closeConnection();
        }
    }





    public Kunde fetchKunde(String email) throws  DatabaseException {
        DatabaseConnection.getInstance().openConnection();
        ResultSet resultSet = null;
        String sqlBefehl = "SELECT * FROM " + table + " WHERE email = ?;";

        Kunde kunde = new Kunde();

        try {

            resultSet = RegistrierterBenutzerDAO.getInstance().fetchUser(email);


            if (resultSet.next()) {
             setUserData(kunde, resultSet);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VertrieblerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return kunde;
    }



}
