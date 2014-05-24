#include <iostream>
#include <string.h>
#include "Accounts.h"
#include "transaction.h"
//#include "StringBuilder.h"
using namespace std;
/**
 * This program is a ticket selling system which allows users to login create 
 * events, sell and buy tickets.
 */

 
int main(int argc, char* argv[]){
	Accounts acct;
	Transaction trans(acct);
	string input;
	string username = "";
	string buyer = "";
	string seller = "";
	string event = "";
	int num_tickets = 0;
	double amount = 0.00;
	string account_type = "";
	bool moresales = true;
	double add_credit_session = 0;
	cout << "Welcome\n";
	while(1){
			cout << "Please Enter a transaction\n";
			cin >> input;
			if(!acct.logged_in){
				//cin >> input;
				if(input.compare("login")==0){
					cout << "Enter Username\n";
					cin >> input;
					trans.login(input);
				}else{
					cout << "Invalid transaction\n";
				}
			}else{
				if((input).compare("buy")==0){
					cout << "Enter seller's name\n";
					cin >> seller;
					cout << "Enter event name\n";
					cin >> event;
					while (acct.num_tickets.find(event+seller)== acct.num_tickets.end())
					{
						cout << "Invalid seller's or event name\n";
						cout << "Enter seller's name\n";
						cin >> seller;
						cout << "Enter event name\n";
						cin >> event;
					}
					cout << "Enter number of tickets to buy\n";
					cin >> input;
					while (atoi(input.c_str()) > 4)
					{
						cout << "Error Cannot buy more than 4 tickets\n";
						cin >> input;
					}
					cout << "Cost per ticket =" << acct.price_tickets[event+seller] << " Total price=" << (acct.price_tickets[event+seller] * atoi(input.c_str())) << "\n Confirm buy? y/n \n";
					string confirm;
					cin >> confirm;
					if (confirm.compare("y") == 0)
					{
						trans.buy(event, atoi(input.c_str()), seller);
					} 
				
			
				}else if((input).compare("sell")==0){
					if((acct.isSeller() && moresales) ){
						if(moresales){
							cout << "Enter Event Name\n";
							cin >> event;
							while (event.size() > 25)
							{
								cout << "Event name too long. Enter Event Name (max 25 characters)\n";
								cin >> event;
							}
							cout << "Enter number of tickets to be sold\n";
							cin >> num_tickets;
							while (num_tickets > 100)
							{
								cout << "Too many tickets. Enter number of tickets to be sold (max 25)\n";
								cin >> num_tickets;
							}
							cout << "Enter cost per ticket\n";
							cin >> amount;
							while (amount > 999.99)
							{
								cout << "Price to high. Enter cost per ticket (max $999.99)\n";
								cin >> amount;
							}
							trans.sell(event, acct.current_user, num_tickets, amount);
							moresales = false;
						}else{
							cout << "no further transactions can be accepted on new tickets for sale until the next session\n";
						}
					}else{
						cout << "Must have a seller's account to sell";
					}
				}else if((input).compare("logout")==0){
					trans.logout();
				}else if((input).compare("create")==0){
					if(acct.isAdmin()){
						cout << "Enter username\n";
						cin >> input;
						while (input.size() > 15)
						{
							cout << "Name too long, please enter username (max 15 characters)\n";
							cin >> input;
						}
						username = input;
						cout << "Enter Account type\n";
						cin >> input;
						while (input.compare("FS") != 0 &&input.compare("BS") != 0 &&input.compare("SS") != 0 && input!= "AA")
						{
							cout << "Account type must be AA, BS, SS or FS\n";
							cin >> input;
						}
						account_type = input;
						cout << "Enter Available credit\n";
						cin >> amount;
						while (amount > 999999 || amount < 0)
						{
							cout << "Invalid amount, please enter a value between 0 and 999,999\n";
							cin >> amount;
						}
						trans.create(username, account_type, amount);
						}
				}else if(input.compare("refund")==0){
						cout << "Enter buyer name\n";
						cin >> input;
						buyer = input;
						cout << "Enter sellers name\n";
						cin >> input;
						seller = input;
						cout << "Enter amount to transfer\n";
						cin >> amount;
						trans.refund( buyer, seller, amount);
				}else if(input.compare("addcredit")==0){
					if(acct.isAdmin()){
						cout << "Enter Username\n";
						cin >> username;
					}else{
						username = acct.current_user;
					}
					if(acct.acc_type.find(username)!=acct.acc_type.end()){
						cout << "Enter amount of credit to add\n";
						cin >> amount;
						if (amount > 1000.00 || (add_credit_session + amount) > 1000.00)
						{
							cout << "Maximum of credit that can be added in a session $1000.00 . Enter amount of credit to add. \n";
							cin >> amount;
						} 
						else
						{
							trans.addcredit(username, amount);
							add_credit_session += amount;
						}
					
					}else{
						cout << "Account does not exist\n";
					}
				}else if(input.compare("delete")==0){
					if(acct.isAdmin()){
						cout << "Enter Account to be deleted\n";
						cin >> username;
						trans.remove(username);
					}
				}
				else{
					cout << "Invalid Selection\n";
				}
		
		}
	
	}
	return 0;
}
