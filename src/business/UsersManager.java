package business;

import dataAccess.IPlayerAccess;
import dataAccess.UsersDBAccess;
import model.User;
import model.exceptions.*;

import java.util.ArrayList;

public class UsersManager {
    private IPlayerAccess playerAccessor = new UsersDBAccess();

    public UsersManager() {}

    public ArrayList<User> getAllUsers() throws CredentialsNotSetException, GetAllDataException {
        return playerAccessor.getAllUsers();
    }

    public void updateUser(User user) throws UpdateDataException, CredentialsNotSetException {
        playerAccessor.updateUser(user);
    }

    public void addUser(String usernameToAdd) throws AddDataException, CredentialsNotSetException, MinecraftDataRetrieverException {
        playerAccessor.addUser(usernameToAdd);
    }
}
