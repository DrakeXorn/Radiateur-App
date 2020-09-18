package controller;

import business.CredentialsManager;
import model.exceptions.FileException;

public class CredentialsController {
    private CredentialsManager credentialsManager;

    public CredentialsController() {
        credentialsManager = new CredentialsManager();
    }

    public void setCredentials(String username, char[] password) throws FileException {
        credentialsManager.setCredentials(username, password);
    }
}
