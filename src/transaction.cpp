#include "transaction.h"
#include "Accounts.h"
#include "StringBuilder.h"
#include <string.h>
using namespace std;

//static const string Daily = "/Transaction/Daily/daily.trans";
static const string Daily = "daily.trans";
static const string Users = "/Transaction/Users/current.trans";
static const string Tickets = "/Transaction/Tickets/avail.trans";
stringBuilder sb;

Transaction::Transaction(Accounts& account): acct(account){
	
}

/**
 * create method creates a user account and takes user name and account 
 * type
 */
void Transaction::create(string user_name, string user_type, double credit){
	if(acct.acc_type.find(user_name)==acct.acc_type.end()){
		acct.acc_type[user_name]=user_type;
		acct.avail_credit[user_name]= credit;
		string transaction = sb.Buildstring(1,user_name,user_type,credit);
		writeTransaction(transaction);
		cout << "Account Successfully Created\n";
	}else{
		cout << "Account Already Exists\n";
	}

}

/**
 * writeTransaction method writes the transaction string to the appropriate 
 * transaction file. 
 */
void Transaction::writeTransaction(string transaction){
	
	std::ofstream ofs;
	ofs.open (Daily, std::ofstream::out | std::ofstream::app);
  	ofs << transaction << endl;
  	ofs.close();

}
/**
 * Refund method processes the refund from a sellers account to the buyers account
 *
 */
void Transaction::refund(string buyer, string seller, double amount){
	if(acct.isAdmin()){
		if(acct.acc_type.find(buyer)!=acct.acc_type.end()){
			if(acct.acc_type.find(seller)!=acct.acc_type.end()){
				acct.avail_credit[buyer]+=amount;
				acct.avail_credit[seller]-=amount;
				string transaction = sb.BuildstringRefund(5,buyer,seller,amount);
				writeTransaction(transaction);
			}else{
				cout << "Invalid Seller Name\n";
			}
		}else{
			cout << "Invalid Buyer Name\n";
		}
	}else{
		cout << "Insufficient Privileges\n";
	}
				

}
/**
 * Addcredit method adds credit to a specified users account
 *
 */
void Transaction::addcredit(string user_name, double credit){
	if(acct.isBuyer()){
		if((acct.avail_credit[user_name]+credit)<999999.00){
			acct.avail_credit[user_name]+=credit;
			cout << "Credit Added Successfully\n";
			string transaction = sb.Buildstring(06, user_name, acct.acc_type[user_name], credit);
			writeTransaction(transaction);
		}else{
			cout << "Credit Limit Exceeded\n";
		}
	}else{
		cout << "Insufficient Privileges\n";
	}
}
/**
 * Sell method adds an event and assigns it a specified number of tickets
 * and a specified price.
 *
 */
void Transaction::sell(string event_name, string seller, int tickets, double price){
	string transaction = sb.Buildstring(3, event_name, seller, tickets, price);
	writeTransaction(transaction);
}

void Transaction::buy(string event_name, int tickets, string seller){
	if ((acct.num_tickets[event_name+seller] - tickets) < 0 )
	{
		cout << "Error, Cannot buy more tickets than than available. Wanted:" << tickets << " Avaialable:" << acct.num_tickets[event_name+seller] << "\n";
	} 
	else if ((tickets * acct.price_tickets[event_name+seller]) > acct.avail_credit[acct.current_user]){
		cout << "Error, User does not have enough credit to buy tickets. Cost:" << (tickets * acct.price_tickets[event_name+seller]) << " Credits:" <<acct.avail_credit[acct.current_user] << "\n" ; 
	}
	else
	{
		acct.num_tickets[event_name+seller] = acct.num_tickets[event_name+seller] - tickets;
		acct.avail_credit[acct.current_user] = acct.avail_credit[acct.current_user] - (tickets * acct.price_tickets[event_name+seller]);
		string transaction = sb.Buildstring(4, event_name, seller, tickets, acct.price_tickets[event_name+seller]);
		writeTransaction(transaction);
		transaction = sb.BuildstringRefund(5,seller,acct.current_user,(tickets * acct.price_tickets[event_name+seller]));
		writeTransaction(transaction);
	}
	
				
}
/**
 * Remove method deletes a users account and all tickets and events
 * associated with that account.
 *
 */
 
void Transaction::remove(string user_name){
	if(user_name.compare(acct.current_user)==0){
		cout << "Cannot remove own account\n";
	}else if(acct.acc_type.find(user_name)!=acct.acc_type.end()){
		//remove from available tickets log as well
		double credit = acct.avail_credit[user_name];
		string user_type = acct.acc_type[user_name];
		string transaction = sb.Buildstring(2,user_name,user_type,credit);
		acct.remove(user_name);
		writeTransaction(transaction);
	}else{
		cout << "Account Does Not Exist\n";
	}
}

/**
 * login method checks to see if the user account exists and then
 * assigns the account to be the current user
 */
void Transaction::login(string user_name){
	acct.current_user = user_name;
	if(acct.isValidAccount(acct.current_user)){
		acct.logged_in = true;
		cout << "Login Successful\n";
	
	}else{
		acct.current_user = "";
		acct.logged_in = false;
		cout << "Invalid Account\n";
	}
}
/**
 * logout method checks if there is a user logged in and if they are,
 * they get logged out.
 */
void Transaction::logout(){
	//write to transaction file
	string transaction = sb.Buildstring(0,acct.current_user, acct.acc_type[acct.current_user], acct.avail_credit[acct.current_user]);
	writeTransaction(transaction);
	acct.current_user = "";
	acct.logged_in = false;
	cout << "Logout Successful\n";
	
}

