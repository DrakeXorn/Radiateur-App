package dataAccess;

import model.User;
import model.exceptions.*;

import java.util.ArrayList;

public interface IPlayerAccess {
    ArrayList<User> getAllUsers() throws CredentialsNotSetException, GetAllDataException;
    void updateUser(User user) throws CredentialsNotSetException, UpdateDataException;
    void addUser(String usernameToAdd) throws AddDataException, CredentialsNotSetException, MinecraftDataRetrieverException;
}
