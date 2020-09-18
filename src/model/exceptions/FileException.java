package model.exceptions;

public class FileException extends Exception {
    private String exceptionReason;
    private String openedFile;

    public FileException(String openedFile, String exceptionReason) {
        this.openedFile = openedFile;
        this.exceptionReason = exceptionReason;
    }

    public String getMessage() {
        return "Erreur lors de l'accès à/au " + openedFile + " : " + exceptionReason;
    }
}
