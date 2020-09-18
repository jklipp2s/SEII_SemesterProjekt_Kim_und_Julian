package org.carlook.model.dao;

import org.carlook.process.control.exception.DatabaseException;
import org.carlook.model.objects.dto.Vertriebler;
import org.carlook.services.db.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class VertrieblerDAO extends RegistrierterBenutzerDAO {

    private static VertrieblerDAO vertrieblerDAO;
    private String table = "vertriebler";

    public static synchronized VertrieblerDAO getInstance() {

        if (vertrieblerDAO == null)
            vertrieblerDAO = new VertrieblerDAO();
        return vertrieblerDAO;
    }


    public void registerVertriebler(Vertriebler vertriebler) throws DatabaseException {
        String sqlBefehl = "INSERT INTO " + table + " VALUES(?)";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);

        try {
            registerUser(vertriebler);
            statement.setString(1, vertriebler.getEmail());
            statement.executeUpdate();


        } catch (
                SQLException ex) {
            Logger.getLogger(VertrieblerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        finally {
            DatabaseConnection.getInstance().closeConnection();
        }

    }


    public Vertriebler fetchVertriebler(String email) throws  DatabaseException {
        DatabaseConnection.getInstance().openConnection();
        ResultSet resultSet = null;
        String sqlBefehl = "SELECT * FROM " + table + " WHERE email = ?;";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);

        Vertriebler vertriebler = new Vertriebler();

        try {

            resultSet = RegistrierterBenutzerDAO.getInstance().fetchUser(email);


            if (resultSet.next()) {
              setUserData(vertriebler, resultSet);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VertrieblerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return vertriebler;
    }


    public boolean isVertriebler(String email) throws  DatabaseException {
        DatabaseConnection.getInstance().openConnection();
        ResultSet resultSet = null;
        String sqlBefehl = "SELECT * FROM " + table + " WHERE email = ?;";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);
        boolean result = false;

        try {
            statement.setString(1,email);
            resultSet = statement.executeQuery();

            result = resultSet.next();


        } catch (SQLException ex) {
            Logger.getLogger(RegistrierterBenutzerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        finally {
            DatabaseConnection.getInstance().closeConnection();
        }

        return result;
    }










}
