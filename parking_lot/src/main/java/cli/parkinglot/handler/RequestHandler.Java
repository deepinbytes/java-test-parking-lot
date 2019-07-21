package cli.parkinglot.handler;

import cli.parkinglot.service.ParkingService;
import cli.parkinglot.constants.Constants;
import cli.parkinglot.exception.ErrorCode;
import cli.parkinglot.exception.ParkingException;
import cli.parkinglot.model.Car;
import cli.parkinglot.service.AbstractService;



public class RequestHandler implements AbstractHandler
{
    private ParkingService parkingService;


    @Override
    public void execute(String input) throws ParkingException
    {
        int level = 1;
        String[] inputs = input.split(" ");
        String key = inputs[0];
        String command = inputs[1];
        String param = inputs[2];
        switch (key)
        {
            case Constants.CREATE_PARKING_LOT:
                try
                {
                    parkingService.createParkingLot(level, Integer.parseInt(command));
                }
                catch (NumberFormatException e)
                {
                    throw new ParkingException(
                            ErrorCode.INVALID_VALUE.getMessage().replace(
                                    "{}", "capacity"
                            )
                    );
                }
                break;
            case Constants.PARK:
                parkingService.park(level, new Car(command, param));
                break;
            case Constants.LEAVE:
                try
                {
                    parkingService.unPark(level, Integer.parseInt(command));
                }
                catch (NumberFormatException e)
                {
                    throw new ParkingException(
                            ErrorCode.INVALID_VALUE.getMessage().replace(
                                    "{}", "slot"
                            )
                    );
                }
                break;
            case Constants.STATUS:
                parkingService.getStatus(level);
                break;
            case Constants.REG_NUMBER_FOR_CARS_WITH_COLOR:
                parkingService.getRegistrationNumberForColor(level, command);
                break;
            case Constants.SLOTS_NUMBER_FOR_CARS_WITH_COLOR:
                parkingService.getSlotNumbersFromColor(level, command);
                break;
            case Constants.SLOTS_NUMBER_FOR_REG_NUMBER:
                parkingService.getSlotNoFromRegistrationNo(level, command);
                break;
            default:
                break;
        }
    }

    @Override
    public void setService(AbstractService service)
    {
        this.parkingService = (ParkingService) service;
    }

}