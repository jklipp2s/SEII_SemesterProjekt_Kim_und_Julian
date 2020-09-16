package org.example.model.dao;

import org.example.process.control.exceptions.DatabaseException;
import org.example.model.objects.Auto;
import org.example.model.objects.Vertriebler;
import org.example.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutoDAO extends AbstractDAO {
    private static AutoDAO autoDAO;
    private String table = "auto";

    public static synchronized AutoDAO getInstance() {

        if (autoDAO == null)
            autoDAO = new AutoDAO();
        return autoDAO;
    }


    public void registerAuto(Auto auto, Vertriebler vertriebler) throws DatabaseException {

        DatabaseConnection.getInstance().openConnection();
        String sqlBefehl = "INSERT INTO " + table + " VALUES(?,?,?,?,?,?,?,?,default)";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);

        try {

            statement.setString(1, auto.getFahrzeugIdentifikationsNummer());
            statement.setString(2, auto.getMarke());
            statement.setInt(3, auto.getBaujahr());
            statement.setString(4, auto.getBeschreibung());
            statement.setString(5, vertriebler.getEmail());
            statement.setInt(6, auto.getPs());
            statement.setDouble(7, auto.getVerbrauch());
            statement.setDouble(8, auto.getVerkaufsPreis());


            statement.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(AutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DatabaseConnection.getInstance().closeConnection();
        }


    }


    public List<Auto> fetchAutos(Vertriebler vertriebler) throws DatabaseException {
        DatabaseConnection.getInstance().openConnection();
        List<Auto> resultList = new ArrayList<>();
        ResultSet resultSet = null;

        String sqlBefehl = "SELECT * FROM " + table + " WHERE vertriebler = ?;";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);


        try {
            statement.setString(1, vertriebler.getEmail());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                resultList.add(setAuto(resultSet));
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DatabaseConnection.getInstance().closeConnection();
        }
        return resultList;
    }



    public Auto fetchAuto(String fin) throws  DatabaseException {
        DatabaseConnection.getInstance().openConnection();
        ResultSet resultSet = null;
        String sqlBefehl = "SELECT * FROM " + table + " WHERE fin = ?;";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);

        Auto auto = null;

        try {
            statement.setString(1,fin);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
             auto = setAuto(resultSet);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        finally {
            DatabaseConnection.getInstance().closeConnection();
        }

        return auto;
    }





    public boolean autoIsRegistered(String fin) throws  DatabaseException {
        DatabaseConnection.getInstance().openConnection();
        ResultSet resultSet = null;
        String sqlBefehl = "SELECT * FROM " + table + " WHERE fin = ?;";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);
        boolean result = false;

        try {
            statement.setString(1,fin);
            resultSet = statement.executeQuery();

            result = resultSet.next();


        } catch (SQLException ex) {
            Logger.getLogger(AutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        finally {
            DatabaseConnection.getInstance().closeConnection();
        }

        return result;
    }



    /**
     * Suchmethode für die ON-THE-FLY-Suche
     * @param marke
     * @param preis
     * @param baujahr
     * @param ps
     * @param eigenschaften
     * @return
     * @throws DatabaseException
     */

    public List<Auto> searchCars(String marke, double preis, int baujahr, double ps, String eigenschaften) throws DatabaseException {
        ResultSet resultSet = null;
        Statement statement = getStatement();
        List<Auto> autoList = new ArrayList<>();




        String markeSearch = " marke ILIKE '%" + marke + "%'\n";
        String preisSearch = " preis <=" + preis + "";
        String baujahrSearch = " baujahr >= " + baujahr + "";
        String psSearch = " ps <= " + ps + "";
        String eigenschaftenSearch = " beschreibung ILIKE '%" + eigenschaften + "%'\n";


        String sqlBefehl = marke.isEmpty() && preis == 0 && baujahr == 0 && ps == 0 && eigenschaften.isEmpty() ? "SELECT * FROM " + table : "SELECT * FROM " + table + " s where ";

        if (!marke.isEmpty()) sqlBefehl +=  markeSearch;
        if (preis != 0)
            sqlBefehl +=  marke.isEmpty() ? preisSearch : " and " + preisSearch;
        if (baujahr != 0)
            sqlBefehl += marke.isEmpty() && preis == 0 ? baujahrSearch : " and " + baujahrSearch;
        if (ps != 0) sqlBefehl += marke.isEmpty() && preis == 0 && baujahr == 0 ? psSearch : " and " + psSearch;
        if (!eigenschaften.isEmpty()) sqlBefehl += marke.isEmpty() && preis == 0 && baujahr == 0 && ps == 0 ? eigenschaftenSearch : " and " + eigenschaftenSearch;
        sqlBefehl += ";";
        try {

            resultSet = statement.executeQuery(sqlBefehl);

            while (resultSet.next()) {
                autoList.add(setAuto(resultSet));
            }


        }
        catch (SQLException e) {
            Logger.getLogger(AutoDAO.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(sqlBefehl);

        } finally {
            DatabaseConnection.getInstance().closeConnection();

        }

        return autoList;
    }




    // Helpermethode
    private Auto setAuto(ResultSet resultSet) throws SQLException {
        Auto auto = new Auto();
        auto.setFahrzeugIdentifikationsNummer(resultSet.getString(1));
        auto.setMarke(resultSet.getString(2));
        auto.setBaujahr(resultSet.getInt(3));
        auto.setBeschreibung(resultSet.getString(4));
        auto.setPs(resultSet.getInt(6));
        auto.setVerbrauch(resultSet.getDouble(7));
        auto.setVerkaufsPreis(resultSet.getDouble(8));
        auto.setVerfuegbar(resultSet.getBoolean(9));
    return auto;
    }










}
