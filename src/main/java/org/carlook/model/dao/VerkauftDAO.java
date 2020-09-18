package org.carlook.model.dao;

import org.carlook.process.control.exception.DatabaseException;
import org.carlook.model.objects.dto.Auto;
import org.carlook.model.objects.dto.Kunde;
import org.carlook.model.objects.dto.User;
import org.carlook.model.objects.dto.Vertriebler;
import org.carlook.services.db.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Klasse für ggf. spätere Erweiterung
 */

public class VerkauftDAO extends AbstractDAO {
private static VerkauftDAO verkauftDAO;
private String table = "verkauft";

    public static synchronized VerkauftDAO getInstance() {

        if(verkauftDAO == null)
            verkauftDAO = new VerkauftDAO();
        return verkauftDAO;
    }



    public void registerAutoVerkauf(Auto auto, Vertriebler vertriebler, Kunde kunde) throws DatabaseException {

        DatabaseConnection.getInstance().openConnection();
        String sqlBefehl = "INSERT INTO " + table + " VALUES(?,?,?,default)";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);

        try {

            statement.setString(1, kunde.getEmail());
            statement.setString(2, auto.getFahrzeugIdentifikationsNummer());
            statement.setString(3, vertriebler.getEmail());


            statement.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(VerkauftDAO.class.getName()).log(Level.SEVERE, null, ex);
        }


        finally {
            DatabaseConnection.getInstance().closeConnection();
        }

    }




    public List<Auto> fetchVerkaufteAutos(User user) throws DatabaseException {
        DatabaseConnection.getInstance().openConnection();
        List<Auto> resultList = new ArrayList<>();
        ResultSet resultSet = null;

        String sqlBefehl = "SELECT  auto.fin,  auto.marke, auto.baujahr," +
                " auto.beschreibung, auto.ps, auto.verbrauch, auto.preis," +
                " auto.verfuegbar " +
                " FROM kunde, verkauft, auto\n" +
                "WHERE verkauft.fin = auto.fin\n" +
                "AND vertriebler.email = ?;";

        PreparedStatement statement = getPreparedStatement(sqlBefehl);


        try {
            statement.setString(1, user.getEmail());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Auto auto = new Auto();
                auto.setFahrzeugIdentifikationsNummer(resultSet.getString(1));
                auto.setMarke(resultSet.getString(2));
                auto.setBaujahr(resultSet.getInt(3));
                auto.setBeschreibung(resultSet.getString(4));
                auto.setPs(resultSet.getInt(5));
                auto.setVerbrauch(resultSet.getDouble(6));
                auto.setVerkaufsPreis(resultSet.getDouble(7));
                auto.setVerfuegbar(resultSet.getBoolean(8));

                resultList.add(auto);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DatabaseConnection.getInstance().closeConnection();
        }
        return resultList;

    }



}
