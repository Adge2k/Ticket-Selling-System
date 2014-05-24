#pragma once
#define STRINGBUILDER_H

#include <string>
#include <iostream>
#include <sstream>
using namespace std;
/**
 *	stringBuilder class builds strings to be stored in the transaction files, user account files,
 *	and available ticket files.
 *
 */
class stringBuilder{
	#define type1 = "XX_UUUUUUUUUUUUUUU_TT_CCCCCCCCC";						//transaction code for create, delete, addcredit and end of session
	#define type2 = "XX_UUUUUUUUUUUUUUU_SSSSSSSSSSSSSSS_CCCCCCCCC";			//transaction code for refund
	#define type3 = "XX_EEEEEEEEEEEEEEEEEEE_SSSSSSSSSSSSS_TTT_PPPPPP";		//transaction code for sell or buy
	#define user_acc = "UUUUUUUUUUUUUUU_TT_CCCCCCCCC";						//code for current accounts
	#define avail_tickets = "EEEEEEEEEEEEEEEEEEE_SSSSSSSSSSSSS_TTT_PPPPPP";	//transaction code for tickets to an  event
	
	public:
		string Buildstring (int code, string username, string user_type, double avail_credit);				//Used to create the transaction code for create, delete, addcredit and end of session
		string BuildstringRefund (int code, string buyer, string seller, double ref_credit);				//Used to create the transaction code for a refund
		string Buildstring (int code, string event_name, string seller, int tickets, double ticket_price);	//Used to create the transaction code selling or buying a ticket
		string Buildstring (string user_name, string user_type, double avail_cradit);						//Used to create the account code for the accounts list
		string Buildstring (string event_name, string seller, int tickets, double ticket_price);			//Used to create the codes for available tickets
		string PadUnderScores(string text, int code_length);
		string PadZeros(double avail_credit, int price_length);
		string DeBuildstring(string trans_code, int type);
		string intToString(int Number);
};