package stepDefinitions;
import org.hamcrest.Matchers;
import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;


public class RoombaTestCases {

	Response response;

	int x,y,roomSizeX,roomSizeY;
	String instructionDirections;

	// Set up api connection for Robot hoover api

	@Given("Establish connection with Roomba {string},{string},{string},{string},{string}.")
	public void establish_connection_with_Roomba(String apiURL, String roomSize, String coords, String patches, String instructions) {

		if(coords.length()>=5) {
			x = Character.getNumericValue(coords.charAt(1));
			y = Character.getNumericValue(coords.charAt(3));
		}
		if(roomSize.length()>=5) {
			roomSizeX = Character.getNumericValue(roomSize.charAt(1));
			roomSizeY = Character.getNumericValue(roomSize.charAt(3));
		}
		instructionDirections = instructions;

		response =
				given()
				.header("Content-Type","application/json")
				.body("{ \"roomSize\" : "+roomSize+", \"coords\" : "+coords+", \"patches\" : "+patches+", \"instructions\" : \""+instructions+"\" }")			
				.when()
				.post(apiURL);

	}

	//Method to test success response 200 

	@Then("Validate if the response code is success.")
	public void validate_if_the_response_code_is() {

		System.out.println("Response Code: "+response.getStatusCode());
		System.out.println("Response:"+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(),200);	


	}

	//Method to test url not found 404 and error response

	@Then("Validate if the response code is for url not found.")
	public void validate_if_the_response_code_is_for_url_not_found() {

		System.out.println("Response Code: "+response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(),404);	
		System.out.println(response.getBody().asString());
		String error = response.jsonPath().get("error");
		Assert.assertEquals(error, "Not Found");


	}

	//Method to test url not found 400 and error response

	@Then("Validate if the response code is Bad Request.")
	public void validate_if_the_response_code_is_Bad_Request() {
		System.out.println("Response Code: "+response.getStatusCode());
		System.out.println("Response:"+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(),400);
		String error = response.jsonPath().get("error");
		Assert.assertEquals(error, "Bad Request");

	}

	//Method to set up connection for api without passing required headers

	@Given("Establish connection with Roomba with no headers {string},{string},{string},{string},{string}.")
	public void establish_connection_with_Roomba_with_no_headers(String apiURL, String roomSize, String coords, String patches, String instructions) {

		response =
				given()
				.body("{ \"roomSize\" : "+roomSize+", \"coords\" : "+coords+", \"patches\" : "+patches+", \"instructions\" : \""+instructions+"\" }")			
				.when()
				.post(apiURL);
	}

	//Method to get response for api without headers 415 and error response

	@Then("Validate if the response code is Unsupported Media Type.")
	public void validate_if_the_response_code_is_Unsupported_Media_Type() {

		System.out.println("Response Code: "+response.getStatusCode());
		System.out.println("Response:"+response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(),415);	
		String error = response.jsonPath().get("error");
		Assert.assertEquals(error, "Unsupported Media Type");

	}

	//Method to validate response schema and response values for valid request

	@Then("Validate if the response schema is valid {string},{string}.")
	public void validate_if_the_response_schema_is_valid(String expectedCoords, String expectedPatches) {


		response.then()
		.assertThat()
		.body("patches", Matchers.notNullValue())
		.body("coords", Matchers.notNullValue())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Response.json"));


		int x = Character.getNumericValue(expectedCoords.charAt(1)); //Typecasting Character to integer
		int y = Character.getNumericValue(expectedCoords.charAt(3));

		ArrayList<Integer> responseCoords = new ArrayList<Integer>();		 
		ArrayList<Integer> expectedCoordsList = new ArrayList<Integer>();
		expectedCoordsList.add(x);
		expectedCoordsList.add(y);
		responseCoords = response.jsonPath().get("coords");
		String responsePatches = Integer.toString(response.jsonPath().get("patches"));

		Assert.assertEquals(responseCoords, expectedCoordsList);
		Assert.assertEquals(responsePatches, expectedPatches);

	}


	//Method to validate response time


	@Then("Validate if the response time is valid.")
	public void validate_if_the_response_time_is_valid() {

		Long time = get().getTime();
		System.out.println(time);
		Assert.assertTrue(time<100);
	}

	//Method to validate response headers

	@Then("Validate if the all headers are valid.")
	public void validate_if_the_all_headers_are_valid() {

		System.out.println(response.headers());
		String contentType = response.header("Content-Type");
		System.out.println(contentType);
		Assert.assertEquals(contentType /* actual value */, "application/json;charset=UTF-8" /* expected value */);

	}

}
