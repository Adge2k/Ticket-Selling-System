
public class Ticket {

	public String user_name;	// sellers username
	public String event_name;	// event name
	public Double price_per_ticket;	 // cost per ticket
	
	/**
	 * Ticket constructor sets the user name, event name and price of the Ticket
	 * 
	 * @param event_name
	 * @param user_name
	 * @param price_per_ticket
	 */
	public Ticket(String event_name,String user_name,Double price_per_ticket){
		this.user_name = user_name;		// sets the user name
		this.event_name = event_name;	// sets the event name
		this.price_per_ticket = price_per_ticket;	// sets the cost per ticket
	}
	
	/**
	 * Returns the username
	 */
	public String getUserName(){
		return this.user_name;
	}
	
	/**
	 * Returns the event name
	 */
	public String getEventName(){
		return this.event_name;
	}
	
	/**
	 * Returns the price per ticket
	 */
	public Double getPricePerTicket() {
		return this.price_per_ticket;
	}
	
	/**
	 * Overrides the equals to allow comparison between two Tickets objects
	 * 
	 */
	@Override
	public boolean equals(Object other){
	    if (other == null) {return false;}
	    if (other == this) return true;
	    if (!(other instanceof Ticket)){return false;}	// checks if the Objects are Tickets
	    Ticket otherTicket = (Ticket)other;
	    if (!(this.user_name.equals(otherTicket.user_name))) {	// checks if the user names are not equal
			return false;
		}
	    if (!(this.event_name.equals(otherTicket.event_name))) {	// checks if the event names are not equal
			return false;
		}
	    if (!(this.price_per_ticket.equals(otherTicket.price_per_ticket) )) {	// checks if the price of tickets are not equal
			return false;
		}
	    
		return true;	// returns true if Tickets are equal
	}
	
	/**
	 * Overrides the hashCode method to perform a custom hashing of the ticket information
	 * by hashing the price, event name, and ticket name.
	 * 
	 */
	@Override
	public int hashCode()
	{
				int hash = 7;
				int var_code;
				long bits = Double.doubleToLongBits(price_per_ticket);	// converts the ticket price to long bits
				var_code = (int)(bits ^ (bits >>> 32));	// uses bitwise operator >>> to shift leftmost zero to the right
				hash = 31 * hash + var_code;
				hash = 31 * hash + (null == user_name ? 0 : user_name.hashCode());	// if username is null returns 0 else hashcode
				hash = 31 * hash + (null == event_name ? 0 : event_name.hashCode());	// if event name is null return 0 else hashcode
				return hash;
	}
}
