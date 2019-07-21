package cli.parkinglot.handler;

import cli.parkinglot.constants.InputMapper;
import cli.parkinglot.exception.ParkingException;
import cli.parkinglot.service.AbstractService;



public interface AbstractHandler {

    void setService(AbstractService service);

    void execute(String action) throws ParkingException;

    default boolean validate(String inputString)
    {
        boolean result = true;
        try
        {
            String[] inputs = inputString.split(" ");
            int params = InputMapper.getCommands().get(inputs[0]);
            switch (inputs.length)
            {
                case 1:
                    if (params != 0)
                        result = false;
                    break;
                case 2:
                    if (params != 1)
                        result = false;
                    break;
                case 3:
                    if (params != 2)
                        result = false;
                    break;
                default:
                    result = false;
            }
        }
        catch (Exception e)
        {
            result = false;
        }
        return result;
    }

}