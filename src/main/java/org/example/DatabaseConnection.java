package org.example;

import org.example.process.control.exceptions.DatabaseException;
import org.example.util.AccessData;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {



    private static DatabaseConnection databaseConnection = null;
    private String database_url = "jdbc:postgresql://dumbo.inf.h-brs.de/jklipp2s";
    private Connection connection;

    private String failure_msg = "Fehler beim Zugriff auf die DB! VPN aktiv?";


    private DatabaseConnection() throws DatabaseException{
        this.initConnection();
    }


    public static DatabaseConnection getInstance() throws DatabaseException {

        if(databaseConnection == null)
            databaseConnection = new DatabaseConnection();

        return databaseConnection;
    }



    public void initConnection() throws DatabaseException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());

            this.openConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void openConnection() throws DatabaseException {
        try {

            Properties props = new Properties();
            props.setProperty("user", AccessData.DATABASE_USER);
            props.setProperty("password", AccessData.DATABASE_PASSWORD);

            this.connection = DriverManager.getConnection(this.database_url, props);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException(failure_msg);
        }
    }


    public Statement getStatement() throws DatabaseException {
        try {
            if(this.connection.isClosed()){
                this.openConnection();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            throw new DatabaseException(failure_msg);
        }
        try {
            return this.connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    public PreparedStatement getPreparedStatement(String sqlBefehl) throws DatabaseException{
        try {
            if(this.connection.isClosed()){
                this.openConnection();
            }
            return this.connection.prepareStatement(sqlBefehl);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    public void closeConnection(){
        try {
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
