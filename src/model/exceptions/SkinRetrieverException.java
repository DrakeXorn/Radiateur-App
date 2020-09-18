package model.exceptions;

public class SkinRetrieverException extends Exception {
    private String exceptionDetails;

    public SkinRetrieverException(String exceptionDetails) {
        this.exceptionDetails = exceptionDetails;
    }

    public String getMessage() {
        return "Erreur lors de la récupération du Skin des serveurs de Mojang :\n" + exceptionDetails;
    }
}
