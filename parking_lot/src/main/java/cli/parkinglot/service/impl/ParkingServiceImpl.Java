package cli.parkinglot.service.impl;


import cli.parkinglot.constants.Constants;
import cli.parkinglot.dao.DataManager;
import cli.parkinglot.dao.impl.DataManagerImpl;
import cli.parkinglot.exception.ErrorCode;
import cli.parkinglot.exception.ParkingException;
import cli.parkinglot.handler.StrategyHandler;
import cli.parkinglot.model.Vehicle;
import cli.parkinglot.service.ParkingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ParkingServiceImpl implements ParkingService
{
    private DataManager<Vehicle> dataManager = null;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void createParkingLot(int capacity) throws ParkingException {
        if (dataManager != null)
            throw new ParkingException(ErrorCode.PARKING_EXISTS.getMessage());
        StrategyHandler strategy = new StrategyHandler();
        this.dataManager = DataManagerImpl.getInstance(capacity, strategy);
        System.out.println("Created parking lot with " + capacity + " slots");
    }

    private void validateParkingLot() throws ParkingException
    {
        if (dataManager == null)
        {
            throw new ParkingException(ErrorCode.PARKING_NOT_EXISTS.getMessage());
        }
    }

    @Override
    public void unPark(int slotNumber) throws ParkingException {
        lock.writeLock().lock();
        validateParkingLot();
        try
        {

            if (dataManager.leaveCar(slotNumber))
                System.out.println("Slot number " + slotNumber + " is free");
            else
                System.out.println("Slot number is Empty Already.");
        }
        catch (Exception e)
        {
            throw new ParkingException(ErrorCode.INVALID_VALUE.getMessage().replace("{}", "slot_number"), e);
        }
        finally
        {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void getStatus() throws ParkingException {
        lock.readLock().lock();
        validateParkingLot();
        try
        {
            System.out.println("Slot No.\tRegistration No.\tColor");
            List<String> statusList = dataManager.getStatus();
            if (statusList.size() == 0)
                System.out.println("Sorry, parking lot is empty.");
            else
            {
                for (String status : statusList)
                {
                    System.out.println(status);
                }
            }
        }
        catch (Exception e)
        {
            throw new ParkingException(ErrorCode.FATAL_ERROR.getMessage(), e);
        }
        finally
        {
            lock.readLock().unlock();
        }
    }

    @Override
    public void getRegistrationNumberForColor(String color) throws ParkingException {
        lock.readLock().lock();
        validateParkingLot();
        try
        {
            List<String> registrationList = dataManager.getRegistrationNumberForColor( color);
            if (registrationList.size() == 0)
                System.out.println("Not Found");
            else
                System.out.println(String.join(",", registrationList));
        }
        catch (Exception e)
        {
            throw new ParkingException(ErrorCode.FATAL_ERROR.getMessage(), e);
        }
        finally
        {
            lock.readLock().unlock();
        }
    }

    @Override
    public void getSlotNumbersFromColor(String colour) throws ParkingException {
        lock.readLock().lock();
        validateParkingLot();
        try
        {
            List<Integer> slotList = dataManager.getSlotNumbersFromColor(colour);
            if (slotList.size() == 0)
                System.out.println("Not Found");
            StringJoiner stringjoiner = new StringJoiner(",");
            for (Integer slot : slotList)
            {
                stringjoiner.add(slot + "");
            }
            System.out.println(stringjoiner.toString());
        }
        catch (Exception e)
        {
            throw new ParkingException(ErrorCode.FATAL_ERROR.getMessage(), e);
        }
        finally
        {
            lock.readLock().unlock();
        }
    }

    @Override
    public int getSlotNoFromRegistrationNo(String registrationNo) throws ParkingException {
        int result ;
        lock.readLock().lock();
        validateParkingLot();
        try
        {
            result = dataManager.getSlotNoFromRegistrationNo(registrationNo);
            System.out.println(result != -1 ? result : "Not Found");
        }
        catch (Exception e)
        {
            throw new ParkingException(ErrorCode.FATAL_ERROR.getMessage(), e);
        }
        finally
        {
            lock.readLock().unlock();
        }
        return result;
    }

    @Override
    public Optional<Integer> park(Vehicle vehicle) throws ParkingException {
        Optional<Integer> result = Optional.empty();
        lock.writeLock().lock();
        validateParkingLot();
        try
        {
            result = Optional.of(dataManager.parkCar(vehicle));
            if (result.get() == Constants.NOT_AVAILABLE)
                System.out.println("Sorry, parking lot is full");
            else if (result.get() == Constants.VEHICLE_ALREADY_EXIST)
                System.out.println("Sorry, vehicle is already parked.");
            else
                System.out.println("Allocated slot number: " + result.get());
        }
        catch (Exception e)
        {
            throw new ParkingException(ErrorCode.FATAL_ERROR.getMessage(), e);
        }
        finally
        {
            lock.writeLock().unlock();
        }
        return result;
    }

    @Override
    public Optional<Integer> getAvailableSlotsCount() throws ParkingException {
        lock.readLock().lock();
        Optional<Integer> count = Optional.empty();
        validateParkingLot();
        try
        {
            count = Optional.of(dataManager.getAvailableSlotsCount());
        }
        catch (Exception e)
        {
            throw new ParkingException(ErrorCode.FATAL_ERROR.getMessage(), e);
        }
        finally
        {
            lock.readLock().unlock();
        }
        return count;
    }

    @Override
    public void Clean() {
        if (dataManager != null)
            dataManager.Clean();
    }
}
