package br.com.mytho.role.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.mytho.role.domain.model.User;
import br.com.mytho.role.domain.model.dao.UserDAO;

@Service
public class UserService implements UserDetailsService {

	private UserDAO users;

	@Autowired
	public UserService(UserDAO users) {
		this.users = users;
	}
	
	public User findBy(String email) { 
		return users.findBy(email);
	}

	@Deprecated
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return findBy(userName);
	}
}
