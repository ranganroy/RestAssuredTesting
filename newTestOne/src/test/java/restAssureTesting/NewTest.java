package restAssureTesting;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import io.restassured.response.*;

public class NewTest {

	int id;


	@Test(priority=1)
	void listOFUsers() {

		/*
		 * given() -> content type, set cookies, add auth, add param, set headers info etc
		 * when() -> get, post, put, del
		 * then() -> validate status code, extract response, extract headers cookies & response body
		 * 
		 * remote repo url: https://github.com/ranganroy/RestAssuredTesting.git
		 * */

		//Response response;

		// response = RestAssured.get("http://");

		given() 

		.when()
		.get("https://reqres.in/api/user?page=2")

		.then()
		.statusCode(200)
		.body("page", equalTo(2))
		.log().all();


	}
	

	@Test(priority=2)
	void userCreation() {
		//post call

		HashMap <String,String> hash = new HashMap ();
		hash.put("name", "Ran");
		hash.put("job", "Trainer");

		id=	given()
				.contentType("application/json")
				.body(hash)

				.when()
				.post("https://reqres.in/api/users")
				.jsonPath().getInt("id");

		// .then()
		// .statusCode(201)
		// .log().all(); 	}

	@Test(priority=3, dependsOnMethods = {"userCreation"})
	void updateUser() {


		HashMap <String,String> hash = new HashMap ();
		hash.put("name", "Jan");
		hash.put("job", "Chef");


		given()
		.contentType("application/json")
		.body(hash)

		.when()
		.put("https://reqres.in/api/users/"+id)	

		.then()
		.statusCode(200)
		.log().all();

	}

	@Test(priority=4)
	void deleteUser() {

		given()

		.when()
		.delete("https://reqres.in/api/users/"+id)

		.then()
		.statusCode(204)
		.log().all();


	} 
	
	@Test
	void testPojoPost() {
		
		TestPojo pj = new TestPojo();
		pj.setName("urs");
		pj.setJob("Teach");
		
		given()
		.contentType("application/json")
		.body(pj)
		
		.when()
		.post("https://reqres.in/api/users")
		
		.then()
		.statusCode(201)
		.body("name", equalTo("urs"))
		.body("job", equalTo("Teach"))
		.log().all();
		
		
	}

}
