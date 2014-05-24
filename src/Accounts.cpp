#include <string>
#include <unordered_map>
#include <iostream>
#include <fstream>
#include <sstream>
#include <list>
#include "Accounts.h"
#include "transaction.h"
//#include "transaction.h"

using namespace std;
/**
 *	The Accounts class is used for creating and removing accounts from the system as well
 *	as determining the type of an account (admin, full standard, buy statndard, sell standard)
 *
 */


static const string admin = "AA";				//code for admin type
static const string full_standard = "FS";		//code for full standard type
static const string buy_standard = "BS";		//code for buy standard type
static const string sell_standard = "SS";		//code for sell standard type
//Transaction trans;



Accounts::Accounts(){
	current_user = "";
	logged_in = false;
	string line;
	//ifstream accounts("../Transaction/Users/current.trans");
	ifstream accounts("current.trans");
	//ifstream tickets("/Transaction/Tickets/avail.trans");
	ifstream tickets("avail.trans");
	
	if(accounts.is_open()){

		string line;

		while (getline(accounts, line))
		{
			istringstream ss(line);
			string user_name, user_type, user_credit;
			getline(ss, user_name, '_');
			getline(ss, user_type, '_');
			getline(ss, user_credit, '_');
			create(user_name,user_type, atof(user_credit.c_str()));
		}
	}
	else
		cout << "failed to open avail.trans\n";

	if(tickets.is_open()){

		string line;

		while (getline(tickets, line))
		{
			istringstream ss(line);
			string userName, eventName, number_of_tickets, price_per_ticket;
			getline(ss, eventName, '_');
			getline(ss, userName, '_');
			getline(ss, number_of_tickets, '_');
			getline(ss, price_per_ticket, '_');
			int num_tickets = atoi(number_of_tickets.c_str());
			string combined_name = eventName + userName;
			createTickets(combined_name,num_tickets, atof(price_per_ticket.c_str()));
		}
	}
	else
		cout << "failed to open current.trans\n";


	for (unordered_map<string, string>::iterator it = acc_type.begin(); it != acc_type.end(); ++it) 
		std::cout << " [" << it->first << ", " << it->second << "]\n"; 

	for (unordered_map<string, int>::iterator it = num_tickets.begin(); it != num_tickets.end(); ++it) 
		std::cout << " [" << it->first << ", " << it->second << "]\n"; 
}

void Accounts::create(string user_name, string type, double credit){
// This method creates a new user. It does this by adding the username to the account type and available credit maps.
	if(acc_type.find(user_name)== acc_type.end()){		//check to see if the user is already created
		acc_type[user_name]=type;						//add username to the account type map
		avail_credit[user_name] = credit;				//add username to the available credit map
	}else{
		cout << " User Account already Exists\n";
	}

}

void Accounts::createTickets(string event_seller_name, int num_of_tickets, double price_per_ticket){
	if(num_tickets.find(event_seller_name)== num_tickets.end()){		//check to see if the user is already created
		num_tickets[event_seller_name] = num_of_tickets;
		price_tickets[event_seller_name] = price_per_ticket;
	}else{
		cout << " Ticket already Exists\n";
	}
}

void Accounts::remove(string user_name){
//This method removes a user from the system by erasing the username in the account type and available credit maps.
	if(acc_type.find(user_name)!= acc_type.end()){		//check to see if the username exists
		acc_type.erase(user_name);						//remove the username 
		avail_credit.erase(user_name);					//remove the username
		cout << "Account removed Successfully\n";		
	}else{
		cout << "User Account does not exist\n";
	}
}

bool Accounts::isValidAccount(string user_name){
// This method checks to see if a username exists, returns true if it does.
	if(acc_type.find(user_name)!=acc_type.end())
		return true;
	return false;
}

bool Accounts::isAdmin(){
//this method checks if the account type is admin, returns true if it is.
	if(acc_type[current_user] == admin)
		return true;
	return false;
}

bool Accounts::isBuyer(){
//this method checks if the account type is a buyer, returns true if it is.
	if((acc_type[current_user] == buy_standard)||(acc_type[current_user] == full_standard)||(acc_type[current_user] == admin))		//buy standard, full standard and admin are all valid buyer types
		return true;
	return false;
}

bool Accounts::isSeller(){
//this method checks if the account type is a seller, returns true if it is.
	if((acc_type[current_user] == sell_standard)||(acc_type[current_user] == full_standard)||acc_type[current_user] == admin)		//sell standard, full standard and admin are all valid seller types
		return true;
	return false;
}
