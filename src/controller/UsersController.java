package controller;

import business.UsersManager;
import model.User;
import model.exceptions.CredentialsNotSetException;
import model.exceptions.GetAllDataException;
import model.exceptions.UpdateDataException;

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
}
