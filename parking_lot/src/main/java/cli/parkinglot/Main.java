package cli.parkinglot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main
{
	public static void main(String[] args)
	{
		BufferedReader bufferReader = null;
		String input = null;
		try
		{
			showCommands();
			switch (args.length)
			{
				case 0: // Interactive
				{
					System.out.println("Please Enter 'X' to end the program");
					System.out.println("Input:");
					while (true)
					{
						try
						{
							bufferReader = new BufferedReader(new InputStreamReader(System.in));
							input = bufferReader.readLine().trim();
							if (input.equals("X"))
							{
								break;
							}
							else
							{
								// Validate and process
							}
						}
						catch (Exception e)
						{
							// Throw parking exception
						}
					}
					break;
				}
				case 1:// File input/output
				{
					File inputFile = new File(args[0]);
					try
					{
						bufferReader = new BufferedReader(new FileReader(inputFile));
						int lineNo = 1;
						while ((input = bufferReader.readLine()) != null)
						{
							//Validate and process
						}
					}
					catch (Exception e)
					{
					// Throw parking exception
					}
					break;
				}
				default:
					System.out.println("Invalid input");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		finally
		{
			try
			{
				if (bufferReader != null)
					bufferReader.close();
			}
			catch (IOException e)
			{
			}
		}
	}
	
	private static void showCommands()
	{
		StringBuffer buffer = new StringBuffer();
		buffer = buffer.append(
				"Please Enter one of the below commands followed by the variable")
				.append("\n");
		buffer = buffer.append(
				"A) For creating parking lot of size n ---> create_parking_lot `capacity`")
				.append("\n");
		buffer = buffer.append(
				"B) Park car ---> park `car_number` `car_color`")
				.append("\n");
		buffer = buffer.append(
				"C) Unpark car ---> leave `slot_number`")
				.append("\n");
		buffer = buffer.append(
				"D) Status ---> status").append("\n");
		buffer = buffer.append(
				"E) Registration no for the given car color ---> registration_numbers_for_cars_with_color `car_color`")
				.append("\n");
		buffer = buffer.append(
				"F) Slot numbers for the given car color ---> slot_numbers_for_cars_with_color `car_color`")
				.append("\n");
		buffer = buffer.append(
				"G) Slot number for the given car number ---> slot_number_for_registration_number `car_number`")
				.append("\n");
		System.out.println(buffer.toString());
	}
}
