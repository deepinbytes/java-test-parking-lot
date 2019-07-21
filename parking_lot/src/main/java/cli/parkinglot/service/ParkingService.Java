package cli.parkinglot.service;

import java.util.Optional;

import cli.parkinglot.exception.ParkingException;
import cli.parkinglot.model.Vehicle;


public interface ParkingService extends AbstractService
{
    void createParkingLot(int capacity) throws ParkingException;

    void unPark(int slotNumber) throws ParkingException;

    void getStatus() throws ParkingException;

    void getRegistrationNumberForColor(String color) throws ParkingException;

    void getSlotNumbersFromColor(String colour) throws ParkingException;

    int getSlotNoFromRegistrationNo(String registrationNo) throws ParkingException;

    Optional<Integer> park(Vehicle vehicle) throws ParkingException;

    Optional<Integer> getAvailableSlotsCount() throws ParkingException;

    void Clean();
}
