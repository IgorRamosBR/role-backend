package br.com.mytho.role.infra.security.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import br.com.mytho.role.domain.service.UserService;

@Configuration
@EnableAuthorizationServer
@EnableWebSecurity
public class AuthorizarionServerConfiguration extends AuthorizationServerConfigurerAdapter {
	public static final String PRIVATE_AREA_SCOPE = "private-area";
	public static final String PUBLIC_AREA_SCOPE = "public-area";
	
	private UserService userService;
	private AuthenticationManager authenticationManager;
	
	@Autowired
	public AuthorizarionServerConfiguration(UserService userService, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore());
		endpoints.authenticationManager(authenticationManager);
		endpoints.userDetailsService(userService);
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
	
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() { 
		DefaultTokenServices tokenService = new DefaultTokenServices();
		tokenService.setSupportRefreshToken(true);
		tokenService.setTokenStore(tokenStore());
		
		return tokenService;
	}
	
	@Bean
	public TokenStore tokenStore() {
		InMemoryTokenStore tokenStore = new InMemoryTokenStore();
		return tokenStore;
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("mobile-client")
			.secret("08282424-432a-11e6-beb8-9e71128cae77")
			.scopes(PUBLIC_AREA_SCOPE, PRIVATE_AREA_SCOPE)
			.authorizedGrantTypes("refresh_token", "client_credentials", "password")
			.accessTokenValiditySeconds(3600 * 2) // 2 hours
			.refreshTokenValiditySeconds(3600 * 24 * 2); // 2 days
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("permitAll()");
	}
}
