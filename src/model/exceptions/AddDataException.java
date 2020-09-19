package model.exceptions;

public class AddDataException extends Exception {
    private String dataAdded, message;

    public AddDataException(String dataAdded, String message) {
        this.dataAdded = dataAdded;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Erreur lors de l'inscription de " + dataAdded + " :\n" + message;
    }
}
