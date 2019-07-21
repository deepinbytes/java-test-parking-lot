package cli.parkinglot.dao.impl;

import cli.parkinglot.constants.Constants;
import cli.parkinglot.dao.DataManager;
import cli.parkinglot.handler.StrategyHandler;
import cli.parkinglot.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DataManagerImpl<T extends Vehicle> implements DataManager<T> {

    private AtomicInteger capacity		= new AtomicInteger();
    private AtomicInteger availability	= new AtomicInteger();

    private Map<Integer, Optional<T>> allocationMap;

    private StrategyHandler Strategy;
    private static DataManagerImpl instance = null;

    private DataManagerImpl(int capacity, StrategyHandler Strategy)
    {
        this.capacity.set(capacity);
        this.availability.set(capacity);
        this.Strategy = Strategy;
        if (Strategy == null)
            Strategy = new StrategyHandler();

        allocationMap = new ConcurrentHashMap<>();
        for (int i = 1; i <= capacity; i++)
        {
            allocationMap.put(i, Optional.empty());
            Strategy.add(i);
        }
    }


    @SuppressWarnings("unchecked")
    public static <T extends Vehicle> DataManagerImpl<T> getInstance(int capacity,
                                                                     StrategyHandler Strategy)
    {
        if (instance == null)
        {
            instance = new DataManagerImpl<T>(capacity, Strategy);

        }
        return instance;
    }


    @Override
    public int parkCar(T vehicle) {
        int availableSlot;
        if (availability.get() == 0)
        {
            return Constants.NOT_AVAILABLE;
        }
        else
        {
            availableSlot = Strategy.getSlot();
            if (allocationMap.containsValue(Optional.of(vehicle)))
                return Constants.VEHICLE_ALREADY_EXIST;

            allocationMap.put(availableSlot, Optional.of(vehicle));
            availability.decrementAndGet();
            Strategy.removeSlot(availableSlot);
        }
        return availableSlot;
    }

    @Override
    public boolean leaveCar(int slotNumber) {
        if (!allocationMap.get(slotNumber).isPresent())
            return false;
        availability.incrementAndGet();
        Strategy.add(slotNumber);
        allocationMap.put(slotNumber, Optional.empty());
        return true;
    }

    @Override
    public List<String> getStatus() {
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= capacity.get(); i++)
        {
            Optional<T> vehicle = allocationMap.get(i);
            if (vehicle.isPresent())
            {
                result.add(i + "\t\t" + vehicle.get().getRegistrationNo() + "\t\t" + vehicle.get().getColor());
            }
        }
        return result;
    }

    @Override
    public List<String> getRegistrationNumberForColor(String color) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i <= capacity.get(); i++)
        {
            Optional<T> vehicle = allocationMap.get(i);
            if (vehicle.isPresent() && color.equalsIgnoreCase(vehicle.get().getColor()))
            {
                result.add(vehicle.get().getRegistrationNo());
            }
        }
        return result;
    }

    @Override
    public List<Integer> getSlotNumbersFromColor(String colour) {
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= capacity.get(); i++)
        {
            Optional<T> vehicle = allocationMap.get(i);
            if (vehicle.isPresent() && colour.equalsIgnoreCase(vehicle.get().getColor()))
            {
                result.add(i);
            }
        }
        return result;
    }

    @Override
    public int getSlotNoFromRegistrationNo(String registrationNo) {
        int result = Constants.NOT_FOUND;
        for (int i = 1; i <= capacity.get(); i++)
        {
            Optional<T> vehicle = allocationMap.get(i);
            if (vehicle.isPresent() && registrationNo.equalsIgnoreCase(vehicle.get().getRegistrationNo()))
            {
                result = i;
            }
        }
        return result;
    }

    @Override
    public int getAvailableSlotsCount() {
        return availability.get();
    }

    @Override
    public void Clean() {
        this.capacity = new AtomicInteger();
        this.availability = new AtomicInteger();
        this.Strategy = null;
        allocationMap = null;
        instance = null;
    }
}
