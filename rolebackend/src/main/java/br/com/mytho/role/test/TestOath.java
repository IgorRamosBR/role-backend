package br.com.mytho.role.test;

import org.junit.Test;
import static io.restassured.RestAssured.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestOath {
	
	@Test
	public void TestIdFromEvent(){
		when().
			get("http://localhost:8080/rolebackend/event/{id}",1).
		then().
			statusCode(200).
			body("name", equalTo("trapin"));
	}
	
	@Test
	public void TestAccessToken(){
		given().
			auth().
			preemptive().
			basic("mobile-client", "08282424-432a-11e6-beb8-9e71128cae77").
			
		when().
			post("http://localhost:8080/rolebackend/oauth/token").
		then().statusCode(200);
		
	}

}
