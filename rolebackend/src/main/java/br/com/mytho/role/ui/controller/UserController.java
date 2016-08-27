package br.com.mytho.role.ui.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mytho.role.domain.model.User;
import br.com.mytho.role.domain.model.dao.UserDAO;

@RestController
@RequestMapping(value="/user",
			    consumes=MediaType.APPLICATION_JSON_VALUE, 
			    produces=MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	private UserDAO users;
	
	@Autowired
	public UserController(UserDAO users) {
		this.users = users;
	}

	@Transactional
	@RequestMapping(method=RequestMethod.POST)
	public User create(@RequestBody @Valid User user) {
		users.create(user);
		return user;
	}
	
}
