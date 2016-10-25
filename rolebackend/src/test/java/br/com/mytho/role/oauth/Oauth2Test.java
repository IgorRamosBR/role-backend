package br.com.mytho.role.oauth;

import static io.restassured.RestAssured.given;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.mytho.role.domain.model.User;
import br.com.mytho.role.domain.model.dao.UserDAO;
import br.com.mytho.role.infra.database.DatabaseConfig;
import br.com.mytho.role.ui.controller.UserController;
//import static io.restassured.matcher.RestAssuredMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserDAO.class,DatabaseConfig.class,UserController.class})
public class Oauth2Test {
	
	private String URI = "http://localhost:8080/rolebackend/oauth/token";
	private String clientId = "mobile-client";
	private String clientSecret = "08282424-432a-11e6-beb8-9e71128cae77";
	
	private User user = new User();
	@Autowired
	private UserDAO userDao;
	@Autowired
	private UserController userController = new UserController(userDao);
	
	
	@Before
	public void initialize(){
		
		user.setName("userTeste");
		user.setEmail("user-teste@gmail.com");
		user.setPassword("123");
		
		
		userController.create(user);
		
	}
	
	@After//Problema em aberto: @BeforeClass e @AfterClass só permite métodos estáticos
	public void finish(){
		userController.delete(user);
	}
	
	@Test
	public void public_scoped_works_with_basic_authentication(){
		given().
		param("scope", "public-area").
		param("grant_type", "client_credentials").
		auth().
		preemptive().
		basic(clientId, clientSecret).
		when().
			post(URI).
		then().statusCode(200);
	}
	
		
	@Test
	public void grant_type_password_works_only_with_username_and_password(){
		
		given().
		param("grant_type", "password").
		param("username", user.getEmail()).
		param("password", user.getPassword()).
		auth().
		preemptive().
		basic(clientId, clientSecret).
		
	when().
		post(URI).
	then().statusCode(200);
	}
	
	
	@Test
	public void bad_username_unauthorized(){
		given().
		param("scope", "public-area").
		param("grant_type", "password").
		param("username", "badUsername").
		param("password", user.getPassword()).
		auth().
		preemptive().
		basic(clientId, clientSecret).
		
	when().
		post(URI).
	then().statusCode(401);
		
	}
	
	
	@Test
	public void bad_password_unathorized(){
		given().
		param("scope", "public-area").
		param("grant_type", "password").
		param("username", user.getEmail()).
		param("password", "badPassword").
		auth().
		preemptive().
		basic(clientId, clientSecret).
		
	when().
		post(URI).
	then().statusCode(400);
		
	}
	
	@Test
	public void authentication_work_without_scope(){
		given().
		param("grant_type", "client_credentials").
		auth().
		preemptive().
		basic(clientId, clientSecret).
		
	when().
		post(URI).
	then().statusCode(200);
		
	}
	
	@Test
	public void authentication_with_private_scope_need_username_and_password(){
		given().
		param("scope", "private-area").
		param("grant_type", "password").
		param("username", "user-teste@gmail.com").
		param("password", "123").
		auth().
		preemptive().
		basic(clientId, clientSecret).
		
	when().
		post(URI).
	then().statusCode(200);
	
	}

}
