package model.exceptions;

public class CredentialsNotSetException extends Exception {
    public CredentialsNotSetException() {}

    public String getMessage() {
        return "Vous devez d'abord définir vos nom d'utilisateur et mot de passe avant de tenter de vous connecter !";
    }
}
