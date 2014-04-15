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
 * Available tickets class handles the information regarding the available tickets
 * 
 *
 */
public class AvailableTickets {
	private HashMap<Ticket, Integer> avail_tickets = new HashMap<Ticket, Integer>();
	/**
	 * Available tickets constructor sets the available tickets class
	 */	
	public AvailableTickets(){
		this.filereader();
	}
	
	/**
	 * create makes the event by adding the ticket object and number of available tickets 
	 * to the available tickets hashMap
	 * 
	 * @param ticket
	 * @param number_of_tickets
	 */	
	public void create(Ticket ticket,int number_of_tickets){
		if (number_of_tickets < 0) {
			System.out.println("ERROR: An event cannot have a negetive amount of tickets!  Event:" + ticket.getEventName() + " Seller:" + ticket.getUserName() + "NumberofTickets:" + number_of_tickets);
		}
		else {
			avail_tickets.put(ticket, number_of_tickets);
		}
	}
	/**
	 * delete removes the Event
	 * 
	 * @param ticket
	 */
	public void delete(Ticket ticket) {
		avail_tickets.remove(ticket);
	}
	/**
	 * setAvailableTickets sets the event by updating the ticket object with the number of available tickets 
	 * to the available tickets hashMap
	 * 
	 * @param ticket
	 * @param number_of_tickets
	 */
	public void setAvailableTickets(Ticket ticket, int number_of_tickets){
		avail_tickets.put(ticket, number_of_tickets);
	}
	
	/**
	 * returns the amount of available tickets for the event
	 * 
	 * @param ticket
	 */
	public Integer getAvailableTickets(Ticket ticket){
		return avail_tickets.get(ticket);
	}
	
	/**
	 *  This method saves the new available tickets changes to a file
	 */
	public void saveAvailableTicketsFile(){
		Writer writer = null;
		
		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		    new FileOutputStream("Available_Tickets.txt"), "utf-8"));	//output file
		    for (java.util.Map.Entry<Ticket, Integer> entry  : avail_tickets.entrySet()) { //turns the hashmap into entries
		    	writer.write(entry.getKey().getEventName() + "_" + entry.getKey().getUserName()+ "_" + Integer.toString(entry.getValue()) + "_" + Double.toString(entry.getKey().getPricePerTicket()));		//writes the available ticket line to the output file
		    }
		} catch (IOException ex) {
		  // report
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}
		
	}
	
	/**
	 * filereader reads from the current available tickets log file and then calls the create method to 
	 * add the tickets to the associated hashMAps
	 * 
	 */
	public void filereader(){
		String available_tickets_file = "availabletickets.txt";	// sets the tickets file to the available tickets files location
		BufferedReader br = null;
		String line = "";
		String Delimiter = "_";	// sets the delimiter to "_"
	 
		try {
	 
			br = new BufferedReader(new FileReader(available_tickets_file));
			while ((line = br.readLine()) != null) {
	 				String[] avail_tickets_line = line.split(Delimiter);	//split the line using the delimeter
	 			if (avail_tickets_line[0] != null && avail_tickets_line[1] != null && avail_tickets_line[2] != null && avail_tickets_line[3] != null) {		//check for null values
	 				Ticket ticket = new Ticket(avail_tickets_line[0], avail_tickets_line[1], Double.parseDouble(avail_tickets_line[3]));	//create a new ticket object from the line
					if (this.avail_tickets.containsKey(ticket)){	//checks to see if this particular ticket exits already (Same seller name, event name and ticket price
						this.create(ticket, this.avail_tickets.get(ticket) + Integer.parseInt(avail_tickets_line[2])); 	//adds to the amount of tickets
					}
					else {
						this.create(ticket, Integer.parseInt(avail_tickets_line[2]));	// creates ticket
					}
				}
	 			else {
					System.out.println("ERROR: Bad input, missing fields. File:" + available_tickets_file);		//write error if input is invalid
				}
				
				
				
	 
			}
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();	// prints errors for file not found exception

		} catch (IOException e) {
			e.printStackTrace();	// prints IO errors
		} finally {
			if (br != null) {
				try {
					br.close();	// closes the buffered reader

				} catch (IOException e) {
					e.printStackTrace();	// prints IO errors
				}
			}
		}
		
	}
}
