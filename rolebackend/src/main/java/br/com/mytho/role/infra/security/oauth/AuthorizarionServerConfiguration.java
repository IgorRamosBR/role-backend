package br.com.mytho.role.infra.security.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.TokenRequest;

import br.com.mytho.role.domain.service.UserService;

@Configuration
@EnableAuthorizationServer
@EnableWebSecurity
public class AuthorizarionServerConfiguration extends AuthorizationServerConfigurerAdapter {
	private static final String PRIVATE_AREA_SCOPE = "private-area";
	private static final String PUBLIC_AREA_SCOPE = "public-area";
	
	private UserService userService;
	private AuthenticationManager authenticationManager;
	
	@Autowired
	public AuthorizarionServerConfiguration(UserService userService, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager);
		endpoints.requestValidator(new OAuth2RequestValidator() {
			
			public void validateScope(TokenRequest request, ClientDetails client) throws InvalidScopeException {
				if("client_credentials".equals(request.getGrantType()) && request.getScope().contains(PRIVATE_AREA_SCOPE))
					throw new InvalidScopeException("this scope is not allowed to client credentials grant");
			}
			
			public void validateScope(AuthorizationRequest request, ClientDetails client) throws InvalidScopeException {
			}
		});
	}
	
	@Autowired
	public void endpoints(AuthenticationManagerBuilder builder) { 
		builder.authenticationProvider(getAuthenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		
		return provider;
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("mobile-client")
			.secret("08282424-432a-11e6-beb8-9e71128cae77")
			.scopes(PUBLIC_AREA_SCOPE, PRIVATE_AREA_SCOPE)
			.authorizedGrantTypes("client_credentials", "password");
	}
}
