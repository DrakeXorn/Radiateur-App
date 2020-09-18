package model.exceptions;

public class GetAllDataException extends Exception {
    private String dataGotten;
    private String exceptionReason;

    public GetAllDataException(String dataGotten, String exceptionReason) {
        this.dataGotten = dataGotten;
        this.exceptionReason = exceptionReason;
    }

    public String getMessage() {
        return "Erreur lors de la récupération des " + dataGotten + ":\n\t" + exceptionReason;
    }
}
