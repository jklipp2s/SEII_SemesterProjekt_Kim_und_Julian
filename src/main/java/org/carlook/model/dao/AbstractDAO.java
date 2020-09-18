package org.carlook.model.dao;

import org.carlook.process.control.exception.DatabaseException;
import org.carlook.services.db.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO {

    protected Statement getStatement() {
        Statement statement = null;
        try {
            statement = DatabaseConnection.getInstance().getStatement();
        } catch (DatabaseException ex) {
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statement;
    }

    protected PreparedStatement getPreparedStatement(String sqlStatemen){
        PreparedStatement statement = null;
        try {
            statement = DatabaseConnection.getInstance().getPreparedStatement(sqlStatemen);
        } catch (
                DatabaseException ex) {
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statement;
    }
}
