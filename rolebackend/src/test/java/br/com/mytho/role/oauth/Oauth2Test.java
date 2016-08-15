package br.com.mytho.role.oauth;

import static io.restassured.RestAssured.given;
//import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class Oauth2Test {
	
	@Test
	public void basicAuthentication(){
		given().
		param("scoped", "public-area").
		auth().
		preemptive().
		basic("mobile-client", "08282424-432a-11e6-beb8-9e71128cae77").
		when().
			post("http://localhost:8080/rolebackend/oauth/token").
		then().statusCode(200);
	}
	
	@Test
	public void authenticationWithMethodGet(){
		//Method get não é permitido, portando deve retornar 405
		given().
		param("grant_type", "password").
		param("username", "login").
		param("password", "123").
		param("scoped", "public-area").
		auth().
		preemptive().
		basic("mobile-client", "08282424-432a-11e6-beb8-9e71128cae77").
		
	when().
		get("http://localhost:8080/rolebackend/oauth/token").
	then().statusCode(405);
	}
	
	@Test
	public void authenticationWithMethodPost(){
		//autenticação básica através do método post
		given().
			param("grant_type", "password").
			param("username", "login").
			param("password", "123").
			param("scoped", "public-area").
			auth().
			preemptive().
			basic("mobile-client", "08282424-432a-11e6-beb8-9e71128cae77").
			
		when().
			post("http://localhost:8080/rolebackend/oauth/token").
		then().statusCode(200);
		
	}
	
	@Test
	public void authenticationWithBadUsername(){
		given().
		param("grant_type", "password").
		param("username", "badusername").
		param("password", "123").
		param("scoped", "public-area").
		auth().
		preemptive().
		basic("mobile-client", "08282424-432a-11e6-beb8-9e71128cae77").
		
	when().
		post("http://localhost:8080/rolebackend/oauth/token").
	then().statusCode(401);
		
	}
	
	@Test
	public void authenticationWithoutScoped(){
		given().
		param("grant_type", "password").
		param("username", "login").
		param("password", "123").
		auth().
		preemptive().
		basic("mobile-client", "08282424-432a-11e6-beb8-9e71128cae77").
		
	when().
		post("http://localhost:8080/rolebackend/oauth/token").
	then().statusCode(400);
		
	}

}
