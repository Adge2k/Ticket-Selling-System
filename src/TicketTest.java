import static org.junit.Assert.*;

import org.junit.Test;


public class TicketTest {

	@Test
	public void testHashCode() {
		Ticket myTestTicket = new Ticket("TestEvent", "TestName", 100.00);			//for regular ticket
		Ticket myTestTicketNullUserName = new Ticket("TestEvent", null, 100.00);	//for null user name check
		Ticket myTestTicketNullEventName = new Ticket(null, "TestName", 100.00);	// for null event name check
		
		
		int i = myTestTicket.hashCode();											//get hashcode for each ticket
		int i1 = myTestTicketNullUserName.hashCode();
		int i2 = myTestTicketNullEventName.hashCode();
		
		assertEquals(-303274780, i);												//compare each with previously calculated values
		assertEquals(-1110861919, i1);
		assertEquals(-1103168548, i2);
		
	}

	@Test
	public void testTicket() {
		Ticket myTestTicket = new Ticket("TestEvent", "TestName", 100.00);
		assertEquals("line 1","TestEvent", myTestTicket.event_name);	//assert that every value was created
		assertEquals("TestName", myTestTicket.user_name);
		assertEquals(100.00, myTestTicket.price_per_ticket,2);
	}

	@Test
	public void testGetUserName() {
		Ticket myTestTicket = new Ticket("TestEvent", "TestName", 100.00);
		assertEquals("TestName", myTestTicket.getUserName());
	}

	@Test
	public void testGetEventName() {
		Ticket myTestTicket = new Ticket("TestEvent", "TestName", 100.00);
		assertEquals("TestEvent", myTestTicket.getEventName());
	}

	@Test
	public void testGetPricePerTicket() {
		Ticket myTestTicket = new Ticket("TestEvent", "TestName", 100.00);
		assertEquals(100.00, myTestTicket.getPricePerTicket(),2);
	}

	@Test
	public void testEqualsObject() {
		Ticket myTestTicket = new Ticket("TestEvent", "TestName", 100.00);				//regular ticket object
		Ticket differentEventName = new Ticket("DifferentEvent", "TestName", 100.00);	// a new ticket with a different event name
		Ticket differentUserName = new Ticket("TestEvent", "DifferentName", 100.00);	// a new ticket with a different user name
		Ticket differentPricePerTicket = new Ticket("TestEvent", "TestName", 0.00);		// a new ticket with a different price per ticket
		Ticket myTestTicket2 = new Ticket("TestEvent", "TestName", 100.00);				// a duplicate ticket that is used to test if the are equal
		assertFalse("other is null", myTestTicket.equals(null));						// check first if statement, checks for null object
		assertTrue("other is this object", myTestTicket.equals(myTestTicket));			// check second if statement,
		assertFalse("other is a different class", myTestTicket.equals("test"));			// check third if statement, checks for same object class
		assertFalse("different event name", myTestTicket.equals(differentEventName));	// check fourth if statement, check for different event name
		assertFalse("different user name", myTestTicket.equals(differentUserName));		// check fifth if statement, check for different names
		assertFalse("different priceperticket", myTestTicket.equals(differentPricePerTicket)); // check sixth if statement, check for different prices
		assertTrue("duplicate tickets are the same", myTestTicket.equals(myTestTicket2)); // final if statement, check if two ticket objects are the same
		
	}

}
