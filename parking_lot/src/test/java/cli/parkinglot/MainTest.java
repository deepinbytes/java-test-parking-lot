package cli.parkinglot;

import cli.parkinglot.exception.ErrorCode;
import cli.parkinglot.exception.ParkingException;
import cli.parkinglot.model.Car;
import cli.parkinglot.service.ParkingService;
import cli.parkinglot.service.impl.ParkingServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainTest {

    private final ByteArrayOutputStream	outContent	= new ByteArrayOutputStream();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init()
    {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUp()
    {
        System.setOut(null);
    }

    @Test
    public void testCreateParkingLot() throws Exception
    {
        ParkingService instance = new ParkingServiceImpl();
        instance.createParkingLot(23);
        assertTrue("createdparkinglotwith23slots".equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
        instance.Clean();
    }

    @Test
    public void testParkingLotStatus() throws Exception
    {
        ParkingService instance = new ParkingServiceImpl();
        thrown.expect(ParkingException.class);
        thrown.expectMessage(is(ErrorCode.PARKING_NOT_EXISTS.getMessage()));
        instance.getStatus();
        assertEquals("Sorry,CarParkingDoesnotExist", outContent.toString().trim().replace(" ", ""));
        instance.createParkingLot(8);
        instance.park(new Car("KA-01-HH-4564", "White"));
        instance.park(new Car("KA-01-HH-6576", "Orange"));
        instance.getStatus();
        assertTrue(
                "Sorry,CarParkingDoesnotExist\ncreatedparkinglotwith8slots\nAllocatedslotnumber:1\nAllocatedslotnumber:2\nSlotNo.\tRegistrationNo.\tColor\n1\tKA-01-HH-1234\tWhite\n2\tKA-01-HH-9999\tWhite"
                        .equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
        instance.Clean();

    }


}