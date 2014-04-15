import static org.junit.Assert.*;

import org.junit.Test;


public class AvailableTicketsTest {

	@Test
	public void testAvailableTickets() {
		AvailableTickets at = new AvailableTickets();
	}

	@Test
	public void testCreate() {
		AvailableTickets at = new AvailableTickets();
		Ticket ticket = new Ticket("ACC EVENT", "TestUser",35.00);
		at.create(ticket, 20);
		assertTrue(at.getAvailableTickets(ticket)==20);
	}

	@Test
	public void testDelete() {
		AvailableTickets at = new AvailableTickets();
		Ticket ticket = new Ticket("ACC EVENT", "TestUser",35.00);
		at.create(ticket, 20);
		at.delete(ticket);
		assertNull(at.getAvailableTickets(ticket));
	}

	@Test
	public void testSet_AvailableTickets() {
		AvailableTickets at = new AvailableTickets();
		Ticket ticket = new Ticket("ACC EVENT", "TestUser",35.00);
		at.create(ticket, 20);
		at.setAvailableTickets(ticket, 15);
		assertTrue(at.getAvailableTickets(ticket)==15);
	}
	
	@Test
	public void testSaveAvailableTicketsFile(){
		AvailableTickets at = new AvailableTickets();
		at.saveAvailableTicketsFile();
	}

	@Test
	public void testFilereader() {
		AvailableTickets at = new AvailableTickets();
		at.filereader();
	}

}
