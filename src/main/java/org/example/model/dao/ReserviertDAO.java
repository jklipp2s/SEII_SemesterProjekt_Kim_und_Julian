package org.example.model.dao;

import org.example.process.control.exceptions.DatabaseException;
import org.example.model.objects.Auto;
import org.example.model.objects.Kunde;
import org.example.model.objects.Reservierung;
import org.example.DatabaseConnection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//

public class ReserviertDAO extends AbstractDAO {
    private static ReserviertDAO reserviertDAO;
    private String table = "reserviert";

    public static synchronized ReserviertDAO getInstance() {

        if (reserviertDAO == null)
            reserviertDAO = new ReserviertDAO();
        return reserviertDAO;
    }


    public void registerAutoReservierung(Auto auto, Kunde kunde, LocalDate besichtigungsdatum) throws DatabaseException {

        DatabaseConnection.getInstance().openConnection();
        String sqlBefehl = "INSERT INTO " + table + " VALUES(?,?,default,?)";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);

        try {

            statement.setString(1, kunde.getEmail());
            statement.setString(2, auto.getFahrzeugIdentifikationsNummer());
            statement.setDate(3, Date.valueOf(besichtigungsdatum));


            statement.executeUpdate();


        } catch (SQLException ex) {
            Logger.getLogger(ReserviertDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DatabaseConnection.getInstance().closeConnection();
        }

    }


    public List<Reservierung> fetchReservierungen(Kunde kunde) throws DatabaseException {
        DatabaseConnection.getInstance().openConnection();
        List<Reservierung> resultList = new ArrayList<>();
        ResultSet resultSet = null;

        String sqlBefehl = "SELECT * FROM " + table + " WHERE kunde = ?;";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);


        try {
            statement.setString(1, kunde.getEmail());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String fin = resultSet.getString(2);

                System.out.println(fin);

                Reservierung reservierung = new Reservierung();
                reservierung.setMarke(AutoDAO.getInstance().fetchAuto(fin).getMarke());
                reservierung.setFahrzeug(fin);
                reservierung.setReg_nr(resultSet.getInt(3));
                reservierung.setBesichtigungsDatum(resultSet.getDate(4).toLocalDate());


                resultList.add(reservierung);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DatabaseConnection.getInstance().closeConnection();
        }
        return resultList;
    }

    public Date getLatestTermin(String email, String fin) throws DatabaseException {
        DatabaseConnection.getInstance().openConnection();
        ResultSet resultSet = null;
        String sqlBefehl = "SELECT max(besichtigungsdatum) FROM " + table + " WHERE kunde = ? " +
                "AND fin = ?";
        PreparedStatement statement = getPreparedStatement(sqlBefehl);
        Date result = null;

        try {
            statement.setString(1, email);
            statement.setString(2, fin);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                result = resultSet.getDate(1);
            }


        } catch (SQLException ex) {
            Logger.getLogger(RegistrierterBenutzerDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(sqlBefehl);
        } finally {
            DatabaseConnection.getInstance().closeConnection();
        }

        return result;
    }




}
