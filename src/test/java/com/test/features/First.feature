Feature: My first API Test with sample API

 @Mytest
Scenario: Get Locations
Given the api is up and running for "http://cmapi.bananaappscenter.com/"
When a user performs a get request to "api/Location/LocationDetails"
Then the response code should be 200
And I should see json response with pairs on the filtered "Msg" node
	|Message   | Success Location Details|
	|StatusCode| 200                     |
	|isError   | false                   |
	|isSuccess | true					 |
	
	