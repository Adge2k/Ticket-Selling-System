#pragma once
#define TRANSACTION_H 
#include <iostream>
#include "Accounts.h"

using namespace std;
/**
 * Transaction class handles all transaction made in the system
 *
 */
 
class Transaction{

	public:
		Accounts& acct;
		
		Transaction(Accounts& account);
		void login(string user_name);
		void logout();
		void buy(string event_name, int tickets, string seller);
		void refund(string buyer, string seller, double amount);
		void addcredit(string user_name, double credit);
		void sell(string event_name, string seller, int tickets, double price);
		void remove(string user_name);
		void create(string user_name, string user_type, double credit);
		void writeTransaction(string transaction);
		
};