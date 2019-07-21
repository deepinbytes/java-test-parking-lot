package cli.parkinglot.handler;

import java.util.TreeSet;

public class StrategyHandler {
    TreeSet<Integer> availableSlots;

    public StrategyHandler()
    {
        availableSlots = new TreeSet<Integer>();
    }

    public void add(int i)
    {
        availableSlots.add(i);
    }

    public int getSlot()
    {
        return availableSlots.first();
    }

    public void removeSlot(int availableSlot)
    {
        availableSlots.remove(availableSlot);
    }
}
