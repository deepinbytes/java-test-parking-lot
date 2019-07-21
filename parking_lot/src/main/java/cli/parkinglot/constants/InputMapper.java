package cli.parkinglot.constants;

import java.util.HashMap;
import java.util.Map;


public class InputMapper
{
	private static volatile Map<String, Integer> cmdParams = new HashMap<String, Integer>();
	
	static
	{
		cmdParams.put(Constants.CREATE_PARKING_LOT, 1);
		cmdParams.put(Constants.PARK, 2);
		cmdParams.put(Constants.LEAVE, 1);
		cmdParams.put(Constants.STATUS, 0);
		cmdParams.put(Constants.REG_NUMBER_FOR_CARS_WITH_COLOR, 1);
		cmdParams.put(Constants.SLOTS_NUMBER_FOR_CARS_WITH_COLOR, 1);
		cmdParams.put(Constants.SLOTS_NUMBER_FOR_REG_NUMBER, 1);
	}
	
	/**
	 * @return the cmdParams
	 */
	public static Map<String, Integer> getCommands()
	{
		return cmdParams;
	}

	
}
