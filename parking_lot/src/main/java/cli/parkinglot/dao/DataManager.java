package cli.parkinglot.dao;

import cli.parkinglot.model.Vehicle;

import java.util.List;

public interface DataManager <T extends Vehicle>{

    public  int parkCar(T vehicle);

    public  boolean leaveCar(int slotNumber);

    public  List<String> getStatus();

    public  List<String> getRegistrationNumberForColor(String color);

    public  List<Integer> getSlotNumbersFromColor(String colour);

    public  int getSlotNoFromRegistrationNo(String registrationNo);

    public  int getAvailableSlotsCount();

    public  void Clean();
}
