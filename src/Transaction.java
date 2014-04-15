import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Transaction class handles the processing of transactions loaded from the daily 
 * transaction log
 *
 */

public class Transaction {
	/**
	 * Transaction class constructor
	 * 
	 * @param acct
	 * @param avail_tickets
	 */
	
	public Transaction(Accounts acct, AvailableTickets avail_tickets){
		this.filereader(acct, avail_tickets);
	}
	
	/**
	 * create transaction processes the transaction that indicates that an account was created
	 * 
	 * @param acct
	 * @param avail_tickets
	 * @param transaction_line
	 */
	public void createTransaction(Accounts acct, AvailableTickets avail_tickets, String transaction_line[]){
		acct.create(transaction_line[1], transaction_line[2], Double.parseDouble(transaction_line[3]));	// creates account
	}
	/**
	 * deteteTransaction processes the transaction that indicates that an account was deleted
	 * 
	 * @param acct
	 * @param avail_tickets
	 * @param transaction_line
	 */
	public void deleteTransaction(Accounts acct, AvailableTickets avail_tickets, String transaction_line[]){
		acct.delete(transaction_line[1]);	// deletes account
	}
	
	/**
	 * sellTransaction processes the transaction that indicates that a seller wants to sell tickets
	 * 
	 * @param acct
	 * @param avail_tickets
	 * @param transaction_line
	 */
	public void sellTransaction(Accounts acct, AvailableTickets avail_tickets, String transaction_line[]){
		Ticket ticket = new Ticket(transaction_line[1], transaction_line[2], Double.parseDouble(transaction_line[4]));	//create a new ticket object from the line
		avail_tickets.create(ticket, Integer.parseInt(transaction_line[3]));	//adds the ticket
	}
	
	/**
	 * buyTransaction processes the transaction that indicates that a buyer wants to buy tickets
	 * 
	 * @param acct
	 * @param avail_tickets
	 * @param transaction_line
	 */
	public void buyTransaction(Accounts acct, AvailableTickets avail_tickets, String transaction_line[]){
		Ticket ticket = new Ticket(transaction_line[1], transaction_line[2], Double.parseDouble(transaction_line[4]));	//create a new ticket object from the line
		Integer previous_ticket_count = avail_tickets.getAvailableTickets(ticket);	//get the amount of tickets available before the sale
		if ((previous_ticket_count - Integer.parseInt(transaction_line[3]) < 0)) {	//check to see that the transaction won't result in a negetive ticket amount
			System.out.println("ERROR: Cannot buy more than the available amount of tickets. Available:" + previous_ticket_count + " Wanted to buy:" + transaction_line[3] + " Transaction:" + transaction_line[0] + "_" +transaction_line[1] + "_" +transaction_line[2] + "_" +transaction_line[3] + "_" +transaction_line[4]); //throw error if it does
		}
		else {
			avail_tickets.setAvailableTickets(ticket, previous_ticket_count - Integer.parseInt(transaction_line[3])); //changes the amount of tickets available to reflect the sale
		}
		
	}
	
	/**
	 * refundTransaction processes the transaction log that indicates a refund was performed
	 * 
	 * @param acct
	 * @param avail_tickets
	 * @param transaction_line
	 */
	public void refundTransaction(Accounts acct, AvailableTickets avail_tickets, String transaction_line[]){
		Double buyer_previous_credit = acct.getAvailableCredit(transaction_line[1]);	// gets previous buyer credit
		Double seller_previous_credit = acct.getAvailableCredit(transaction_line[2]);	// gets previous seller credit
		acct.setAvailableCredit(transaction_line[1], buyer_previous_credit + Double.parseDouble(transaction_line[3]));	// sets new buyer credit
		acct.setAvailableCredit(transaction_line[2], seller_previous_credit - Double.parseDouble(transaction_line[3]));	// sets new seller credit
	}
	/**
	 * addCreditTransaction processes the transaction log that indicates credit was added
	 * 
	 * @param acct
	 * @param avail_tickets
	 * @param transaction_line
	 */	
	public void addCreditTransaction(Accounts acct, AvailableTickets avail_tickets, String transaction_line[]){
		Double previous_credit = acct.getAvailableCredit(transaction_line[1]);	// gets previous credit
		acct.setAvailableCredit(transaction_line[1], previous_credit + Double.parseDouble(transaction_line[3]));	// sets new credit
	}
	
