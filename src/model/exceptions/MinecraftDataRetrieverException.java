package model.exceptions;

public class MinecraftDataRetrieverException extends Exception {
    private String exceptionDetails;

    public MinecraftDataRetrieverException(String exceptionDetails) {
        this.exceptionDetails = exceptionDetails;
    }

    public String getMessage() {
        return "Erreur lors de la récupération du Skin des serveurs de Mojang :\n" + exceptionDetails;
    }
}
