package br.com.mytho.role.oauth;

import static io.restassured.RestAssured.given;

import org.junit.Test;
//import static io.restassured.matcher.RestAssuredMatchers.*;

public class Oauth2Test {
	
	@Test
	public void basicAuthentication(){
		given().
		param("scope", "public-area").
		param("grant_type", "client_credentials").
		auth().
		preemptive().
		basic("mobile-client", "08282424-432a-11e6-beb8-9e71128cae77").
		when().
			post("http://localhost:8080/rolebackend/oauth/token").
		then().statusCode(200);
	}
	

	@Test
	public void authenticationWithMethodPost(){
		//autenticação básica através do método post
		given().
			param("scope", "public-area").
			param("grant_type", "client_credentials").
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
		param("scope", "public-area").
		param("grant_type", "client_credentials").
		auth().
		preemptive().
		basic("mobile-client", "08282424-432a-11e6-beb8-9e71128cae77").
		
	when().
		get("http://localhost:8080/rolebackend/oauth/token").
	then().statusCode(405);
	}
	
	@Test
	public void basicAuthenticationWithPassword(){
		given().
		param("grant_type", "password").
		param("username", "login").
		param("password", "123").
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
		param("scope", "public-area").
		param("grant_type", "password").
		param("username", "badUsername").
		param("password", "123").
		auth().
		preemptive().
		basic("mobile-client", "08282424-432a-11e6-beb8-9e71128cae77").
		
	when().
		post("http://localhost:8080/rolebackend/oauth/token").
	then().statusCode(401);
		
	}
	
	@Test
	public void authenticationWithBadPassword(){
		given().
		param("scope", "public-area").
		param("grant_type", "password").
		param("username", "login").
		param("password", "badPassword").
		auth().
		preemptive().
		basic("mobile-client", "08282424-432a-11e6-beb8-9e71128cae77").
		
	when().
		post("http://localhost:8080/rolebackend/oauth/token").
	then().statusCode(401);
		
	}
	
	@Test
	public void authenticationWithClientCredentialsAndWithoutScoped(){
		given().
		param("grant_type", "client_credentials").
		auth().
		preemptive().
		basic("mobile-client", "08282424-432a-11e6-beb8-9e71128cae77").
		
	when().
		post("http://localhost:8080/rolebackend/oauth/token").
	then().statusCode(400);
		
	}
	
	@Test
	public void authenticationWithPrivateScope(){
		given().
		param("scope", "private-area").
		param("grant_type", "password").
		param("username", "login").
		param("password", "123").
		auth().
		preemptive().
		basic("mobile-client", "08282424-432a-11e6-beb8-9e71128cae77").
		
	when().
		post("http://localhost:8080/rolebackend/oauth/token").
	then().statusCode(200);
	
	}

}
