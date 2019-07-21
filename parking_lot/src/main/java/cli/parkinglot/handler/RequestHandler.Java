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
        String[] inputs = input.split(" ");
        String key = inputs[0];
        String command = inputs[1];
        String param = inputs[2];
        switch (key)
        {
            case Constants.CREATE_PARKING_LOT:
                try
                {
                    parkingService.createParkingLot(Integer.parseInt(command));
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
                parkingService.park(new Car(command, param));
                break;
            case Constants.LEAVE:
                try
                {
                    parkingService.unPark(Integer.parseInt(command));
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
                parkingService.getStatus();
                break;
            case Constants.REG_NUMBER_FOR_CARS_WITH_COLOR:
                parkingService.getRegistrationNumberForColor(command);
                break;
            case Constants.SLOTS_NUMBER_FOR_CARS_WITH_COLOR:
                parkingService.getSlotNumbersFromColor(command);
                break;
            case Constants.SLOTS_NUMBER_FOR_REG_NUMBER:
                parkingService.getSlotNoFromRegistrationNo(command);
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