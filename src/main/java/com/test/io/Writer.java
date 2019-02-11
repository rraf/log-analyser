package com.test.io;

import com.test.model.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * I'm conscious that I'm not doing any error handling in the interest of time.
 * Should use resource try-catch or if classic then catch exceptions and log and rethrow.
 *
 */
public class Writer {
    Logger LOGGER = LoggerFactory.getLogger(Writer.class);
    private final Connection c;
    public Writer(String databaseName) throws SQLException {
        LOGGER.info("Database name: {}", databaseName);
        c = DriverManager.getConnection("jdbc:hsqldb:file:" + databaseName);
        createTableIfNeeded(c);
    }

    public void write(Entry entry) throws SQLException {

        PreparedStatement statement = c.prepareStatement("Insert into log VALUES(?, ?, ?, ?, ?)");
        statement.setString(1, entry.getId());
        statement.setLong(2, entry.getDuration());
        statement.setString(3, entry.getLogType());
        statement.setString(4, entry.getHost());
        statement.setBoolean(5, entry.alert());
        statement.execute();
    }

    private void createTableIfNeeded(Connection c) throws SQLException {
        c.createStatement().execute("CREATE TABLE IF NOT EXISTS log (" +
                "id varchar(32), duration INTEGER, type varchar(64), hostname varchar(256), alert boolean)");
    }

    public void close() throws SQLException {
        c.close();
    }
}
