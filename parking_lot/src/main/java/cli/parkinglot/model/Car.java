package cli.parkinglot.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


public class Car extends Vehicle
{
	
	public Car(String registrationNo, String color)
	{
		super(registrationNo, color);
	}
	
	@Override
	public void writeBlob(ObjectOutput out) throws IOException
	{
		super.writeBlob(out);
	}
	
	@Override
	public void readBlob(ObjectInput in) throws IOException, ClassNotFoundException
	{
		super.readBlob(in);
	}
}
