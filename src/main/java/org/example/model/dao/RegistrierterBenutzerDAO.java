package org.example.model.dao;

import org.example.process.control.exceptions.DatabaseException;
import org.example.model.objects.User;
import org.example.DatabaseConnection;

import java.sql.*;
import java.util.Date;
import java.util.logging.*;


public class RegistrierterBenutzerDAO extends AbstractDAO {
    private static RegistrierterBenutzerDAO registrierterBenutzerDao;
    private String table = "registrierter_benutzer";

    public static synchronized RegistrierterBenutzerDAO getInstance() {

        if (registrierterBenutzerDao == null)
            registrierterBenutzerDao = new RegistrierterBenutzerDAO();
        return registrierterBenutzerDao;
    }


    protected void registerUser(User user) throws DatabaseException {

        DatabaseConnection.getInstance().openConnection();
        String sqlBefehl = "INSERT INTO " + table + " VALUES(?,?,?,?,default,null)";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);

        try {

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getVorname());
            statement.setString(3, user.getNachname());
            statement.setString(4, user.getPasswort());

            statement.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(RegistrierterBenutzerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected ResultSet fetchUser(String email) throws DatabaseException {
        DatabaseConnection.getInstance().openConnection();
        ResultSet resultSet = null;
        String sqlBefehl = "SELECT * FROM " + table + " WHERE email = ?;";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);


        try {
            statement.setString(1, email);
            resultSet = statement.executeQuery();


        } catch (SQLException ex) {
            Logger.getLogger(RegistrierterBenutzerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultSet;
    }


    public Date fetchDate(String email) throws DatabaseException {
        DatabaseConnection.getInstance().openConnection();
        ResultSet resultSet = null;
        String sqlBefehl = "SELECT registrationdate FROM " + table + " WHERE email = ?;";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);

        Date date = null;

        try {
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next())
                date = resultSet.getDate(1);


        } catch (SQLException ex) {
            Logger.getLogger(RegistrierterBenutzerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return date;
    }


    public boolean emailIsAvailable(String email) throws DatabaseException {
        DatabaseConnection.getInstance().openConnection();
        ResultSet resultSet = null;
        String sqlBefehl = "SELECT * FROM " + table + " WHERE email = ?;";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);
        boolean result = false;

        try {
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            result = !resultSet.next();


        } catch (SQLException ex) {
            Logger.getLogger(RegistrierterBenutzerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DatabaseConnection.getInstance().closeConnection();
        }

        return result;
    }


    public boolean authenticate(String email, String password) throws DatabaseException {
        DatabaseConnection.getInstance().openConnection();
        ResultSet resultSet = null;
        String sqlBefehl = "SELECT email FROM " + table + " WHERE email ILIKE ? AND passwort = ?;";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);
        boolean result = false;

        try {
            statement.setString(1, email);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            result = resultSet.next();


        } catch (SQLException ex) {
            Logger.getLogger(RegistrierterBenutzerDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DatabaseConnection.getInstance().closeConnection();
        }

        return result;
    }


    public void updateAnrede(User user, String anrede) throws DatabaseException {

        DatabaseConnection.getInstance().openConnection();
        String sqlBefehl = "UPDATE registrierter_benutzer" +
                " SET anrede = ? " +
                " WHERE email = ? ;";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);

        try {

            statement.setString(1, anrede);
            statement.setString(2, user.getEmail());

            statement.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(RegistrierterBenutzerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void updatePrename(User user, String prename) throws DatabaseException {

        DatabaseConnection.getInstance().openConnection();
        String sqlBefehl = "UPDATE registrierter_benutzer" +
                " SET vorname = ? " +
                " WHERE email = ? ;";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);

        try {

            statement.setString(1, prename);
            statement.setString(2, user.getEmail());

            statement.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(RegistrierterBenutzerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void updateName(User user, String name) throws DatabaseException {

        DatabaseConnection.getInstance().openConnection();
        String sqlBefehl = "UPDATE registrierter_benutzer" +
                " SET nachname = ? " +
                " WHERE email = ? ;";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);

        try {

            statement.setString(1, name);
            statement.setString(2, user.getEmail());

            statement.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(RegistrierterBenutzerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    protected void setUserData(User user, ResultSet resultSet) throws SQLException {
        user.setEmail(resultSet.getString(1));
        user.setVorname(resultSet.getString(2));
        user.setNachname(resultSet.getString(3));
        user.setAnrede(resultSet.getString(6));
    }


}
