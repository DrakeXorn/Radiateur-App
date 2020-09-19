package dataAccess;

import model.exceptions.CredentialsNotSetException;
import model.exceptions.CryptoException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    public static Connection getInstance() throws SQLException, CredentialsNotSetException, ParserConfigurationException, CryptoException, SAXException, IOException {
        if (connection == null) {
            ICredentialsAccess credentialsAccessor = new CredentialsXMLAccess();

            connection = DriverManager.getConnection("jdbc:mariadb://213.32.6.39:3306/s8_minecraft?passwordCharacterEncoding&autoReconnect=true", credentialsAccessor.getUsername(), String.copyValueOf(credentialsAccessor.getPassword()));
        }

        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
