/********************************************************************************************
 * how to build a RESTful API in using Spring MVC with Spring Boot frameworks.              *
 *       																					*
 ********************************************************************************************/

/**
 * 
 * @author DEEPAK
 *
 */


 APIs

	These are the set of APIs you need to build for the system.
 
	Create Moderator
	Resource: /moderators
	Description: Add a moderator to the system.
	Request: 
	POST /moderators (with the following payload in the request body)
	HTTP Headers:
	Content-type: application/json
		{
		 "name": "John Smith",
		 "emailId": "John.Smith@Gmail.com",
		 "password": "secret"
		}
		Response: HTTP Code: 201
		{
	    "id": 504,
	    "createdDate": "2015/03/08 12:36:35",
	    "emailId": "John.Smith@Gmail.com",
	    "password": "secret",
	    "name": "John Smith"
		}

	 View Moderator
		Resource: /moderators/{mod_id}
		Description: View a moderator resource
		Request:
		GET /moderators/501
		Accept: application/json
		Response:
		HTTP Code: 200
		{
		 "id" : "501",
		 "name": "John Smith",
		 "emailId": "John.Smith@Gmail.com",
		 "password": "secret",
		 "created_at" : "2014-09-16T13:28:06.419Z"
		}

	Update Moderator
		Resource: /moderators/{mod_id}
		Description: Update an existing moderator information.
		Request: 
		PUT /moderators/501 (with the following payload in the request body)
		HTTP Headers:
		Content-type: application/json
		{
		 "emailId": "John.Smith2@Gmail.com",
		 "password": "newsecret"
		}
		Response:
		HTTP Code: 201
		{
		 "id" : "501",
		 "name": "John Smith",
		 "emailId": "John.Smith2@Gmail.com",
		 "password": "newsecret",
		 "created_at" : "2014-09-16T13:28:06.419Z"
		}
        
	Create a Poll
		Resource: /moderators/{mod_id}/polls
		Description: Create a new poll
		{
		 "question": "What type of smartphone do you have?",
		 "startat": "2015-02-23T13:00:00.000Z",
		 "expiredat" : "2015-02-24T13:00:00.000Z",
		 "choice": [ "Android", "iPhone" ]
		}
		Response:HTTP Code: 201
		{
		 "id" : "1ADC2FZ",   # Convert long/int to Base 36 for readability
		 "question": "What type of smartphone do you have?",
		 "started_at": "2015-02-23T13:00:00.000Z",
		 "expired_at" : "2015-02-24T13:00:00.000Z",
		 "choice": [ "Android", "iPhone" ]
		}
		
	View a Poll Without Result
		Resource: /polls/{id}
		Description: View a poll.
		{
		 "id" : "1ADC2FZ",
		 "question": "What type of smartphone do you have?",
		 "started_at": "2015-02-23T13:00:00.000Z",
		 "expired_at" : "2015-02-24T13:00:00.000Z",
		 "choice": [ "Android", "iPhone" ]
		}

	View a Poll With Result
		Resource: /moderators/{mod_id}/polls/{Id}
		Description: View a poll with current result.
		{
		 "id" : "1ADC2FZ",
		 "question": "What type of smartphone do you have?",
		 "started_at": "2015-02-23T13:00:00.000Z",
		 "expired_at" : "2015-02-24T13:00:00.000Z",
		 "choice": [ "Android", "iPhone" ],
		 "results": [ 500, 600 ]
		}
		
	List All Polls
		Resource: /moderators/{mod_id}/polls
		Description: List all polls created by the given moderator.
		Response:HTTP Code: 200
		[
		   {
		 "id" : "1001",
		 "question": "What type of smartphone do you have?",
		 "started_at": "2015-02-23T13:00:00.000Z",
		 "expired_at" : "2015-02-24T13:00:00.000Z",
		 "choice": [ "Android", "iPhone" ],
		 "results": [ 500, 600 ]
		 },
		 {
		 "id" : "1002",
		 "question": "Are you a truant?",
		 "started_at": "2015-02-23T13:00:00.000Z",
		 "expired_at" : "2015-02-24T13:00:00.000Z",
		 "choice": [ "Yes", "No" ],
		 "results": [ 30, 70 ]
		 }
		]
		
	Delete a Poll
		Resource: /moderators/{mod_id}/polls/{id}
		Description: Delete a poll
		Response:
		HTTP Code: 204
	
	Vote a Poll
		Resource: /polls/{id}?choice={choice_index}
		Description: Vote a poll
		PUT /polls/1001?choice=0     
		Response:
		HTTP Code: 204
		
 API Validation

Protect your APIs with HTTP Basic Auth for all moderator related endpoints: /mod/* except POST /mod
Username: foo
Password: bar
NOTE: Other /polls/* endpoints should be accessible without any authentication.

How to test your APIs?
You can use either curl command line tool. or GUI tools like Postman or Advanced REST client Chrome plugin.
 