	/**
	 * filereader reads the transaction log file and perform the appropriate action to 
	 * process each transaction
	 * 
	 * @param acct
	 * @param avail_tickets
	 */
	public void filereader(Accounts acct, AvailableTickets avail_tickets){
		String transaction_file = "transaction.txt";	// sets the transaction files location
		BufferedReader br = null;
		String line = "";
		String Delimiter = "_";		// sets the delimiter to "_"
	 
		try {
	 
			br = new BufferedReader(new FileReader(transaction_file));
			while ((line = br.readLine()) != null) {	// reads each line one at a time until null
		 		String[] transaction_line = line.split(Delimiter);	// splits the line on the delimiter
		 		
				switch (Integer.parseInt(transaction_line[0])) {	// checks the transaction code
				
				case 1: 
					if (transaction_line[0].isEmpty() || transaction_line[1].isEmpty() || transaction_line[2].isEmpty() || transaction_line[3].isEmpty())	//check for bad inputs
						System.out.print("ERROR: Bad input, missing fields. File:" + transaction_file);
					else {
						this.createTransaction(acct, avail_tickets, transaction_line);	// if code is 1, create is performed
					}
					break;
					
				case 2: 
					if (transaction_line[0].isEmpty() || transaction_line[1].isEmpty() || transaction_line[2].isEmpty() || transaction_line[3].isEmpty())	//check for bad inputs
						System.out.print("ERROR: Bad input, missing fields. File:" + transaction_file); //Throw error with discription and offending file name
					else {
						this.deleteTransaction(acct, avail_tickets, transaction_line);	// if code is 2, delete is performed
					}
					break;
				
				case 3: 
					if (transaction_line[0].isEmpty() || transaction_line[1].isEmpty() || transaction_line[2].isEmpty() || transaction_line[3].isEmpty() || transaction_line[4].isEmpty())	//check for bad inputs
						System.out.print("ERROR: Bad input, missing fields. File:" + transaction_file); //Throw error with discription and offending file name
					else {
						sellTransaction(acct, avail_tickets, transaction_line);
					}
					break;
				
				case 4: 
					if (transaction_line[0].isEmpty() || transaction_line[1].isEmpty() || transaction_line[2].isEmpty() || transaction_line[3].isEmpty() || transaction_line[4].isEmpty())	//check for bad inputs
						System.out.print("ERROR: Bad input, missing fields. File:" + transaction_file); //Throw error with discription and offending file name
					else {
						buyTransaction(acct, avail_tickets, transaction_line);
					}
					break;
					
				case 5: 	// if code is 5, refund is performed
					if (transaction_line[0].isEmpty() || transaction_line[1].isEmpty() || transaction_line[2].isEmpty() || transaction_line[3].isEmpty())	//check for bad inputs
						System.out.print("ERROR: Bad input, missing fields. File:" + transaction_file); //Throw error with discription and offending file name
					else {
						this.refundTransaction(acct, avail_tickets, transaction_line);
					}
					break;
				
				case 6: 	// if code is 6, addcredit is performed
					if (transaction_line[0].isEmpty() || transaction_line[1].isEmpty() || transaction_line[2].isEmpty() || transaction_line[3].isEmpty())	//check for bad inputs
						System.out.print("ERROR: Bad input, missing fields. File:" + transaction_file); //Throw error with discription and offending file name
					else {
						this.addCreditTransaction(acct, avail_tickets, transaction_line);
					}
					break;
				
				
	
				default:	
					System.out.print("ERROR: Bad input, incorrect transaction code: " + transaction_file); //Throw error with discription and offending file name
					break;
				}
					
		 
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();	// prints File Not Found errors
		} catch (IOException e) {
			e.printStackTrace();	// prints IO errors
		} finally {
			if (br != null) {
				try {
					br.close();		// closes the buffered reader

				} catch (IOException e) {
					e.printStackTrace();	// print IO errors
				}
			}
		}
	}
	
	
	
}
