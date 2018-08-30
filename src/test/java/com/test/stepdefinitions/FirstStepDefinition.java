package com.test.stepdefinitions;

import com.jayway.restassured.response.Response;
import com.test.util.ResponseHolder;
import java.util.LinkedHashMap;
import java.util.Map;
import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.jayway.restassured.RestAssured;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;

public class FirstStepDefinition {
	String url;
	Response response;
	String responseMap;
	Map<String,String> query;

	@Given("^the api is up and running for \"([^\"]*)\"$")
	public void the_api_is_up_and_running_for(String url) throws Throwable {
	    this.url=url;
	    response=RestAssured.given().when().get(url);
	    System.out.println("inside given=============");
	    Assert.assertEquals(response.getStatusCode(), 200);
	}

	@When("^a user performs a get request to \"([^\"]*)\"$")
	public void a_user_performs_a_get_request_to(String apiURI) throws Throwable {
	  this.url=this.url+apiURI;
	  if(query==null){
		  response=RestAssured.given().when().get(this.url);
	  }else{
		  response=RestAssured.given().queryParameters(query).when().get(this.url);
	  }
	  ResponseHolder.setResponse(response);
	}

	@Then("^the response code should be (\\d+)$")
	public void the_response_code_should_be(int expResponseCode) throws Throwable {
	 Assert.assertEquals(ResponseHolder.getResponseCode(), expResponseCode);   
	}

	@Then("^I should see json response with pairs on the filtered \"([^\"]*)\" node$")
	public void i_should_see_json_response_with_pairs_on_the_filtered_node(String filter, DataTable table) throws Throwable {
		Map<String,String> query=new LinkedHashMap<String,String>();
		for(DataTableRow row:table.getGherkinRows()){
			query.put(row.getCells().get(0), row.getCells().get(1));
			
		}
		System.out.println("The test values are:"+query);
		//ObjectReader reader=new ObjectMapper().;response.jsonPath().getMap(filter);
		/*@SuppressWarnings("deprecation")
		ObjectReader reader=new ObjectMapper().reader(Map.class);
		responseMap=reader.readValue(ResponseHolder.getResponseBody());
		responseMap=(Map<String,Object>)responseMap*/
		//JsonPath jsonPath=response.jsonPath();
		Map<String,Object> msg=response.jsonPath().getMap(filter);
		System.out.println("The node is---"+msg);
		//String node=jsonPath.get(filter);
		for(String key:query.keySet()){
			System.out.println(msg.containsKey(key));
			Assert.assertTrue(msg.containsKey(key));
			Assert.assertEquals(query.get(key), msg.get(key).toString());
			System.out.println(query.get(key)+ msg.get(key).toString());
		}
	    
	}
}
