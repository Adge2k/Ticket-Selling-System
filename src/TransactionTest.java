import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TransactionTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() throws FileNotFoundException, UnsupportedEncodingException {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	    
	    PrintWriter writer2 = new PrintWriter("test.txt", "UTF-8");
		writer2.println("TestUserAccount_AA_100.00");
		writer2.close();
		
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("04_TestEvent_TestUserAccount_1_100.00");
		writer.close();
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}

	@Test
	public void testTransaction() throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("01_TestUserAccount_AA_100.00");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		assertTrue( acct.getAccountTypeHashmap().containsKey("TestUserAccount"));
	}

	@Test
	public void testCreateTransaction() {
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		String[] transactline = {null,"TestUserAccount","AA","100.00"};
		trans.createTransaction(acct, avtickets, transactline);
		assertTrue( acct.getAccountTypeHashmap().containsKey("TestUserAccount"));
		assertEquals("AA",acct.getAccountTypeHashmap().get("TestUserAccount"));
		assertTrue( acct.getAvailableCreditHashmap().containsKey("TestUserAccount"));
		assertEquals(100.00,acct.getAvailableCredit("TestUserAccount"), 2);
	}

	@Test
	public void testDeleteTransaction() {
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		String[] transactline = {null,"TestUserAccount","AA","100.00"};
		trans.createTransaction(acct, avtickets, transactline);
		assertTrue( acct.getAccountTypeHashmap().containsKey("TestUserAccount"));
		trans.deleteTransaction(acct, avtickets, transactline);
		assertFalse( acct.getAccountTypeHashmap().containsKey("TestUserAccount"));
		assertFalse( acct.getAvailableCreditHashmap().containsKey("TestUserAccount"));
	}

	@Test
	public void testSellTransaction() {
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		String[] transactline = {null,"TestUserAccount","TestEventName","4","100.00"};
		trans.sellTransaction(acct, avtickets, transactline);
		Ticket ticket = new Ticket("TestUserAccount","TestEventName",100.00);
		int i = avtickets.getAvailableTickets(ticket);
		assertEquals(4, i);
	}

	@Test
	public void testBuyTransaction() {
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		String[] transactline = {null,"TestUserAccount","TestEventName","4","100.00"};
	}

	@Test
	public void testRefundTransaction() {
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		String[] transactline = {null,"TestBuyersAccount","TestSellersAccount","100.00"};
		acct.create("TestSellersAccount","AA",100.00);
		Double i = acct.getAvailableCredit("TestSellersAccount");
		assertEquals(100.00, i,2);
		acct.create("TestBuyersAccount","AA",0.00);
		i = acct.getAvailableCredit("TestBuyersAccount");
		assertEquals(0.00, i,2);
		trans.refundTransaction(acct, avtickets, transactline);
		i = acct.getAvailableCredit("TestSellersAccount");
		assertEquals(0.00, i,2);
		i = acct.getAvailableCredit("TestBuyersAccount");
		assertEquals(100.00, i,2);
		
	}

	@Test
	public void testAddCreditTransaction() throws FileNotFoundException, UnsupportedEncodingException {
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		String[] transactline = {null,"TestUserAccount","AA","100.00"};
		acct.create("TestUserAccount","AA",100.00);
		Double i = acct.getAvailableCredit("TestUserAccount");
		assertEquals(100.00, i,2);
		trans.addCreditTransaction(acct, avtickets, transactline);
		i = acct.getAvailableCredit("TestUserAccount");
		assertEquals(200.00, i,2);
	}

	@Test
	public void testFilereaderCaseCreate() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("01_TestUserAccount_AA_100.00");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		assertTrue(acct.getAccountTypeHashmap().containsKey("TestUserAccount"));	
	}
	
	@Test
	public void testFilereaderCaseNullValuesCreate() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("01_TestUserAccount__100.00");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		assertEquals("ERROR: Bad input, missing fields. File:transaction.txt", outContent.toString());	
	}
	
	@Test
	public void testFilereaderCaseDelete() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("02_TestUserAccount_AA_100.00");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		assertFalse(acct.getAccountTypeHashmap().containsKey("TestUserAccount"));	
	}
	
	@Test
	public void testFilereaderCaseNullValuesDelete() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("02_TestUserAccount__200.56");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		assertEquals("ERROR: Bad input, missing fields. File:transaction.txt", outContent.toString());	
	}
	
	@Test
	public void testFilereaderCaseAddCredit() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("06_TestUserAccount_AA_100.00");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		assertEquals(200.00,acct.getAvailableCredit("TestUserAccount"),2);
	}
	
	@Test
	public void testFilereaderCaseNullValuesAddCredit() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("06_TestUserAccount__200.56");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		assertEquals("ERROR: Bad input, missing fields. File:transaction.txt", outContent.toString());	
	}
	
	@Test
	public void testFilereaderCaseSell() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("03_TestEvent_TestUserAccount_5_100.00");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);

		Ticket ticket = new Ticket("TestEvent", "TestUserAccount", 100.00);
		int i = avtickets.getAvailableTickets(ticket);
		assertEquals(5,i);
	}
	
	@Test
	public void testFilereaderCaseNullValuesSell() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("03_TestEvent__5_100.00");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		assertEquals("ERROR: Bad input, missing fields. File:transaction.txt", outContent.toString());	
	}

	@Test
	public void testFilereaderCaseBuy() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("04_TestEvent_TestUserAccount_1_100.00");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);

		Ticket ticket = new Ticket("TestEvent", "TestUserAccount", 100.00);
		int i = avtickets.getAvailableTickets(ticket);
		assertEquals(4,i);
	}
	
	@Test
	public void testFilereaderCaseNullValuesBuy() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("04_TestEvent__5_100.00");
		writer.close();

		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		assertEquals("ERROR: Bad input, missing fields. File:transaction.txt", outContent.toString());
	}
	
	@Test
	public void testFilereaderCaseRefund() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("05_TestUserAccount_TestUserAccount2_100.00");
		writer.close();
		
		PrintWriter writer2 = new PrintWriter("test.txt", "UTF-8");
		writer2.println("TestUserAccount_AA_100.00");
		writer2.println("TestUserAccount2_AA_100.00");
		writer2.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);

		Double i = acct.getAvailableCredit("TestUserAccount");
		assertEquals(200.00,i,2);
		i = acct.getAvailableCredit("TestUserAccount2");
		assertEquals(0.00,i,2);
	}
	
	@Test
	public void testFilereaderCaseNullValuesRefund() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("05_TestUserAccount__100.00");
		writer.close();
		
		PrintWriter writer2 = new PrintWriter("test.txt", "UTF-8");
		writer2.println("TestUserAccount_AA_100.00");
		writer2.println("TestUserAccount2_AA_100.00");
		writer2.close();

		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		assertEquals("ERROR: Bad input, missing fields. File:transaction.txt", outContent.toString());
	}
	
	@Test
	public void testFilereaderCaseDefault() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("08_TestUserAccount_AA_100.00");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		assertEquals("ERROR: Bad input, incorrect transaction code: transaction.txt", outContent.toString());	
	}
	
	@Test
	public void testFilereaderLoopZero() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println();
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		int i = acct.getAccountTypeHashmap().size();
		assertEquals(1,i);
	}
	
	@Test
	public void testFilereaderLoopOnce() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("01_TestUserAccount_AA_100.00");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		int i = acct.getAccountTypeHashmap().size();
		assertEquals(1,i);	
	}
	
	@Test
	public void testFilereaderLoopTwice() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("01_TestUserAccount_AA_100.00");
		writer.println("01_TestUserAccount2_AA_100.00");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		int i = acct.getAccountTypeHashmap().size();
		assertEquals(2,i);		
	}
	
	@Test
	public void testFilereaderLoopMany() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("transaction.txt", "UTF-8");
		writer.println("01_TestUserAccount_AA_100.00");
		writer.println("01_TestUserAccount2_AA_100.00");
		writer.println("01_TestUserAccount3_AA_100.00");
		writer.close();
		
		Accounts acct  = new Accounts();
		AvailableTickets avtickets  = new AvailableTickets();
		Transaction trans = new Transaction(acct, avtickets);
		int i = acct.getAccountTypeHashmap().size();
		assertEquals(3,i);		
	}
	
}
