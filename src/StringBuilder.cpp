#include "StringBuilder.h"
string stringBuilder::Buildstring (int code, string username, string user_type, double avail_credit){
// this method creates the transaction code create, delete, addcredit and end of session transactoins
	
	string temp = "0";
	temp +=  intToString(code);						//add code
	temp +=  "_";
	temp +=  PadUnderScores(username,15);		//add username
	temp +=  "_";
	temp +=  user_type;						//add uer type
	temp +=  "_";
	temp +=  PadZeros(avail_credit, 9);		//add available credit
	

	return temp;										//return the transaction code
}

string stringBuilder::BuildstringRefund (int code, string buyer, string seller, double ref_credit){
// this method creates the transaction code for a refund
	
	string temp = "0";
	temp +=  intToString(code);						//add code
	temp +=  "_";
	temp +=  PadUnderScores(buyer,15);			//add buyer name
	temp +=  "_";
	temp +=  PadUnderScores(seller,15);		//add seller name
	temp +=  "_";
	temp +=  PadZeros(ref_credit, 9);			//add refund amount
	
	
	return temp;
}


string stringBuilder::Buildstring (int code, string event_name, string seller, int tickets, double ticket_price){
// this method creates the transaction code for selling or buying a ticket
	
	string temp = "0";
	temp +=  intToString(code);						//add code					
	temp +=  "_";
	temp +=  PadUnderScores(event_name,25);	//add event name
	temp +=  "_";
	temp +=  PadUnderScores(seller,15);		//add seller name
	temp +=  "_";
	temp +=  PadZeros((double)tickets, 3);		//add number of tickets
	temp +=  "_";
	temp +=  PadZeros(ticket_price, 6);		//add ticket price
	
	
	return temp;

}
string stringBuilder::Buildstring (string user_name, string user_type, double avail_credit){
	string temp;
	temp +=  PadUnderScores(user_name,25); 	//add user name
	temp +=  "_";
	temp +=  user_type;						//add user type
	temp +=  "_";
	temp +=  PadZeros(avail_credit,9);			//add available credit
	
	return temp;

}

string stringBuilder::Buildstring (string event_name, string seller, int tickets, double ticket_price){
	string temp;
	temp +=  PadUnderScores(event_name,25);	//add event name
	temp +=  "_";
	temp +=  PadUnderScores(seller,15);		//add seller name
	temp +=  "_";
	temp +=  PadZeros((double)tickets, 3);		//add number of tickets
	temp +=  "_";
	temp +=  PadZeros(ticket_price, 6);		//add ticket price
	
	
	return temp;
}

string stringBuilder::PadUnderScores(string text, int code_length){
	string temp = "";
	temp +=  text;								//add text	
	for(int i =0; i<code_length-text.length(); i++) //pad with underscores
		temp +=  " ";
		
	return temp; 									//return the string
}

string stringBuilder::PadZeros(double avail_credit, int price_length){
	string temp = "";
	for(int i=0; i<price_length-intToString(avail_credit).length(); i++)			//create temporary string of padded 0s to the available credit
		temp +=  "0";
	temp +=  intToString(avail_credit);						//append the padded 0s to the available credit
	
	return temp;
}

string stringBuilder::intToString(int Number){
	string Result;//string which will contain the result

	stringstream convert; // stringstream used for the conversion

	convert << Number;//add the value of Number to the characters in the stream

	Result = convert.str();//set Result to the content of the stream
	return Result;
}