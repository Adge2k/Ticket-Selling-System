/**
* This Is the Backend process of the Ticket Selling Service. This will be an overnight batch processor.
* This program Has three inputs, the Old User Accounts file, Old Available Tickets File, Merged Daily Transaction File.
* It reads from the daily transaction file and applies any changes to the User accounts file and the available tickets file.
* When it is complete It will output two files, The new updated User account file as Current_User_Accounts.txt and the
* new update Available tickets file to Available_Tickets.txt
* 
* The program is run from the main class with arguments for the input files.
* 
* It Takes the file names as arguements from the command line in the following way:
* 
* java main <Old User Accounts file> <Old Available Tickets File> <Merged Daily Transaction File>
* 
* 
*/

public class Main {

	
	
	private static String[] savedArgs; 									//string array to hold command line arguements
    public static String[] getArgs() {									//used to access the commmand line arguements from other classes
        return savedArgs;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		savedArgs = args;												//save the arguements
		Accounts acct = new Accounts(); 								//create the accounts
		AvailableTickets avail_tickets = new AvailableTickets();		//create the available tickets
		Transaction transacts = new Transaction(acct, avail_tickets);	//create the transactions, process them
		acct.saveCurrentUserAccountsFile();								//save the new Accounts file
		avail_tickets.saveAvailableTicketsFile();						//save the new available tickets file
		
		
	}

}
