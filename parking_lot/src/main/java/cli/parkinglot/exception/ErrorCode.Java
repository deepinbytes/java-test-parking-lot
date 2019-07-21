package cli.parkinglot.exception;


public enum ErrorCode {
    PARKING_EXISTS("Parking already exists!"),
    PARKING_NOT_EXISTS("Parking does not Exist"),
    INVALID_VALUE("Invalid value"),
    INVALID_FILE("Invalid file"),
    INVALID_CMD("Invalid command"),
    FATAL_ERROR("Fatal Error ");

    private String message = "";

    ErrorCode(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}