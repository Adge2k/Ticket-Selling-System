
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;


/**
 * 
 * This class handles all the User account operations. This includes reading the old current user accounts file,
 * creating, deleting, and modifying accounts. Its consists of two hashmaps that map the user name to its user type 
 * and current balance
 *
 */
public class Accounts {
	
	
	private HashMap<String, String> acc_type = new HashMap<String, String>();		//Hashmap for the account type	
	private HashMap<String, Double> avail_credit = new HashMap<String, Double>();	//Hashmap for the users credit
	
	/**
	 * Constructor for the Accounts class
	 */	
	 public Accounts(){
		this.filereader(); //call the file reader immediatly
				
	}

	/**
	 * create adds a new user to the acc_type and avail_credit hashMaps
	 * 
	 * @param user_name
	 * @param acc_type
	 * @param avail_credit
	 */
	public void create(String user_name, String acc_type, Double avail_credit ){
		if (acc_type.contains(user_name)) { //Check to see if the user already exits
			System.out.println("ERROR: User account " + user_name + " already exists!"); 	//if it does throw error
		}
		else {
			this.acc_type.put(user_name, acc_type); 			//add the user
			this.avail_credit.put(user_name, avail_credit);
		}
	}
	
	/**
	 * delete removes the specified user from the lists of available users
	 * 
	 * @param user_name
	 */
	public void delete(String user_name){
		this.acc_type.remove(user_name);		//remove the suer from both hashmaps
		this.avail_credit.remove(user_name);
	}
	
	/**
	 * getAvailableCredit returns the users available credit
	 * 
	 * @param user_name
	 * @return user_name
	 */
	public double getAvailableCredit(String user_name){
		return this.avail_credit.get(user_name);	
	}
	
	/**
	 * setAvailableCredit sets the available credit for the specified user
	 * 
	 * @param user_name
	 * @param credit
	 */
	public void setAvailableCredit(String user_name,Double credit){
		this.avail_credit.put(user_name, credit);
	}
	
	/**
	 * returns the account type hashmap
	 * @return acc_type
	 */
	public HashMap<String, String> getAccountTypeHashmap(){
		return acc_type;
	}
	
	/**
	 * returns the available credits hashmap
	 * @return avail_credit
	 */
	public HashMap<String, Double> getAvailableCreditHashmap(){
		return avail_credit;
	}
	
	/**
	 * Saves the current user file with all the changes to a file.
	 */
	public void saveCurrentUserAccountsFile(){	
			Writer writer = null;	//create write
	
			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			    new FileOutputStream("Current_User_Accounts.txt"), "utf-8")); //outputfile named
			    for (java.util.Map.Entry<String, String> entry  : acc_type.entrySet()) {
			    	writer.write(entry.getKey() + "_" + entry.getValue()+ "_" + avail_credit.get(entry.getKey())); 	//write the user line to the file
			    }
			} catch (IOException ex) {
			  // report
			} finally {
			   try {writer.close();} catch (Exception ex) {}
			}
	}
	
	
	
	/**
	 * filereader reads from the current users log file and then calls the create method to 
	 * add the users to the associated hashMAps
	 * 
	 */
	public void filereader(){
		String accounts_file = "test.txt";	// current users file
		BufferedReader br = null;
		String line = "";
		String Delimiter = "[_]+";	// sets the delimiter to "_"
	 
		try {
	 
			br = new BufferedReader(new FileReader(accounts_file));	// opens the current users account file
			while ((line = br.readLine()) != null) {
				String[] account_line = line.split(Delimiter); 	// splits the string by the delimiter
				if (account_line[0] != null && account_line[1] != null && account_line[2] != null)
					this.create(account_line[0], account_line[1], Double.parseDouble(account_line[2]));	//creates the account
				else {
					System.out.println("ERROR: Bad input, missing fields. File:" + accounts_file);
				}
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();	// prints errors for file not found exception
		} catch (IOException e) {
			e.printStackTrace();	// prints IO errors
		} finally {
			if (br != null) {
				try {
					br.close();	//closes the buffered reader
				} catch (IOException e) {
					e.printStackTrace();	// prints IO errors
				}
			}
		}
		
	}
	
}
