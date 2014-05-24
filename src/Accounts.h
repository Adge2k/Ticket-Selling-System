#pragma once
#define ACCOUNTS_H




#include <string>
#include <unordered_map>
#include <iostream>
#include <fstream>

using namespace std;
/**
 *	The Accounts class is used for creating and removing accounts from the system as well
 *	as determining the type of an account (admin, full standard, buy statndard, sell standard)
 *
 */

class Accounts{
	private:
		/*static const string admin;				//code for admin type
		static const string full_standard;		//code for full standard type
		static const string buy_standard;		//code for buy standard type
		static const string sell_standard;		//code for sell standard type*/
		
	
	public:
		unordered_map<string, string> acc_type;			//map of Username to account type
		unordered_map<string, double> avail_credit;		//map of username to available  credit
		unordered_map<string, int> num_tickets;		//map of username to available  credit
		unordered_map<string, double> price_tickets;		//map of username to available  credit
		Accounts();
		string current_user;						//
		bool logged_in;							//used for determining if an account is logged in
		void create(string user_name, string type, double credit);	//method for creating accounts
		void remove(string user_name);								//method for removing (deleteing) accounts
		bool isAdmin();												//method for checking admin account type
		bool isSeller();											//method for checking seller account type
		bool isBuyer();												//method for checking buyer account type
		bool isValidAccount(string user_name);										//method for checking if account exists
		void createTickets(string event_seller_name, int num_of_tickets, double price_per_ticket);
		


};
