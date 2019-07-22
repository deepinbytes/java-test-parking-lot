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
        assertTrue("createdparkinglotwith23slots".equalsIgnoreCase(
                outContent.toString().trim().replace(" ", "")));
        instance.Clean();
    }

    @Test
    public void testParkingLotStatus() throws Exception
    {
        ParkingService instance = new ParkingServiceImpl();
        thrown.expect(ParkingException.class);
        thrown.expectMessage(is(ErrorCode.PARKING_NOT_EXISTS.getMessage()));
        instance.getStatus();
        //assertEquals("Sorry,CarParkingDoesnotExist", outContent.toString().trim().replace(" ", ""));
        instance.createParkingLot(8);
        instance.park(new Car("KA-01-HH-4564", "White"));
        instance.park(new Car("KA-01-HH-6576", "Orange"));
        instance.getStatus();
        assertTrue(
                ("Sorry,CarParkingDoesnotExist\ncreatedparkinglotwith8slots\nAllocatedslotnumber:1" +
                        "\nAllocatedslotnumber:2\nSlotNo.\tRegistrationNo.\tColor\n1\tKA-01-HH-4564" +
                        "\tWhite\n2\tKA-01-HH-6576\tOrange")
                        .equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
        instance.Clean();

    }

    @Test
    public void testParkingLotExists() throws Exception
    {
        ParkingService instance = new ParkingServiceImpl();
        instance.createParkingLot(25);
        assertTrue("createdparkinglotwith25slots".equalsIgnoreCase(
                outContent.toString().trim().replace(" ", "")));
        thrown.expect(ParkingException.class);
        thrown.expectMessage(is(ErrorCode.PARKING_EXISTS.getMessage()));
        instance.createParkingLot(265);
        instance.Clean();
    }

    @Test
    public void testParkingLotNotExists() throws Exception{
        ParkingService instance = new ParkingServiceImpl();
        thrown.expect(ParkingException.class);
        thrown.expectMessage(is(ErrorCode.PARKING_NOT_EXISTS.getMessage()));
        instance.park(new Car("KA-01-HH-4564", "White"));
        instance.Clean();
    }

    @Test
    public void testEmptyParkingLot() throws Exception {
        ParkingService instance = new ParkingServiceImpl();
        thrown.expect(ParkingException.class);
        thrown.expectMessage(is(ErrorCode.PARKING_NOT_EXISTS.getMessage()));
        instance.getStatus();
        assertTrue("Sorry,CarParkingDoesnotExist".equalsIgnoreCase(
                outContent.toString().trim().replace(" ", "")));
    }

    @Test
    public void testCapacity() throws Exception
    {
        ParkingService instance = new ParkingServiceImpl();
        instance.createParkingLot(20);
        instance.park(new Car("KA-01-HH-2131", "White"));
        instance.park(new Car("KA-01-HH-4243", "White"));
        instance.park(new Car("KA-01-BB-5422", "White"));
        assertEquals(17, instance.getAvailableSlotsCount());
        instance.Clean();
    }

    @Test
    public void testFullParkingLot() throws Exception
    {
        ParkingService instance = new ParkingServiceImpl();
        instance.createParkingLot(2);
        instance.park(new Car("KA-01-HH-2131", "White"));
        instance.park(new Car("KA-01-HH-4243", "White"));
        instance.park(new Car("KA-01-BB-5422", "Black"));
        assertTrue(containsIgnoreCase(outContent.toString().trim().replace(" ",""),
                "sorry,parkinglotisfull"));
        instance.Clean();
    }

    public static boolean containsIgnoreCase(String str, String subString) {
        return str.toLowerCase().contains(subString.toLowerCase());
    }

    @Test
    public void testUnParking() throws Exception
    {
        ParkingService instance = new ParkingServiceImpl();
        thrown.expect(ParkingException.class);
        thrown.expectMessage(is(ErrorCode.PARKING_NOT_EXISTS.getMessage()));
        instance.unPark(12);
        assertEquals("Sorry,CarParkingDoesnotExist", outContent.toString().trim().replace(
                " ", ""));
        instance.createParkingLot(10);
        instance.park(new Car("KA-01-HH-2131", "White"));
        instance.park(new Car("KA-01-HH-4243", "Black"));
        instance.park(new Car("KA-01-BB-5422", "Black"));
        instance.unPark(9);
        assertTrue(
                ("Sorry,CarParkingDoesnotExist\ncreatedparkinglotwith10slots\nAllocatedslotnumber:1" +
                        "\nAllocatedslotnumber:2\nAllocatedslotnumber:3\nSlotnumber9isfree")
                        .equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
        instance.Clean();
    }

    @Test
    public void testVehicleAlreadyPresent() throws Exception
    {
        ParkingService instance = new ParkingServiceImpl();
        instance.createParkingLot(3);
        instance.park(new Car("KA-01-HH-4243", "Black"));
        instance.park(new Car("KA-01-HH-4243", "Black"));
        assertTrue(containsIgnoreCase(outContent.toString().trim().replace(" ",""),
                "Sorry,vehicleisalreadyparked"));
        instance.Clean();
    }

    @Test
    public void testSlotEmptyAlready() throws Exception
    {
        ParkingService instance = new ParkingServiceImpl();
        instance.createParkingLot(20);
        instance.park(new Car("KA-01-HH-4243", "White"));
        instance.park(new Car("KA-01-HH-5422", "White"));
        instance.unPark(1);
        instance.unPark(1);
        assertTrue(containsIgnoreCase(outContent.toString().trim().replace(" ",""),
                "SlotnumberisEmptyAlready"));
        instance.Clean();
    }

    @Test
    public void testGetSlotsByRegistrationNo() throws Exception
    {
        ParkingService instance = new ParkingServiceImpl();
        instance.createParkingLot(20);
        instance.park(new Car("KA-01-HH-4243", "White"));
        instance.park(new Car("KA-01-HH-5422", "White"));
        instance.park(new Car("KA-01-HH-234", "White"));
        instance.park(new Car("KA-01-HH-34", "White"));
        instance.park(new Car("KA-01-HH-5423422", "White"));
        instance.park(new Car("KA-01-HH-23", "White"));
        instance.park(new Car("KA-01-HH-234", "White"));
        instance.park(new Car("KA-01-HH-345", "White"));
        instance.park(new Car("KA-01-HH-654", "White"));
        instance.park(new Car("KA-01-HH-645", "White"));
        instance.park(new Car("KA-01-HH-676", "White"));

        instance.getSlotNoFromRegistrationNo("KA-01-HH-345");
        assertTrue(containsIgnoreCase(outContent.toString().trim().replace(" ",""),
                "8"));
        instance.getSlotNoFromRegistrationNo("KA-01-HH-5423");
        assertTrue(containsIgnoreCase(outContent.toString().trim().replace(" ",""),
                "NotFound"));
        instance.Clean();
    }

    @Test
    public void testGetRegistrationNoByColor() throws Exception
    {
        ParkingService instance = new ParkingServiceImpl();
        instance.createParkingLot(10);
        instance.park(new Car("KA-01-HH-4243", "White"));
        instance.park(new Car("KA-01-HH-5422", "White"));
        instance.park(new Car("KA-01-HH-234", "White"));
        instance.park(new Car("KA-01-HH-34", "White"));
        instance.park(new Car("KA-01-HH-5423422", "White"));
        instance.park(new Car("KA-01-HH-23", "White"));
        instance.park(new Car("KA-01-HH-234", "White"));
        instance.park(new Car("KA-01-HH-345", "White"));
        instance.park(new Car("KA-01-HH-654", "White"));
        instance.park(new Car("KA-01-HH-645", "White"));
        instance.park(new Car("KA-01-HH-676", "White"));
        instance.getRegistrationNumberForColor("White");
        String output =outContent.toString();
        assertTrue(containsIgnoreCase(output.trim().replace(" ",""),
                "KA-01-HH-4243,KA-01-HH-5422,KA-01-HH-234,KA-01-HH-34,KA-01-HH-5423422," +
                        "KA-01-HH-23,KA-01-HH-345,KA-01-HH-654,KA-01-HH-645,KA-01-HH-676"));
        instance.Clean();

    }

    @Test
    public void testGetSlotByRegistrationNo() throws Exception
    {
        ParkingService instance = new ParkingServiceImpl();
        instance.createParkingLot(10);
        instance.park(new Car("KA-01-HH-4243", "White"));
        instance.park(new Car("KA-01-HH-5422", "White"));
        instance.park(new Car("KA-01-HH-234", "White"));
        instance.park(new Car("KA-01-HH-34", "White"));
        instance.park(new Car("KA-01-HH-5423422", "White"));
        instance.park(new Car("KA-01-HH-23", "White"));
        instance.park(new Car("KA-01-HH-234", "White"));
        instance.park(new Car("KA-01-HH-345", "White"));
        instance.park(new Car("KA-01-HH-654", "White"));
        instance.park(new Car("KA-01-HH-645", "White"));
        instance.park(new Car("KA-01-HH-676", "White"));
        instance.getSlotNoFromRegistrationNo("KA-01-HH-654");
        String output =outContent.toString();
        assertTrue(containsIgnoreCase(output.trim().replace(" ",""),
                "9"));
        instance.Clean();
    }

    @Test
    public void testGetSlotByColor() throws Exception
    {
        ParkingService instance = new ParkingServiceImpl();
        instance.createParkingLot(10);
        instance.park(new Car("KA-01-HH-4243", "White"));
        instance.park(new Car("KA-01-HH-5422", "White"));
        instance.park(new Car("KA-01-HH-234", "White"));
        instance.park(new Car("KA-01-HH-34", "White"));
        instance.park(new Car("KA-01-HH-5423422", "White"));
        instance.park(new Car("KA-01-HH-23", "White"));
        instance.park(new Car("KA-01-HH-234", "White"));
        instance.park(new Car("KA-01-HH-345", "White"));
        instance.park(new Car("KA-01-HH-654", "White"));
        instance.park(new Car("KA-01-HH-645", "White"));
        instance.park(new Car("KA-01-HH-676", "White"));
        instance.getSlotNumbersFromColor("White");
        String output =outContent.toString();
        assertTrue(containsIgnoreCase(output.trim().replace(" ",""),
                "1,2,3,4,5,6,7,8,9,10"));
        instance.Clean();
    }


}