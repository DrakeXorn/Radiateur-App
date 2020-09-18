package business;

import dataAccess.CredentialsXMLAccess;
import dataAccess.ICredentialsAccess;
import model.exceptions.FileException;

public class CredentialsManager {
    private ICredentialsAccess credentialsAccessor = new CredentialsXMLAccess();

    public CredentialsManager() {}

    public void setCredentials(String username, char[] password) throws FileException {
        credentialsAccessor.setCredentials(username, password);
    }
}
