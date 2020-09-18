package dataAccess;

import model.exceptions.CredentialsNotSetException;
import model.exceptions.CryptoException;
import model.exceptions.FileException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface ICredentialsAccess {
    String getUsername() throws CredentialsNotSetException, ParserConfigurationException, CryptoException, SAXException, IOException;
    char[] getPassword() throws CredentialsNotSetException, ParserConfigurationException, CryptoException, SAXException, IOException;
    void setCredentials(String username, char[] password) throws FileException;
}
