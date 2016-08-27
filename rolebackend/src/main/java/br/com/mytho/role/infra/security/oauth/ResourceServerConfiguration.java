package br.com.mytho.role.infra.security.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import br.com.mytho.role.domain.model.User;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/user").access(String.format("#oauth2.hasScope('%s') and hasAuthority('%s')", 
														AuthorizarionServerConfiguration.PRIVATE_AREA_SCOPE,
														User.class.getSimpleName()))
			.anyRequest().authenticated();
	} 
}
