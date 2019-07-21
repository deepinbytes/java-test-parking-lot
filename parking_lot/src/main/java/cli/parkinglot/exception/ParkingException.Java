package cli.parkinglot.exception;


public class ParkingException extends Exception
{
    private static final long serialVersionUID = 9069017828807189906L;

    /**
     * @param message
     */
    public ParkingException(String message)
    {
        super(message);
    }

    /**
     * @param message
     * @param throwable
     */
    public ParkingException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
