package model.exceptions;

public class UpdateDataException extends Exception {
    private String dataUpdated;
    private String exceptionReason;

    public UpdateDataException(String dataUpdated, String exceptionReason) {
        this.dataUpdated = dataUpdated;
        this.exceptionReason = exceptionReason;
    }

    public String getMessage() {
        return "Erreur lors de la mise à jour de " + dataUpdated + ".\n\t" + exceptionReason;
    }
}
