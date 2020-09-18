package business;

import dataAccess.IPlayerAccess;
import dataAccess.UsersDBAccess;
import model.User;
import model.exceptions.CredentialsNotSetException;
import model.exceptions.GetAllDataException;
import model.exceptions.UpdateDataException;

import java.util.ArrayList;

public class UsersManager {
    public IPlayerAccess playerAccessor = new UsersDBAccess();

    public UsersManager() {}

    public ArrayList<User> getAllUsers() throws CredentialsNotSetException, GetAllDataException {
        return playerAccessor.getAllUsers();
    }

    public void updateUser(User user) throws UpdateDataException, CredentialsNotSetException {
        this.playerAccessor.updateUser(user);
    }
}
