package cli.parkinglot.service;

import java.util.Optional;

import cli.parkinglot.exception.ParkingException;
import cli.parkinglot.model.Vehicle;


public interface ParkingService extends AbstractService
{
    void createParkingLot(int level, int capacity) throws ParkingException;

    void unPark(int level, int slotNumber) throws ParkingException;

    void getStatus(int level) throws ParkingException;

    void getRegistrationNumberForColor(int level, String color) throws ParkingException;

    void getSlotNumbersFromColor(int level, String colour) throws ParkingException;

    int getSlotNoFromRegistrationNo(int level, String registrationNo) throws ParkingException;

    Optional<Integer> park(int level, Vehicle vehicle) throws ParkingException;

    Optional<Integer> getAvailableSlotsCount(int level) throws ParkingException;

    void Clean();
}
