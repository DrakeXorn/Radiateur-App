package dataAccess;

import model.User;
import model.exceptions.CredentialsNotSetException;
import model.exceptions.GetAllDataException;
import model.exceptions.UpdateDataException;

import java.util.ArrayList;

public interface IPlayerAccess {
    ArrayList<User> getAllUsers() throws CredentialsNotSetException, GetAllDataException;
    void updateUser(User user) throws CredentialsNotSetException, UpdateDataException;
}
