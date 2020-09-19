package controller;

import business.UsersManager;
import model.User;
import model.exceptions.*;

import java.util.ArrayList;

public class UsersController {
    private UsersManager manager = new UsersManager();

    public UsersController() {}

    public ArrayList<User> getAllUsers() throws CredentialsNotSetException, GetAllDataException {
        return manager.getAllUsers();
    }

    public void updateUser(User user) throws UpdateDataException, CredentialsNotSetException {
        manager.updateUser(user);
    }

    public void addUser(String usernameToAdd) throws AddDataException, CredentialsNotSetException, MinecraftDataRetrieverException {
        manager.addUser(usernameToAdd);
    }
}
