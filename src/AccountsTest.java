import static org.junit.Assert.*;

import org.junit.Test;


public class AccountsTest {
	Accounts acct = new Accounts();
	@Test
	public void testAccounts() {
		Accounts acct = new Accounts();
		assertNotNull(acct.getAccountTypeHashmap().get("TestUserAccount"));
		assertNotNull(acct.getAvailableCreditHashmap().get("TestUserAccount"));
		assertNull(acct.getAccountTypeHashmap().get("Bob"));
		assertNull(acct.getAvailableCreditHashmap().get("Bob"));
	}

	@Test
	public void testCreate() {
		Accounts acct = new Accounts();
		acct.create("TestUser","FS",5.00);
		assertEquals("FS",acct.getAccountTypeHashmap().get("TestUser"));
		assertTrue(5.00 == acct.getAvailableCreditHashmap().get("TestUser"));
	}

	@Test
	public void testDelete() {
		Accounts acct = new Accounts();
		acct.create("TestUser","FS",5.00);
		acct.delete("TestUser");
		assertNull(acct.getAccountTypeHashmap().get("TestUser"));
		assertNull(acct.getAvailableCreditHashmap().get("TestUser"));
	}

	@Test
	public void testGetAvailableCredit() {
		Accounts acct = new Accounts();
		acct.create("TestUser","FS",35.00);
		assertTrue(acct.getAvailableCredit("TestUser")==35.00);
	}

	@Test
	public void testSetAvailableCredit() {
		Accounts acct = new Accounts();
		acct.create("TestUser","FS",5.00);
		acct.setAvailableCredit("TestUser", 35.00);
		assertTrue(acct.getAvailableCredit("TestUser")==35.00);
	}
	
	@Test
	public void saveCurrentUserAccountsFile(){	
		Accounts acct = new Accounts();
		acct.saveCurrentUserAccountsFile();
	}
	
	@Test
	public void testFilereader() {
		Accounts acct = new Accounts();
		acct.filereader();
	}

}
